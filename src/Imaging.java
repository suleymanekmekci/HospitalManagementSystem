public class Imaging extends OperationDecorator {
    public Imaging(Examination examination) {
        super(examination);
    }

    public Imaging() {

    }

    @Override
    public String printOperations() {
        return examination.printOperations() + " imaging";
    }

    @Override
    public int cost() {
        return examination.cost() + 10;
    }

}
