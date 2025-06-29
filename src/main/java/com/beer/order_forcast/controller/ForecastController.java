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
public class ForecastController {

    // 需要调用table全部要调用对应service，所以干脆默认调用全部service好了！
    private final WeatherHistoryService weatherHistoryService;
    private final SalesHistoryService salesHistoryService;
    private final WeatherService weatherService;
    private final ProductService productService;

    public ForecastController(
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

    @GetMapping("/forecast")
    public String showForecastPage(HttpSession session,
            Model model) {

        String name = (String) session.getAttribute("userName");
        Integer userId = (Integer) session.getAttribute("userId");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        model.addAttribute("userName", name);
        model.addAttribute("userId", userId);
        model.addAttribute("isAdmin", isAdmin);

        //本物データ取得（PYTHON側FUNCTION呼び出しなど）
        //

        //

        // mock数据传给前端测试迁移和传参，非最终数据！！！
        // 汇总区域：Map<String, Integer>
        Map<String, Integer> predictedDemandMap = new HashMap<>();
        predictedDemandMap.put("ビールA", 300);
        predictedDemandMap.put("ビールB", 250);
        model.addAttribute("predictedDemandMap", predictedDemandMap);

        // 每日预测（不使用 Forecast 类）
        List<Map<String, Object>> forecastList = new ArrayList<>();

        Map<String, Object> day1 = new HashMap<>();
        day1.put("date", LocalDate.of(2025, 6, 19));
        day1.put("weather", "晴れ");
        day1.put("temperatureRange", "25〜30℃");
        day1.put("predictedDemandMap", Map.of("ビールA", 50, "ビールB", 40));
        forecastList.add(day1);

        Map<String, Object> day2 = new HashMap<>();
        day2.put("date", LocalDate.of(2025, 6, 20));
        day2.put("weather", "くもり");
        day2.put("temperatureRange", "23〜28℃");
        day2.put("predictedDemandMap", Map.of("ビールA", 55, "ビールB", 45));
        forecastList.add(day2);

        model.addAttribute("sevenDayForecastList", forecastList);

        return "forecast";
    }











    
    // 之后再说分界线！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
    // @GetMapping("/forecast")
    // public String getForecast(HttpSession session) {
    //     String email = session.getAttribute("email").toString();
    //     LocalDate date = LocalDate.now();
    //     // 向python请求
    //     // List<Integer> forecast_list = xxxx(date);
    //     List<Integer> forecast_list = new ArrayList<>();
    //     // Integer sales_sum = forecast_service.forecast_sum(forecast_list);
    //     return "forecast";
    // }
    // 之后再说分界线！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
}
