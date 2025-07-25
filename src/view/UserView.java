package view;

import db.MemberDAO;
import dto.MachineDto;
import dto.MembershipDto;
import exception.InputValidation;
import service.AdminProductService;
import service.UserService;

import java.util.*;

public class UserView {
    private Scanner sc = new Scanner(System.in);
    private InputValidation validation = new InputValidation();
    private UserService userService = new UserService();
    private AdminProductService adminService = new AdminProductService();
    private MembershipDto loginUserDto;
    private AdminView adminView = new AdminView();


//    public static String generateCardNumber(int length) {
//        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        StringBuilder cardNumber = new StringBuilder();
//        Random rand = new Random();
//
//        for (int i = 0; i < length; i++) {
//            int index = rand.nextInt(chars.length());
//            cardNumber.append(chars.charAt(index));
//        }
//
//        return cardNumber.toString();
//    }

    public String generateCardNumber(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rand = new Random();
        MemberDAO dao = new MemberDAO(); // 중복 확인 위해 DAO 사용

        String cardNumber;

        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int index = rand.nextInt(chars.length());
                sb.append(chars.charAt(index));
            }

            cardNumber = sb.toString();

            // 중복 아니면 탈출
            if (!dao.isCardNumberExists(cardNumber)) {
                break;
            }
        }

        return cardNumber;
    }

    public void joinMembership() {
        MemberDAO dao = new MemberDAO();

        System.out.println("====회원 가입====");
        boolean idOk = true;
        String id = "";
        while (idOk) {
            try {
                System.out.print("회원 아이디를 입력하세요: ");
                id = sc.next();
                validation.idCheck(id);  // 정규식 등 유효성 검사

                if (dao.isIdExists(id)) {
                    System.out.println("❌ 이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요.");
                    continue;  // 다시 입력 받기
                }
                break;  // 중복 아니면 빠져나감
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


//            String cardNumber = generateCardNumber(7);
        String cardNumber = generateCardNumber(7);
        System.out.println("💳 생성된 카드번호: " + cardNumber);


        MembershipDto membershipDto = new MembershipDto();
        membershipDto.setMembershipId(id);
        membershipDto.setMembershipPassword(password);
        membershipDto.setMembershipName(name);
        membershipDto.setMembershipPhone(phone);
        membershipDto.setMembershipCard(cardNumber);

        int result = userService.insertData(membershipDto);
        if (result > 0) {
            System.out.println("회원가입이 정상적으로 되었습니다. 회원 카드번호는 :" + cardNumber);
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
        // 1. 전체 제품 보기
        List<MachineDto> productList = adminService.getListAll();
        if (productList.isEmpty()) {
            System.out.println("❌ 등록된 제품이 없습니다.");
            return;
        }

        // 2. 제품 출력 + 번호-제품 매핑
        Map<Integer, MachineDto> productMap = new HashMap<>();
        int index = 1;
        for (MachineDto dto : productList) {
            System.out.println(index + ". " + dto.getProductName() + " - " + dto.getPrice() + "원 (재고: " + dto.getStock() + ")");
            productMap.put(index, dto); // 화면 출력용 번호와 실제 제품 매핑
            index++;
        }

        // 3. 제품 선택
        System.out.println("구매할 제품 번호:");
        int selectedIndex = sc.nextInt();
        MachineDto product = productMap.get(selectedIndex);  // ✅ index로 직접 꺼낸다!

        if (product == null) {
            System.out.println("❌ 선택한 번호에 해당하는 제품이 없습니다.");
            return;
        }

        // 4. 재고 확인
        if (product.getStock() <= 0) {
            System.out.println("❌ 구매 불가 (재고 없음)");
            return;
        }

        // 4. 잔액 확인
        if (loginUserDto.getChargeAccount() < product.getPrice()) {
            System.out.println("❌ 잔액 부족");
            return;
        }

        // 5. 구매 처리
        int remainingBalance = loginUserDto.getChargeAccount() - product.getPrice();
        loginUserDto.setChargeAccount(remainingBalance);
        userService.updateData(loginUserDto); // 회원 정보 업데이트 (잔액 차감)

        int newStock = product.getStock() - 1;
        product.setStock(newStock);
        adminService.updateData(product); // 제품 정보 업데이트 (재고 차감)

        // 7. 결과 출력
        System.out.println("✅ 구매 완료! 남은 잔액: " + remainingBalance + "원");


//        MachineDto machineDto = new MachineDto();
//        adminView.findAllView();
//        System.out.println("구입하실 제품 번호 골라주세요");
//        int selectedProductId = sc.nextInt();
//        MachineDto selectedProduct = adminService.findById(selectedProductId);
//
//        if (selectedProduct == null) {
//            System.out.println("❌ 해당 제품이 존재하지 않습니다.");
//            return;
//        }
//
//        if (selectedProduct.getStock() <= 0) {
//            System.out.println("❌ 재고가 없습니다.");
//            return;
//        }
//
//        if (loginUserDto.getChargeAccount() < selectedProduct.getPrice()) {
//            System.out.println("❌ 잔액이 부족합니다.");
//            return;
//        }
//
//        int newBalance = loginUserDto.getChargeAccount() - selectedProduct.getPrice();
//        int newStock = selectedProduct.getStock() - 1;
//
//// 회원 잔액 업데이트
//        loginUserDto.setChargeAccount(newBalance);
//        userService.updateData(loginUserDto);
//
//// 제품 재고 업데이트
//        selectedProduct.setStock(newStock);
//        adminService.updateData(selectedProduct);
//
//        System.out.println("✅ " + selectedProduct.getProductName() + " 구매 성공!");
//        System.out.println("💰 남은 잔액: " + newBalance + "원");
//
//

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
                memberDAO.updateCharge(dto.getMembershipCard(), ChargeAccount);

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

    public void updateData() {

        MemberDAO dao = new MemberDAO();
        System.out.println("===회원 정보 수정===");
        MembershipDto membershipDto = null;
        while (true) {
            System.out.println("수정할 회원의 ID를 입력하세요:");
            String updatedId = sc.next();

            if (!dao.isIdExists(updatedId)) {
                System.out.println("❌ 해당 ID를 가진 회원이 없습니다. 다시 입력해주세요.");
            } else {
                // 조회 성공
                membershipDto = userService.findById(updatedId); // findById()는 해당 ID로 dto 반환
                break;
            }
        }

        // 수정작업 진행
        boolean yesOrNo = true;
        // 아이디 수정 처리
        while (yesOrNo) {
            System.out.println("수정 전 아이디 : " + membershipDto.getMembershipId());
            System.out.println("수정할까요(Y/N)?");
            String strYesOrNo = sc.next();
            if (strYesOrNo.toUpperCase().equals("Y")) {
                System.out.println("수정할 아이디 : ");
                String newId = sc.next();

                if (dao.isDuplicateIdOnUpdate(newId, membershipDto.getMembershipId())) {
                    System.out.println("❌ 이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요.");
                } else {
                    System.out.println("✅ 사용 가능한 아이디입니다.");
                    membershipDto.setMembershipId(newId); // 아이디 변경
                    int result = userService.updateData(membershipDto); // 실제 DB 업데이트 호출
                    if (result > 0) {
                        System.out.println("✅ 아이디 수정 완료");
                    } else {
                        System.out.println("❌ 아이디 수정 실패");
                    }
                    yesOrNo = false;
                }
            } else {
                System.out.println("❌ 수정이 취소되었습니다.");
                yesOrNo = false;
            }
        }
        // 비밀번호 수정 처리
        yesOrNo = true;
        while (yesOrNo) {
            System.out.println("수정 전 비밀번호 : " + membershipDto.getMembershipPassword());
            System.out.println("수정할까요(Y/N)?");
            String strYesOrNo = sc.next();
            if (strYesOrNo.toUpperCase().equals("Y")) {
                System.out.println("수정할 비밀번호 : ");
                membershipDto.setMembershipPassword(sc.next());
                yesOrNo = false;
            } else {
                yesOrNo = false;
            }
        }

        //  수정 처리
        yesOrNo = true;
        while (yesOrNo) {
            System.out.println("수정 전 이름 : " + membershipDto.getMembershipName());
            System.out.println("수정할까요(Y/N)?");
            String strYesOrNo = sc.next();
            if (strYesOrNo.toUpperCase().equals("Y")) {
                System.out.println("수정할 이름 : ");
                membershipDto.setMembershipName(sc.next());
                yesOrNo = false;
            } else {
                yesOrNo = false;
            }
        }

        // 전화번호 수정 처리
        yesOrNo = true;
        while (yesOrNo) {
            System.out.println("수정 전 전화번호 : " + membershipDto.getMembershipPhone());
            System.out.println("수정할까요(Y/N)?");
            String strYesOrNo = sc.next();
            if (strYesOrNo.toUpperCase().equals("Y")) {
                System.out.println("수정할 전번 : ");
                membershipDto.setMembershipPhone(sc.next());
                yesOrNo = false;
            } else {
                yesOrNo = false;
            }
        }


        // 위에서 수정작업 완료
        int result = userService.updateData(membershipDto);
        if (result > 0) {
            System.out.println("수정되었습니다.");
        } else {
            System.out.println("수정 실패");
        }


    }


    public void deleteView() {
        System.out.println("=== 회원정보 삭제 ===");
        System.out.println("삭제할 ID를 입력하세요");
        int deleteId = sc.nextInt();
        // 삭제 요청 후 결과를 int 타입으로 받기
        int result = userService.deleteData(deleteId);
        // result 값이 양수면 성공, 그렇지 않으면 실패
        if (result > 0) {
            System.out.println("정상적으로 삭제되었습니다.");
        } else {
            System.out.println("삭제되지 않았습니다.");
            System.out.println("관리자에게 문의하세요.");
        }
    }

    public void findAllView() {
        List<MembershipDto> dtoList = new ArrayList<>();
        System.out.println("=== 회원정보 목록 ===");
        dtoList = userService.getListAll();
        // 서비스에 DB에서 리스트요청하기

        // 출력
//        dtoList.stream()
//                .forEach(x -> System.out.println(x));
//        for (MembershipDto dto : dtoList) {
//            String insertDate;
//            if (dto.getInsertedDate() != null) {
//                insertDate = dto.getInsertedDate()
//                        .format(DateTimeFormatter
//                                .ofPattern("yyyy-MM-dd HH:mm:ss"));
//            } else {
//                insertDate = "";
//            }

//            String updateDate;
//            if (dto.getUpdatedDate() != null) {
//                updateDate = dto.getUpdatedDate()
//                        .format(DateTimeFormatter
//                                .ofPattern("yyyy-MM-dd HH:mm:ss"));
//            } else {
//                updateDate = "";
//            }
        for (MembershipDto dto : dtoList) {
            String output = "아이디=" + dto.getMembershipId() +
                    ", 비밀번호='" + dto.getMembershipPassword() + '\'' +
                    ", 이름=" + dto.getMembershipName() +
                    ", 전화번호='" + dto.getMembershipPhone() + '\'' +
                    ", 잔액='" + dto.getChargeAccount() + '\'' +
                    ", 카드번호='" + dto.getMembershipCard() + '\'';
            //        ", insertedDate='" + insertDate + '\'' +
            //       ", updatedDate='" + updateDate;
            System.out.println(output);
        }
    }
}





