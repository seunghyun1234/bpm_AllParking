package kr.allparking.bpm_AllParking.repository;

import kr.allparking.bpm_AllParking.dto.BoardDTO;
import kr.allparking.bpm_AllParking.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
    //update board_table set board_hits=board_hits+1 where id=?
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);
//    List<BoardEntity> findByBoardWriter(String boardWriter);
      Page<BoardEntity> findAllByBoardTitleContaining(String searchKeyword, Pageable pageable);
//    List<BoardEntity> findByBoardContents(String boardContents);
}
