package kr.allparking.bpm_AllParking.controller;


import kr.allparking.bpm_AllParking.dto.MemberDTO;
import kr.allparking.bpm_AllParking.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    //생성자 주입방식
    private final MemberService memberService;
    //회원가입  페이지 출력요청
    @GetMapping("/join")
    public String saveForm(){
        return "join";
    }
    @PostMapping("/joinProc")
    public String save(@ModelAttribute MemberDTO memberDTO){
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);

        memberService.save(memberDTO);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null){
            session.setAttribute("loginId",loginResult.getMemberId());
            return "index";
        }else {
            return "login";
        }
    }

    @GetMapping("/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList",memberDTOList);
        return "list";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "detail";

    }
    @GetMapping("/update")
    public String updateForm(HttpSession session,Model model){
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember",memberDTO);
        return "update1";

    }
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        memberService.deleteById(id);
        return "redirect:/member/";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }
    @PostMapping("/id-check")
    public @ResponseBody String emailCheck(@RequestParam("memberId") String memberId){
        System.out.println("memberId = " + memberId);
        String checkResult = memberService.memberIdCheck(memberId);
//        if(checkResult != null){
//            return "ok";
//        }else {
//            return "no";
//        }
        return checkResult;


    }

}
