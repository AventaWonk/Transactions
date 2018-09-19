package ru.iac.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.iac.testtask.model.Product;
import ru.iac.testtask.service.ProductService;
import ru.iac.testtask.util.PaginationUtil;
import ru.iac.testtask.validator.ProductValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    private ProductValidator productValidator;

    @GetMapping("/")
    public String index(@RequestParam(required = false, name="limit", defaultValue = "10") int limit,
                        Model model) {
        int productCount = this.productService.getProductCount();
        int totalPages = PaginationUtil.calculatePageCount(productCount, limit);
        List<Product> productList = this.productService.getAllProducts(0, limit);

        model.addAttribute("products", productList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", 1);

        return "product/index";
    }

    @GetMapping("/{page}")
    public String getByPage(@PathVariable("page") int page,
                            @RequestParam(required = false, name="limit", defaultValue = "10") int limit,
                            Model model) {
        int productCount = this.productService.getProductCount();
        int totalPages = PaginationUtil.calculatePageCount(productCount, limit);
        List<Product> productList = this.productService.getAllProducts((page - 1) * limit, limit);

        model.addAttribute("products", productList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "product/index";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Product> searchByName(@RequestParam String query,
                                      @RequestParam(required = false, defaultValue = "1") int page,
                                      @RequestParam(required = false, name="limit", defaultValue = "10") int limit) {
        return this.productService.searchByName(query, (page - 1) * page, limit);
    }

    @GetMapping("/create")
    public String create() {
        return "product/edit";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Product product,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/product/create";
        }

        this.productService.addProduct(product);
        redirectAttributes.addFlashAttribute("message", "The product was successfully saved");

        return "redirect:/product/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);

        return "product/edit";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute Product product,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/product/edit";
        }

        this.productService.updateProduct(product);
        redirectAttributes.addFlashAttribute("message", "The product was successfully updated");

        return "redirect:/product/";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable int id, RedirectAttributes redirectAttributes) {
        this.productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("message", "The product was successfully deleted");

        return "redirect:/product/";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(this.productValidator);
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setProductValidator(ProductValidator productValidator) {
        this.productValidator = productValidator;
    }
}
