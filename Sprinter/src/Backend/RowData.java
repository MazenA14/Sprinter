package Backend;

public class RowData {
    // Variables
    double storyPoints;
    String feature;
    double backend;
    double mobile;
    double qcCreation;
    double qcExecution;
    double qcTotal;
    double qcAutomation;

    int day = 1;
    String backend2;
    String android_A;
    String android_B;
    String ios_A;
    String ios_B;
    String qc_A;
    String qc_B;
    String qc_C;
    String qc_D;

    // Constructor
    public RowData(double storyPoints, String feature, double backend, double mobile, double qcCreation, double qcExecution, double qcTotal, double qcAutomation) {
        this.storyPoints = storyPoints;
        this.feature = feature;
        this.backend = backend;
        this.mobile = mobile;
        this.qcCreation = qcCreation;
        this.qcExecution = qcExecution;
        this.qcTotal = qcTotal;
        this.qcAutomation = qcAutomation;
    }

    public RowData(int day, String backend2, String android_A, String android_B, String ios_A, String ios_B, String qc_A, String qc_B, String qcCreation, String qc_D) {
        this.day = day;
        this.backend2 = backend2;
        this.android_A = android_A;
        this.android_B = android_B;
        this.ios_A = ios_A;
        this.ios_B = ios_B;
        this.qc_A = qc_A;
        this.qc_B = qc_B;
        this.qc_C = qcCreation;
        this.qc_D = qc_D;
    }

    // region Getters

    // Getters 1
    public double getStoryPoints() {
        return storyPoints;
    }

    public String getFeature() {
        return feature;
    }

    public double getBackend() {
        return backend;
    }

    public double getMobile() {
        return mobile;
    }

    public double getQcCreation() {
        return qcCreation;
    }

    public double getQcExecution() {
        return qcExecution;
    }

    public double getQcTotal() {
        return qcTotal;
    }

    public double getQcAutomation() {
        return qcAutomation;
    }

    // Getters 2

    public int getDay() {
        return day;
    }

    public String getBackend2() {
        return backend2;
    }

    public String getAndroid_A() {
        return android_A;
    }

    public String getAndroid_B() {
        return android_B;
    }

    public String getIos_A() {
        return ios_A;
    }

    public String getIos_B() {
        return ios_B;
    }

    public String getQc_A() {
        return qc_A;
    }

    public String getQc_B() {
        return qc_B;
    }

    public String getQc_C() {
        return qc_C;
    }

    public String getQc_D() {
        return qc_D;
    }


    // endregion

    public String toTableRow() {
        return String.format("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s",
                storyPoints, feature, backend, mobile, qcCreation, qcExecution, qcTotal, qcAutomation);
    }

    public String toTableRow2() {
        return String.format("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s",
                day, backend2, android_A, android_B, ios_A, ios_B, qc_A, qc_B, qc_C, qc_D);
    }
}
