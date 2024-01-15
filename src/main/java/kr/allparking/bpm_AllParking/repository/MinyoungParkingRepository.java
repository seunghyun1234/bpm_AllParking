package kr.allparking.bpm_AllParking.repository;

import kr.allparking.bpm_AllParking.entity.MinyoungParking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinyoungParkingRepository extends JpaRepository<MinyoungParking,String> {
    List<MinyoungParking> findByFcltyNMContainingOrRdnmadrNMContainingOrAdstrdNMContaining(String fcltyNM, String rdnmadrNM, String adstrdNM);
}
