package view;

import dto.MachineDto;
import dto.MembershipDto;

public class UserView {
MembershipDto membershipDto = new MembershipDto();

    public void joinMembership() {


        System.out.println("회원 아이디 : " + membershipDto.getMembershipId());
        System.out.println("비밀번호: " + );
        System.out.println("회원명: ");
        System.out.println("전화번호: ");
        System.out.println("충전금액: ");

    }

    public void login() {
        System.out.println("아이디를 입력하세요.");
        System.out.println("비밀번호를 입력하세요");
    }

}
