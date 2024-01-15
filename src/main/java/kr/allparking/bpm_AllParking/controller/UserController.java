package kr.allparking.bpm_AllParking.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.allparking.bpm_AllParking.dto.UserDTO;
import kr.allparking.bpm_AllParking.dto.UserJoinDTO;
import kr.allparking.bpm_AllParking.dto.UserLoginDTO;
import kr.allparking.bpm_AllParking.entity.UserEntity;
import kr.allparking.bpm_AllParking.repository.UserRepository;
import kr.allparking.bpm_AllParking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/join")
    public String saveForm(Model model){
        model.addAttribute("userJoinDTO",new UserJoinDTO());

        return "user/join";

    }
//    @PostMapping("/joinProc")
//    public String save (UserDTO userDTO){
//        System.out.println("userDTO = " + userDTO);
//        userService.userProcess(userDTO);
//
//        return "/login";
//    }
    @PostMapping("/joinProc")
    public String save(@Valid @ModelAttribute UserJoinDTO userJoinDTO , BindingResult bindingResult, Model model){
        if (userService.joinValid(userJoinDTO, bindingResult).hasErrors()) {
//            model.addAttribute("userJoinDTO",userJoinDTO);
            return "user/join";
        }
        userService.join(userJoinDTO);
        model.addAttribute("message","회원가입에 성공했습니다\n로그인하세요");
        model.addAttribute("nextUrl","/member/login");
        return "printMessage";

    }
//    @PostMapping("/joinProc")
//    public @ResponseBody String save(@ModelAttribute UserDTO userDTO){
//        System.out.println("userDTO = " + userDTO);
//        Long userId=userService.userSave(userDTO);
//        if(userId==null){
//            return "save_fail";
//        }else {
//            return "save_success";
//        }
//
//    }

    @GetMapping("/login")
    public String loginForm(Model model, HttpServletRequest request){
        String uri =request.getHeader("Referer");
        if(uri !=null && !uri.contains("/login")&& !uri.contains("/join")){
            request.getSession().setAttribute("prevPage",uri);
        }
        model.addAttribute("userLoginDTO",new UserLoginDTO());

        return "user/login";
    }



    @PostMapping("/loginProc")
    public String login(@ModelAttribute UserLoginDTO userLoginDTO, HttpSession session) {
        UserDTO loginResult =userService.login(userLoginDTO);
        if(loginResult!=null){
            session.setAttribute("loginId",loginResult.getUsername());
            return "redirect:/";
        }else {
            return "login";
        }

    }
//    @PostMapping("/loginProc")
//    public String login(@ModelAttribute UserDTO userDTO, HttpSession session) {
//        return "redirect:/";
//
//    }
//@GetMapping("/mypage")
//public String myPage(Principal principal, Model model) {
//    // Get the username of the current user
//    String username = principal.getName();
//
//    // Get the user information using the userService
//    UserDTO userDTO = userService.myInfo(username);
//
//    // Add the userDTO to the model with the attribute name "user"
//    model.addAttribute("user", userDTO);
//
//    // Return the view name "mypage"
//    return "mypage";
//}
    @GetMapping("/mypage")
    public String mypage(Principal principal,Model model){
        String loginId = principal.getName();
        UserDTO userDTO=userService.findByUsername(loginId);
        model.addAttribute("user",userDTO);
        return "user/mypage";
    }

    @GetMapping("/edit")
    public String edit(Authentication auth, Model model){
        UserEntity userEntity=userService.myInfo(auth.getName());
        model.addAttribute("userDTO",UserDTO.toUserDTO(userEntity));
        return "user/editmy";
    }
    @PostMapping("/editProc")
    public String userEdit(@Valid @ModelAttribute UserDTO userDTO,BindingResult bindingResult,Authentication auth,Model model){
        if (userService.editValid(userDTO,bindingResult,auth.getName()).hasErrors()){
            return "user/editmy";
        }
        userService.edit(userDTO,auth.getName());
        model.addAttribute("message","정보가 수정되었습니다.");
        model.addAttribute("nextUrl","/member/mypage");
        return "printMessage";
    }

    @GetMapping("/delete")
    public String userDeletePage(Authentication auth, Model model) {
        UserEntity user = userService.myInfo(auth.getName());
        model.addAttribute("userDTO", UserDTO.toUserDTO(user));
        return "user/delete";
    }
    @PostMapping("/delete")
    public String userDelete(@ModelAttribute UserDTO dto, Authentication auth, Model model) {
        Boolean deleteSuccess = userService.delete(auth.getName(), dto.getNowPassword());
        if (deleteSuccess) {
            model.addAttribute("message", "탈퇴 되었습니다.");
            model.addAttribute("nextUrl", "/logout");
            return "printMessage";
        } else {
            model.addAttribute("message", "현재 비밀번호가 틀려 탈퇴에 실패하였습니다.");
            model.addAttribute("nextUrl", "/member/delete");
            return "printMessage";
        }
    }



}
