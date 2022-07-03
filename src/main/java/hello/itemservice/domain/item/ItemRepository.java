package hello.itemservice.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemRepository {

    private final EntityManager em;

    @Transactional
    public Long save(Item item) {
        em.persist(item);
        return item.getId();
    }

    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item as i", Item.class)
                .getResultList();
    }

    @Transactional
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item item = findById(itemId);
        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity());
        item.setOpen(updateParam.getOpen());
        item.setRegions(updateParam.getRegions());
    }
}
