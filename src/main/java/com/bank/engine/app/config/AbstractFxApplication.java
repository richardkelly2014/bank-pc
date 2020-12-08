package com.bank.engine.app.config;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import static com.bank.engine.app.util.ScreenUtil.getScreenSizeByScale;
import static com.sun.javafx.application.LauncherImpl.launchApplication;

@Slf4j
public abstract class AbstractFxApplication extends Application {

    private static Class<? extends Application> appClass;
    private static String[] args = new String[0];
    private static Application application;
    private static Class<? extends AbstractFxView> mainView;
    protected static ConfigurableApplicationContext applicationContext;


    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void run(final Class<? extends Application> appClass, final String[] args,
                           final Class<? extends AbstractFxView> mainView) {

        run(appClass, args, mainView, null);
    }

    public static void run(final Class<? extends Application> appClass, final String[] args,
                           final Class<? extends AbstractFxView> mainView,
                           final Class<? extends Preloader> preLoaderClass) {
        AbstractFxApplication.appClass = appClass;
        AbstractFxApplication.args = args;
        AbstractFxApplication.mainView = mainView;

        if (preLoaderClass != null) {
            launchApplication(appClass, preLoaderClass, args);
        } else {
            launchApplication(appClass, args);
        }
    }

    public AbstractFxApplication() {

        AbstractFxApplication.application = this;
    }

    @Override
    public void init() throws Exception {
        log.info("spring start");
        //异步加载启动spring
        CompletableFuture.supplyAsync(() -> applicationContext = SpringApplication.run(appClass, args))
                .whenComplete((ctx, exception) -> {
                    countDownLatch.countDown();
                    log.info("spring ok");
                });
        //等待加载完毕
        log.info("spring waiting");
        countDownLatch.await();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (applicationContext != null) {
            applicationContext.stop();
        }
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void start(Stage stage) throws Exception {

        AbstractFxView mainView = SpringUtils.getBean(AbstractFxApplication.mainView);

        JFXDecorator decorator = new JFXDecorator(stage, mainView.getView(), false, true, true);
        decorator.setCustomMaximize(true);
        decorator.setGraphic(new SVGGlyph(""));

        double[] screenSize = getScreenSizeByScale(0.9, 0.9);
        Scene scene = new Scene(decorator, screenSize[0], screenSize[1]);
        scene.getStylesheets().addAll(
                JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
                JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
                getClass().getResource("/css/jfoenix-main-demo.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setTitle(mainView.getDefaultTitle());
        stage.setOnCloseRequest(this::applicationClose);
        stage.show();
    }

    protected void applicationClose(WindowEvent event) {
        try {
            stop();
        } catch (Exception e) {
        }
    }
}
