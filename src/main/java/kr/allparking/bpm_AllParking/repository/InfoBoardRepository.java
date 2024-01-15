package kr.allparking.bpm_AllParking.repository;

import kr.allparking.bpm_AllParking.entity.InfoBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InfoBoardRepository extends JpaRepository<InfoBoardEntity,Long> {
    //update board_table set board_hits=board_hits+1 where id=?
    @Modifying
    @Query(value = "update InfoBoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);
}
