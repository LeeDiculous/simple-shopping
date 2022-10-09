package com.woo.jpa.shopping.web;

import com.woo.jpa.shopping.domain.entity.Customer;
import com.woo.jpa.shopping.domain.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final CustomerService customerService;

/*    @GetMapping()
    public String homeLogin(@CookieValue(name = "customerId", required = false) Long customerId, Model model) {

        if (customerId == null) {
            return "login/home";
        }

        Customer loginCustomer = customerService.getCustomer(customerId);
        if (loginCustomer == null) {
            return "login/home";
        }

        model.addAttribute("customer", loginCustomer);
        return "login/loginHome";
    }*/

/*    @GetMapping()
    public String homeLoginV2(HttpServletRequest request, Model model) {
        //세션이 없으면 home
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "login/home";
        }

        Customer loginCustomer = (Customer) session.getAttribute(SessionConst.LOGIN_CUSTOMER);

        //세션에 회원 데이터가 없으면 home
        if (loginCustomer == null) {
            return "login/home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("customer", loginCustomer);
        return "login/loginHome";
    }*/

    @GetMapping()
    public String homeLoginV2Spring(@SessionAttribute(name = SessionConst.LOGIN_CUSTOMER, required = false) Customer loginCustomer, Model model) {

        //세션에 회원 데이터가 없으면 home
        if (loginCustomer == null) {
            return "login/home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("customer", loginCustomer);
        return "login/loginHome";
    }
}
