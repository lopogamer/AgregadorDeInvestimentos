package com.luanr.agregadorinvestimentos.service;

import com.luanr.agregadorinvestimentos.client.alpha_vantagem.AlphaVantagemClient;
import com.luanr.agregadorinvestimentos.client.alpha_vantagem.dto.SearchStockResponseDto;
import com.luanr.agregadorinvestimentos.client.brapi_client.BrapiClient;
import com.luanr.agregadorinvestimentos.client.brapi_client.dto.DetaliedBrapiResponseDto;
import com.luanr.agregadorinvestimentos.client.brapi_client.dto.DetaliedStockDto;
import com.luanr.agregadorinvestimentos.dto.requests.CreateStockDto;
import com.luanr.agregadorinvestimentos.dto.responses.StockResponseDto;
import com.luanr.agregadorinvestimentos.entity.Stock;
import com.luanr.agregadorinvestimentos.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StockService {

    @Value("${BRAPI_TOKEN}")
    private String BRAPI_TOKEN;

    @Value("${ALPHA_VANTAGE_TOKEN}")
    private String ALPHA_VANTAGE_TOKEN;


    private final StockRepository stockRepository;
    private final BrapiClient brapiClient;
    private final AlphaVantagemClient alphaVantagemClient;

    public StockService(StockRepository stockRepository, BrapiClient brapiClient, AlphaVantagemClient alphaVantagemClient) {
        this.stockRepository = stockRepository;
        this.brapiClient = brapiClient;
        this.alphaVantagemClient = alphaVantagemClient;
    }

    public void createStock(CreateStockDto createStockDto) {
        var response = verifyExistence(createStockDto.stockId());
        if (response.results() != null && !response.results().isEmpty()) {
            DetaliedStockDto stock = response.results(). getFirst();
            var entity = new Stock(
                    createStockDto.stockId(),
                    stock.longName(),
                    stock.currency()
            );
            stockRepository.save(entity);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found.");
        }
    }

    private DetaliedBrapiResponseDto verifyExistence(String stockId) {
        return brapiClient.getDetaliedQuote(BRAPI_TOKEN, stockId);
    }

    public List<StockResponseDto> getAllStock() {
        return stockRepository.findAll().stream().map(s -> new StockResponseDto(
                s.getStockId(),
                s.getDescription(),
                s.getCurrency()
        )).toList();
    }

    public void deleteStock(String stockId) {
        var exist = stockRepository.existsById(stockId);
        if(exist) {
            stockRepository.deleteById(stockId);
        }
    }

    public SearchStockResponseDto searchStock(String keyword) {
        return alphaVantagemClient.searchStock(ALPHA_VANTAGE_TOKEN, keyword);
    }
}
