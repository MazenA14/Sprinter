package Gui.Controller;

import Backend.DataLoader;
import Backend.Function;
import Backend.RowData;
import javafx.scene.image.ImageView;
import Gui.View.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.util.Callback;

public class StartController {

    // region Variables
    @FXML
    private BorderPane exceptionPopup;

    @FXML
    private Button cancelButton;

    @FXML
    private Rectangle dimmedScreen;

    @FXML
    private Button generateButton;

    @FXML
    private Button generateButton2;

    @FXML
    private TextField headerName;

    @FXML
    private BorderPane headerSet;

    @FXML
    private TableView<RowData> mainTable;

    @FXML
    private Button okPopup;

    @FXML
    private Button okPopup1;

    @FXML
    private Text popupText;

    @FXML
    private Button uploadButton;

    @FXML
    private TextField velocityEntry;

    String uploadPath;
    ArrayList<RowData> table;
    ArrayList<RowData> workflowTable;
    ArrayList<RowData> validFeatures;
    TableColumn<RowData, ?> selectedColumn;
    // endregion

    @FXML
    void openExplorer(ActionEvent event) throws IOException {
        mainTable.setStyle("-fx-background-radius: 5; -fx-border-color: black; -fx-border-radius: 5; -fx-border-width: 2;");
        // Open File Explorer
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Workflow Document");

        // Find Path
        File selectedFile = fileChooser.showOpenDialog(App.mainStage);
        if (selectedFile != null && selectedFile.getPath().toLowerCase().endsWith(".csv")) {
            uploadButton.setStyle("-fx-background-color: #28A745;-fx-background-radius: 20;");
            String filePath = selectedFile.getAbsolutePath();
            uploadPath = filePath;
            table =  DataLoader.loadfile(uploadPath);
            displayTable(table);
            generateButton.setDisable(false);
            velocityEntry.setDisable(false);
        }
        else{
            if (selectedFile != null && !selectedFile.getPath().toLowerCase().endsWith(".csv"))
                runException("Please choose a CSV file");
        }
    }

    @FXML
    void generateFirstTable(ActionEvent event) throws IOException {
        String text1 = velocityEntry.getText();
        velocityEntry.clear();
        if(!text1.isEmpty()){
            int velocity = Integer.parseInt(text1);

            // Calling check method for sp that returns a table of validFeatures
            validFeatures = Function.checkSP(table, velocity);

            // Displaying
            displayTable(validFeatures);
            exportToCSV1(validFeatures);
            generateButton2.setDisable(false);
        }
        else runException("StoryPoints field is empty");
    }

    @FXML
    void generateSecondTable(ActionEvent event) throws IOException {
        String [][] tempTable = Function.createWorkFlow();
        workflowTable = convertTable(regressionBugFixing(tempTable));
        displayTable2(workflowTable);
        exportToCSV2(workflowTable);
    }

    @FXML
    void closeException(ActionEvent event) {
        exceptionPopup.setVisible(false);
        if(!headerName.getText().equals("example"))
            dimmedScreen.setVisible(true);
        else dimmedScreen.setVisible(false);
    }

    @FXML
    void closeException2(ActionEvent event) {
        if(headerName.getText().isEmpty())
            runException("Please enter header name");
        else {
            selectedColumn.setGraphic(new Label(headerName.getText()));
            headerName.setText("example");
            changeHeader(selectedColumn);
            dimmedScreen.setVisible(false);
            headerSet.setVisible(false);
        }
    }

    @FXML
    void cancelHeader(ActionEvent event) {
        headerSet.setVisible(false);
        headerName.setText("example");
        dimmedScreen.setVisible(false);
    }

    @FXML
    void hoverOff1(MouseEvent event) {
        Button hoveredButton = (Button) event.getSource();
        if(!hoveredButton.getStyle().contains("#28A745"))
            hoveredButton.setStyle("-fx-background-radius: 20; -fx-background-color: #F4A261;");
    }

    @FXML
    void hoverOn1(MouseEvent event) {
        Button hoveredButton = (Button) event.getSource();
        if(!hoveredButton.getStyle().contains("#28A745"))
            hoveredButton.setStyle("-fx-background-radius: 20; -fx-background-color: #E76F51;");
    }

