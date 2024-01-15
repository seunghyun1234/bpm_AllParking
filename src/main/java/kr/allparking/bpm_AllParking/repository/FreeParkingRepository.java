package kr.allparking.bpm_AllParking.repository;

import kr.allparking.bpm_AllParking.entity.FreeParking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeParkingRepository extends JpaRepository<FreeParking,String> {
    List<FreeParking> findByFcltyNMContainingOrRdnmadrNMContainingOrAdstrdNMContaining(String fcltyNM, String rdnmadrNM, String adstrdNM);
}
