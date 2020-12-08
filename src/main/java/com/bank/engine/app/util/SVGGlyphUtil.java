package com.bank.engine.app.util;

import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import javafx.scene.paint.Color;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class SVGGlyphUtil {

    /**
     * 创建svg
     *
     * @param svgName
     * @return
     */
    public static SVGGlyph createSvg(String svgName, String color, int size) {
        try {
            SVGGlyph svgGlyph = SVGGlyphLoader.getIcoMoonGlyph(svgName);
            if (StringUtils.isNotBlank(color)) {
                svgGlyph.setFill(Color.valueOf(color));
            }
            svgGlyph.setSize(size);
            return svgGlyph;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
