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
public class History_dayController {
    private final WeatherHistoryService weatherHistoryService;
    private final SalesHistoryService salesHistoryService;
    private final WeatherService weatherService;
    private final ProductService productService;

    public History_dayController(
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

    @GetMapping("/history_day")
    public String showHistoryWeekPage(
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


        
        return "aaa";
    }

}
