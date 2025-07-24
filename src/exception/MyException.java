package exception;

public class MyException extends Exception{
    public static final Long serialVersionUID = 1L;
    public MyException(){};  // 기본생성자

    public MyException(String message) {
        super(message);
    }
}
