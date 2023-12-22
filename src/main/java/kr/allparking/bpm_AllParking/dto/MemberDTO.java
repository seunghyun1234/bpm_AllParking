package kr.allparking.bpm_AllParking.dto;

import kr.allparking.bpm_AllParking.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long id;
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberPhone;
    private String memberEmails;
    private String memberCarNum;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO= new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberId(memberEntity.getMemberId());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberPhone(memberEntity.getMemberPhone());
        memberDTO.setMemberEmails(memberDTO.getMemberEmails());
        memberDTO.setMemberCarNum(memberDTO.getMemberCarNum());
        return memberDTO;
    }

}
