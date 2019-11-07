package com.kvart.controller;

import com.kvart.model.Stock;
import com.kvart.repo.StockRepo;
import com.kvart.service.PublicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/public")
public class PublicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private PublicService publicService;

    @Autowired
    private StockRepo stockRepo;

    @PostMapping
    public @ResponseBody CompletableFuture<ResponseEntity>  saveStock
            (@RequestParam(value = "comment", required=true) String comment,
             @RequestParam(value = "sizeOfTheCapital", required=true) Integer sizeOfTheCapital,
             @RequestParam(value = "edrpou", required=true) Integer edrpou,
             @RequestParam(value = "quantity", required=true) Integer quantity,
             @RequestParam(value = "nominalValue", required=true) Double nominalValue,
             @RequestParam(value = "duty", required=true) Double duty,
             @RequestParam(value = "date", required=true) Date date) {

        try {
            return publicService.getSaveStock(comment, sizeOfTheCapital, edrpou, quantity, nominalValue, duty, date)
                    .thenApply(ResponseEntity::ok);
        } catch(Exception ex) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @PutMapping (value = "/{id}")
    public @ResponseBody CompletableFuture<ResponseEntity>  putStock
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
            return publicService.getPutStock(id, stock).thenApply(ResponseEntity::ok);
        } catch(Exception ex) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @GetMapping (value = "/{id}")
    public @ResponseBody CompletableFuture<ResponseEntity> get
            (@PathVariable("id") Integer id) {

        try {
            return publicService.getter(id).thenApply(ResponseEntity::ok);
        } catch(Exception ex) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @GetMapping (value = "/edrpou/{edrpou}")
    public @ResponseBody CompletableFuture<ResponseEntity> getByEdrpou
            (@PathVariable("edrpou") Integer edrpou) {

        try {
            return publicService.getterByEdrpou(edrpou).thenApply(ResponseEntity::ok);
        } catch(Exception ex) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @GetMapping
    public @ResponseBody CompletableFuture<ResponseEntity> getSort
            (@RequestParam(value = "sort", required=true) String sort,
             @RequestParam(value = "page", required=true) Integer page) {

        int value = (page * 10) - 9;

        if (stockRepo.count() < value) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } else {
            try {
                return publicService.getterSort(sort, page).thenApply(ResponseEntity::ok);
            } catch(Exception ex) {
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            }
        }
    }

}
