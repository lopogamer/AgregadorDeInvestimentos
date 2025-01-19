package com.luanr.agregadorinvestimentos.repository;

import com.luanr.agregadorinvestimentos.entity.AccountStock;
import com.luanr.agregadorinvestimentos.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
