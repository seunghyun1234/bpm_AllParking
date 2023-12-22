package kr.allparking.bpm_AllParking.service;

import kr.allparking.bpm_AllParking.dto.InfoBoardDTO;
import kr.allparking.bpm_AllParking.entity.InfoBoardEntity;
import kr.allparking.bpm_AllParking.repository.InfoBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//DTO -> Entity (Entity class)
//Entity -> DTO (DTO class)
@Service
@RequiredArgsConstructor
//@Transactional
public class InfoBoardService {
    private final InfoBoardRepository infoBoardRepository;


    public void save(InfoBoardDTO infoBoardDTO) throws IOException {
        //파일 첨부 여부에 따라 로직 분리

        InfoBoardEntity infoBoardEntity = InfoBoardEntity.toSaveEntity(infoBoardDTO);
        infoBoardRepository.save(infoBoardEntity);

    }
    @Transactional
    public List<InfoBoardDTO> findAll() {
        List<InfoBoardEntity> infoBoardEntityList = infoBoardRepository.findAll(Sort.by(Sort.Direction.DESC,"createdTime"));
        List<InfoBoardDTO> infoBoardDTOList = new ArrayList<>();
        for(InfoBoardEntity infoBoardEntity : infoBoardEntityList){
            infoBoardDTOList.add(InfoBoardDTO.toBoardDTO(infoBoardEntity));
        }
        return infoBoardDTOList;
    }
    @Transactional
    public void updateHits(Long id) {
        infoBoardRepository.updateHits(id);

    }

    public InfoBoardDTO findById(Long id) {
        Optional<InfoBoardEntity> optionalBoardEntity = infoBoardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){
            InfoBoardEntity infoBoardEntity = optionalBoardEntity.get();
            InfoBoardDTO infoBoardDTO = InfoBoardDTO.toBoardDTO(infoBoardEntity);
            return infoBoardDTO;

        }else {

            return null;
        }
    }

    public InfoBoardDTO update(InfoBoardDTO infoBoardDTO) {
        InfoBoardEntity infoBoardEntity = InfoBoardEntity.toUpdateEntity(infoBoardDTO);
        infoBoardRepository.save(infoBoardEntity);
        return findById(infoBoardDTO.getId());
    }

    public void delete(Long id) {
        infoBoardRepository.deleteById(id);
    }
    @Transactional
    public Page<InfoBoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber()-1;
        int pageLimit=10;
        //한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        //page 위치에 있는 값은 0부터 시작
        Page<InfoBoardEntity> boardEntities =
                infoBoardRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent());//요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements());//전체 글 갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber());//DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages());//전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize());//한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious());//이전 페이지 존재여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst());//첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast());//마지막 페이지 여부

        //목록 : id , writer , title , hits , createdTime
        Page<InfoBoardDTO> boardDTOS = boardEntities.map(board -> new InfoBoardDTO(board.getId(),board.getBoardWriter(),board.getBoardTitle(),board.getBoardHits(),board.getCreatedTime()));
        return boardDTOS;
    }
}
