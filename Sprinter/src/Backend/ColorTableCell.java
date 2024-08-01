package Backend;

import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

public class ColorTableCell<T> extends TableCell<RowData, T> {
//    private final Color color;
    String colorCode;

    public ColorTableCell(String colorCode) {
//        this.color = color;
        this.colorCode = colorCode;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setStyle("");
        } else {
            setText(item.toString());
            setStyle("-fx-background-color: " + colorCode + ";");
//            setStyle("-fx-background-color: " + toHex(color) + ";");
        }
    }

    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}