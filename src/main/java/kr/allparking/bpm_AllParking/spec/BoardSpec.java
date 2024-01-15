package kr.allparking.bpm_AllParking.spec;

import kr.allparking.bpm_AllParking.entity.BoardEntity;
import org.springframework.data.jpa.domain.Specification;

public class BoardSpec {
    public static Specification<BoardEntity> titleOrContentOrWriter(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            String likeKeyword = "%" + searchKeyword + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("boardTitle"), likeKeyword),
                    criteriaBuilder.like(root.get("boardContent"), likeKeyword),
                    criteriaBuilder.like(root.get("boardWriter"), likeKeyword)
            );
        };
    }
}
