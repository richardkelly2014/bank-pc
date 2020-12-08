package com.bank.engine.app.config;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXDecorator;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.bank.engine.app.util.FileUtil.createURL;
import static com.bank.engine.app.util.ScreenUtil.getScreenSizeByScale;

/**
 * view and controller
 */
@Slf4j
public abstract class AbstractFxView implements Initializable {

    private final URL resource;
    private final FXMLViewAndController annotation;
    private FXMLLoader fxmlLoader;

    private Stage stage;
    private Modality currentStageModality;
    private EventHandler<WindowEvent> closeEvent;

    private boolean init = false;

    public AbstractFxView() {
        annotation = getFXMLAnnotation();
        resource = getURLResource(annotation);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initialize();

        init = true;
    }

    public boolean getInit() {
        return init;
    }

    /**
     * controller 初始化
     */
    public abstract void initialize();

    public void showView(Window window, Modality modality) {
        if (stage == null || currentStageModality != modality || !Objects.equals(stage.getOwner(), window)) {
            stage = createStage(modality);
            stage.initOwner(window);
        }
        stage.show();
    }

    public void showView(Modality modality) {
        if (stage == null || currentStageModality != modality) {
            stage = createStage(modality);
        }
        stage.show();
    }

    public void showViewAndWait(Window window, Modality modality) {
        if (stage == null || currentStageModality != modality || !Objects.equals(stage.getOwner(), window)) {
            stage = createStage(modality);
            stage.initOwner(window);
        }
        stage.showAndWait();
    }

    public void showViewAndWait(Modality modality) {
        if (stage == null || currentStageModality != modality) {
            stage = createStage(modality);
        }
        stage.showAndWait();
    }

    public void setCloseEvent(EventHandler<WindowEvent> event) {
        this.closeEvent = event;
    }

    public void close() {
        if (this.stage != null) {
            this.stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }

    /**
     * 创建 窗口
     *
     * @param modality
     * @return
     */
    private Stage createStage(Modality modality) {
        currentStageModality = modality;
        Stage stage = new Stage();

        stage.initModality(modality);
        stage.initStyle(getDefaultStyle());
        stage.setTitle(getDefaultTitle());

        JFXDecorator decorator = new JFXDecorator(stage, getView(), false, true, true);
        decorator.setCustomMaximize(true);

        double[] screenSize = getScreenSizeByScale(0.8, 0.8);
        Scene scene = new Scene(decorator, screenSize[0], screenSize[1]);
        scene.getStylesheets().addAll(
                JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
                JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
                getClass().getResource("/css/jfoenix-main-demo.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setOnCloseRequest(this::closeRequest);
        return stage;
    }

    /**
     * 创建 场景
     *
     * @return
     */
    public Parent getView() {

        ensureFxmlLoaderInitialized();

        final Parent parent = fxmlLoader.getRoot();
        return parent;
    }

    protected String getDefaultTitle() {

        return annotation.title();
    }

    protected StageStyle getDefaultStyle() {
        final String style = annotation.stageStyle();
        return StageStyle.valueOf(style.toUpperCase());
    }

    /**
     * init Fxml
     */
    private void ensureFxmlLoaderInitialized() {
        if (fxmlLoader != null) {
            return;
        }
        fxmlLoader = loadSynchronously(resource);
    }

    private FXMLLoader loadSynchronously(final URL resource) throws IllegalStateException {

        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(resource);
        loader.setController(this);
        try {
            long start = System.currentTimeMillis();

            loader.load();

            log.info("load view class={},{}ms", super.getClass().getName(), (System.currentTimeMillis() - start));
        } catch (final IOException | IllegalStateException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot load " + getClass().getSimpleName().toLowerCase(), e);
        }

        return loader;
    }

    private FXMLViewAndController getFXMLAnnotation() {
        final Class<? extends AbstractFxView> theClass = this.getClass();
        final FXMLViewAndController annotation = theClass.getAnnotation(FXMLViewAndController.class);
        return annotation;
    }

    private URL getURLResource(final FXMLViewAndController annotation) {
        //优先加载template
        if (annotation != null && !annotation.value().equals("")) {

            String path = annotation.value();

            if (StringUtils.startsWith(path, "/")) {
                return getClass().getResource(annotation.value());
            } else {
                URL url = createURL(annotation.value());
                if (url != null) {
                    return url;
                } else {
                    throw new NullPointerException("value null");
                }
            }
        } else {
            throw new NullPointerException("value null");
        }
    }


    private void closeRequest(WindowEvent event) {
        if (closeEvent != null) {
            closeEvent.handle(event);
        }
    }

}
