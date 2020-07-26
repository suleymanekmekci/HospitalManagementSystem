public class Tests extends OperationDecorator {
    public Tests(Examination examination) {
        super(examination);
    }

    public Tests() {
        super();
    }

    @Override
    public String printOperations() {
        return examination.printOperations() + " tests";
    }

    @Override
    public int cost() {
        return examination.cost() + 7;
    }
}
