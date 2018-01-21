package com.tgorgics.demo;

import com.tgorgics.demo.persistence.model.ExchangeRate;
import com.tgorgics.demo.persistence.repository.ExchangeRateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;

@SpringBootApplication
public class DemoExcerciseApplication extends SpringBootServletInitializer {

	@Bean
    CommandLineRunner dummy(ExchangeRateRepository exchangeRateRepository) {
	    return args -> {
            ExchangeRate er = new ExchangeRate();
            er.setCurrencyFrom("EUR");
            er.setCurrencyTo("HUF");
            er.setBuyingRate(new BigDecimal(289.15));
            er.setSellingRate(new BigDecimal(300.12));

            exchangeRateRepository.save(er);

            ExchangeRate er2 = new ExchangeRate();
            er2.setCurrencyFrom("USD");
            er2.setCurrencyTo("HUF");
            er2.setBuyingRate(new BigDecimal(259.78));
            er2.setSellingRate(new BigDecimal(270.56));

            exchangeRateRepository.save(er2);
        };
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoExcerciseApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoExcerciseApplication.class, args);
	}

}
