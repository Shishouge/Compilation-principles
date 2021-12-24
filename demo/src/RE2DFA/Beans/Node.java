package RE2DFA.Beans;

public class Node {

    private int id;

    private static int ID=0;

    public Node(){
        this.id=ID++;
    }

    public static void init(){
        ID = 0;
    }

    public int getId() {
        return id;
    }

    public static void reset(){
        ID=0;
    }

    @Override
    public String toString() {
        return id+"";
    }

}