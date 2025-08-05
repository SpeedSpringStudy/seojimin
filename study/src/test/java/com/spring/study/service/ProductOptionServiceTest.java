package com.spring.study.service;

import static org.junit.jupiter.api.Assertions.*;

import com.spring.study.common.exception.custonException.BusinessException;
import com.spring.study.domain.entity.ProductOption;
import com.spring.study.repository.ProductOptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootTest
@EnableRetry
class ProductOptionServiceTest {

    @Autowired
    private ProductOptionRepository repository;

    @Autowired
    private ProductOptionService service;

    @Autowired
    private OptionService optionService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductOptionRunner runner;

    @Test
    void concurrentDecreaseQuantityTest() throws InterruptedException {
        // 1. 초기 재고 10으로 세팅
        ProductOption po = ProductOption.builder()
                .option(optionService.getOption(1L))
                .product(productService.getProduct(1L))
                .quantity(10)
                .build();
        repository.save(po);

        Long id = po.getId();

        // 2. 동시에 5개 스레드가 각각 3개씩 감소 시도 (총 15개 감소 시도)
        int threadCount = 5;
        int decreasePerThread = 3;

        Runnable task = () -> {
            try {
                runner.run(id, decreasePerThread);
                System.out.println(Thread.currentThread().getName() + " 감소 성공");
            } catch (BusinessException e) {
                System.out.println(Thread.currentThread().getName() + " 재고 부족 예외: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " 기타 실패: " + e.getMessage());
            }
        };

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(task, "스레드-" + i);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // 3. 최종 재고 출력
        ProductOption updated = repository.findById(id).orElseThrow();
        System.out.println("최종 재고: " + updated.getQuantity());
    }
}