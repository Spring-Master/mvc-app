package hello.itemservice.domain.item.form;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.ItemType;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class ItemForm {

//    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    @NotBlank
    private String itemName;
//    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @NotNull
    @Range(min = 1000, max = 1000_000)
    private Integer price;
    @NotNull
    @Range(min = 0, max = 9999)
    private Integer quantity;
    private Boolean open;
    private List<String> regions = new ArrayList<>();
    private ItemType itemType;
    private DeliveryCode deliveryCode;

    public void validate(Errors errors) {
        if (price != null && quantity != null) {
            int total = price * quantity;
            if (total <= 10_000) {
                errors.reject("totalPriceMain", new Object[]{10_000, total}, null);
            }
        }
    }
}
