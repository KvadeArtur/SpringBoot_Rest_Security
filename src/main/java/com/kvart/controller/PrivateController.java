package com.kvart.controller;

import com.kvart.repo.StockRepo;
import com.kvart.service.PrivateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/private")
@PreAuthorize("hasAuthority('ADMIN')")
public class PrivateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateController.class);

    @Autowired
    private PrivateService privateService;

    @Autowired
    private StockRepo stockRepo;


    @GetMapping (value = "/{id}")
    public @ResponseBody CompletableFuture<ResponseEntity> get
            (@PathVariable("id") Integer id) {

        try {
            return privateService.getter(id).thenApply(ResponseEntity::ok);
        } catch(Exception ex) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @GetMapping (value = "/edrpou/{edrpou}")
    public @ResponseBody CompletableFuture<ResponseEntity> getByEdrpou
            (@PathVariable("edrpou") Integer edrpou) {

        try {
            return privateService.getterByEdrpou(edrpou).thenApply(ResponseEntity::ok);
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
                return privateService.getterSort(sort, page).thenApply(ResponseEntity::ok);
            } catch(Exception ex) {
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            }
        }
    }

}
