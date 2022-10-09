package com.woo.jpa.shopping.web;

import com.woo.jpa.shopping.domain.entity.Customer;
import com.woo.jpa.shopping.domain.entity.Product;
import com.woo.jpa.shopping.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private final ProductService productService;

    //상품 등록 화면으로 이동
    @GetMapping("/new")
    public String insertProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product/insertProduct";
    }

    //상품 등록 기능 처리
    @PostMapping("/new")
    public String insertProduct(@ModelAttribute Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (!StringUtils.hasText(product.getName()) || product.getPrice() == null || product.getQuantity() == null) {
            bindingResult.addError(new ObjectError("product", "올바른 상품 정보를 입력해주세요."));
            return "/product/insertProduct";
        }

        productService.insertProduct(product);
        redirectAttributes.addAttribute("productId", product.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/product/{productId}";
    }

    //상품 상세 조회
    @GetMapping("/{productId}")
    public String getProduct(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productService.getProduct(productId));
        return "/product/getProduct";
    }

    //상품 목록
    @GetMapping("/getProductList")
    public String getProductList(Model model, @SessionAttribute(name = SessionConst.LOGIN_CUSTOMER, required = false)Customer loginCustomer) {
        model.addAttribute("productList", productService.getProductList());

        if (loginCustomer == null) {
            return "product/getProductList";
        }

        model.addAttribute("status", true);
        model.addAttribute("customer", loginCustomer);
        return "product/getProductList";
    }

    //상품 수정
    @GetMapping("/{productId}/edit")
    public String editProduct(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productService.getProduct(productId));
        return "product/editProduct";
    }

    //상품 수정 POST
    @PostMapping("/{productId}/edit")
    public String editProduct(@PathVariable Long productId, @ModelAttribute Product product, BindingResult bindingResult) {

        if (!StringUtils.hasText(product.getName()) || product.getPrice() == null || product.getQuantity() == null) {
            bindingResult.addError(new ObjectError("product", "올바른 상품 정보를 입력해주세요."));
            return "/product/editProduct";
        }

        productService.updateProduct(productId, product);
        return "redirect:/product/{productId}";
    }
}
