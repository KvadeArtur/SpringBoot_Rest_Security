package com.kvart.service;

import com.kvart.model.Stock;
import com.kvart.repo.StockRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        Pageable sorted;
        Page<Stock> stockList = null;

        if (sort.equals("sizeOfTheCapital")) {

            sorted = PageRequest.of(page - 1, 10, Sort.by("sizeOfTheCapital"));
            stockList = stockRepo.findAll(sorted);

        } else if (sort.equals("edrpou")){

            sorted = PageRequest.of(page - 1, 10, Sort.by("edrpou"));
            stockList = stockRepo.findAll(sorted);

        } else if (sort.equals("nominalValue")) {

            sorted = PageRequest.of(page - 1, 10, Sort.by("nominalValue"));
            stockList = stockRepo.findAll(sorted);

        } else if (sort.equals("date")) {

            sorted = PageRequest.of(page - 1, 10, Sort.by("date"));
            stockList = stockRepo.findAll(sorted);

        }

        return CompletableFuture.completedFuture(stockList.toString());
    }
}
