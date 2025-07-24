package view;

import exception.InputValidation;

import java.util.Scanner;

public class AdminView {
    private Scanner sc = new Scanner(System.in);
    private InputValidation validation = new InputValidation();

    public void adminView() {
        System.out.println("관리자 화면입니다.");
        System.out.println("1. 자판기관리 2.회원관리 3.판매관리 4.종료");
        int num = sc.nextInt();

        switch (num) {
            case 1:
                vendingMachineManagement();
                break;
            case 2:
                membershipManagement();
                break;

            case 3:
                salesManagement();
                break;

            case 4:
                return;
        }
    }

    public void vendingMachineManagement() {
        System.out.println("자판기 관리 화면 입니다.");
        System.out.println("1.물품입력 2.물품수정 3.물품삭제 4.물품조회");

    }

    public void membershipManagement() {
        System.out.println("회원 관리 화면입니다.");
        System.out.println("1.회원정보입력 2.회원정보수정 3.회원정보삭제 4.회원정보조회");
    }

    public void salesManagement() {
        System.out.println("판매관리 화면입니다.");
        System.out.println("1. 제품별 판매현황 2.회원별 판매현황");
    }



}
