package kr.allparking.bpm_AllParking.service;

import kr.allparking.bpm_AllParking.dto.InfoCommentDTO;
import kr.allparking.bpm_AllParking.entity.InfoBoardEntity;
import kr.allparking.bpm_AllParking.entity.InfoCommentEntity;
import kr.allparking.bpm_AllParking.repository.InfoBoardRepository;
import kr.allparking.bpm_AllParking.repository.InfoCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InfoCommentService {
    private final InfoCommentRepository infoCommentRepository;
    private final InfoBoardRepository infoBoardRepository;

    public Long save(InfoCommentDTO infoCommentDTO) {
        //부모엔티티 조회
        Optional<InfoBoardEntity> optionalBoardEntity = infoBoardRepository.findById(infoCommentDTO.getBoardId());
        if (optionalBoardEntity.isPresent()){
            InfoBoardEntity infoBoardEntity = optionalBoardEntity.get();
            InfoCommentEntity infoCommentEntity = InfoCommentEntity.toSaveEntity(infoCommentDTO, infoBoardEntity);
            return infoCommentRepository.save(infoCommentEntity).getId();

        }else {
            return null;

        }


    }

    public List<InfoCommentDTO> findAll(Long boardId) {
        //select * from comment_table where board_id=? order by id desc;
        InfoBoardEntity infoBoardEntity = infoBoardRepository.findById(boardId).get();
        List<InfoCommentEntity> infoCommentEntityList = infoCommentRepository.findAllByInfoBoardEntityOrderByIdDesc(infoBoardEntity);
        //EntityList - > DTOList
        List<InfoCommentDTO> infoCommentDTOList = new ArrayList<>();
        for (InfoCommentEntity infoCommentEntity : infoCommentEntityList){
            InfoCommentDTO infoCommentDTO = InfoCommentDTO.toCommentDTO(infoCommentEntity,boardId);
            infoCommentDTOList.add(infoCommentDTO);
        }

        return infoCommentDTOList;

    }
}
