package kr.allparking.bpm_AllParking.service;

import kr.allparking.bpm_AllParking.dto.BoardDTO;

import kr.allparking.bpm_AllParking.entity.BoardEntity;
import kr.allparking.bpm_AllParking.repository.BoardRepository;
import kr.allparking.bpm_AllParking.spec.BoardSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//DTO -> Entity (Entity class)
//Entity -> DTO (DTO class)
@Service
@RequiredArgsConstructor
//@Transactional
public class BoardService {
    private final BoardRepository boardRepository;


    public void save(BoardDTO boardDTO) throws IOException {
        //파일 첨부 여부에 따라 로직 분리

        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);

    }
    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll(Sort.by(Sort.Direction.DESC,"createdTime"));
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity: boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }
    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);

    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;

        }else {

            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
    @Transactional
    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber()-1;
        int pageLimit=10;
        //한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        //page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(),board.getBoardWriter(),board.getBoardTitle(),board.getBoardHits(),board.getCreatedTime()));



        //목록 : id , writer , title , hits , createdTime
        return boardDTOS;

    }
//    @Transactional
//    public Page<BoardDTO> boardSearchList(String searchKeyword, Pageable pageable) {
//        Page<BoardEntity> boardEntities =boardRepository.findAllByBoardTitleContaining(searchKeyword,pageable);
//
//        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(),board.getBoardWriter(),board.getBoardTitle(),board.getBoardHits(),board.getCreatedTime()));
//        return boardDTOS;
//    }


//    public List<BoardEntity> searchBoards(String searchKey, String searchValue) {
//        switch (searchKey) {
//            case "subject":
//                return boardRepository.findByBoardTitle(searchValue);
//            case "content":
//                return boardRepository.findByBoardContents(searchValue);
//            case "writer_name":
//                return boardRepository.findByBoardWriter(searchValue);
//            default:
//                return Collections.emptyList();
//        }
//    }
}
