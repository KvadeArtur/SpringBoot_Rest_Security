package com.kvart.service;

import com.kvart.model.Stock;
import com.kvart.repo.StockRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class PublicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublicService.class);

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

        List<String> publicEdrpou = new ArrayList<>();

        for (Stock stock: listEdrpou) {
            publicEdrpou.add(stock.toStringPublic());
        }

        return CompletableFuture.completedFuture(publicEdrpou.toString());
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

        List<String> publicSort = new ArrayList<>();

        for (Stock stock: stockList) {
            publicSort.add(stock.toStringPublic());
        }

        return CompletableFuture.completedFuture(publicSort.toString());
    }
}
