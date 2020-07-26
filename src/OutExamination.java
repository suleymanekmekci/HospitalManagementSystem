public class OutExamination implements Examination {
    @Override
    public String printOperations() {
        return "Outpatient";
    }

    @Override
    public int cost() {
        return 15;
    }
}
