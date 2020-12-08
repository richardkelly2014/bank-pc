package com.bank.engine.app.util;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ScreenUtil {

    /**
     * 计算 窗口的大小
     *
     * @param width
     * @param height
     * @return
     */
    public static double[] getScreenSizeByScale(double width, double height) {
        double screenWidth = 800;
        double screenHeight = 600;
        try {
            Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
            screenWidth = bounds.getWidth() * width;
            screenHeight = bounds.getHeight() * height;
        } catch (Exception e) {
        }

        return new double[]{screenWidth, screenHeight};
    }
}
