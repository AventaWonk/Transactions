package ru.iac.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.iac.testtask.dto.AmountDto;
import ru.iac.testtask.dto.TransactionGroupDto;
import ru.iac.testtask.model.Transaction;
import ru.iac.testtask.service.ProductService;
import ru.iac.testtask.service.ShopService;
import ru.iac.testtask.service.TransactionService;
import ru.iac.testtask.util.OrderByStatementUtil;
import ru.iac.testtask.validator.TransactionValidator;
import static ru.iac.testtask.util.PaginationUtil.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Roman
 */
@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService transactionService;

    private ShopService shopService;

    private ProductService productService;

    private TransactionValidator transactionValidator;

    @GetMapping("/")
    public String index(@RequestParam(required = false, defaultValue = "10") int limit,
                        @RequestParam(required = false, defaultValue = "default") String sort,
                        @RequestParam(required = false, defaultValue = "default") String order,
                        Model model) {
        OrderByStatementUtil orderUtil = new OrderByStatementUtil(Transaction.getSortableColumns(), sort, order);
        int transactionCount = this.transactionService.getTransactionCount();
        int totalPages = calculatePageCount(transactionCount, limit);
        List<Transaction> transactionList = this.transactionService.getAllTransactions(0, limit, orderUtil);

        model.addAttribute("transactions", transactionList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", 1);

        return "transaction/index";
    }

    @GetMapping(path = "/", params = {"group"})
    public String indexGroup(@RequestParam String group,
                             @RequestParam(required = false, defaultValue = "10") int limit,
                             @RequestParam(required = false, defaultValue = "default") String sort,
                             @RequestParam(required = false, defaultValue = "default") String order,
                             Model model) {
        OrderByStatementUtil orderUtil = new OrderByStatementUtil(Transaction.getSortableColumns(), sort, order);
        AmountDto<TransactionGroupDto> transactionGroupAmountDto = this.transactionService.
                getTransactionGroupsByColumn(group, 0, limit, orderUtil);
        int totalPages = calculatePageCount(transactionGroupAmountDto.getTotalOperations(), limit);

        model.addAttribute("transactionGroups", transactionGroupAmountDto.getOperations());
        model.addAttribute("groupedName", group);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", 1);
        return "transaction/group/index";
    }

    @GetMapping("/{page}")
    public String getPage(@PathVariable int page,
                            @RequestParam(required = false, defaultValue = "10") int limit,
                            @RequestParam(required = false, defaultValue = "default") String sort,
                            @RequestParam(required = false, defaultValue = "default") String order,
                            Model model) {
        int transactionCount = this.transactionService.getTransactionCount();
        int totalPages = calculatePageCount(transactionCount, limit);
        int offset = calculateOffset(page, limit);
        OrderByStatementUtil orderUtil = new OrderByStatementUtil(Transaction.getSortableColumns(), sort, order);
        List<Transaction> transactionList = this.transactionService.getAllTransactions(offset, limit, orderUtil);

        model.addAttribute("transactions", transactionList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "transaction/index";
    }

    @GetMapping(path = "/{page}",  params = {"group"})
    public String getPageByGroup(@PathVariable int page,
                                 @RequestParam String group,
                                 @RequestParam(required = false, defaultValue = "10") int limit,
                                 @RequestParam(required = false, defaultValue = "default") String sort,
                                 @RequestParam(required = false, defaultValue = "default") String order,
                                 Model model) {
        int offset = calculateOffset(page, limit);
        OrderByStatementUtil orderUtil = new OrderByStatementUtil(Transaction.getSortableColumns(), sort, order);
        AmountDto<TransactionGroupDto> transactionGroupAmountDto = this.transactionService.
                getTransactionGroupsByColumn(group, offset, limit, orderUtil);
        int totalPages = calculatePageCount(transactionGroupAmountDto.getTotalOperations(), limit);

        model.addAttribute("transactionGroups", transactionGroupAmountDto.getOperations());
        model.addAttribute("groupedName", group);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "transaction/group/index";
    }

    @GetMapping("/create")
    public String create() {
        return "transaction/edit";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Transaction transaction,
                       BindingResult bindingResult,
                       @RequestParam int shopId,
                       @RequestParam int productId,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/transaction/create";
        }

        transaction.setShop(this.shopService.getShopById(shopId));
        transaction.setProduct(this.productService.getProductById(productId));
        this.transactionService.addTransaction(transaction);
        redirectAttributes.addFlashAttribute("message", "The transaction was successfully saved");

        return "redirect:/transaction/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("transaction", this.transactionService.getTransactionById(id));

        return "transaction/edit";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute Transaction transaction,
                         BindingResult bindingResult,
                         @RequestParam int shopId,
                         @RequestParam int productId,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/transaction/edit";
        }

        Transaction detachedTransaction = transactionService.getTransactionById(transaction.getId());
        transaction.setShop(this.shopService.getShopById(shopId));
        transaction.setProduct(this.productService.getProductById(productId));
        transaction.setDate(detachedTransaction.getDate());
        this.transactionService.updateTransaction(transaction);
        redirectAttributes.addFlashAttribute("message", "The transaction was successfully updated");

        return "redirect:/transaction/";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable int id, RedirectAttributes redirectAttributes) {
        this.transactionService.deleteTransaction(id);
        redirectAttributes.addFlashAttribute("message", "The transaction was successfully deleted");

        return "redirect:/transaction/";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(this.transactionValidator);
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setTransactionValidator(TransactionValidator transactionValidator) {
        this.transactionValidator = transactionValidator;
    }
}
