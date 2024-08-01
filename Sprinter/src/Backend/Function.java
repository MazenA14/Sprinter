package Backend;

import Gui.Controller.StartController;
import Gui.View.App;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class Function {
    // Variable
    static ArrayList<RowData> validFeatures;
    public static int totalDays = 40; // Day limit
    public static String[][] sprintTable = new String[totalDays][9];;
    static int qcCol = 0;

    // Checking sum of features according to sp
    public static ArrayList<RowData> checkSP(ArrayList<RowData> table, int sp) {
        validFeatures = new ArrayList<>();
        for(RowData data:table){
            if(data.storyPoints == -1) continue;
            if(sp > 0 && sp >= data.storyPoints){
                sp -= (int) data.storyPoints;
                validFeatures.add(data);
            }
        }
        return validFeatures;
    }

    public static String[][] createWorkFlow() { // Same as makeSprint() method in C++
        int beIndex, mobIndex, mobIndex1, mobIndex2, qccIndex, qcIndex, beStart, mobStart, qcStart, col;
        int qcCounter = 5;
        for (int i = 0; i < totalDays; i++)
            Arrays.fill(sprintTable[i], "");

        //Distributing test case creation over QC cols
//        qcCreation();

        //Loop over all features
        for (RowData feature:validFeatures) {
            //Unless there is a dependency, we start checking column from 0
            beStart = 0; //never something other than 0
            mobStart = 0;
            qcStart = 0;

            if (qcCounter >= 8)
                qcCounter = 5;

            //If the feature requires both backend and mobile development;
            if ((feature.backend) > 0 && (feature.mobile > 0)) {
                //Check capacity within BE column
                beIndex = checkCapacity(beStart, 0, feature.backend); //Add exception if -1 returned
                assignCells(beIndex, feature.backend, 0, feature.feature);

                //Check capacity within Mob column
                mobStart = (int) (beIndex + feature.backend); //WITHOUT ADDING ONE
                mobIndex1 = checkCapacity(mobStart, 1, feature.mobile);
                mobIndex2 = checkCapacity(mobStart, 2, feature.mobile); //Add exceptions for all cases
                if (mobIndex1 < mobIndex2) {
                    mobIndex = mobIndex1;
                    col = 1;
                } else {
                    mobIndex = mobIndex2;
                    col = 2;
                }
                assignCells(mobIndex, feature.mobile, col, feature.feature);

                //Check capacity within QC
                qcStart = (int) (mobIndex + feature.mobile);
                qcIndex = checkCapacity(qcStart, qcCounter, feature.qcExecution);
                qccIndex = checkCapacity(0, qcCounter, feature.qcCreation);
                qcCounter++;
                //while the test cases only finds capacity after execution, assign next col
                while ((qcIndex < qccIndex) && qcCounter < 9) {
                    qcIndex = checkCapacity(qcStart, qcCounter, feature.qcExecution);
                    qccIndex = checkCapacity(0, qcCounter, feature.qcCreation);
                    qcCounter++;
                }
                if (qcIndex >= qccIndex) {
                    assignCells(qccIndex, feature.qcCreation, qcCounter - 1, feature.feature + " TC");
                    assignCells(qcIndex, feature.qcExecution, qcCounter - 1, feature.feature);
                } else
                    System.out.println("No sufficient capacity");
            }

            //If the feature requires only mobile development
            else if ((feature.backend <= 0) && (feature.mobile > 0)) {
                //Check capacity within Mob column
                mobIndex1 = checkCapacity(mobStart, 1, feature.mobile);
                mobIndex2 = checkCapacity(mobStart, 2, feature.mobile); //Add exceptions for all cases
                if(mobIndex1 < mobIndex2){
                    mobIndex = mobIndex1;
                    col = 1;
                }
                else{
                    mobIndex = mobIndex2;
                    col = 2;
                }
                assignCells(mobIndex, feature.mobile, col, feature.feature);

                //Check capacity within QC
                qcStart = (int) (mobIndex + feature.mobile);
                qcIndex = checkCapacity(qcStart, qcCounter, feature.qcExecution);
                qccIndex = checkCapacity(0, qcCounter, feature.qcCreation);
                qcCounter++;
                //while the test cases only finds capacity after execution, assign next col
                while ((qcIndex < qccIndex) && qcCounter < 9) {
                    qcIndex = checkCapacity(qcStart, qcCounter, feature.qcExecution);
                    qccIndex = checkCapacity(0, qcCounter, feature.qcCreation);
                    qcCounter++;
                }
                if (qcIndex >= qccIndex) {
                    assignCells(qccIndex, feature.qcCreation, qcCounter - 1, feature.feature + " TC");
                    assignCells(qcIndex, feature.qcExecution, qcCounter - 1, feature.feature);
                } else
                    System.out.println("No sufficient capacity");
            }
            //If the feature requires only backend development
            else {
                //Check capacity within BE column
                beIndex = checkCapacity(beStart, 0, feature.backend);
                assignCells(beIndex, feature.backend, 0, feature.feature);

                //Check capacity within QC
                qcStart = (int) (beIndex + feature.backend);
                qcIndex = checkCapacity(qcStart, qcCounter, feature.qcExecution);
                qccIndex = checkCapacity(0, qcCounter, feature.qcCreation);
                qcCounter++;
                //while the test cases only finds capacity after execution, assign next col
                while ((qcIndex < qccIndex) && qcCounter < 9) {
                    qcIndex = checkCapacity(qcStart, qcCounter, feature.qcExecution);
                    qccIndex = checkCapacity(0, qcCounter, feature.qcCreation);
                    qcCounter++;
                }
                if (qcIndex >= qccIndex) {
                    assignCells(qccIndex, feature.qcCreation, qcCounter - 1, feature.feature + " TC");
                    assignCells(qcIndex, feature.qcExecution, qcCounter - 1, feature.feature);
                } else
                    System.out.println("No sufficient capacity");
            }
        }

        //Copying Android to iOS
        for(int i = 0; i < totalDays; i++){
            //Android A to iOS A
            sprintTable[i][3] = sprintTable[i][1];

            //Android B to iOS B
            sprintTable[i][4] = sprintTable[i][2];
        }
        return sprintTable;
    }

    static int checkCapacity(int start, int col, double workingDays) {
        double WD = start + workingDays; // Calculate the target cell after adding workingDays
        // Check capacity within the column

        for (int j = start; j < totalDays; j++) {
            double remainingDays = workingDays; // Reset the remaining working days for each iteration
            int k = j;

            while (remainingDays > 0) {
                // If the current cell is empty
                if (sprintTable[k][col].isEmpty()) {
                    k++; // Move to the next row
                    if(remainingDays == 0.5)
                        remainingDays -= 0.5;
                    else
                        remainingDays--;
                }
                // If the current cell is half-empty
                else if (sprintTable[k][col].charAt(sprintTable[k][col].length() - 1) == '/') {
                    remainingDays -= 0.5; // Decrease remaining working days by 0.5
                    k++; // Move to the next row
                }
                // If the current cell is not empty
                else {
                    break; // Break the loop and move to the next starting row
                }
            }

            // If we have successfully allocated the required working days
            if (remainingDays == 0) {
                return j; // Return the starting row where the allocation is successful
            }
        }

        return -1; // Return -1 if no suitable starting row is found
    }

    static void assignCells(int startIndex, double workingDays, int col, String feature) {
        int i = startIndex;
        double rowRange = startIndex + workingDays;

        while(i < rowRange && workingDays > 0){
            //If cell is empty and working days greater than half
            if(sprintTable[i][col].isEmpty() && workingDays > 0.5) {
                sprintTable[i][col] = feature;
                workingDays--;
                i++;
            }
            //If cell is empty and the remaining working days is half
            else if(sprintTable[i][col].isEmpty() && workingDays == 0.5) {
                sprintTable[i][col] = feature + '/';
                workingDays -= 0.5;
            }
            //If cell is half empty
            else if (sprintTable[i][col].charAt(sprintTable[i][col].length() - 1) == '/') {
                sprintTable[i][col] += feature;
                workingDays -= 0.5;
                i++;
            }
        }
    }

//    public static void qcCreation() {
//        int k = 0;
//        for (int i = 0; i < totalDays; i++) {
//            for (int j = 5; j < 9; j++) {
//                sprintTable[i][j] = validFeatures.get(k).getFeature() + " TC";
//                k++;
//                if (k >= validFeatures.size()) {
//                    break;
//                }
//            }
//            if (k >= validFeatures.size()) {
//                break;
//            }
//        }
//    }

    public static void runException(String text) {
        App.controller.getPopupText().setText("Please increase day limit");
        App.controller.getExceptionPopup().setVisible(true);
    }
}
