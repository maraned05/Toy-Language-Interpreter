package model.adt;
import java.util.List;
import java.util.ArrayList;

public class MyList<T> implements IMyList<T> {
    private List<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    public List<T> getAll() {
        return this.list;
    }

    public void add(T elem) {
        this.list.add(elem);
    }

    void setList(List<T> list) {
        this.list = list;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (T element : list) {
            str.append(element.toString());
            str.append(" ");
        }
        return str.toString();
    }
}
