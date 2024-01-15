package kr.allparking.bpm_AllParking.repository;

import kr.allparking.bpm_AllParking.entity.BoardEntity;
import kr.allparking.bpm_AllParking.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {

    //select * from comment_table where board_id=? order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
