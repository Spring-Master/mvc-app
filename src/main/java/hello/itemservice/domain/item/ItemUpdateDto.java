package hello.itemservice.domain.item;

import lombok.Data;

import java.util.List;

@Data
public class ItemUpdateDto {

    private final String itemName;
    private final Integer price;
    private final Integer quantity;
    private final Boolean open;
    private final List<String> regions;

}
