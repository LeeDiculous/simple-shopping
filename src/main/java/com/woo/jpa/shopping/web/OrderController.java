package com.woo.jpa.shopping.web;

import com.woo.jpa.shopping.domain.entity.Customer;
import com.woo.jpa.shopping.domain.entity.Item;
import com.woo.jpa.shopping.domain.entity.Order;
import com.woo.jpa.shopping.domain.entity.Product;
import com.woo.jpa.shopping.domain.service.CustomerService;
import com.woo.jpa.shopping.domain.service.OrderService;
import com.woo.jpa.shopping.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private final CustomerService customerService;

    @Autowired
    private final ProductService productService;

    @Autowired
    private final OrderService orderService;

    @ModelAttribute("customerList")
    public List<Customer> customerList() {
        return customerService.getCustomerList();
    }

    @ModelAttribute("productList")
    public List<Product> productList() {
        return productService.getProductList();
    }

    @GetMapping("/new")
    public String insertOrder() {
        return "order/insertOrder";
    }

    @PostMapping("/new")
    public String insertOrder(Long customerId, Long productId, Integer count) {
        orderService.insertOrder(customerId, productId, count);
        return "redirect:/order/getOrderList";
    }

    @RequestMapping("/getOrderList/{customerId}") // get과 post 방식 둘 다 쓰여야 하기 때문
    public String getOrderList(@ModelAttribute Order order, Model model, @PathVariable Long customerId) {
        List<Order> orderList = orderService.getOrderList(order);
        String customerName = customerService.getCustomer(customerId).getName();
        model.addAttribute("orderList", orderList);
        model.addAttribute("customerName", customerName);
        return "order/getOrderList";
    }

    //주문 취소 처리
    @GetMapping("{orderId}/orderCancel")
    public String orderCancel(@PathVariable Long orderId) {
        orderService.orderCancel(orderId);
        return "redirect:/order/getOrderList";
    }

    @GetMapping("{customerId}/loginOrder")
    public String loginOrder(@PathVariable Long customerId,
                             @SessionAttribute(name = SessionConst.LOGIN_CUSTOMER) Customer loginCustomer,
                             Model model) {

        model.addAttribute("customer", loginCustomer);
        return "order/loginOrder";
    }

    @PostMapping("{customerId}/loginOrder")
    public String loginOrder(@PathVariable Long customerId,
                             @SessionAttribute(name = SessionConst.LOGIN_CUSTOMER) Customer loginCustomer,
                             Long productId,
                             Integer count,
                             RedirectAttributes redirectAttributes) {


//        if (productId == null || count == null) {
//            bindingResultId.addError(new FieldError("productId", "order.id" ,"올바른 상품을 선택해 주세요."));
//            bindingResultCount.addError(new FieldError("count","order.count" ,"올바른 수량을 선택해 주세요."));
//        }

        Order order = orderService.insertOrder(loginCustomer.getId(), productId, count);

        redirectAttributes.addAttribute("orderId", order.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/order/{customerId}/getOrder{orderId}";
    }

    @GetMapping("{customerId}/getOrder{orderId}")
    public String getOrder(@PathVariable Long customerId, @PathVariable Long orderId,
                           @SessionAttribute(name = SessionConst.LOGIN_CUSTOMER) Customer loginCustomer,
                           Model model) {

        Order order = orderService.getOrder(orderId);
        Item item = orderService.getItem(orderId);

        model.addAttribute("order", order);
        model.addAttribute("item", item);
        model.addAttribute("customer", loginCustomer);

        return "order/getOrder";
    }
}
