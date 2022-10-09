package com.woo.jpa.shopping.web;

import com.woo.jpa.shopping.domain.entity.Customer;
import com.woo.jpa.shopping.domain.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    //<----- 회원 등록 화면으로 이동 ----->
    @GetMapping("/new")
    public String insertCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/insertCustomer";
    }

    //<----- 회원 등록 기능 처리 ----->
    @PostMapping("/new")
    public String insertCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes, BindingResult bindingResult) {

        //검증 로직
        if (!StringUtils.hasText(customer.getLogin().getLoginId())) {
            bindingResult.addError(new FieldError("customer", "login.loginId", "로그인 ID를 입력하세요."));
        }

        if (!StringUtils.hasText(customer.getLogin().getPassword())) {
            bindingResult.addError(new FieldError("customer","login.password", "비밀번호를 입력하세요."));
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "customer/insertCustomer";
        }

        //성공 로직
        customerService.insertCustomer(customer);
        redirectAttributes.addAttribute("customerId", customer.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/customer/{customerId}";
    }

    //<----- 회원 정보 조회 ----->
    @GetMapping("/{customerId}")
    public String getCustomer(@PathVariable long customerId, Model model) {
        Customer customer = customerService.getCustomer(customerId);
        model.addAttribute("customer", customer);
        return "customer/getCustomer";
    }

    //<----- 회원 목록 조회 ----->
    @GetMapping("/getCustomerList")
    public String getCustomerList(Model model) {
        model.addAttribute("customerList", customerService.getCustomerList());
        return "customer/getCustomerList";
    }
}
