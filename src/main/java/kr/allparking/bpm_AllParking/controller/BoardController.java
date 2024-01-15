package kr.allparking.bpm_AllParking.controller;

import jakarta.servlet.http.HttpSession;
import kr.allparking.bpm_AllParking.dto.BoardDTO;
import kr.allparking.bpm_AllParking.dto.CommentDTO;

import kr.allparking.bpm_AllParking.dto.UserDTO;
import kr.allparking.bpm_AllParking.entity.BoardEntity;
import kr.allparking.bpm_AllParking.service.BoardService;
import kr.allparking.bpm_AllParking.service.CommentService;
import kr.allparking.bpm_AllParking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final UserService userService;
    @GetMapping("/save")
    public String saveForm(Principal principal, Model model){
        String loginId = principal.getName();
        UserDTO userDTO =userService.findByUsername(loginId);
        model.addAttribute("username",userDTO.getUsername());

        return "board/boardwrite1";
    }
    @PostMapping("/savePcro")
    public String save(@ModelAttribute BoardDTO boardDTO,Model model) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        model.addAttribute("message","게시글 작성이 완료되었습니다.");
        model.addAttribute("nextUrl","/board/paging");
        return "printMessage";
    }
    @GetMapping("/")
    public String findAll(Model model){
        //db에서  전체 게시글 데이터를 가져와서 List.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList",boardDTOList);
        return "board/boardinfo1";

    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable,Principal principal){
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 DATAIL.HTML에 출력
        */
        String loginId = principal.getName();
        UserDTO userDTO =userService.findByUsername(loginId);
        boardService.updateHits(id);
        BoardDTO boardDTO=boardService.findById(id);
        //댓글 목록 가져오기
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList",commentDTOList);
        model.addAttribute("board",boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("username",userDTO.getUsername());
        if (userDTO.getUsername().equals(boardDTO.getBoardWriter())){
            model.addAttribute("showButtons", true);
        }else {
            model.addAttribute("showButtons", false);
        }

        return "board/board1";

    }
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id,Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate",boardDTO);
        return "board/update1";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO,Model model){
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board",board);
        model.addAttribute("message","게시글 수정이 완료되었습니다.");
        model.addAttribute("nextUrl","/board/paging");
        return "printMessage";

//        return "redirect:/board/"+boardDTO.getId();

    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,Model model){
        boardService.delete(id);
        model.addAttribute("message","게시글 삭제가 완료되었습니다.");
        model.addAttribute("nextUrl","/board/paging");
        return "printMessage";

    }
    // /board/pageing?page=1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page=1,size = 10) Pageable pageable, Model model,
                         String searchKeyword){
//        Page<BoardDTO> boardList =null;
//        if (searchKeyword == null){
//            boardList =boardService.paging(pageable);
//        }else {
//            boardList=boardService.boardSearchList(searchKeyword, pageable);
//        }
            pageable.getPageNumber();
            Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 10;
        int startPage =(((int)(Math.ceil((double) pageable.getPageNumber() / blockLimit)))-1)*blockLimit +1;
        int endPage = ((startPage+blockLimit-1) < boardList.getTotalPages()) ? startPage + blockLimit -1 :boardList.getTotalPages();

        model.addAttribute("boardList",boardList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "board/boardinfo1";


    }

//    @GetMapping("/search")
//    public String search(@RequestParam String searchKey,
//                         @RequestParam String searchValue,
//                         Model model) {
//        List<BoardEntity> searchResults = boardService.searchBoards(searchKey, searchValue);
//
//        model.addAttribute("searchResults", searchResults);
//        return "board/boardinfo1"; // 검색 결과를 보여줄 뷰 페이지
//    }
}
