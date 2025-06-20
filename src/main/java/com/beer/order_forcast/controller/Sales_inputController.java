package com.beer.order_forcast.controller;

import java.sql.Timestamp;
//function package
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

//springboot package
// 控制器类注解
import org.springframework.stereotype.Controller;
// 向 HTML 页面传值
import org.springframework.ui.Model;
// Web 请求映射和参数绑定相关
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//redirect
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//other created packages
import com.beer.order_forcast.model.SalesHistory;
import com.beer.order_forcast.service.ProductService;
import com.beer.order_forcast.service.SalesHistoryService;
import com.beer.order_forcast.service.WeatherHistoryService;
import com.beer.order_forcast.service.WeatherService;

//servlet package
import jakarta.servlet.http.HttpSession;

@Controller
public class Sales_inputController {
    private final WeatherHistoryService weatherHistoryService;
    private final SalesHistoryService salesHistoryService;
    private final WeatherService weatherService;
    private final ProductService productService;

    public Sales_inputController(
            WeatherHistoryService weatherHistoryService,
            SalesHistoryService salesHistoryService,
            WeatherService weatherService,
            ProductService productService) {
        this.weatherHistoryService = weatherHistoryService;
        this.salesHistoryService = salesHistoryService;
        this.weatherService = weatherService;
        this.productService = productService;

        System.out.println("controller導入検査");
    }

    @GetMapping("/sales_input")
    public String showSalesInputPage(
            HttpSession session,
            Model model,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {

        String name = (String) session.getAttribute("userName");
        Integer userId = (Integer) session.getAttribute("userId");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        model.addAttribute("userName", name);
        model.addAttribute("userId", userId);
        model.addAttribute("isAdmin", isAdmin);

        model.addAttribute("beerList", productService.findAllActiveProducts());
        return "sales_input";
    }

    @PostMapping("/sales_input")
    public String handleSalesInput(
            // @RequestParam Map<String, String> salesMap,
            @RequestParam Map<String, String> form,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        // Map<String, Integer> productMap = productService.getValidProductMap(); // 商品名
        // -> 単価

        // for (String productName : productMap.keySet()) {
        // String quantityStr = salesMap.get(productName);
        // if (quantityStr == null || quantityStr.isBlank()) {
        // // error message需要前端加显示
        // redirectAttributes.addFlashAttribute("error", "すべての商品に販売数を入力してください。");
        // return "redirect:/sales_input";
        // }
        // }
        

        LocalDate today = LocalDate.now();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        for (Map.Entry<String, String> entry : form.entrySet()) {
            String key = entry.getKey(); // e.g., input1, input2
            if (key.startsWith("input")) {
                try {
                    Integer productId = Integer.parseInt(key.substring(5)); // 截掉 "input"
                    Integer count = Integer.parseInt(entry.getValue());
                    if (count <= 0)
                        continue;

                    Integer price = productService.findPriceByProductId(productId);
                    Integer revenue = count * price;

                    SalesHistory sh = new SalesHistory();
                    sh.setProduct_id(productId);
                    sh.setCreator_id(userId);
                    sh.setUpdate_id(userId);
                    sh.setDate(today);
                    sh.setSales_count(count);
                    sh.setSales_revenue(revenue);
                    sh.setCreated_at(now);
                    sh.setUpdated_at(now);
                    sh.setIs_deleted(false);

                    salesHistoryService.save(sh);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 成功时跳转到确认页（后续可以在confirm页面中显示“登録成功”）
        return "redirect:/confirm";

    }

    @GetMapping("/confirm")
    public String showConfirmPage(
            HttpSession session,
            Model model,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {

        String name = (String) session.getAttribute("userName");
        Integer userId = (Integer) session.getAttribute("userId");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        model.addAttribute("userName", name);
        model.addAttribute("userId", userId);
        model.addAttribute("is_admin", isAdmin);

        return "registration_result";
        // return "redirect:/history_all";

    }

}
