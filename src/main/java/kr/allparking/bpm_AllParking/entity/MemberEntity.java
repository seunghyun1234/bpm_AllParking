package kr.allparking.bpm_AllParking.entity;

import kr.allparking.bpm_AllParking.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String memberId;
    @Column(nullable = false)
    private String memberPassword;
    @Column(nullable = false)
    private String memberName;
    @Column(nullable = false)
    private String memberPhone;
    @Column(nullable = false)
    private String memberEmails;
    @Column(nullable = false)
    private String memberCarNum;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone());
        memberEntity.setMemberEmails(memberDTO.getMemberEmails());
        memberEntity.setMemberCarNum(memberDTO.getMemberCarNum());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone());
        memberEntity.setMemberEmails(memberDTO.getMemberEmails());
        memberEntity.setMemberCarNum(memberDTO.getMemberCarNum());
        return memberEntity;
    }
}
