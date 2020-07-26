import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AdmissionDao implements BaseDao {
    private ArrayList<Admission> admissions = new ArrayList<>();

    // output holds all patient transactions by order. after receiving all transactions,
    // it will write output to file directly
    // so that we don't have to open and close file constantly
    String output = "";

    public ArrayList<Admission> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(ArrayList<Admission> admissions) {
        this.admissions = admissions;
    }





    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    // return admission by id
    @Override
    public Admission getByID(int ID) {
        Admission returnAdmission = null;
        for (Admission admission : admissions) {
            if (admission.getID() == ID) {
                returnAdmission = admission;
            }

        }
        return returnAdmission;
    }

    //delete admission by id
    @Override
    public void deleteByID(int ID) {
        try {
            for (Admission admission : admissions) {
                if (admission.getID() == ID) {

                    admissions.remove(admission);

                }
            }

        } catch (Exception e) {

        }

    }

    @Override
    public void add(String string) {

    }


    public void createAdmission(String string, Admission admission) {
        // my input manager works as this:
        // when first word of line is equal CreateAdmission, it saves this line to string and continues to loop lines and adds lines to same string
        // while it sees AddExamination. Meanwhile it saves admissionid and patientid to variables. Creates new admission with admissionid and patientid
        // and set two properties with constructor. then inputmanager calls admissiondao.createAdmission() function with passing the string i stated above
        // and the admission which just created. inside the function, program splits the operations and add them inside examination. after completed,
        // it adds admission to admission arrayList (admissions)
        ArrayList<Examination> examinationArrayList = new ArrayList<>();

        int firstLoop = 0;



        for (String line : string.split("AddExamination")) {
            if (firstLoop == 0) {
                firstLoop++;

                output += "Admission " + admission.getID() + " created\n";
            }
            else {

                output += line.split(" ")[2] + " examination added to admission " + admission.getID() + "\n";
                Examination examination = null;

                int firstCheck = 0;
                for (String operation : line.split(" ")){
                    if (firstCheck == 0) {
                        firstCheck++;
                    }
                    else {
                        if (operation.equals("Inpatient")) {
                            examination = new InExamination();

                        }
                        else if (operation.equals("Outpatient")) {
                            examination = new OutExamination();

                        }
                        else if (operation.equals("measurements")) {
                            examination = new Measurements(examination);

                        }
                        else if (operation.equals("doctorvisit")) {
                            examination = new DoctorVisit(examination);
                        }
                        else if (operation.equals("tests")) {
                            examination = new Tests(examination);
                        }
                        else if (operation.equals("imaging")) {
                            examination = new Imaging(examination);
                        }

                    }


                }
                examinationArrayList.add(examination);

            }
            admission.setExaminationArrayList(examinationArrayList);


        }


        admissions.add(admission);

    }


    // returns all admissions from admissionArrayList
    @Override
    public ArrayList<Object> getALL() {

        ArrayList<Object> admissionsAsObject = new ArrayList<>();
        admissionsAsObject.addAll(admissions);

        return admissionsAsObject;
    }

    @Override
    public void createWritingString(ArrayList<Object> objects) {
        // loops inside the admissions and adds all the admissions with the properties to string named writingOutput


        String writingOutput = "";

        for (Object object : objects) {

            Admission admission = ((Admission) object);
            writingOutput += admission.getID() + "\t" + admission.getPatientID() + "\n";



            for (Examination examination : admission.getExaminationArrayList()) {
                String tempOutput = "";
                String[] splitted =  examination.printOperations().split(" ");
                boolean firstCheck = true;

                for (String string :splitted) {
                    if (firstCheck) {
                        firstCheck = false;
                        tempOutput += string + "\t";
                    }
                    else {
                        tempOutput += " " + string;
                    }
                }




                writingOutput += tempOutput + "\n";
            }
        }
        writeToFile("admission",writingOutput);
    }

    @Override
    public void writeToFile(String fileName, String string) {
        try {
            PrintWriter printWriter = new PrintWriter(fileName + ".txt", "UTF-8");
            printWriter.write(string);
            printWriter.close();

        }
        catch (Exception e) {
        }

    }
}
