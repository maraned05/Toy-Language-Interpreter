import controller.*;
import repository.Repository;
import view.View;

public class App {
    public static void main(String[] args) throws Exception {
        Repository repo = new Repository("random.txt");
        Controller controller = new Controller(repo, true);
        View view = new View(controller);
        view.executeProgram();
    }
}
