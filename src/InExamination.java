public class InExamination implements Examination {


    @Override
    public String printOperations() {
        return "Inpatient";
    }

    @Override
    public int cost() {
        return 10;
    }
}
