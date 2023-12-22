package kr.allparking.bpm_AllParking.repository;

import kr.allparking.bpm_AllParking.entity.InfoBoardEntity;
import kr.allparking.bpm_AllParking.entity.InfoCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoCommentRepository extends JpaRepository<InfoCommentEntity,Long> {

    //select * from comment_table where board_id=? order by id desc;
    List<InfoCommentEntity> findAllByInfoBoardEntityOrderByIdDesc(InfoBoardEntity infoBoardEntity);
}
