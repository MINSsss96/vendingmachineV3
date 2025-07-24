package db;

import dto.MembershipDto;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                    return membershipDto;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 로그인 실패
        return null;
    }

    public void updateCharge(String memberId, int newCharge) {
        String sql = "UPDATE membership SET mem_chargeAccount = ? WHERE mem_id = ?";
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, newCharge);
            psmt.setString(2, memberId);
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
}

