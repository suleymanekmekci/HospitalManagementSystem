import java.util.ArrayList;

public class Admission {
    private int ID;
    private int patientID;
    private ArrayList<Examination> examinationArrayList;

    public Admission(int ID, int patientID, ArrayList<Examination> examinationArrayList) {
        this.ID = ID;
        this.patientID = patientID;
        this.examinationArrayList = examinationArrayList;
    }

    public Admission(int ID, int patientID) {
        this.ID = ID;
        this.patientID = patientID;
    }



    public Admission() {

    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public ArrayList<Examination> getExaminationArrayList() {
        return examinationArrayList;
    }

    public void setExaminationArrayList(ArrayList<Examination> examinationArrayList) {
        this.examinationArrayList = examinationArrayList;
    }
}
