package com.excelian.fxserver.repository;

import com.excelian.fxserver.domain.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CurrencyRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

}
