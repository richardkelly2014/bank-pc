package com.bank.engine.app.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FXMLViewAndController {
    /**
     * Value refers to a relative path from where to load a certain fxml file.
     *
     * @return the relative file path of a views fxml file.
     */
    String value() default "";

    /**
     * The default title for this view for modal.
     *
     * @return The default title string.
     */
    String title() default "";

    /**
     * The style to be applied to the underlying stage
     * when using this view as a modal window.
     */
    String stageStyle() default "UTILITY";
}
