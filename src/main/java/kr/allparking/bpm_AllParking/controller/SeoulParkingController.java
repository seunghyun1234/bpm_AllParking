package kr.allparking.bpm_AllParking.controller;

import kr.allparking.bpm_AllParking.entity.FreeParking;
import kr.allparking.bpm_AllParking.entity.GongYoungParking;
import kr.allparking.bpm_AllParking.entity.MinyoungParking;
import kr.allparking.bpm_AllParking.service.SeoulParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//여기서 객체 관리들을 위한 어노테이션 Controller
@RequestMapping("/seoulparking")
public class SeoulParkingController {

    private final SeoulParkingService seoulParkingService;

    @Autowired
    public SeoulParkingController(SeoulParkingService seoulParkingService) {
        this.seoulParkingService = seoulParkingService;
    }

    @GetMapping("/free")
    public String showMu(){
        return "local/free";
    }
    @GetMapping("/min")
    public String showMin(){
        return "local/minyoung";
    }
    @GetMapping("/gong")
    public String showGong(Model model){
        return "local/map1";
    }

    @GetMapping("/parkingList")
    @ResponseBody
    public List<GongYoungParking> getParkingList() {
        return seoulParkingService.getGongYoungParkingLocations();
    }
    @GetMapping("/freeparkingList")
    @ResponseBody
    public List<FreeParking> getParkingList1() {
        return seoulParkingService.getFreeParkingLocations();
    }
    @GetMapping("/minyoungparkingList")
    @ResponseBody
    public List<MinyoungParking> getParkingList2() {
        return seoulParkingService.getMinyoungParkingLocations();
    }

    @PostMapping("/search")
    public ResponseEntity<List<FreeParking>> searchParking(@RequestParam String searchText) {
        try {//seoulParkingService내의 searchParking메서드로 매개변수에 RequestParam을 이용해서 서버로 데이터로 전달

            List<FreeParking> searchResult = seoulParkingService.searchParking(searchText);
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/search1")
    public ResponseEntity<List<MinyoungParking>> searchParking1(@RequestParam String searchText) {
        try {
            List<MinyoungParking> searchResult = seoulParkingService.searchParking1(searchText);
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/search2")
    public ResponseEntity<List<GongYoungParking>> searchParking2(@RequestParam String searchText) {
        try {
            List<GongYoungParking> searchResult = seoulParkingService.searchParking2(searchText);
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
    /*
    이 메서드는 seoulParkingService.getAllParkingLocations()를 호출하여
    서비스에서 모든 주차장 위치 정보를 가져옵니다. 그 후, 해당 정보를
    HTTP 응답의 본문으로 사용하기 위해 @ResponseBody 어노테이션을 통해 리스트 형태로 반환합니다.
    간단히 말해서, 이 코드는 "/parkingList" 경로로 GET 요청이 오면 서비스에서
    주차장 위치 정보를 가져와 클라이언트에게 응답으로 전송하는 역할을 합니다.
    반환되는 데이터는 JSON 형식으로 변환되어 전송될 것입니다.
     */