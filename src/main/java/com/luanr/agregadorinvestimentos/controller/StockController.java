package com.luanr.agregadorinvestimentos.controller;


import com.luanr.agregadorinvestimentos.dto.CreateStockDto;
import com.luanr.agregadorinvestimentos.dto.StockResponseDto;
import com.luanr.agregadorinvestimentos.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/stock")
public class StockController {
    StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDto createStockDto){
        stockService.createStock(createStockDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<StockResponseDto>> getAllStock(){
         var stocks = stockService.getAllStock();
         return ResponseEntity.ok(stocks);
    }
    @DeleteMapping("/{stockId}")
    public ResponseEntity<Void> deleteStock(@PathVariable String stockId){
        stockService.deleteStock(stockId);
        return ResponseEntity.noContent().build();
    }
}
