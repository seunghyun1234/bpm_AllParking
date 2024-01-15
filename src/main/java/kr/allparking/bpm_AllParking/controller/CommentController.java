package kr.allparking.bpm_AllParking.controller;

import kr.allparking.bpm_AllParking.dto.CommentDTO;
import kr.allparking.bpm_AllParking.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO, Model model){
        System.out.println("commentDTO = " + commentDTO);
        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null){
            //작성 성공 댓글목록을 가져와서 리턴
            //댓글목록 : 해당 게시글의 댓글 전체
            List<CommentDTO> commentDTOList =commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        }else {
            
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.",HttpStatus.NOT_FOUND);

        }


    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
        commentService.delete(id);
        model.addAttribute("message","댓글 삭제가 완료되었습니다.");
        model.addAttribute("nextUrl","/board/{id}");
        return "printMessage";

    }


}