    @FXML
    void hoverOff2(MouseEvent event) {
        Button hoveredButton = (Button) event.getSource();
        hoveredButton.setStyle("-fx-background-radius: 20; -fx-background-color: #FFABAB;");
    }

    @FXML
    void hoverOn2(MouseEvent event) {
        Button hoveredButton = (Button) event.getSource();
        hoveredButton.setStyle("-fx-background-radius: 20; -fx-background-color: #FF6F61;");
    }

    public ArrayList<RowData> convertTable(String[][] table) {
        ArrayList<RowData> convertedTable = new ArrayList<>();
        for (int i = 0; i < table.length; i++)
            convertedTable.add(new RowData(i + 1, table[i][0], table[i][1], table[i][2], table[i][3], table[i][4], table[i][5], table[i][6], table[i][7], table[i][8]));
        return convertedTable;
    }

    public static void print2DArray(String[][] array) {
        // Print the top border
        printRowSeparator(array[0].length);

        for (int i = 0; i < array.length; i++) {
            // Print the row with column separators
            for (int j = 0; j < array[i].length; j++) {
                String temp;
                if(array[i][j].isEmpty()) temp = "  ";
                else temp = array[i][j];
                System.out.print("| " + temp + " ");
            }
            System.out.println("|");

            // Print the row separator after each row
            printRowSeparator(array[i].length);
        }
    }

    public static void printRowSeparator(int columnCount) {
        for (int i = 0; i < columnCount; i++)
            System.out.print("+----");
        System.out.println("+");
    }

