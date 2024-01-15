package kr.allparking.bpm_AllParking.entity;

import kr.allparking.bpm_AllParking.dto.InfoBoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "infoboard_table")
@Getter
@Setter
@ToString(exclude = "infoCommentEntityList")
public class InfoBoardEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,nullable = false)
    private String boardWriter;
    @Column(nullable = false)
    private String boardPass;
    @Column(nullable = false)
    private String boardTitle;
    @Column(length = 500,nullable = false)
    private String boardContents;
    @Column
    private int boardHits;
    @OneToMany(mappedBy = "infoBoardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InfoCommentEntity> infoCommentEntityList = new ArrayList<>();




    public static InfoBoardEntity toSaveEntity(InfoBoardDTO infoBoardDTO){
        InfoBoardEntity infoBoardEntity =new InfoBoardEntity();
        infoBoardEntity.setBoardWriter(infoBoardDTO.getBoardWriter());
        infoBoardEntity.setBoardPass(infoBoardDTO.getBoardPass());
        infoBoardEntity.setBoardTitle(infoBoardDTO.getBoardTitle());
        infoBoardEntity.setBoardContents(infoBoardDTO.getBoardContents());
        infoBoardEntity.setBoardHits(0);

        return infoBoardEntity;

    }

    public static InfoBoardEntity toUpdateEntity(InfoBoardDTO infoBoardDTO) {
        InfoBoardEntity infoBoardEntity =new InfoBoardEntity();
        infoBoardEntity.setId((infoBoardDTO.getId()));
        infoBoardEntity.setBoardWriter(infoBoardDTO.getBoardWriter());
        infoBoardEntity.setBoardPass(infoBoardDTO.getBoardPass());
        infoBoardEntity.setBoardTitle(infoBoardDTO.getBoardTitle());
        infoBoardEntity.setBoardContents(infoBoardDTO.getBoardContents());
        infoBoardEntity.setBoardHits(0);
        return infoBoardEntity;
    }


}
