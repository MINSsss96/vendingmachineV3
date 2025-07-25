package main;

import db.DBConn;
import dto.MembershipDto;
import view.AdminView;
import view.UserView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = db.DBConn.getConnection();
        PreparedStatement psmt = null;

        UserView userView = new UserView();
        AdminView adminView = new AdminView();
        while (true) {
            System.out.println("1.로그인  2.회원가입 0.종료");

            Scanner sc = new Scanner(System.in);
            int num = sc.nextInt();
            switch (num) {
                case 0:
                    return;
                case 1:
                    MembershipDto loginMember = userView.login();
                    if (loginMember != null) {
                        userView.mainView();  // 로그인 성공했을 때만 메인 뷰 진입
                    }
                    break;
                case 2:
                    userView.joinMembership();
                    break;
                case -1:
                    adminView.adminView();
                    break;

            }
        }


    }
}
