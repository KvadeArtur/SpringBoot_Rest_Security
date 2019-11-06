package com.kvart.repo;

import com.kvart.model.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockRepo extends CrudRepository<Stock, Integer> {

    List<Stock> findByEdrpou (Integer edrpou);
}
