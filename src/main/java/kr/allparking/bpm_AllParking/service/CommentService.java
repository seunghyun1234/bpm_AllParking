package kr.allparking.bpm_AllParking.service;

import kr.allparking.bpm_AllParking.dto.CommentDTO;
import kr.allparking.bpm_AllParking.entity.BoardEntity;
import kr.allparking.bpm_AllParking.entity.CommentEntity;
import kr.allparking.bpm_AllParking.repository.BoardRepository;
import kr.allparking.bpm_AllParking.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        //부모엔티티 조회
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if (optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
            return commentRepository.save(commentEntity).getId();

        }else {
            return null;

        }


    }

    public List<CommentDTO> findAll(Long boardId) {
        //select * from comment_table where board_id=? order by id desc;
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        //EntityList - > DTOList
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList){
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity,boardId);
            commentDTOList.add(commentDTO);
        }

        return commentDTOList;

    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
