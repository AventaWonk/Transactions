package ru.iac.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.iac.testtask.dto.AmountDto;
import ru.iac.testtask.model.Product;
import ru.iac.testtask.model.Transaction;
import ru.iac.testtask.service.ProductService;
import ru.iac.testtask.service.TransactionService;
import ru.iac.testtask.util.OrderByStatementUtil;
import static ru.iac.testtask.util.PaginationUtil.*;

import java.util.Date;
import java.util.Locale;

/**
 * @author Romans
 */
@Controller
@RequestMapping("/transaction/search")
public class TransactionSearchController {

    private TransactionService transactionService;

    private ProductService productService;

    @PostMapping("/")
    public String search(@RequestParam(required=false, name="product", defaultValue = "-1") int productId,
                         @RequestParam(required=false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
                         @RequestParam(required=false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,
                         RedirectAttributes redirectAttributes) {
        if (productId == -1 && start == null && end == null) {
            return "redirect:/transaction/";
        }

        if (start != null || end != null) {
            if (end == null) {
                end = new Date();
            } else  if (start == null) {
                start = new Date(1L);
            }

            DateFormatter dateFormatter = new DateFormatter("dd/MM/yyyy HH:mm");
            redirectAttributes.addAttribute("start", dateFormatter.print(start, Locale.getDefault()));
            redirectAttributes.addAttribute("end", dateFormatter.print(end, Locale.getDefault()));
        }

        if (productId != -1) {
            redirectAttributes.addAttribute("product", productId);
        }

        return "redirect:/transaction/search/";
    }


    @GetMapping(path = "/", params = {"product"})
    public String searchByProduct(@RequestParam("product") int productId,
                         @RequestParam(required=false, defaultValue = "10") int limit,
                         @RequestParam(required=false, defaultValue = "default") String sort,
                         @RequestParam(required=false, defaultValue = "default") String order,
                         Model model) {
        Product product = this.productService.getProductById(productId);
        OrderByStatementUtil orderUtil = new OrderByStatementUtil(Transaction.getSortableColumns(), sort, order);
        AmountDto<Transaction> transactionAmountDto = this.transactionService.
                getTotalAmountByProductId(productId, 0, limit, orderUtil);
        int totalPages = calculatePageCount(transactionAmountDto.getTotalOperations(), limit);

        model.addAttribute("transactions", transactionAmountDto.getOperations());
        model.addAttribute("product", product);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalAmount", transactionAmountDto.getTotalAmount());

        return "transaction/search/index";
    }

    @GetMapping(path = "/", params = {"start", "end"})
    public String searchByPeriod(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
                                 @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,
                                 @RequestParam(required=false, defaultValue = "10") int limit,
                                 @RequestParam(required=false, defaultValue = "default") String sort,
                                 @RequestParam(required=false, defaultValue = "default") String order,
                                  Model model) {
        OrderByStatementUtil orderUtil = new OrderByStatementUtil(Transaction.getSortableColumns(), sort, order);
        AmountDto<Transaction> transactionAmountDto = this.transactionService
                .getTotalAmountByPeriod(start, end, 0, limit, orderUtil);
        int totalPages = calculatePageCount(transactionAmountDto.getTotalOperations(), limit);

        model.addAttribute("transactions", transactionAmountDto.getOperations());
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalAmount", transactionAmountDto.getTotalAmount());

        return "transaction/search/index";
    }

    @GetMapping(path = "/", params = {"product", "start", "end"})
    public String searchByProductAndPeriod(@RequestParam("product") int productId,
                                           @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
                                           @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,
                                           @RequestParam(required=false, defaultValue = "10") int limit,
                                           @RequestParam(required=false, defaultValue = "default") String sort,
                                           @RequestParam(required=false, defaultValue = "default") String order,
                                           Model model) {
        Product product = this.productService.getProductById(productId);
        OrderByStatementUtil orderUtil = new OrderByStatementUtil(Transaction.getSortableColumns(), sort, order);
        AmountDto<Transaction> transactionAmountDto = this.transactionService
                .getTotalAmountByProductIdAndPeriod(productId, start, end, 0, limit, orderUtil);
        int totalPages = calculatePageCount(transactionAmountDto.getTotalOperations(), limit);

        model.addAttribute("transactions", transactionAmountDto.getOperations());
        model.addAttribute("product", product);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalAmount", transactionAmountDto.getTotalAmount());

        return "transaction/search/index";
    }

    @GetMapping(path = "/{page}", params = {"product"})
    public String searchByProduct(@PathVariable int page,
                                  @RequestParam("product") int productId,
                                  @RequestParam(required=false, defaultValue = "10") int limit,
                                  @RequestParam(required=false, defaultValue = "default") String sort,
                                  @RequestParam(required=false, defaultValue = "default") String order,
                                  Model model) {
        int offset = calculateOffset(page, limit);
        OrderByStatementUtil orderUtil = new OrderByStatementUtil(Transaction.getSortableColumns(), sort, order);
        AmountDto<Transaction> transactionAmountDto = this.transactionService.
                getTotalAmountByProductId(productId, offset, limit, orderUtil);
        int totalPages = calculatePageCount(transactionAmountDto.getTotalOperations(), limit);

        model.addAttribute("transactions", transactionAmountDto.getOperations());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalAmount", transactionAmountDto.getTotalAmount());

        return "transaction/search/index";
    }

    @GetMapping(path = "/{page}", params = {"start", "end"})
    public String searchByPeriod(@PathVariable int page,
                                 @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
                                 @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,
                                 @RequestParam(required=false, defaultValue = "10") int limit,
                                 @RequestParam(required=false, defaultValue = "default") String sort,
                                 @RequestParam(required=false, defaultValue = "default") String order,
                                 Model model) {
        int offset = calculateOffset(page, limit);
        OrderByStatementUtil orderUtil = new OrderByStatementUtil(Transaction.getSortableColumns(), sort, order);
        AmountDto<Transaction> transactionAmountDto = this.transactionService
                .getTotalAmountByPeriod(start, end, offset, limit, orderUtil);
        int totalPages = calculatePageCount(transactionAmountDto.getTotalOperations(), limit);

        model.addAttribute("transactions", transactionAmountDto.getOperations());
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalAmount", transactionAmountDto.getTotalAmount());

        return "transaction/search/index";
    }

    @GetMapping(path = "/{page}", params = {"product", "start", "end"})
    public String searchByProductAndPeriod(@PathVariable int page,
                                           @RequestParam("product") int productId,
                                           @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
                                           @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,
                                           @RequestParam(required=false, defaultValue = "10") int limit,
                                           @RequestParam(required=false, defaultValue = "default") String sort,
                                           @RequestParam(required=false, defaultValue = "default") String order,
                                           Model model) {
        int offset = calculateOffset(page, limit);
        OrderByStatementUtil orderUtil = new OrderByStatementUtil(Transaction.getSortableColumns(), sort, order);
        AmountDto<Transaction> transactionAmountDto = this.transactionService
                .getTotalAmountByProductIdAndPeriod(productId, start, end, offset, limit, orderUtil);
        int totalPages = calculatePageCount(transactionAmountDto.getTotalOperations(), limit);

        model.addAttribute("transactions", transactionAmountDto.getOperations());
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalAmount", transactionAmountDto.getTotalAmount());

        return "transaction/search/index";
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
