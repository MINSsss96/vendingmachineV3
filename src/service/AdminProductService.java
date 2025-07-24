package service;

import dto.MachineDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AdminProductService implements AdminProductCrudInterface {

    Connection conn = db.DBConn.getConnection();
    PreparedStatement psmt = null;
    String sql;


    @Override
    public int insertData(MachineDto dto) {
        try {
        sql = "INSERT INTO PRODUCT(PROD_NAME, PROD_PRICE, PROD_Stock)";
        sql = sql + "values(?, ?, ?, ?)";
        psmt = conn.prepareStatement(sql);
        psmt.setString(1, dto.getProductName());
        psmt.setInt(2, dto.getPrice());
        psmt.setInt(3, dto.getStock());


        int result = psmt.executeUpdate();
        psmt.close();
        return result;

    } catch (
    SQLException e) {
        System.out.println(e.toString());;

    }
        return 0;
    }

    @Override
    public int updateData(MachineDto dto) {
        return 0;
    }

    @Override
    public int deleteData(MachineDto dto) {
        return 0;
    }

    @Override
    public List<MachineDto> getListAll() {
        return List.of();
    }


    @Override
    public MachineDto findById(int id) {
        return null;
    }

    @Override
    public List<MachineDto> searchList(String keyword) {
        return List.of();
    }


}
