package com.kvart.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.regex.Pattern;

@Service
public class StockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    @Autowired
    private StockRepo stockRepo;

    @Async
    public CompletableFuture<String> getSaveStock(String comment, Integer sizeOfTheCapital, Integer edrpou,
                             Integer quantity, Double nominalValue, Double duty, Date date) {

        LOGGER.info("Save new stock");

        Stock stock = new Stock(comment, sizeOfTheCapital, edrpou, quantity, nominalValue, duty, date);

        stockRepo.save(stock);

        return CompletableFuture.completedFuture("Done");
    }

    @Async
    public CompletableFuture<String> getPutStock(Integer id, Stock stock) {

        LOGGER.info("Put stock by id");

        StringBuilder changes = new StringBuilder();

        changes.append(stockRepo.findById(id).toString());
        changes.append("\nChange to:\n ");

        if (stock.getComment() != null) {
            stockRepo.findById(id).get().setComment(stock.getComment());
            changes.append("New Comment: " + stock.getComment() + "; ");
        }

        if (stock.getSizeOfTheCapital() != null) {
            stockRepo.findById(id).get().setSizeOfTheCapital(stock.getSizeOfTheCapital());
            changes.append("New Size of the capital: " + stock.getSizeOfTheCapital() + "; ");
        }

        if (stock.getEdrpou() != null) {
            stockRepo.findById(id).get().setEdrpou(stock.getEdrpou());
            changes.append("New EDRPOU: " + stock.getEdrpou() + "; ");
        }

        if (stock.getQuantity() != null) {
            stockRepo.findById(id).get().setQuantity(stock.getQuantity());
            changes.append("New Quantity: " + stock.getQuantity() + "; ");
        }

        if (stock.getNominalValue() != null) {
            stockRepo.findById(id).get().setNominalValue(stock.getNominalValue());
            changes.append("New Nominal value: " + stock.getNominalValue() + "; ");
        }

        if (stock.getDuty() != null) {
            stockRepo.findById(id).get().setDuty(stock.getDuty());
            changes.append("New Duty: " + stock.getDuty() + "; ");
        }

        if (stock.getDate() != null) {
            stockRepo.findById(id).get().setDate(stock.getDate());
            changes.append("New Date: " + stock.getDate() + "; ");
        }

        stockRepo.findById(id).get().getChanges().add(changes.toString());

        return CompletableFuture.completedFuture("Stock is changed");
    }



    @Async
    public CompletableFuture<String> getter(Integer id) {

        LOGGER.info("Request to get by id");

        String stringResult = stockRepo.findById(id).get().toStringPublic();

        return CompletableFuture.completedFuture(stringResult);
    }

    @Async
    public CompletableFuture<String> getterByEdrpou(Integer edrpou) {

        LOGGER.info("Request to get by edrpou");

        List<Stock> listEdrpou = stockRepo.findByEdrpou(edrpou);

        return CompletableFuture.completedFuture(listEdrpou.toString());
    }
}
