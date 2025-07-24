package service;

import dto.MembershipDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserService implements UserCrudInterface{

    Connection conn = db.DBConn.getConnection();
    PreparedStatement psmt = null;
    String sql;


    @Override
    public int insertData(MembershipDto dto) {
        try {
            sql = "INSERT INTO MEMBERSHIP(mem_id, mem_password, mem_name, mem_phone)";
            sql = sql + "values(?, ?, ?, ?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getMembershipId());
            psmt.setString(2, dto.getMembershipPassword());
            psmt.setString(3, dto.getMembershipName());
            psmt.setString(4, dto.getMembershipPhone());

            int result = psmt.executeUpdate();
            psmt.close();
            return result;

        } catch (SQLException e) {
            System.out.println(e.toString());;

        }

        return 0;
    }

    @Override
    public int updateData(MembershipDto dto) {
        return 0;
    }

    @Override
    public int deleteData(MembershipDto dto) {
        return 0;
    }

    @Override
    public List<MembershipDto> getListAll() {
        return List.of();
    }

    @Override
    public MembershipDto findById(int id) {
        return null;
    }

    @Override
    public List<MembershipDto> searchList(String keyword) {
        return List.of();
    }

    @Override
    public List<MembershipDto> charge(MembershipDto dto) {
        try {
            sql = "UPDATE membership SET ChargeAccount = ChargeAccount + ? WHERE mem_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getChargeAccount());
            pstmt.setString(2, dto.getMembershipId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
            ;
        }

// 충전 완료 후 잔액 확인
        ResultSet rs = null;
        try {
            sql = "SELECT ChargeAccount FROM members WHERE id = ?";
            PreparedStatement pstmt2 = conn.prepareStatement(sql);
            pstmt2.setString(1, dto.getMembershipId());
            rs = pstmt2.executeQuery();
            if (rs.next()) {
                int ChargeAccount = rs.getInt("ChargeAccount");
                System.out.println("✅ 충전이 완료되었습니다!");
                System.out.println("💰 현재 잔액: " + ChargeAccount + "원");
            } else {
                System.out.println("❌ 잔액 조회 실패");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            ;
        }


        return List.of();
    }
}
