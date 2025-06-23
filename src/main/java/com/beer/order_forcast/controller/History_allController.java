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
public class History_allController {
    private final WeatherHistoryService weatherHistoryService;
    private final SalesHistoryService salesHistoryService;
    private final WeatherService weatherService;
    private final ProductService productService;

    public History_allController(
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

    @GetMapping("/history_all")
    public String showHomePage(
            HttpSession session,
            Model model,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {

        // String name = (String) session.getAttribute("userName");
        Integer userId = (Integer) session.getAttribute("userId");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
         if (isAdmin == null) {
        return "redirect:/login";
        }   if (!isAdmin) {
        return "redirect:/home";
        }
        // model.addAttribute("userName", name);
        model.addAttribute("userId", userId);
        model.addAttribute("isAdmin", isAdmin);

        // by defaultで当日の日付代入
        if (year == null)
            year = LocalDate.now().getYear();
        if (month == null)
            month = LocalDate.now().getMonthValue();

        model.addAttribute("years", List.of(2024, 2025)); // ロジック追加：データベースにあるデータによって調整
        model.addAttribute("months", List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedMonth", month);

        // 真实参数取得，需修改数据库！！！！
        List<SalesHistory> salesHistoryList = salesHistoryService.findAll();
        // 或按年月筛选的结果

        // 需补充
        model.addAttribute("historyList", salesHistoryService.getSalesWeatherByMonth(year, month));

        Map<String, Integer> dowMap = new HashMap<>();
        dowMap.put("月", 0);
        dowMap.put("火", 1);
        dowMap.put("水", 2);
        dowMap.put("木", 3);
        dowMap.put("金", 4);
        dowMap.put("土", 5);
        // dowMap.put("日", 6);
        // 獲得該月第一天
        LocalDate firstDateOfMonth = LocalDate.of(year, month, 1);
        String firstDowStr = salesHistoryService.getJapaneseShortWeekday(firstDateOfMonth.getDayOfWeek());
        System.out.println("firstDowStr = " + firstDowStr); //
        // model.addAttribute("firstDayOfWeek", firstDowStr);

        int blankCount = switch (firstDateOfMonth.getDayOfWeek()) {
            case MONDAY -> 0;
            case TUESDAY -> 1;
            case WEDNESDAY -> 2;
            case THURSDAY -> 3;
            case FRIDAY -> 4;
            case SATURDAY -> 5;
            case SUNDAY -> 0;
            default -> -1; // SUNDAY -> 不显示
        };
        model.addAttribute("blankCount", blankCount);

        model.addAttribute("dowMap", dowMap);

        return "home";

    }
}
