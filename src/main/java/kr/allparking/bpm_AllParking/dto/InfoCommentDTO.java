package kr.allparking.bpm_AllParking.dto;

import kr.allparking.bpm_AllParking.entity.InfoCommentEntity;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class InfoCommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    public static InfoCommentDTO toCommentDTO(InfoCommentEntity infoCommentEntity, Long boardId) {
        InfoCommentDTO infoCommentDTO =new InfoCommentDTO();
        infoCommentDTO.setId(infoCommentEntity.getId());
        infoCommentDTO.setCommentWriter(infoCommentEntity.getCommentWriter());
        infoCommentDTO.setCommentContents(infoCommentEntity.getCommentContents());
        infoCommentDTO.setCommentCreatedTime(infoCommentEntity.getCreatedTime());
//        commentDTO.setBoardId(commentEntity.getBoardEntity().getId());//service 메서드에 틀랜젝션
        infoCommentDTO.setBoardId(boardId);
        return infoCommentDTO;

    }
}
