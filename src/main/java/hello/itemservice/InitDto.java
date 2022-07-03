package hello.itemservice;


import com.github.javafaker.Faker;
import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
        private List<String> regions = Arrays.asList("SEOUL", "BUSAN", "DAEGU");

        public void dbInit() {
            Faker faker = new Faker();
            Random random = new Random();

            for (int i = 0; i < 10; i++) {
                Collections.shuffle(regions);
                Item item = new Item();
                item.setItemName(faker.book().title());
                item.setPrice(faker.number().numberBetween(12, 15) * 1000);
                item.setQuantity(faker.number().numberBetween(1, 10));
                item.setOpen(true);
                item.setRegions(regions.subList(0, random.nextInt(regions.size())));
                item.setItemType(ItemType.BOOK);
                item.setDeliveryCode(DeliveryCode.getRandom());
                em.persist(item);
            }
        }
    }
}
