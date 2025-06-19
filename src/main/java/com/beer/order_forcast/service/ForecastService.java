package com.beer.order_forcast.service;

import org.springframework.stereotype.Service;
import com.beer.order_forcast.repository.*;
import java.util.*;

@Service
public class ForecastService {
    public List<List<Integer>> getPredictDemand(){
        //ここ最終的にはpython function呼び出して　何とか予測のデータを取得
        List<List<Integer>> listsquare = new ArrayList<>();
        return listsquare;
    }

    
}
