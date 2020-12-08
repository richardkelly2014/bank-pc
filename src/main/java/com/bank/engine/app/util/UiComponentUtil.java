package com.bank.engine.app.util;

import com.jfoenix.controls.JFXTreeTableColumn;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import lombok.experimental.UtilityClass;

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

}
