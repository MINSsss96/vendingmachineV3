package dto;

public class MembershipDto {
    public String membershipCard;
    public String membershipId;
    public String membershipPassword;
    public String membershipName;
    public String membershipPhone;
    public int chargeAccount;

    @Override
    public String toString() {
        return "MembershipDto{" +
                "membershipCard='" + membershipCard + '\'' +
                ", membershipId='" + membershipId + '\'' +
                ", membershipPassword='" + membershipPassword + '\'' +
                ", membershipName='" + membershipName + '\'' +
                ", membershipPhone='" + membershipPhone + '\'' +
                ", chargeAccount=" + chargeAccount +
                '}';
    }

    public String getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(String membershipCard) {
        this.membershipCard = membershipCard;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getMembershipPassword() {
        return membershipPassword;
    }

    public void setMembershipPassword(String membershipPassword) {
        this.membershipPassword = membershipPassword;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public String getMembershipPhone() {
        return membershipPhone;
    }

    public void setMembershipPhone(String membershipPhone) {
        this.membershipPhone = membershipPhone;
    }

    public int getChargeAccount() {
        return chargeAccount;
    }

    public void setChargeAccount(int chargeAccount) {
        this.chargeAccount = chargeAccount;
    }
}
