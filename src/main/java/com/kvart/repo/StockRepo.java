package com.kvart.repo;

import com.kvart.model.Stock;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StockRepo extends PagingAndSortingRepository<Stock, Integer> {

    List<Stock> findByEdrpou (Integer edrpou);
}
