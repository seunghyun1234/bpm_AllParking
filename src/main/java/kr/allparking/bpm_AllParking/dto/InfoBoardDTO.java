package kr.allparking.bpm_AllParking.dto;

import kr.allparking.bpm_AllParking.entity.InfoBoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든필드를 매개변수로 하는 생성자
@ToString
public class InfoBoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
    private List<MultipartFile> boardFile; //save.html -> Controller 파일을 담는 용도
    private List<String> originalFileName; // 원본 파일 이름
    private List<String> storedFileName; //서버 저장용 파일


    public InfoBoardDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static InfoBoardDTO toBoardDTO(InfoBoardEntity infoBoardEntity){
        InfoBoardDTO infoBoardDTO =new InfoBoardDTO();
        infoBoardDTO.setId(infoBoardEntity.getId());
        infoBoardDTO.setBoardWriter(infoBoardEntity.getBoardWriter());
        infoBoardDTO.setBoardPass(infoBoardEntity.getBoardPass());
        infoBoardDTO.setBoardTitle(infoBoardEntity.getBoardTitle());
        infoBoardDTO.setBoardContents(infoBoardEntity.getBoardContents());
        infoBoardDTO.setBoardHits(infoBoardEntity.getBoardHits());
        infoBoardDTO.setBoardCreatedTime(infoBoardEntity.getCreatedTime());
        infoBoardDTO.setBoardUpdatedTime(infoBoardEntity.getUpdatedTime());

        return infoBoardDTO;

    }



}
