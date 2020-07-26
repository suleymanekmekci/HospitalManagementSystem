public class Measurements extends OperationDecorator {
    public Measurements(Examination examination) {
        super(examination);
    }

    public Measurements() {

    }

    @Override
    public String printOperations() {
        return examination.printOperations() + " measurements";
    }

    @Override
    public int cost() {
        return examination.cost() + 5;
    }
}
