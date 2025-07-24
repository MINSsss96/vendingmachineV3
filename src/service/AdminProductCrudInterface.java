package service;

import dto.MachineDto;

import java.util.List;

public interface AdminProductCrudInterface {

    int insertData(MachineDto dto);

    int updateData(MachineDto dto);

    int deleteData(MachineDto dto);

    List<MachineDto> getListAll();


    MachineDto findById(int id);

    List<MachineDto> searchList(String keyword);


}
