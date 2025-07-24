package db;

import dto.MembershipDto;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class MemberDAO {
    private Connection conn;

    public MemberDAO() {
        conn = DBConn.getConnection(); // DB 연결
    }

    // 로그인 검증
    public MembershipDto login(String id, String password) {
        String sql = "SELECT * FROM membership WHERE mem_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String dbPw = rs.getString("mem_password");
                if (password.equals(dbPw)) {
                    // 로그인 성공: Member 객체 반환
                    MembershipDto membershipDto = new MembershipDto();
                    membershipDto.setMembershipId(rs.getString("mem_id"));
                    membershipDto.setMembershipPassword(rs.getString("mem_password"));
                    membershipDto.setMembershipName(rs.getString("mem_name"));
                    membershipDto.setMembershipPhone(rs.getString("mem_phone"));
                    membershipDto.setChargeAccount(rs.getInt("mem_ChargeAccount"));
                    membershipDto.setMembershipCard(rs.getString("mem_card"));
                    return membershipDto;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 로그인 실패
        return null;
    }

    public void updateCharge(String memberCard, int newCharge) {
        String sql = "UPDATE membership SET mem_chargeAccount = ? WHERE mem_id = ?";
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, newCharge);
            psmt.setString(2, memberCard);
            int result = psmt.executeUpdate();
            if (result > 0) {
                System.out.println("✅ 충전 금액이 DB에 저장되었습니다.");
            } else {
                System.out.println("❌ 충전 금액 저장 실패.");
            }
        } catch (SQLException e) {
            System.out.println("❌ 충전 DB 업데이트 실패");
            e.printStackTrace();
        }
    }



    public boolean isCardNumberExists(String cardNumber) {
        String sql = "SELECT COUNT(*) FROM member WHERE card_number = ?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, cardNumber);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // 1 이상이면 중복 있음
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}

