package com.softnet.sta.repository;

import com.softnet.sta.database.entity.StaTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StaTransactionRepository extends JpaRepository<StaTransaction, Long>, JpaSpecificationExecutor<StaTransaction> {
}
