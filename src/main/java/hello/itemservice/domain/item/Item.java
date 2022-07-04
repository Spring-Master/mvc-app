package hello.itemservice.domain.item;

import hello.itemservice.domain.converter.ListToStringConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private Boolean open;
    @Convert(converter = ListToStringConverter.class)
    private List<String> regions = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private ItemType itemType;
    @Enumerated(EnumType.STRING)
    private DeliveryCode deliveryCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getPrice() == item.getPrice() && getQuantity() == item.getQuantity() && Objects.equals(getId(), item.getId()) && Objects.equals(getItemName(), item.getItemName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getItemName(), getPrice(), getQuantity());
    }
}
