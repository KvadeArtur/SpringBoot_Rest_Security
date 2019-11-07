package com.kvart.service;

import com.kvart.model.Stock;
import com.kvart.repo.StockRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
}
