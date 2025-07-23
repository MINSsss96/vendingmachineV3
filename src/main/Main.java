package main;

import db.DBConn;
import view.UserView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = db.DBConn.getConnection();
        PreparedStatement psmt = null;

        UserView userView = new UserView();
        while (true) {
            System.out.println("1.회원가입  2.로그인 (종료는 0)");

            Scanner sc = new Scanner(System.in);
            int num = sc.nextInt();
            switch (num) {
                case 0:
                    return;
                case 1:
                    userView.login();
                    break;
                case 2:
                    userView.joinMembership();
                    break;
                case -1 :
                    break;

            }
        }


    }
}
