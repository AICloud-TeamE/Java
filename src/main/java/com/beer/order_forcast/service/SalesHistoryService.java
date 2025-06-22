package com.beer.order_forcast.service;

import com.beer.order_forcast.repository.*;
import com.beer.order_forcast.model.*;

import org.springframework.stereotype.Service;
import com.beer.order_forcast.repository.*;

import java.time.DayOfWeek;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class SalesHistoryService {
    private final SalesHistoryRepository repository;
    private final ProductRepository productRepository;
    private final WeatherHistoryRepository weatherHistoryRepository;
    private final WeatherRepository weatherRepository;
    private final AccountRepository accountRepository;

    public SalesHistoryService(
            SalesHistoryRepository repository,
            ProductRepository productRepository,
            WeatherHistoryRepository weatherHistoryRepository,
            AccountRepository accountRepository,
            WeatherRepository weatherRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.weatherHistoryRepository = weatherHistoryRepository;
        this.weatherRepository = weatherRepository;
        this.accountRepository = accountRepository;
    }

    public List<SalesHistory> findAll() {
        return repository.findAll();
    }

    public List<SalesHistory> getByYearAndMonth(int year, int month) {
        return repository.findByYearAndMonth(year, month);
    }

    // 单日historydto
    public SalesWeatherHistoryDTO buildOneDaySalesWeatherDTO(LocalDate date) {
        WeatherHistory wh = weatherHistoryRepository.findByDate(date);
        if (wh == null) {
            System.out.println("⚠ 該当日のWeatherHistoryが見つかりません: " + date);

        }

        List<SalesHistory> salesHistoryList = repository.findByDate(date);

        List<ProductSalesDTO> productSales = new ArrayList<>();
        int totalSales = 0;
        SalesWeatherHistoryDTO dto = new SalesWeatherHistoryDTO();

        for (SalesHistory salesHistory : salesHistoryList) {
            ProductSalesDTO oneProductSales = new ProductSalesDTO();
            oneProductSales.setUnit(salesHistory.getSales_count());
            oneProductSales.setRevenue(salesHistory.getSales_revenue());
            oneProductSales.setPrice();
            oneProductSales.setName(productRepository.findById(salesHistory.getProduct_id()).orElse(null).getName());
            totalSales += salesHistory.getSales_revenue();
            productSales.add(oneProductSales);

            Optional<Account> accOpt = accountRepository.findById(salesHistory.getUpdate_id());
            String editorName;

            if (accOpt.isPresent()) {
                editorName = accOpt.get().getName();
            } else {
                editorName = "不明";
            }

            dto.setEditor(editorName);

            Timestamp timestamp = salesHistory.getUpdated_at();
            LocalDateTime ldt = timestamp.toLocalDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            String formattedTime = ldt.format(formatter);
            dto.setEditHistory(formattedTime);

            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日
            // HH:mm");
            // String formattedUpdateHistory =
            // salesHistory.getUpdated_at().format(formatter);

            // dto.setEditHistory(formattedUpdateHistory);
        }

        dto.setDate(date);

        if (wh != null) {
            String weather = weatherRepository.findById(wh.getWeather_id()).orElse(null).getWeather();
            dto.setWeather(weather);
            dto.setHighestTemperature(wh.getHighest_temperature());
            dto.setLowestTemperature(wh.getLowest_temperature());
        } else {
            dto.setWeather("データなし");
            dto.setHighestTemperature(0f);
            dto.setLowestTemperature(0f);
        }

        dto.setTotalSales(totalSales);
        dto.setProductSales(productSales);

        return dto;
    }

    // history day編集画面のeditを保存
    public String editHistoryOfDay(String name, Integer unit, Integer price, LocalDate date) {
        Integer productId = productRepository.findByName(name).getId();
        SalesHistory sh = repository.findByDateAndProductId(date, productId);
        if (sh == null) {
            return "該当する販売履歴が見つかりませんでした。";
        }

        sh.setSales_count(unit);
        sh.setSales_revenue(unit * price);
        try {
            repository.save(sh);
            return "販売実績更新成功！";
        } catch (Exception e) {
            return "エラー：" + e.getMessage();
        }
    }

    // 找这周dto
    public List<SalesWeatherHistoryDTO> getHistoryOfWeek(LocalDate originDate, Integer offset) {
        LocalDate mondayOfWeek = originDate.with(DayOfWeek.MONDAY);
        LocalDate startDate = mondayOfWeek.plusWeeks(offset);
        LocalDate endDate = startDate.plusDays(6);

        List<SalesWeatherHistoryDTO> weekDTOList = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {

            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                continue;
            }

            SalesWeatherHistoryDTO dto = buildOneDaySalesWeatherDTO(date);
            weekDTOList.add(dto); // 不判断null，build方法内自己保证不为null
        }
        return weekDTOList;

    }

    // 下面是一个超~~~~~~~~~~~~~~~~~~~~~级长的dto构造
    // 用于すべて実績页面
    public List<SalesWeatherHistoryDTO> getSalesWeatherByMonth(int year, int month) {

        // 最终返回用的DTO列表
        List<SalesWeatherHistoryDTO> dtoList = new ArrayList<>();

        // ① 先从history表取出指定年月的所有记录
        List<WeatherHistory> weatherHistories = weatherHistoryRepository.findByYearAndMonth(year, month);
        List<SalesHistory> salesHistories = repository.findByYearAndMonth(year, month);

        // ③ 遍历 weatherHistories，每天构建一条 DTO
        for (WeatherHistory wh : weatherHistories) {
            LocalDate date = wh.getDate();
            Float maxTemp = wh.getHighest_temperature();
            Float minTemp = wh.getLowest_temperature();

            // 查天气名称
            String weatherName = "";
            Optional<Weather> optionalWeather = weatherRepository.findById(wh.getWeather_id());
            if (optionalWeather.isPresent()) {
                weatherName = optionalWeather.get().getWeather();
            }

            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String weekdayName = getJapaneseShortWeekday(dayOfWeek);
            int weekdayNumber = dayOfWeek.getValue();

            if (dayOfWeek == DayOfWeek.SUNDAY) {
                continue;
            }

            // 累加该日所有销售额
            int totalSales = 0;
            for (SalesHistory sh : salesHistories) {
                if (sh.getDate().equals(date)) {
                    totalSales += sh.getSales_revenue();
                }
            }

            // DTO拼装
            SalesWeatherHistoryDTO dto = new SalesWeatherHistoryDTO();
            dto.setDate(date);
            dto.setWeather(weatherName);
            dto.setHighestTemperature(maxTemp);
            dto.setLowestTemperature(minTemp);
            dto.setTotalSales(totalSales);

            dtoList.add(dto);
        }
        // gpt加的超级大屎山
        // ====== 增加内容 START ======
        Set<LocalDate> existingDates = new HashSet<>();
        for (SalesWeatherHistoryDTO dto : dtoList) {
            existingDates.add(dto.getDate());
        }

        int lengthOfMonth = YearMonth.of(year, month).lengthOfMonth();
        for (int day = 1; day <= lengthOfMonth; day++) {
            LocalDate date = LocalDate.of(year, month, day);
            if (existingDates.contains(date))
                continue;

            // DayOfWeek dow = date.getDayOfWeek();
            // if (dow == DayOfWeek.SUNDAY)
            // continue;

            // String weekdayName = getJapaneseShortWeekday(dow);
            SalesWeatherHistoryDTO blankDay = new SalesWeatherHistoryDTO();
            blankDay.setDate(date);
            blankDay.setWeather("");
            blankDay.setHighestTemperature(null);
            blankDay.setLowestTemperature(null);
            blankDay.setTotalSales(null);
            dtoList.add(blankDay);
        }

        // 確保日付順に並ぶ（可选推荐）
        dtoList.sort(Comparator.comparing(SalesWeatherHistoryDTO::getDate));
        // ====== 增加内容 END ======

        //

        return dtoList;

    }

    // 👆上に呼び出されます
    public String getJapaneseShortWeekday(DayOfWeek dow) {
        switch (dow) {
            case MONDAY:
                return "月";
            case TUESDAY:
                return "火";
            case WEDNESDAY:
                return "水";
            case THURSDAY:
                return "木";
            case FRIDAY:
                return "金";
            case SATURDAY:
                return "土";
            case SUNDAY:
                return "日";
            default:
                return "";
        }

    }

    public void save(SalesHistory salesHistory) {
        repository.save(salesHistory);
    }

}
