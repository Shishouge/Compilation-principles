package RE2DFA.Beans;

public class Operator {
    
    private static int UNION = 1;
    private static int CLOSURE = 3;
    private static int CONNECT = 2;

    public static int getValue(String opr){
        int result = 0;
        switch (opr){
            case  "*":
                result = CLOSURE;
                break;
            case "^":
                result = CONNECT;
                break;
            case "|":
                result = UNION;
                break;
        }
        return result;
    }
}
