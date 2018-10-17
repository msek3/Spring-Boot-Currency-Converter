package web;

public class Validator {
    public static boolean validate(String value){
        if(value == null || value.trim().equals(""))
            return false;
        return value.matches("\\d+\\.\\d+") || value.matches("\\d+");
    }
}
