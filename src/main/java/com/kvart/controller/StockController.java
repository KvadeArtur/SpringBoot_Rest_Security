package com.kvart.controller;

import com.kvart.model.Stock;
import com.kvart.repo.StockRepo;
import com.kvart.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Controller
@RequestMapping("/public")
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepo stockRepo;

    @PostMapping
    public @ResponseBody ResponseEntity  saveStock
            (@RequestParam(value = "comment", required=true) String comment,
             @RequestParam(value = "sizeOfTheCapital", required=true) Integer sizeOfTheCapital,
             @RequestParam(value = "edrpou", required=true) Integer edrpou,
             @RequestParam(value = "quantity", required=true) Integer quantity,
             @RequestParam(value = "nominalValue", required=true) Double nominalValue,
             @RequestParam(value = "duty", required=true) Double duty,
             @RequestParam(value = "date", required=true) Date date) {

        try {
            stockService.getSaveStock(comment, sizeOfTheCapital, edrpou, quantity, nominalValue, duty, date);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public @ResponseBody ResponseEntity  putStock
            (@PathVariable("id") Integer id,
             @RequestParam(value = "comment", required=false) String comment,
             @RequestParam(value = "sizeOfTheCapital", required=false) Integer sizeOfTheCapital,
             @RequestParam(value = "edrpou", required=false) Integer edrpou,
             @RequestParam(value = "quantity", required=false) Integer quantity,
             @RequestParam(value = "nominalValue", required=false) Double nominalValue,
             @RequestParam(value = "duty", required=false) Double duty,
             @RequestParam(value = "date", required=false) Date date) {

        try {
            Stock stock = new Stock(comment, sizeOfTheCapital, edrpou, quantity, nominalValue, duty, date);
            stockService.getPutStock(id, stock);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @GetMapping
//    public @ResponseBody CompletableFuture<ResponseEntity> filters
//            (@RequestParam(value = "page", required=true) Integer page,
//             @RequestParam(value = "nameFilter", required=true) String nameFilter) {
//
//        int value = (page * 10) - 9;
//
//        if (stockRepo.count() < value) {
//            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//        } else {
//            return stockService.getFilter(page, nameFilter).<ResponseEntity>thenApply(ResponseEntity::ok)
//                    .exceptionally(handleGetProductFailure);
//        }
//    }
//
//    private static Function<Throwable, ResponseEntity<? extends String>> handleGetProductFailure = throwable -> {
//        LOGGER.error("Failed to read records: {}", throwable);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    };

}
