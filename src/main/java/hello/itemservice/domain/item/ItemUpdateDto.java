package hello.itemservice.domain.item;

import lombok.Data;

@Data
public class ItemUpdateDto {

    private final String itemName;
    private final Integer price;
    private final Integer quantity;

}
