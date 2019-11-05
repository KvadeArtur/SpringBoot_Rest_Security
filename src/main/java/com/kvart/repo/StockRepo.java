package com.kvart.repo;

import com.kvart.model.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepo extends CrudRepository<Stock, Integer> {

}
