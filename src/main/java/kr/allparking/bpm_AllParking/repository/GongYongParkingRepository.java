package kr.allparking.bpm_AllParking.repository;

import kr.allparking.bpm_AllParking.entity.GongYoungParking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GongYongParkingRepository extends JpaRepository<GongYoungParking,String> {
    List<GongYoungParking> findByFcltyNMContainingOrRdnmadrNMContainingOrAdstrdNMContaining(
            String fcltyNM, String rdnmadrNM, String adstrdNM);
}
