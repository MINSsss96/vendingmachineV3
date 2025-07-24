package service;

import dto.MembershipDto;

import java.util.List;

public interface UserCrudInterface {

    int insertData(MembershipDto dto);

    int updateData(MembershipDto dto);

    int deleteData(MembershipDto dto);

    List<MembershipDto> getListAll();

    MembershipDto findById(int id);

    List<MembershipDto> searchList(String keyword);


    List<MembershipDto> charge(MembershipDto dto);
}
