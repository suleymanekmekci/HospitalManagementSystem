public class DoctorVisit extends OperationDecorator {
    public DoctorVisit(Examination examination) {
        super(examination);
    }

    public DoctorVisit() {

    }

    @Override
    public String printOperations() {
        return examination.printOperations() + " doctorvisit";
    }

    @Override
    public int cost() {
        return examination.cost() + 15;
    }
}
