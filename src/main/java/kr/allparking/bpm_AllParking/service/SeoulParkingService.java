package kr.allparking.bpm_AllParking.service;

import kr.allparking.bpm_AllParking.entity.FreeParking;
import kr.allparking.bpm_AllParking.entity.GongYoungParking;
import kr.allparking.bpm_AllParking.entity.MinyoungParking;
import kr.allparking.bpm_AllParking.repository.FreeParkingRepository;
import kr.allparking.bpm_AllParking.repository.GongYongParkingRepository;
import kr.allparking.bpm_AllParking.repository.MinyoungParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeoulParkingService {
    /*
    @Autowired
    private SeoulParkingRepository seoulParkingRepository;
    랑 동일한 결과를 나타내는데 왜 이렇게 했을까?
    스프링이 해당 필드에 대한 주입을 자동으로 처리합니다.
    그러나 주의할 점은 필드 주입은 생성자 주입에 비해 몇 가지 단점이 있을 수 있으며,
    테스트나 의존성 주입에 대한 좋은 관행을 따르는 것이 좋습니다.
    생성자 주입은 의존성 주입을 명시적으로 나타내어 가독성을 향상시키며,
    테스트 용이성을 제공합니다.
     */

    @Autowired
    private MinyoungParkingRepository minyoungParkingRepository;
    private final GongYongParkingRepository gongYongParkingRepository;
    @Autowired
    private FreeParkingRepository freeParkingRepository;
    @Autowired
    public SeoulParkingService(GongYongParkingRepository gongYongParkingRepository) {
        this.gongYongParkingRepository = gongYongParkingRepository;
    }
    public List<MinyoungParking> getMinyoungParkingLocations(){
        ; return minyoungParkingRepository.findAll();
    }
    public List<GongYoungParking> getGongYoungParkingLocations() {
        //리턴타입은 entity클래스 내의 List로 변환한 SeoulParking필드의 모든 데이터이다
        return gongYongParkingRepository.findAll();
        //findAll()함수로 데이터 베이스내의 모든 정보를 갖고와서
        //return 값에 의해서 List<SeoulParking>에 저장된다
    }
    public List<FreeParking> getFreeParkingLocations(){
        return freeParkingRepository.findAll();
    }

    public List<FreeParking> searchParking(String searchText) {
        // 검색어를 대소문자 구분 없이 포함하는 데이터를 찾음
        String searchTextLowerCase = searchText.toLowerCase();

        // 검색 메서드 호출
        return freeParkingRepository.findByFcltyNMContainingOrRdnmadrNMContainingOrAdstrdNMContaining(
                searchTextLowerCase, searchTextLowerCase, searchTextLowerCase);
    }
    public List<MinyoungParking> searchParking1(String searchText) {
        // 검색어를 대소문자 구분 없이 포함하는 데이터를 찾음
        String searchTextLowerCase = searchText.toLowerCase();

        // 검색 메서드 호출
        return minyoungParkingRepository.findByFcltyNMContainingOrRdnmadrNMContainingOrAdstrdNMContaining(
                searchTextLowerCase, searchTextLowerCase, searchTextLowerCase);
    }
    public List<GongYoungParking> searchParking2(String searchText) {
        // 검색어를 대소문자 구분 없이 포함하는 데이터를 찾음
        String searchTextLowerCase = searchText.toLowerCase();

        // 검색 메서드 호출
        return gongYongParkingRepository.findByFcltyNMContainingOrRdnmadrNMContainingOrAdstrdNMContaining(
                searchTextLowerCase, searchTextLowerCase, searchTextLowerCase);
    }


}

