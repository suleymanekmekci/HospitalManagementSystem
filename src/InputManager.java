import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class InputManager {
    public static void inputTransactions(String fileName) {

        String output = "";

        BaseDao patientDao = new PatientDao();
        BaseDao admissionDao = new AdmissionDao();

        //new
        ArrayList<Patient> patients = new ArrayList<>();

        for (Object object : patientDao.getALL()) {
            patients.add((Patient)object);
        }
        //new



//        ArrayList<Patient> patients = patientDao.getALL();
        ((PatientDao)patientDao).setPatients(patients);



        // SET ALL ADMISSIONS FROM ADMISSION.TXT <--
        String[] lines = ReadFromFile.readFile("admission.txt");
        ArrayList<ArrayList<String>> admissionArrayArrayList = new ArrayList<>();


        // SPLIT ADMISSION.TXT FILE TO GET EACH ADMISSION THEN ADD THEM TO admissionArrayArrayList
        int first = 0;
        int counter = 0;
        ArrayList<String> tempString = null;
        for (String line : lines) {

            counter++;
            if (first == 0) {
                tempString = new ArrayList<>();
                tempString.add(line);
                first++;
            }
            else {
                if (! (line.split("\t")[0].equals("Inpatient") || line.split("\t")[0].equals("Outpatient"))) {

                    admissionArrayArrayList.add(tempString);
                    tempString = new ArrayList<>();
                }
                tempString.add(line);
                if (lines.length == counter) {
                    admissionArrayArrayList.add(tempString);
                }
            }
        }






        //loop into each admission
        for (ArrayList<String> line: admissionArrayArrayList) {

            Admission admission = new Admission();
            ArrayList<Examination> examinationArrayList = new ArrayList<>();
            int patientID = 0;


            for (int i = 0; i < line.size(); i++) {


                if (i == 0) {

                    int admissionID = Integer.parseInt(line.get(i).split("\t")[0]);

                    patientID = Integer.parseInt(line.get(i).split("\t")[1]);

                    // set the admission id
                    admission.setID(admissionID);
                    admission.setPatientID(patientID);


                }

                else {
                    ArrayList<String> operationsString = new ArrayList<>();
                    operationsString.add(line.get(i).split("\t")[0]);

                    for (String operations: line.get(i).split("\t")[1].split(" ")) {
                        operationsString.add(operations);

                    }

                    // EXAMINATION DECORATOR PATTERN
                    Examination examination = null;


                    for (String operation : operationsString) {
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
                    examinationArrayList.add(examination);
                }
                admission.setExaminationArrayList(examinationArrayList);

            }



            ((Patient)patientDao.getByID(patientID)).setAdmission(admission);



            ((AdmissionDao)admissionDao).getAdmissions().add(admission);
        }
        // SET ALL ADMISSIONS FROM ADMISSION.TXT ->






        String[] inputLines = ReadFromFile.readFile(fileName);


        int lineNumber = 0;




        while (lineNumber < inputLines.length) {

            String line = inputLines[lineNumber];
            lineNumber++;
            String operation = line.split(" ")[0];

            if (operation.equals("AddPatient")) {
                patientDao.add(line);
                output += ((PatientDao) patientDao).getOutput();

                ((PatientDao) patientDao).setOutput("");

            }

            else if (operation.equals("RemovePatient")) {

                int patientId = Integer.parseInt(line.split(" ")[1]);

                try {

                    Admission tempAdmission = ((Patient) patientDao.getByID(patientId)).getAdmission();

                    admissionDao.deleteByID(tempAdmission.getID());
                } catch (Exception e) {

                }
                patientDao.deleteByID(patientId);

                output += ((PatientDao) patientDao).getOutput();

                ((PatientDao) patientDao).setOutput("");


            }

            else if (operation.equals("CreateAdmission")) {
                String admissionBuilder = "";


                int patientId = Integer.parseInt(line.split(" ")[2]);
                int admissionId = Integer.parseInt(line.split(" ")[1]);

                Admission admission = new Admission(admissionId,patientId);


                while (inputLines[lineNumber].split(" ")[0].equals("AddExamination")) {
                    admissionBuilder += inputLines[lineNumber];

                    lineNumber++;
                }

                ((AdmissionDao) admissionDao).createAdmission(admissionBuilder,admission);


                //set admission to patient
                ((Patient) patientDao.getByID(patientId)).setAdmission((Admission)admissionDao.getByID(admissionId));



                output += ((AdmissionDao) admissionDao).getOutput();
                ((AdmissionDao) admissionDao).setOutput("");


            }
            else if (operation.equals("TotalCost")) {
                int admissionID = Integer.parseInt(line.split(" ")[1]);

                Admission tempAdmission = (Admission) admissionDao.getByID(admissionID);




                String outputBuilder = "TotalCost for admission " +
                        tempAdmission.getID() + "\n" ;
                output += outputBuilder;

                int totalCost = 0;

                for (Examination examination: tempAdmission.getExaminationArrayList()) {

                    totalCost += examination.cost();

                    output += "\t" + examination.printOperations() + " " + examination.cost() + "$\n";


                }

                String outputBuilder2 = "\tTotal: " + totalCost + "$\n";

                output += outputBuilder2;


            }
            else if (operation.equals("ListPatients")) {
                output += "Patient List:\n";

                String outputBuilder = "";
                ArrayList<Patient> patientsToSort = ((PatientDao) patientDao).getPatients();

                //sort by name
                patientsToSort.sort(Comparator.comparing(Patient::getName));

                for (Patient patient : patientsToSort) {
                    output += patient.getID() + " " + patient.getName() + " " + patient.getSurname() + " " + patient.getPhoneNumber() + " Address: " + patient.getAddress() + "\n";
                }

            }


        }






        ((PatientDao) patientDao).getPatients().sort(Comparator.comparing(Patient::getID));
        ((AdmissionDao)admissionDao).getAdmissions().sort(Comparator.comparing(Admission::getID));

        ArrayList<Object> patientsAsObject = new ArrayList<>();
        patientsAsObject.addAll(((PatientDao) patientDao).getPatients());

        ArrayList<Object> admissionsAsObject = new ArrayList<>();
        admissionsAsObject.addAll(((AdmissionDao) admissionDao).getAdmissions());


        try {
            PrintWriter printWriter = new PrintWriter("output.txt", "UTF-8");
            printWriter.write(output);
            printWriter.close();
        }
        catch (Exception e) {
        }

        patientDao.createWritingString(patientsAsObject);
        admissionDao.createWritingString(admissionsAsObject);




    }
}
