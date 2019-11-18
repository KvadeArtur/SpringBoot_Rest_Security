package com.kvart.service;

import com.kvart.DTO.StockDTO;
import com.kvart.model.Stock;
import com.kvart.repo.StockRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class PublicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublicService.class);

    @Autowired
    private StockRepo stockRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Async
    public CompletableFuture<String> getSaveStock(String comment, Integer sizeOfTheCapital, Integer edrpou,
                             Integer quantity, Double nominalValue, Double duty) {

        LOGGER.info("Save new stock");

        Stock stock = new Stock(comment, sizeOfTheCapital, edrpou, quantity, nominalValue, duty);

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

        String stringResult = convertToDto(stockRepo.findById(id).get()).toString();

        return CompletableFuture.completedFuture(stringResult);
    }

    @Async
    public CompletableFuture<String> getterByEdrpou(Integer edrpou) {

        LOGGER.info("Request to get by edrpou");

        List<Stock> listEdrpou = stockRepo.findByEdrpou(edrpou);

        listEdrpou.stream()
                .map(stock -> convertToDto(stock))
                .collect(Collectors.toList());


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

        stockList.stream()
                .map(stock -> convertToDto(stock))
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(stockList.toString());
    }

    private StockDTO convertToDto(Stock stock) {
        StockDTO stockDTO = modelMapper.map(stock, StockDTO.class);
        return stockDTO;
    }
}
