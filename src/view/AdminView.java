package view;

import db.MemberDAO;
import dto.MachineDto;
import dto.MembershipDto;
import exception.InputValidation;
import exception.MyException;
import service.AdminProductService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminView {
    private Scanner sc = new Scanner(System.in);
    private InputValidation validation = new InputValidation();
    private AdminProductService adminProductService = new AdminProductService();

    public void adminView() {
        while (true) {
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
    }

    public void vendingMachineManagement() {
        while (true) {
            System.out.println("자판기 관리 화면 입니다.");
            System.out.println("1.제품입력 2.제품수정 3.제품삭제 4.제품조회 5.돌아가기");
            int num = sc.nextInt();
            switch (num) {
                case 1:
                    insertProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    findAllView();
                    break;
                case 5:
                    return;
            }
        }

    }

    public void membershipManagement() {
        UserView userView = new UserView();
        while (true) {
            System.out.println("===회원 관리 화면===");
            System.out.println("1.회원정보입력 2.회원정보수정 3.회원정보삭제 4.회원정보조회 5.돌아가기");
            int num = sc.nextInt();
            switch (num) {
                case 1:
                    userView.joinMembership();
                    break;
                case 2:
                    userView.updateData();
                    break;
                case 3:
                    userView.deleteView();
                    break;
                case 4:
                    userView.findAllView();
                    break;
                case 5:
                    return;
            }
        }
    }

    public void salesManagement() {
        System.out.println("판매관리 화면입니다.");
        System.out.println("1. 제품별 판매현황 2.회원별 판매현황");
    }


    public void insertProduct() {
        System.out.println("====제품입력 화면====");

        boolean nameOK = true;
        String name = "";
        while (nameOK) {
            try {
                System.out.println("제품 이름을 입력하세요");
                name = sc.next();
                validation.productNameCheck(name);
                nameOK = false;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }


        boolean priceOk = true;
        int price = -1;
        while (priceOk) {
            try {
                System.out.println("제품 가격을 입력하세요");
                price = sc.nextInt();
                validation.productPriceCheck(price);
                priceOk = false;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }


        boolean stockOk = true;
        int stock = -1;
        while (stockOk) {
            try {
                System.out.println("제품 재고를 입력하세요");
                stock = sc.nextInt();
                validation.productStockCheck(stock);
                stockOk = false;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }


        // 입력 받은 후 빈 TelDto에 넣는다.
        // id를 제외한 정보 입력(id는 자동생성)
        MachineDto dto = new MachineDto();
        dto.setProductName(name);
        dto.setPrice(price);
        dto.setStock(stock);
        // 입력날짜
        // dto.setInsertedDate(LocalDateTime.now());
        // dto.setUpdatedDate(null);

        // 서비스에 insert 요청하기
        int result = adminProductService.insertData(dto);
        // result > 0 : insert 성공, result = 0 : 실패
        if (result > 0) {
            System.out.println("정상적으로 입력되었습니다.");
        } else {
            System.out.println("입력되지 않았습니다.");
        }
    }

    public void updateProduct() {
        System.out.println("====제품수정 화면====");

        System.out.println("수정할 제품 번호를 입력하세요");
        int updatedId = sc.nextInt();
        MachineDto machineDto = adminProductService.findById(updatedId);

        // 수정작업 진행
        boolean yesOrNo = true;
        while (yesOrNo) {
            System.out.println("수정 전 제품 이름 : " + machineDto.getProductName());
            System.out.println("수정할까요(Y/N)?");
            String strYesOrNo = sc.next();
            if (strYesOrNo.toUpperCase().equals("Y")) {
                System.out.println("수정할 제품 이름 : ");
                machineDto.setProductName(sc.next());
                yesOrNo = false;
            } else {
                yesOrNo = false;
            }
        }
        // 아이디 수정 처리

        // 비밀번호 수정 처리
        yesOrNo = true;
        while (yesOrNo) {
            System.out.println("수정 전 가격 : " + machineDto.getPrice());
            System.out.println("수정할까요(Y/N)?");
            String strYesOrNo = sc.next();
            if (strYesOrNo.toUpperCase().equals("Y")) {
                System.out.println("수정할 가격 : ");
                machineDto.setPrice(sc.nextInt());
                yesOrNo = false;
            } else {
                yesOrNo = false;
            }
        }

        //  수정 처리
        yesOrNo = true;
        while (yesOrNo) {
            System.out.println("수정 전 재고 : " + machineDto.getStock());
            System.out.println("수정할까요(Y/N)?");
            String strYesOrNo = sc.next();
            if (strYesOrNo.toUpperCase().equals("Y")) {
                System.out.println("수정할 재고 : ");
                machineDto.setStock(sc.nextInt());
                yesOrNo = false;
            } else {
                yesOrNo = false;
            }
        }

        int result = adminProductService.updateData(machineDto);
        if (result > 0) {
            System.out.println("수정되었습니다.");
        } else {
            System.out.println("수정 실패");
        }
    }

    public void deleteProduct() {
        System.out.println("====제품삭제 화면====");
        System.out.println("삭제할 제품을 입력하세요");
        int deleteId = sc.nextInt();
        // 삭제 요청 후 결과를 int 타입으로 받기
        int result = adminProductService.deleteData(deleteId);
        // result 값이 양수면 성공, 그렇지 않으면 실패
        if (result > 0) {
            System.out.println("정상적으로 삭제되었습니다.");
        } else {
            System.out.println("삭제되지 않았습니다.");
            System.out.println("관리자에게 문의하세요.");
        }
    }

    public void findAllView() {
        List<MachineDto> dtoList = new ArrayList<>();
        System.out.println("=== 제품정보 목록 ===");
        dtoList = adminProductService.getListAll();
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
        for (MachineDto dto : dtoList) {
            String output = "제품 아이디 =" + dto.getProductId() +
                    ", 제품 이름 =" + dto.getProductName() +
                    ", 가격 ='" + dto.getPrice() + '\'' +
                    ", 재고 =" + dto.getStock() ;

            //        ", insertedDate='" + insertDate + '\'' +
            //       ", updatedDate='" + updateDate;
            System.out.println(output);
        }
    }
}
