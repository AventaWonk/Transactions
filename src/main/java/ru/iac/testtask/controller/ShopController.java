package ru.iac.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.iac.testtask.model.Shop;
import ru.iac.testtask.service.ShopService;
import ru.iac.testtask.util.PaginationUtil;
import ru.iac.testtask.validator.ShopValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private ShopService shopService;
    private ShopValidator shopValidator;

    @GetMapping("/")
    public String index(@RequestParam(required = false, name = "limit", defaultValue = "10") int limit,
                        Model model) {
        int shopCount = this.shopService.getShopCount();
        int totalPages = PaginationUtil.calculatePageCount(shopCount, limit);
        final List<Shop> shopList = this.shopService.getAllShops(0, limit);

        model.addAttribute("shops", shopList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", 1);

        return "shop/index";
    }

    @GetMapping("/{page}")
    public String getByPage(@PathVariable int page,
                            @RequestParam(required = false, name = "limit", defaultValue = "10") int limit,
                            Model model) {
        int shopCount = this.shopService.getShopCount();
        int totalPages = PaginationUtil.calculatePageCount(shopCount, limit);
        final List<Shop> shopList = this.shopService.getAllShops((page - 1) * limit, limit);

        model.addAttribute("shops", shopList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "shop/index";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Shop> searchByName(@RequestParam String query,
                                   @RequestParam(required = false, defaultValue = "1") int page,
                                   @RequestParam(required = false, name="limit", defaultValue = "10") int limit) {
        return this.shopService.searchByAddress(query, (page - 1) * page, limit);
    }

    @GetMapping("/create")
    public String create() {
        return "shop/edit";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Shop shop,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/shop/create";
        }

        this.shopService.addShop(shop);
        redirectAttributes.addFlashAttribute("message", "The shop was successfully saved");

        return "redirect:/shop/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("shop", this.shopService.getShopById(id));

        return "shop/edit";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute Shop shop,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/shop/edit";
        }

        this.shopService.updateShop(shop);
        redirectAttributes.addFlashAttribute("message", "The shop was successfully updated");

        return "redirect:/shop/";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable int id, RedirectAttributes redirectAttributes) {
        this.shopService.deleteShop(id);
        redirectAttributes.addFlashAttribute("message", "The shop was successfully deleted");

        return "redirect:/shop/";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(this.shopValidator);
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    @Autowired
    public void setShopValidator(ShopValidator shopValidator) {
        this.shopValidator = shopValidator;
    }
}
