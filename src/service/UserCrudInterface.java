package service;

import dto.MembershipDto;

import java.util.List;

public interface UserCrudInterface {

    int insertData(MembershipDto dto);

    int updateData(MembershipDto dto);

    int deleteData(int id);

    List<MembershipDto> getListAll();

    MembershipDto findById(String id);

    List<MembershipDto> searchList(String keyword);


    List<MembershipDto> charge(MembershipDto dto);
}
