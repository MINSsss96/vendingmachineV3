package service;

import dto.MachineDto;
import dto.MembershipDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminProductService implements AdminProductCrudInterface {

    Connection conn = db.DBConn.getConnection();
    PreparedStatement psmt = null;
    String sql;


    @Override
    public int insertData(MachineDto dto) {
        try {
            sql = "INSERT INTO PRODUCT(PROD_NAME, PROD_PRICE, PROD_Stock )";
            sql = sql + " values( ?, ?, ? )";
            Connection conn = db.DBConn.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getProductName());
            psmt.setInt(2, dto.getPrice());
            psmt.setInt(3, dto.getStock());


            int result = psmt.executeUpdate();
            psmt.close();
            return result;

        } catch (
                SQLException e) {
            System.out.println(e.toString());
            ;

        }
        return 0;
    }

    @Override
    public int updateData(MachineDto dto) {
        int result = 0;
        try {
            sql = "UPDATE PRODUCT SET ";
            sql = sql + " PROD_name = ?, ";
            sql = sql + " PROD_price = ?, ";
            sql = sql + " PROD_stock = ? ";
            sql = sql + " Where prod_id= ? ";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getProductName());
            psmt.setInt(2, dto.getPrice());
            psmt.setInt(3, dto.getStock());
            psmt.setInt(4, dto.getProductId());
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
            sql = "DELETE FROM MACHINEDTO WHERE prod_name = ?";
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
    public List<MachineDto> getListAll() {
        List<MachineDto> machineDtoList = new ArrayList<>();
        ResultSet rs = null;

        try {
            sql = "SELECT * FROM PRODUCT";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();

            while (rs.next()) {
                MachineDto dto = new MachineDto();
                dto.setProductId(rs.getInt("PROD_id"));
                dto.setProductName(rs.getString("PROD_NAME"));
                dto.setPrice(rs.getInt("PROD_PRICE"));
                dto.setStock(rs.getInt("PROD_STOCK"));

                machineDtoList.add(dto);
            }
            rs.close();
            psmt.close();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return machineDtoList;
    }


    @Override
    public MachineDto findById(int id) {
        ResultSet rs = null;
        try {
            sql = "SELECT PROD_id, PROD_NAME, PROD_PRICE, PROD_STOCK" +
                    " FROM PRODUCT WHERE PROD_NAME = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);
            rs = psmt.executeQuery();
            // 레코드 셋의 자료를 while로 순회하면서 읽는다.
            while (rs.next()) {
                MachineDto dto = new MachineDto();
                dto.setProductId(rs.getInt("PROD_id"));
                dto.setProductName(rs.getString("PROD_NAME"));
                dto.setPrice(rs.getInt("PROD_PRICE"));
                dto.setStock(rs.getInt("PROD_STOCK"));

                return dto;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<MachineDto> searchList(String keyword) {
        return List.of();
    }


}
