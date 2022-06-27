package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    ItemRepository itemRepository;

    @Test
    void save() {
        // given
        Item item = createItem("The Book", 10000, 12);
        itemRepository.save(item);
        em.flush();

        // when
        Item findItem = em.find(Item.class, item.getId());

        // then
        assertThat(findItem).isEqualTo(item);
    }

    @Test
    void findById() {
        // given
        Item item1 = createItem("The Book", 10000, 12);
        Item item2 = createItem("The Book", 12000, 15);
        itemRepository.save(item1);
        itemRepository.save(item2);
        em.flush();

        // when
        Item findItem1 = itemRepository.findById(item1.getId());
        Item findItem2 = itemRepository.findById(item2.getId());

        // then
        assertThat(findItem1).isEqualTo(item1);
        assertThat(findItem2).isEqualTo(item2);
    }

    @Test
    void update() {
        // given
        Item item = createItem("The Book", 10000, 12);
        itemRepository.save(item);

        ItemUpdateDto itemParam = new ItemUpdateDto("The New Book", 12000, 15);
        itemRepository.update(item.getId(), itemParam);
        em.flush();

        // when
        Item findItem = itemRepository.findById(item.getId());

        // then
        assertThat(findItem).isEqualTo(item);
    }

    private Item createItem(String itemName, int price, int quantity ) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        return item;
    }
}