package service;

import dto.MembershipDto;
import repository.MembershipRepository;

public class UserService {

    public void insertMembership(String membershipId, String membershipPassword, String membershipName, String membershipPhone, int chargeAccount){
        MembershipDto membershipDto = new MembershipDto();
        membershipDto.setMembershipId(membershipId);
        membershipDto.setMembershipPassword(membershipPassword);
        membershipDto.setMembershipName(membershipName);
        membershipDto.setMembershipPhone(membershipPhone);
        membershipDto.setChargeAccount(chargeAccount);
        MembershipRepository.membershipDtoList.add(membershipDto);
    }




}
