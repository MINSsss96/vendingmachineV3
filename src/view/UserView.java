package view;

import db.MemberDAO;
import dto.MembershipDto;
import exception.InputValidation;
import service.AdminService;
import service.UserService;

import java.util.List;
import java.util.Scanner;

public class UserView {
    private Scanner sc = new Scanner(System.in);
    private InputValidation validation = new InputValidation();
    private UserService userService = new UserService();
    private AdminService adminService = new AdminService();
    private MembershipDto loginUserDto;

    public void joinMembership() {


        System.out.println("====회원 가입====");
        boolean idOk = true;
        String id = "";
        while (idOk) {
            try {
                System.out.println("회원 아이디를 입력하세요");
                id = sc.next();
                validation.idCheck(id);
                idOk = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }

        }


        boolean passwordOk = true;
        String password = "";
        while (passwordOk) {
            try {
                System.out.println("회원 비밀번호를 입력하세요");
                password = sc.next();
                validation.passwordCheck(password);
                passwordOk = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }

        }


        boolean nameOk = true;
        String name = "";
        while (nameOk) {
            try {
                System.out.println("회원명을 입력하세요");
                name = sc.next();
                validation.nameCheck(name);
                nameOk = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }

        }


        boolean phoneOk = true;
        String phone = "";
        while (phoneOk) {
            try {
                System.out.println("회원 전화번호를 입력하세요");
                phone = sc.next();
                validation.phoneCheck(phone);
                phoneOk = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }

        }


        MembershipDto membershipDto = new MembershipDto();
        membershipDto.setMembershipId(id);
        membershipDto.setMembershipPassword(password);
        membershipDto.setMembershipName(name);
        membershipDto.setMembershipPhone(phone);

        int result = userService.insertData(membershipDto);
        if (result > 0) {
            System.out.println("회원가입이 정상적으로 되었습니다.");
        } else {
            System.out.println("회원가입이 되지 않았습니다.");
        }


    }


    public MembershipDto login() {
        Scanner sc = new Scanner(System.in);
        MemberDAO memberDAO = new MemberDAO();

        System.out.println("아이디를 입력하세요:");
        String id = sc.nextLine();
        System.out.println("비밀번호를 입력하세요:");
        String pw = sc.nextLine();

        MembershipDto loginMember = memberDAO.login(id, pw);

        if (loginMember != null) {
            System.out.println("🎉 로그인 성공: " + loginMember.getMembershipName() + "님 환영합니다!");
            this.loginUserDto = loginMember; // ✅ 저장 필수!
            // 로그인 후 바로 메인 뷰
        } else {
            System.out.println("❌ 로그인 실패: 아이디 또는 비밀번호가 틀렸습니다.");
        }
        return loginMember;

    }

    public void mainView() {
        while (true) {
            // 현재 회원 정보 불러오기
            MembershipDto loginUserDto = this.loginUserDto;
            MembershipDto membershipDto = loginUserDto; // 로그인 시 저장해둔 로그인 회원 정보
            System.out.println("\n==========================");
            System.out.println("💳 " + membershipDto.getMembershipName() + "님의 현재 잔액: " + membershipDto.getChargeAccount() + "원");
            System.out.println("1. 제품 구매");
            System.out.println("2. 금액 충전");
            System.out.println("3. 로그아웃");
            System.out.println("==========================");
            System.out.print("메뉴를 선택하세요: ");

            int menu = sc.nextInt();

            switch (menu) {
                case 1:
                    // 제품 구매 로직 호출
                    buyProduct();
                    break;
                case 2:
                    chargeMoney(membershipDto);
                    break;
                case 3:
                    System.out.println("👋 로그아웃 되었습니다.");
                    return; // main으로 돌아가기
                default:
                    System.out.println("❌ 잘못된 입력입니다.");
            }
        }


    }

    public void buyProduct() {


    }

    public void chargeMoney(MembershipDto dto) {

        int charge = 0;
        while (true) {
            System.out.print("충전할 금액을 입력하세요 (1000원 단위): ");
            charge = sc.nextInt();

            if (charge <= 0) {
                System.out.println("❌ 0원 이하의 금액은 충전할 수 없습니다.");
                continue;
            }
            if (charge % 1000 != 0) {
                System.out.println("❌ 1000원 단위로만 충전할 수 있습니다.");
                continue;
            }

            System.out.println("💳 입력한 금액은 " + charge + "원입니다.");
            System.out.print("정말 충전하시겠습니까? (Y/N): ");
            String confirm = sc.next();

            if (confirm.equalsIgnoreCase("Y")) {
                // 💡 기존 금액 + 충전 금액
                int ChargeAccount = dto.getChargeAccount() + charge;

                // 💾 DB 업데이트
                MemberDAO memberDAO = new MemberDAO();
                memberDAO.updateCharge(dto.getMembershipId(), ChargeAccount);

                // 💡 DTO도 업데이트
                dto.setChargeAccount(ChargeAccount);

                System.out.println("✅ 충전이 완료되었습니다. 현재 잔액: " + ChargeAccount + "원");
                break;
            } else {
                System.out.println("❌ 충전이 취소되었습니다.");
                return;
            }


        }

    }
}

