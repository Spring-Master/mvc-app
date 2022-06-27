package hello.itemservice;


import com.github.javafaker.Faker;
import hello.itemservice.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDto {

    private final InitService initService;

    @PostConstruct
    void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            Faker faker = new Faker();

            for (int i = 0; i < 10; i++) {
                Item item = new Item();
                item.setItemName(faker.book().title());
                item.setPrice(faker.number().numberBetween(12, 15) * 1000);
                item.setQuantity(faker.number().numberBetween(1, 10));
                em.persist(item);
            }
        }
    }
}
