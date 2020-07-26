import java.io.PrintWriter;
import java.util.ArrayList;

public class PatientDao implements BaseDao {
    ArrayList<Patient> patients = new ArrayList<>();

    public PatientDao() {
        patients = new ArrayList<Patient>();
    }

    // output holds all patient transactions by order. after receiving all transactions,
    // it will write output to file directly
    // so that we don't have to open and close file constantly
    String output = "";

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public Patient getByID(int ID) {
        Patient returnPatient = null;
        for (Patient patient : patients) {
            if (patient.getID() == ID) {
                returnPatient = patient;
            }
            
        }
        return returnPatient;
    }

    @Override
    public void deleteByID(int ID) {
        try {
            for (Patient patient : patients) {
                if (patient.getID() == ID) {
                    //Patient 46 Can removed

                    String name = patient.getName();
                    String IDString = Integer.toString(patient.getID());
                    patients.remove(patient);

                    output += "Patient " + IDString + " " + name + " removed\n";

                }
            }

        } catch (Exception e) {

        }


    }

    @Override
    public void add(String string) {

        String[] splitted = string.split(" ");
        int ID = Integer.parseInt(splitted[1]);
        String name = splitted[2];
        String surname = splitted[3];
        String phoneNumber = splitted[4];
        StringBuilder address = new StringBuilder();
        for (int i = 5; i < splitted.length; i++) {
            address.append(splitted[i] + " ");

        }


        Patient patient = new Patient();
        patient.setID(ID);
        patient.setName(name);
        patient.setSurname(surname);
        patient.setPhoneNumber(phoneNumber);
        patient.setAddress(address.toString());

        patients.add(patient);

        output += "Patient " + ID + " " + name + " added\n";

    }



    @Override
    public ArrayList<Object> getALL() {


        String[] lines = ReadFromFile.readFile("patient.txt");
        for (String line : lines) {
            String[] splitted = line.split("\t");

            int ID = Integer.parseInt(splitted[0]);
            String name = splitted[1].split(" ")[0];
            String surname = splitted[1].split(" ")[1];
            String phoneNumber = splitted[2];
            String address = splitted[3].split(":")[1].substring(1);

            Patient patient = new Patient();
            patient.setID(ID);
            patient.setName(name);
            patient.setSurname(surname);
            patient.setPhoneNumber(phoneNumber);
            patient.setAddress(address);



            patients.add(patient);
        }

        ArrayList<Object> patientsAsObject = new ArrayList<>();
        patientsAsObject.addAll(patients);
        return patientsAsObject;
    }

    @Override
    public void createWritingString(ArrayList<Object> objects) {
        String writingOutput = "";
        for (Object object : objects) {
            Patient patient = ((Patient) object);
            writingOutput += patient.getID() + "\t" + patient.getName() + " " + patient.getSurname() + "\t" + patient.getPhoneNumber() + "\t" + "Address: " + patient.getAddress() + "\n";

        }

        writeToFile("patient",writingOutput);
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
