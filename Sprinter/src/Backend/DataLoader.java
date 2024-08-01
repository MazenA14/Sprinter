package Backend;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader {
    // Variables
//    private static final String EXCEL_FILE_PATH = "C:\\Users\\Mazen\\Desktop\\Sprinter\\src\\Static\\SimpleTable.csv";
    static ArrayList<RowData> table;
    static double storyPoints;
    static String feature;
    static double backend;
    static double mobile;
    static double qcCreation;
    static double qcExecution;
    static double qcTotal;
    static double qcAutomation;
    public static String EXCEL_FILE_PATH = "none";

    // Loading excel file
    public static ArrayList<RowData> loadfile(String filePath) throws IOException {
        table = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        br.readLine();
        while (br.ready()) {
            String nextLine = br.readLine();
            String[] data = nextLine.split(",");

            // TODO: Exceptions

            if(canParseDouble(data[0])) storyPoints = Integer.parseInt(data[0]);
            else storyPoints = -1;

            feature = data[1];

            if(data[2] == null) backend = 0;
            else backend = Double.parseDouble(data[2]);

            if(data[3] == null) mobile = 0;
            else mobile = Double.parseDouble(data[3]);

            if(data[4] == null) qcCreation = 0;
            else qcCreation = Double.parseDouble(data[4]);

            if(data[5] == null) qcExecution = 0;
            else qcExecution = Double.parseDouble(data[5]);

            if(data[6] == null) qcTotal = 0;
            else qcTotal = Double.parseDouble(data[6]);

            if(data[7] == null) qcAutomation = 0;
            else qcAutomation = Double.parseDouble(data[7]);

            table.add(new RowData(storyPoints, feature, backend, mobile, qcCreation, qcExecution, qcTotal, qcAutomation));
        }
        br.close();
        return table;
    }

    // Check if String entered can be changed to an integer
    public static boolean canParseDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Display Table
    public static void displayTable(ArrayList<RowData> table){
        // Define column headers
        String headers = String.format("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s",
                "StoryPoints", "Feature", "BE", "Mobile", "QC Creation", "QC Execution", "QC Total", "QC Automation");

        // Print table headers
        System.out.println(headers);
        System.out.println(new String(new char[headers.length()]).replace("\0", "-")); // Separator line

        // Print each row in the table
        for (RowData row : table)
            System.out.println(row.toTableRow());
    }

    // Display Table
    public static void displayTable2(ArrayList<RowData> table){
        // Define column headers
        String headers = String.format("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s",
                "Day", "Backend", "Android A", "Android B", "IOS A", "IOS B", "QC A", "QC B", "QC C", "QC D");

        // Print table headers
        System.out.println(headers);
        System.out.println(new String(new char[headers.length()]).replace("\0", "-")); // Separator line

        // Print each row in the table
        for (RowData row : table)
            System.out.println(row.toTableRow2());
    }
}
