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
import ru.iac.testtask.validator.ProductValidator;
import static ru.iac.testtask.util.PaginationUtil.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Roman
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    private ProductValidator productValidator;

    /**
     * Returns limited list of products as a JSON by page
     *
     * @param query searching product name
     * @param page  page
     * @param limit max returning products number
     * @return      JSON product list
     */
    @GetMapping("/search")
    @ResponseBody
    public List<Product> searchByName(@RequestParam String query,
                                      @RequestParam(required = false, defaultValue = "1") int page,
                                      @RequestParam(required = false, defaultValue = "10") int limit) {
        int offset = calculateOffset(page, limit);

        return this.productService.searchByName(query, offset, limit);
    }

    /**
     * Returns index product view and displays limited product list
     *
     * @param limit max returning products number
     * @param model view model
     * @return      product index view
     */
    @GetMapping("/")
    public String index(@RequestParam(required = false, defaultValue = "10") int limit,
                        Model model) {
        int productCount = this.productService.getProductCount();
        int totalPages = calculatePageCount(productCount, limit);
        List<Product> productList = this.productService.getAllProducts(0, limit);

        model.addAttribute("products", productList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", 1);

        return "product/index";
    }

    /**
     * Returns index product view and displays limited product list by page
     *
     * @param page  page
     * @param limit max returning products number
     * @param model view model
     * @return      index product view
     */
    @GetMapping("/{page}")
    public String getPage(@PathVariable int page,
                          @RequestParam(required = false, defaultValue = "10") int limit,
                          Model model) {
        int productCount = this.productService.getProductCount();
        int totalPages = calculatePageCount(productCount, limit);
        int offset = calculateOffset(page, limit);
        List<Product> productList = this.productService.getAllProducts(offset, limit);

        model.addAttribute("products", productList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "product/index";
    }

    /**
     * @return edit template
     */
    @GetMapping("/create")
    public String create() {
        return "product/edit";
    }

    /**
     * Saves a new product entity
     *
     * @param product            product entity
     * @param bindingResult      product binding result
     * @param redirectAttributes attributes for a redirect scenario
     * @return                   redirect to product index or to edit on error
     */
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

    /**
     * Returns product edit view
     *
     * @param id    editing product id
     * @param model view model
     * @return      product edit view
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);

        return "product/edit";
    }

    /**
     * Updates an existing product entity
     *
     * @param product            updating product entity
     * @param bindingResult      product binding result
     * @param redirectAttributes attributes for a redirect scenario
     * @return                   redirect to product index or product edin on error
     */
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

    /**
     * Removes an existing product by id
     *
     * @param id                 removing product id
     * @param redirectAttributes attributes for a redirect scenario
     * @return                   redirect to product index
     */
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
