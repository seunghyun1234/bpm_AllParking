package kr.allparking.bpm_AllParking.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        // 예외를 로그에 기록
        e.printStackTrace();

        // 모델에 오류 세부 정보 추가하여 오류 페이지에 표시
        model.addAttribute("errorType", "Unexpected Error");
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again.");

        // 오류 페이지 뷰 이름 반환
        return "error";
    }
}