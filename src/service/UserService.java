package service;

import db.DBConn;
import dto.MembershipDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserService implements UserCrudInterface {

    Connection conn = db.DBConn.getConnection();
    PreparedStatement psmt = null;
    String sql;


    @Override
    public int insertData(MembershipDto dto) {
        try {
            sql = "INSERT INTO MEMBERSHIP(mem_id, mem_password, mem_name, mem_phone, mem_card )";
            sql = sql + " values( ?, ?, ?, ?, ? )";
            Connection conn = db.DBConn.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getMembershipId());
            psmt.setString(2, dto.getMembershipPassword());
            psmt.setString(3, dto.getMembershipName());
            psmt.setString(4, dto.getMembershipPhone());
            psmt.setString(5, dto.getMembershipCard());

            int result = psmt.executeUpdate();
            psmt.close();
            return result;

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return 0;
    }

    @Override
    public int updateData(MembershipDto dto) {
        int result = 0;
        try {
            sql = "UPDATE membership SET ";
            sql = sql + " mem_id = ?, ";
            sql = sql + " mem_password = ?, ";
            sql = sql + " mem_name = ?, ";
            sql = sql + " mem_phone = ?, ";
            sql = sql + " mem_chargeAccount = ?, ";
            sql = sql + " mem_card = ? ";
            sql = sql + " where mem_no = ? ";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getMembershipId());
            psmt.setString(2, dto.getMembershipPassword());
            psmt.setString(3, dto.getMembershipName());
            psmt.setString(4, dto.getMembershipPhone());
            psmt.setInt(5, dto.getChargeAccount());
            psmt.setString(6, dto.getMembershipCard());
            psmt.setInt(7, dto.getMembershipNo());

            result = psmt.executeUpdate();
            psmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public int deleteData(int id) {
        int result = 0;
        try {
            sql = "DELETE FROM MACHINEDTO WHERE prod_id= ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);
            result = psmt.executeUpdate();
            psmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public List<MembershipDto> getListAll() {
        List<MembershipDto> membershipDtoList = new ArrayList<>();
        ResultSet rs = null;

        try {
            sql = "SELECT * FROM membership";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
                MembershipDto dto = new MembershipDto();
                dto.setMembershipId(rs.getString("mem_id"));
                dto.setMembershipPassword(rs.getString("mem_password"));
                dto.setMembershipName(rs.getString("mem_name"));
                dto.setMembershipPhone(rs.getString("mem_phone"));
                dto.setChargeAccount(rs.getInt("mem_chargeAccount"));
                dto.setMembershipCard(rs.getString("mem_card"));
                membershipDtoList.add(dto);
            }
            rs.close();
            psmt.close();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return membershipDtoList;
    }

    @Override
    public MembershipDto findById(String id) {
        // id를 받아서 해당 레코드 읽어오는 작업
        ResultSet rs = null;
        try {
            sql = "SELECT mem_no, mem_id, mem_password, mem_name, mem_phone, mem_chargeAccount, mem_card " +
                    " FROM membership WHERE mem_id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, id);
            rs = psmt.executeQuery();
            // 레코드 셋의 자료를 while로 순회하면서 읽는다.
            while (rs.next()) {
                MembershipDto dto = new MembershipDto();
                dto.setMembershipNo(rs.getInt("mem_no"));
                dto.setMembershipId(rs.getString("mem_id"));
                dto.setMembershipPassword(rs.getString("mem_password"));
                dto.setMembershipName(rs.getString("mem_name"));
                dto.setMembershipPhone(rs.getString("mem_phone"));
                dto.setChargeAccount(rs.getInt("mem_chargeAccount"));
                dto.setMembershipCard(rs.getString("mem_card"));
                return dto;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<MembershipDto> searchList(String keyword) {
        return List.of();
    }

    @Override
    public List<MembershipDto> charge(MembershipDto dto) {
        try {
            sql = "UPDATE membership SET ChargeAccount = ChargeAccount + ? WHERE mem_card = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getChargeAccount());
            pstmt.setString(2, dto.getMembershipCard());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
            ;
        }

// 충전 완료 후 잔액 확인
        ResultSet rs = null;
        try {
            sql = "SELECT ChargeAccount FROM membership WHERE mem_card = ?";
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
