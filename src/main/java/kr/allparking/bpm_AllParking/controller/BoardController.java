package kr.allparking.bpm_AllParking.controller;

import kr.allparking.bpm_AllParking.dto.BoardDTO;
import kr.allparking.bpm_AllParking.dto.CommentDTO;

import kr.allparking.bpm_AllParking.service.BoardService;
import kr.allparking.bpm_AllParking.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    @GetMapping("/save")
    public String saveForm(){
        return "boardwritefree";
    }
    @PostMapping("/savePcro")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "redirect:/board/paging";
    }
    @GetMapping("/")
    public String findAll(Model model){
        //db에서  전체 게시글 데이터를 가져와서 List.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList",boardDTOList);
        return "boardinfo1";

    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable){
        /*
            해당 게시글의 조회수를 하나 올리고 
            게시글 데이터를 가져와서 DATAIL.HTML에 출력
        */
        boardService.updateHits(id);
        BoardDTO boardDTO=boardService.findById(id);
        //댓글 목록 가져오기
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList",commentDTOList);
        model.addAttribute("board",boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "board1";

    }
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id,Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate",boardDTO);
        return "update1";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO,Model model){
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board",board);
        return "detail";
//        return "redirect:/board/"+boardDTO.getId();

    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/paging";
    }
    // /board/pageing?page=1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page=1) Pageable pageable, Model model){
        pageable.getPageNumber();
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 10;
        int startPage =(((int)(Math.ceil((double) pageable.getPageNumber() / blockLimit)))-1)*blockLimit +1;
        int endPage = ((startPage+blockLimit-1) < boardList.getTotalPages()) ? startPage + blockLimit -1 :boardList.getTotalPages();



        //page 갯수 20개
        //현재 사용자가 3페이지
        //1 2 3 4 5
        //보여지는 페이지 갯수 3개
        //1 2 3
        //4 5 6
        //보여지는 페이지 갯수 3개
        //총 페이지 갯수 8개
        model.addAttribute("boardList",boardList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "boardinfo1";


    }
}
