public class Patient {
    private int ID;
    private String surname;
    private String name;
    private String address;
    private String phoneNumber;
    private Admission admission;

    public Patient(int ID, String surname, String name, String address, String phoneNumber, Admission admission) {
        this.ID = ID;
        this.surname = surname;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.admission = admission;
    }

    public Patient() {

    }

    public Admission getAdmission() {
        return admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
