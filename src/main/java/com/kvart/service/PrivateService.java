package com.kvart.service;

import com.kvart.model.Stock;
import com.kvart.repo.StockRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PrivateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateService.class);

    @Autowired
    private StockRepo stockRepo;

    @Async
    public CompletableFuture<String> getter(Integer id) {

        LOGGER.info("Request to get by id");

        String stringResult = stockRepo.findById(id).get().toString();

        return CompletableFuture.completedFuture(stringResult);
    }

    @Async
    public CompletableFuture<String> getterByEdrpou(Integer edrpou) {

        LOGGER.info("Request to get by edrpou");

        List<Stock> listEdrpou = stockRepo.findByEdrpou(edrpou);

        return CompletableFuture.completedFuture(listEdrpou.toString());
    }

    @Async
    public CompletableFuture<String> getterSort(String sort, Integer page) {

        LOGGER.info("Request to get with sort");

        int to = page * 10;
        int from = to - 9;

        List<Stock> stockList = (List<Stock>) stockRepo.findAll();

        if (to > stockList.size()) {
            to = stockList.size();
        }

        stockList = stockList.subList(from, to);

        if (sort.equals("sizeOfTheCapital")) {

            Collections.sort(stockList, new Comparator<Stock>() {
                @Override
                public int compare(Stock o1, Stock o2) {
                    return Integer.compare(o1.getSizeOfTheCapital(), o2.getSizeOfTheCapital());
                }
            });
        } else if (sort.equals("edrpou")){

            Collections.sort(stockList, new Comparator<Stock>() {
                @Override
                public int compare(Stock o1, Stock o2) {
                    return Integer.compare(o1.getEdrpou(), o2.getEdrpou());
                }
            });
        } else if (sort.equals("nominalValue")) {

            Collections.sort(stockList, new Comparator<Stock>() {
                @Override
                public int compare(Stock o1, Stock o2) {
                    return Double.compare(o1.getNominalValue(), o2.getNominalValue());
                }
            });
        } else if (sort.equals("date")) {

            Collections.sort(stockList, new Comparator<Stock>() {
                @Override
                public int compare(Stock o1, Stock o2) {
                    return o1.getDate().toString().compareTo(o2.getDate().toString());
                }
            });
        }

        return CompletableFuture.completedFuture(stockList.toString());
    }
}
