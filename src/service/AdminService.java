package service;

import dto.MachineDto;

import java.util.List;

public class AdminService implements AdminCrudInterface {


    @Override
    public int insertData(MachineDto dto) {
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
