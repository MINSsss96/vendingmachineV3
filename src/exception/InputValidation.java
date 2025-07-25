package exception;

import java.util.regex.Pattern;

public class InputValidation{

    public void idCheck(String id) throws MyException {
        boolean check = Pattern.matches("^[a-zA-Z0-9]{5,12}$", id);
        if (! check) {
            throw new MyException("✔ 영어와 숫자만 입력하세요, 5~12자");
        }
    }


    public void passwordCheck(String password) throws MyException {
        boolean check = Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$", password);
        if (! check) {
            throw new MyException("✔ 영문+숫자+특수문자, 8~16자");
        }
    }

    public void nameCheck(String name) throws MyException {
        boolean check = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", name);
        if (! check) {
            throw new MyException("✔ 이름은 한글로 입력하세요");
        }
    }

    public void phoneCheck(String phone) throws MyException {
        boolean check = Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phone);
        if (! check) {
            throw new MyException("✔ 휴대폰 입력형식은 XXX-XXXX-XXXX입니다.");
        }
    }

    public void chargeAccountCheck(int chargeAccount) throws MyException {
        if (chargeAccount < 0 ) {
            throw new MyException("✔ 충전은 0원 이상부터 가능합니다.");
        }
    }



    public void productNameCheck(String name) throws MyException {
        boolean check = Pattern.matches("^[가-힣a-zA-Z0-9\\s-_]{1,50}$", name);
        if (! check) {
            throw new MyException("✔ 한글, 영어 대소문자, 숫자 공백, 허용할 특수문자, , 1~50자");
        }
    }


    public void productPriceCheck(int price) throws MyException {
        if (price < 0 ){
            throw new MyException("✔ 숫자만 허용");
        }
    }


    public void productStockCheck(int stock) throws MyException {
        if (stock < 0 ){
            throw new MyException("✔ 0 이상 숫자만 허용");
        }
    }
}
