package kr.allparking.bpm_AllParking.entity;

import kr.allparking.bpm_AllParking.dto.InfoCommentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "infocomment_table")
public class InfoCommentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length =20, nullable = false)
    private String commentWriter;
    @Column
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private InfoBoardEntity infoBoardEntity;

    public static InfoCommentEntity toSaveEntity(InfoCommentDTO infoCommentDTO, InfoBoardEntity infoBoardEntity) {
        InfoCommentEntity infoCommentEntity = new InfoCommentEntity();
        infoCommentEntity.setCommentWriter(infoCommentDTO.getCommentWriter());
        infoCommentEntity.setCommentContents(infoCommentDTO.getCommentContents());
        infoCommentEntity.setInfoBoardEntity(infoBoardEntity);
        return infoCommentEntity;
    }
}
