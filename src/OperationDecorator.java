
public class OperationDecorator implements Examination {
    protected Examination examination;

    public OperationDecorator(Examination examination) {
        this.examination = examination;
    }

    public OperationDecorator() {

    }

    @Override
    public String printOperations() {
        return examination.printOperations();
    }

    @Override
    public int cost() {
        return examination.cost();
    }
}
