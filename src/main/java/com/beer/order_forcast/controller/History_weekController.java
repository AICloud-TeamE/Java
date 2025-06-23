package com.beer.order_forcast.controller;

//other created packages
import com.beer.order_forcast.model.*;
import com.beer.order_forcast.repository.*;
import com.beer.order_forcast.service.*;

//function package
import java.time.*;
import java.util.*;

//servlet package
import jakarta.servlet.http.*;

//springboot package
// 控制器类注解
import org.springframework.stereotype.Controller;

// 向 HTML 页面传值
import org.springframework.ui.Model;

// Web 请求映射和参数绑定相关
import org.springframework.web.bind.annotation.*;

//redirect
import org.springframework.web.servlet.mvc.support.*;
import org.thymeleaf.expression.Lists;

@Controller
public class History_weekController {

    private final WeatherHistoryService weatherHistoryService;
    private final SalesHistoryService salesHistoryService;
    private final WeatherService weatherService;
    private final ProductService productService;

    public History_weekController(
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

    @GetMapping("/history_week")
    public String showHistoryWeekPage(
            HttpSession session,
            Model model,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "offset", defaultValue = "0") int offset) {

        String name = (String) session.getAttribute("userName");
        Integer userId = (Integer) session.getAttribute("userId");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null) {
        return "redirect:/login";
        }if (!isAdmin) {
        return "redirect:/home";
        }
        model.addAttribute("userName", name);
        model.addAttribute("userId", userId);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("offset", offset); 

        model.addAttribute("historyList",salesHistoryService.getHistoryOfWeek(LocalDate.now(), offset));



        // mock传参，后面要用正式的！！
        // List<Map<String, Object>> historyList = new ArrayList<>();

        // historyList.add(Map.of(
        //         "day", "月",
        //         "date", "6月10日",
        //         "weather", "sunny",
        //         "highestTemperature", 31.5,
        //         "lowestTemperature", 25.8,
        //         "totalSales", 45000));
        // historyList.add(Map.of(
        //         "day", "火",
        //         "date", "6月11日",
        //         "weather", "cloudy",
        //         "highestTemperature", 28.0,
        //         "lowestTemperature", 23.5,
        //         "totalSales", 38000));

        // model.addAttribute("historyList", historyList);

        // //真实参数取得，需修改数据库！！！！
        // List<SalesHistory> salesHistoryList = salesHistoryService.findAll();
        // // 或按年月筛选的结果
        // //需补充
        // model.addAttribute("historyList", salesHistoryList);

        return "history_week";
    }

}