    public void displayTable(ArrayList<RowData> table) {
        mainTable.getColumns().clear();

        ObservableList<RowData> data = FXCollections.observableArrayList(table);

        TableColumn<RowData, Double> column1 = new TableColumn<>("");
        column1.setCellValueFactory(new PropertyValueFactory<>("StoryPoints"));
        column1.setStyle("-fx-font-size: 14px;");
        column1.setCellFactory(createCenteredDoubleCellFactory());
        column1.setSortable(false);
        column1.setResizable(false);
        column1.setReorderable(false);
        column1.setGraphic(new Label("SP"));
        changeHeader(column1);
//        column1.setCellFactory(col -> new ColorTableCell<>("#FF8913"));

        TableColumn<RowData, String> column2 = new TableColumn<>("");
        column2.setCellValueFactory(new PropertyValueFactory<>("Feature"));
        column2.setStyle("-fx-font-size: 14px;");
        column2.setCellFactory(createCenteredStringCellFactory());
        column2.setSortable(false);
        column2.setResizable(false);
        column2.setReorderable(false);
        column2.setGraphic(new Label("Feature"));
        changeHeader(column2);

        TableColumn<RowData, Double> column3 = new TableColumn<>("");
        column3.setCellValueFactory(new PropertyValueFactory<>("Backend"));
        column3.setStyle("-fx-font-size: 14px;");
        column3.setCellFactory(createCenteredDoubleCellFactory());
        column3.setSortable(false);
        column3.setResizable(false);
        column3.setReorderable(false);
        column3.setGraphic(new Label("BE"));
        changeHeader(column3);

        TableColumn<RowData, Double> column4 = new TableColumn<>("");
        column4.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
        column4.setStyle("-fx-font-size: 14px;");
        column4.setCellFactory(createCenteredDoubleCellFactory());
        column4.setSortable(false);
        column4.setResizable(false);
        column4.setReorderable(false);
        column4.setGraphic(new Label("Mobile"));
        changeHeader(column4);

        TableColumn<RowData, Double> column5 = new TableColumn<>("");
        column5.setCellValueFactory(new PropertyValueFactory<>("QcCreation"));
        column5.setStyle("-fx-font-size: 14px;");
        column5.setCellFactory(createCenteredDoubleCellFactory());
        column5.setSortable(false);
        column5.setResizable(false);
        column5.setReorderable(false);
        column5.setGraphic(new Label("QC Creation"));
        changeHeader(column5);

        TableColumn<RowData, Double> column6 = new TableColumn<>("");
        column6.setCellValueFactory(new PropertyValueFactory<>("QcExecution"));
        column6.setStyle("-fx-font-size: 14px;");
        column6.setCellFactory(createCenteredDoubleCellFactory());
        column6.setSortable(false);
        column6.setResizable(false);
        column6.setReorderable(false);
        column6.setGraphic(new Label("QC Execution"));
        changeHeader(column6);

        TableColumn<RowData, Double> column7 = new TableColumn<>("");
        column7.setCellValueFactory(new PropertyValueFactory<>("QcTotal"));
        column7.setStyle("-fx-font-size: 14px;");
        column7.setCellFactory(createCenteredDoubleCellFactory());
        column7.setSortable(false);
        column7.setResizable(false);
        column7.setReorderable(false);
        column7.setGraphic(new Label("QC Total"));
        changeHeader(column7);

        TableColumn<RowData, Double> column8 = new TableColumn<>("");
        column8.setCellValueFactory(new PropertyValueFactory<>("QcAutomation"));
        column8.setStyle("-fx-font-size: 14px;");
        column8.setCellFactory(createCenteredDoubleCellFactory());
        column8.setSortable(false);
        column8.setResizable(false);
        column8.setReorderable(false);
        column8.setGraphic(new Label("QC Automation"));
        changeHeader(column8);

        column1.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.125));
        column2.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.125));
        column3.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.125));
        column4.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.125));
        column5.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.125));
        column6.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.125));
        column7.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.125));
        column8.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.125));

        mainTable.setStyle("-fx-background-radius: 5; -fx-border-color: black; -fx-border-radius: 5; -fx-border-width: 2;");
        mainTable.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8);
        mainTable.setItems(data);
        mainTable.setEditable(false);
    }

    public void displayTable2(ArrayList<RowData> table) {
        mainTable.getColumns().clear();

        ObservableList<RowData> data = FXCollections.observableArrayList(table);

        TableColumn<RowData, Integer> column1 = new TableColumn<>("");
        column1.setCellValueFactory(new PropertyValueFactory<>("Day"));
        column1.setStyle("-fx-font-size: 14px;");
        column1.setCellFactory(createCenteredIntegerCellFactory());
        column1.setSortable(false);
        column1.setResizable(false);
        column1.setReorderable(false);
        column1.setGraphic(new Label("Days"));
        changeHeader(column1);
//        column1.setReorderable(false);

        TableColumn<RowData, String> column10 = new TableColumn<>("");
        column10.setCellValueFactory(new PropertyValueFactory<>("Backend2"));
        column10.setStyle("-fx-font-size: 14px;");
        column10.setCellFactory(createCenteredStringCellFactory2());
        column10.setSortable(false);
        column10.setResizable(false);
        column10.setReorderable(false);
        column10.setGraphic(new Label("Backend"));
        changeHeader(column10);

        TableColumn<RowData, String> column2 = new TableColumn<>("");
        column2.setCellValueFactory(new PropertyValueFactory<>("Android_A"));
        column2.setStyle("-fx-font-size: 14px;");
        column2.setCellFactory(createCenteredStringCellFactory2());
        column2.setSortable(false);
        column2.setResizable(false);
        column2.setReorderable(false);
        column2.setGraphic(new Label("Android A"));
        changeHeader(column2);

        TableColumn<RowData, String> column3 = new TableColumn<>("");
        column3.setCellValueFactory(new PropertyValueFactory<>("Android_B"));
        column3.setStyle("-fx-font-size: 14px;");
        column3.setCellFactory(createCenteredStringCellFactory2());
        column3.setSortable(false);
        column3.setResizable(false);
        column3.setReorderable(false);
        column3.setGraphic(new Label("Android B"));
        changeHeader(column3);

        TableColumn<RowData, String> column4 = new TableColumn<>("");
        column4.setCellValueFactory(new PropertyValueFactory<>("Ios_A"));
        column4.setStyle("-fx-font-size: 14px;");
        column4.setCellFactory(createCenteredStringCellFactory2());
        column4.setSortable(false);
        column4.setResizable(false);
        column4.setReorderable(false);
        column4.setGraphic(new Label("IOS A"));
        changeHeader(column4);

        TableColumn<RowData, String> column5 = new TableColumn<>("");
        column5.setCellValueFactory(new PropertyValueFactory<>("Ios_B"));
        column5.setStyle("-fx-font-size: 14px;");
        column5.setCellFactory(createCenteredStringCellFactory2());
        column5.setSortable(false);
        column5.setResizable(false);
        column5.setReorderable(false);
        column5.setGraphic(new Label("IOS B"));
        changeHeader(column5);

        TableColumn<RowData, String> column6 = new TableColumn<>("");
        column6.setCellValueFactory(new PropertyValueFactory<>("Qc_A"));
        column6.setStyle("-fx-font-size: 14px;");
        column6.setCellFactory(createCenteredStringCellFactory2());
        column6.setSortable(false);
        column6.setResizable(false);
        column6.setReorderable(false);
        column6.setGraphic(new Label("QC A"));
        changeHeader(column6);

        TableColumn<RowData, String> column7 = new TableColumn<>("");
        column7.setCellValueFactory(new PropertyValueFactory<>("Qc_B"));
        column7.setStyle("-fx-font-size: 14px;");
        column7.setCellFactory(createCenteredStringCellFactory2());
        column7.setSortable(false);
        column7.setResizable(false);
        column7.setReorderable(false);
        column7.setGraphic(new Label("QC B"));
        changeHeader(column7);

        TableColumn<RowData, String> column8 = new TableColumn<>("");
        column8.setCellValueFactory(new PropertyValueFactory<>("Qc_C"));
        column8.setStyle("-fx-font-size: 14px;");
        column8.setCellFactory(createCenteredStringCellFactory2());
        column8.setSortable(false);
        column8.setResizable(false);
        column8.setReorderable(false);
        column8.setGraphic(new Label("QC C"));
        changeHeader(column8);

        TableColumn<RowData, String> column9 = new TableColumn<>("");
        column9.setCellValueFactory(new PropertyValueFactory<>("Qc_D"));
        column9.setStyle("-fx-font-size: 14px;");
        column9.setCellFactory(createCenteredStringCellFactory2());
        column9.setSortable(false);
        column9.setResizable(false);
        column9.setReorderable(false);
        column9.setGraphic(new Label("QC D"));
        changeHeader(column9);

        column1.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.1)); // (double) 100/10
        column10.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.1)); // (double) 100/10
        column2.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.1)); // (double) 100/10
        column3.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.1)); // (double) 100/10
        column4.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.1)); // (double) 100/10
        column5.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.1)); // (double) 100/10
        column6.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.1)); // (double) 100/10
        column7.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.1)); // (double) 100/10
        column8.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.1)); // (double) 100/10
        column9.prefWidthProperty().bind(mainTable.widthProperty().multiply(0.1)); // (double) 100/10

        mainTable.setStyle("-fx-background-radius: 5; -fx-border-color: black; -fx-border-radius: 5; -fx-border-width: 2;");
        mainTable.getColumns().addAll(column1, column10, column2, column3, column4, column5, column6, column7, column8, column9);
        mainTable.setItems(data);
        mainTable.setEditable(false);
    }

    public void runException(String text) {
        popupText.setText(text);
        exceptionPopup.setVisible(true);
        dimmedScreen.setVisible(true);
    }

    public static void handleImageClick(String text) {
        App.controller.getPopupText().setText(text);
        App.controller.getExceptionPopup().setVisible(true);
    }

    public Callback<TableColumn<RowData, Double>, TableCell<RowData, Double>> createCenteredDoubleCellFactory() {
        return column -> new TableCell<RowData, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(item % 1 == 0) {
                        double temp = item;
                        int temp2 = (int) temp;
                        setText(String.valueOf(temp2)); // Format the double value
                    }
                    else setText(String.format("%.1f", item)); // Format the double value
                    setAlignment(Pos.CENTER); // Center align the text
                }
            }
        };
    }

    public Callback<TableColumn<RowData, String>, TableCell<RowData, String>> createCenteredStringCellFactory() {
        return column -> new TableCell<RowData, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER); // Center align the text
                }
            }
        };
    }

    public Callback<TableColumn<RowData, Integer>, TableCell<RowData, Integer>> createCenteredIntegerCellFactory() {
        return column -> new TableCell<RowData, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(String.valueOf(item)); // Format the double value -> String.format("%.2f", item)
                    setAlignment(Pos.CENTER); // Center align the text
                }
            }
        };
    }

    public Callback<TableColumn<RowData, String>, TableCell<RowData, String>> createCenteredStringCellFactory2() {
        return column -> new TableCell<RowData, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER); // Center align the text
                }
            }
        };
    }

    public void exportToCSV1(ArrayList<RowData> table) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Prompt the user to select a file location and name
        File file = fileChooser.showSaveDialog(App.mainStage);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Write CSV header with HTML for bold formatting
                writer.append("StoryPoints,Feature,Backend,Mobile,QC Creation,QC Execution,QC Total,QC Automation\n");

                // Write data rows
                for (RowData rowData : table) {
                    writer.append(String.valueOf(rowData.getStoryPoints())).append(',')
                            .append(String.valueOf(rowData.getFeature())).append(',')
                            .append(String.valueOf(rowData.getBackend())).append(',')
                            .append(String.valueOf(rowData.getMobile())).append(',')
                            .append(String.valueOf(rowData.getQcCreation())).append(',')
                            .append(String.valueOf(rowData.getQcExecution())).append(',')
                            .append(String.valueOf(rowData.getQcTotal())).append(',')
                            .append(String.valueOf(rowData.getQcAutomation())).append('\n');
                }
            } catch (IOException e) {
                runException("Error writing CSV file1: " + e.getMessage());
            }
        }
    }

    public void exportToCSV2(ArrayList<RowData> table) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Prompt the user to select a file location and name
        File file = fileChooser.showSaveDialog(App.mainStage);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Write CSV header with HTML for bold formatting
                writer.append("Day,Backend,Android A,Android B,IOS A,IOS B,QC A,QC B,QC C,QC D\n");

                // Write data rows
                for (RowData rowData : table) {
                    writer.append(String.valueOf(rowData.getDay())).append(',')
                            .append(String.valueOf(rowData.getBackend2())).append(',')
                            .append(String.valueOf(rowData.getAndroid_A())).append(',')
                            .append(String.valueOf(rowData.getAndroid_B())).append(',')
                            .append(String.valueOf(rowData.getIos_A())).append(',')
                            .append(String.valueOf(rowData.getIos_B())).append(',')
                            .append(String.valueOf(rowData.getQc_A())).append(',')
                            .append(String.valueOf(rowData.getQc_B())).append(',')
                            .append(String.valueOf(rowData.getQc_C())).append(',')
                            .append(String.valueOf(rowData.getQc_D())).append('\n');
                }
            } catch (IOException e) {
                runException("Error writing CSV file2: " + e.getMessage());
            }
        }
    }

    public Text getPopupText() {
        return popupText;
    }

    public BorderPane getExceptionPopup() {
        return exceptionPopup;
    }

    public <T> void changeHeader(TableColumn<RowData, T> column) {
        column.getGraphic().setOnMouseClicked(event -> changeHeaderHelper(column));
    }

    public <T> void changeHeaderHelper(TableColumn<RowData, T> column) {
        dimmedScreen.setVisible(true);
        headerName.setText("");
        headerSet.setVisible(true);
        selectedColumn = column;
    }

    public String [][] regressionBugFixing(String [][] table) {
        // region Regression
        boolean flag = true;
        int days = 0;
        for(int i = 0; i < table.length; i++) {
            for(int j = 0; j < table[i].length; j++)
                if(!table[i][j].isEmpty()) {
                    flag = false;
                    break;
                }
            if(flag) break;
            flag = true;
            days++;
        }
        days += 2;
        String [][] output = new String[days][9];
        for(int i = 0; i < output.length; i++)
            for(int j = 0; j < output[i].length; j++)
                output[i][j] = table[i][j];

        for(int i = 0; i < output[days - 2].length; i++) {
            output[days - 2][i] = "Regression";
            output[days - 1][i] = "Regression";
        }
        //endregion

        // region Bug Fixing
        int max1 = 0;
        int max2 = 0;
        int temp1 = 0;
        int temp2 = 0;
        for(int i = 0; i < table[0].length; i++) {
            if(!table[i][1].isEmpty()){
                max1 += temp1;
                temp1 = 0;
            }
            if(!table[i][2].isEmpty()){
                max2 += temp2;
                temp2 = 0;
            }
            temp1++;
            temp2++;
        }

        int max = Math.max(max1, max2) + 1;
        while(output[max][0].isEmpty()) {
            for(int j = 1; j < 5; j++)
                output[max][j] = "Bug Fixing";
            max++;
        }
        //endregion
        return output;
    }
}
