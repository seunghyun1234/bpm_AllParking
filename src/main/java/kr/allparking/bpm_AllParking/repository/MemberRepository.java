package kr.allparking.bpm_AllParking.repository;

import kr.allparking.bpm_AllParking.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    Optional<MemberEntity> findByMemberId(String memberId);

}
