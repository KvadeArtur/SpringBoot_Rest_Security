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
    public void getSaveStock(String comment, int sizeOfTheCapital, int edrpou,
                             int quantity, double nominalValue, double duty, Date date) {

        LOGGER.info("Save new stock");

        Stock stock = new Stock(comment, sizeOfTheCapital, edrpou, quantity, nominalValue, duty, date);

        stockRepo.save(stock);
    }

    @Async
    public void getPutStock(Integer id, Stock stock) {

        LOGGER.info("Put stock by id");

        StringBuilder changes = new StringBuilder();

        changes.append(stockRepo.findById(id).toString());
        changes.append("\nChange to:\n ");
        changes.append(stock.toString());

        stockRepo.findById(id).get().setComment(stock.getComment());
        stockRepo.findById(id).get().setSizeOfTheCapital(stock.getSizeOfTheCapital());
        stockRepo.findById(id).get().setEdrpou(stock.getEdrpou());
        stockRepo.findById(id).get().setQuantity(stock.getQuantity());
        stockRepo.findById(id).get().setNominalValue(stock.getNominalValue());
        stockRepo.findById(id).get().setDuty(stock.getDuty());
        stockRepo.findById(id).get().setDate(stock.getDate());
        stockRepo.findById(id).get().getChanges().add(changes.toString());

    }



//    @Async
//    public CompletableFuture<String> getFilter(int page, String nameFilter) {
//
//        LOGGER.info("Request to get a list product with filter");
//
//        int to = page * 10;
//        int from = to - 9;
//
//        List<Stock> productList = (List<Stock>) stockRepo.findAll();
//
//        if (to > productList.size()) {
//            to = productList.size();
//        }
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        StringBuilder result = new StringBuilder();
//
//        Pattern pattern = Pattern.compile(nameFilter, Pattern.CASE_INSENSITIVE);
//
//        for (Stock product: productList.subList(from, to)) {
//            if (!pattern.matcher(product.getName()).matches()) {
//                String json = gson.toJson(product);
//                result.append(json);
//            }
//        }
//
//        final String stringResult = result.toString();
//        return CompletableFuture.completedFuture(stringResult);
//    }
}
