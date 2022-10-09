package com.woo.jpa.shopping.web;

import com.woo.jpa.shopping.domain.entity.Customer;
import com.woo.jpa.shopping.domain.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final CustomerService customerService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute Customer customer) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Customer customer, BindingResult bindingResult, HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectURL) {
        Customer loginCustomer = customerService.login(customer.getLogin().getLoginId(), customer.getLogin().getPassword());

        //로그인 오류
        if (loginCustomer == null) {
            bindingResult.addError(new FieldError("customer", "login.loginId", "아이디 혹은 비밀번호가 맞지 않습니다."));
            bindingResult.addError(new FieldError("customer", "login.password", "아이디 혹은 비밀번호가 맞지 않습니다."));
            return "login/loginForm";
        }

        //로그인 성공
/*        // <----- 쿠키 생성 로직 ----->
        Cookie idCookie = new Cookie("customerId", String.valueOf(loginCustomer.getId()));
        response.addCookie(idCookie);

        log.info("로그인한 회원은 = {} 입니다.", loginCustomer.getName());
        return "redirect:/home";*/

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();

        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_CUSTOMER, loginCustomer);
        return "redirect:" + redirectURL;

    }

    //로그아웃
/*    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {

        expireCookie(response, "customerId");
        return "redirect:/home";
    }*/

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); //세션을 제거한다.
        }
        return "redirect:/home";
    }


/*    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }*/


}
