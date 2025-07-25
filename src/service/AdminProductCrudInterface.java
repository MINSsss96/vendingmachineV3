package service;

import dto.MachineDto;

import java.util.List;

public interface AdminProductCrudInterface {

    int insertData(MachineDto dto);

    int updateData(MachineDto dto);

    int deleteData(int id);

    List<MachineDto> getListAll();


    MachineDto findById(int id);

    List<MachineDto> searchList(String keyword);


}
