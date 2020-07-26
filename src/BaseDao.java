import java.util.ArrayList;

public interface BaseDao {

    Object getByID(int ID); // read a single entry from the file
    void deleteByID(int ID); // delete a single entry from file
    void add(String string); // add or update an entry
    ArrayList<Object> getALL(); // get all entries
    void createWritingString(ArrayList<Object> objects); // update patient.txt
    void writeToFile(String fileName, String string);


}
