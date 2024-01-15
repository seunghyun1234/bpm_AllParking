package kr.allparking.bpm_AllParking.controller;

import kr.allparking.bpm_AllParking.dto.InfoBoardDTO;
import kr.allparking.bpm_AllParking.dto.InfoCommentDTO;
import kr.allparking.bpm_AllParking.service.InfoBoardService;
import kr.allparking.bpm_AllParking.service.InfoCommentService;
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
@RequestMapping("/info")

public class InfoBoardController {
    private final InfoBoardService infoBoardService;
    private final InfoCommentService infoCommentService;
    @GetMapping("/save")
    public String saveForm(){
        return "infoboard/boardwrite";
    }
    @PostMapping("/savePcro")
    public String save(@ModelAttribute InfoBoardDTO infoBoardDTO) throws IOException {
        System.out.println("boardDTO = " + infoBoardDTO);
        infoBoardService.save(infoBoardDTO);
        return "redirect:/info/paging";
    }
    @GetMapping("/")
    public String findAll(Model model){
        //db에서  전체 게시글 데이터를 가져와서 List.html에 보여준다.
        List<InfoBoardDTO> infoBoardDTOList = infoBoardService.findAll();
        model.addAttribute("boardList", infoBoardDTOList);
        return "infoboard/boardinfo";

    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable){
        /*
            해당 게시글의 조회수를 하나 올리고 
            게시글 데이터를 가져와서 DATAIL.HTML에 출력
        */
        infoBoardService.updateHits(id);
        InfoBoardDTO infoBoardDTO = infoBoardService.findById(id);
        //댓글 목록 가져오기
        List<InfoCommentDTO> infoCommentDTOList = infoCommentService.findAll(id);
        model.addAttribute("commentList", infoCommentDTOList);
        model.addAttribute("board", infoBoardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "infoboard/board";

    }
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        InfoBoardDTO boardDTO = infoBoardService.findById(id);
        model.addAttribute("boardUpdate",boardDTO);
        return "infoboard/update";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute InfoBoardDTO infoBoardDTO, Model model){
        InfoBoardDTO board = infoBoardService.update(infoBoardDTO);
        model.addAttribute("board",board);
        return "infoboard/board";
//        return "redirect:/board/"+boardDTO.getId();

    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        infoBoardService.delete(id);
        return "redirect:/info/paging";
    }
    // /board/pageing?page=1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page=1) Pageable pageable, Model model){
        pageable.getPageNumber();
        Page<InfoBoardDTO> boardList = infoBoardService.paging(pageable);
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
        return "infoboard/boardinfo";


    }
}
