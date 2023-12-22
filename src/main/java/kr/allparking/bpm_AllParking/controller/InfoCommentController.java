package kr.allparking.bpm_AllParking.controller;

import kr.allparking.bpm_AllParking.dto.InfoCommentDTO;
import kr.allparking.bpm_AllParking.service.InfoCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/info/comment")
public class InfoCommentController {
    private final InfoCommentService infoCommentService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute InfoCommentDTO infoCommentDTO){
        System.out.println("commentDTO = " + infoCommentDTO);
        Long saveResult = infoCommentService.save(infoCommentDTO);
        if (saveResult != null){
            //작성 성공 댓글목록을 가져와서 리턴
            //댓글목록 : 해당 게시글의 댓글 전체
            List<InfoCommentDTO> infoCommentDTOList = infoCommentService.findAll(infoCommentDTO.getBoardId());
            return new ResponseEntity<>(infoCommentDTOList, HttpStatus.OK);
        }else {
            
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.",HttpStatus.NOT_FOUND);

        }
    }


}
