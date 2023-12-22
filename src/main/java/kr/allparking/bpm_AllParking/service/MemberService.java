package kr.allparking.bpm_AllParking.service;

import kr.allparking.bpm_AllParking.dto.MemberDTO;
import kr.allparking.bpm_AllParking.entity.MemberEntity;
import kr.allparking.bpm_AllParking.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        //1.dto-> entity 변환
        //2.repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        //save메서드는 내가 정의 한게 아님 save 메서드가 insert 쿼리를 만들어줌
        memberRepository.save(memberEntity);
        //repository의 save메서드 호출 (조건. entity객체를 넘겨줘야함)
    }
    public MemberDTO login(MemberDTO memberDTO){
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberId(memberDTO.getMemberId());
        if (byMemberEmail.isPresent()){
            MemberEntity memberEntity = byMemberEmail.get();
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;

            }else {
                return null;
            }
        }else {
            return null;

        }



    }
    public List<MemberDTO> findAll(){
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));

        }
        return memberDTOList;

    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else {
            return null;
        }
    }

    public MemberDTO updateForm(String myId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(myId);
        if(optionalMemberEntity.isPresent()){
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {

        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public String memberIdCheck(String memberId) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberId(memberId);
        if(byMemberEmail.isPresent()){
            //조회결과가 있다 => 사용할 수 없다
            return null;
        }else {
            return "ok";
        }

    }
}
