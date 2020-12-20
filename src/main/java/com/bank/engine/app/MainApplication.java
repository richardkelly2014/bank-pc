package com.bank.engine.app;

import com.bank.engine.app.config.AbstractFxApplication;
import com.bank.engine.app.util.DefaultThreadFactory;
import com.bank.engine.app.view.AppController;
import com.jfoenix.svg.SVGGlyphLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class MainApplication extends AbstractFxApplication {

    public static void main(String[] args) {
        DefaultThreadFactory.runLater(() -> {

            try {

                SVGGlyphLoader.loadGlyphsFont(
                        MainApplication.class.getResourceAsStream("/fonts/icomoon.svg"),
                        "icomoon.svg"
                );

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        java.awt.Toolkit.getDefaultToolkit();

        run(MainApplication.class, args, AppController.class);
    }
}
