package com.bank.engine.app.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.experimental.UtilityClass;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.function.Function;

@UtilityClass
public class UiComponentUtil {

    /**
     * JFX tree table view
     *
     * @param column
     * @param mapper
     * @param <S>
     * @param <T>
     */
    public static <S, T> void setupCellValueFactory(JFXTreeTableColumn<S, T> column,
                                                    Function<S, ObservableValue<T>> mapper) {

        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<S, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    /**
     * 涨幅
     *
     * @param column
     * @param <S>
     * @param <T>
     */
    public static <S, T> void rateSetupCellFactory(JFXTreeTableColumn<S, T> column) {
        column.setCellFactory((TreeTableColumn<S, T> param) -> {
            JFXTreeTableCell<S, T> cell = new JFXTreeTableCell<S, T>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        String text = (String) item;
                        Label label = new Label(text);
                        if (text.startsWith("+")) {
                            label.setTextFill(Color.RED);
                        } else if (text.startsWith("-")) {
                            label.setTextFill(Color.GREEN);
                        }
                        setGraphic(label);
                    }
                }
            };
            return cell;
        });
    }

    public static JFXButton createIconButton(Ikon icon) {
        JFXButton jfxButton = new JFXButton(null, FontIcon.of(icon));
        jfxButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        jfxButton.setButtonType(JFXButton.ButtonType.RAISED);
        return jfxButton;
    }

    public static JFXButton createIconButton(Ikon icon, int iconSize, Color iconColor) {
        JFXButton jfxButton = new JFXButton(null, FontIcon.of(icon, iconSize, iconColor));
        jfxButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        jfxButton.setButtonType(JFXButton.ButtonType.RAISED);
        return jfxButton;
    }

}
