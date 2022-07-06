package hello.itemservice.web;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.form.ItemForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/validation/api/items")
public class ValidationApiController {

    private final ItemRepository itemRepository;

    @PostMapping("/add")
    public Object addItem(
            @RequestBody @Valid ItemForm itemForm,
            Errors errors
    ) {
        itemForm.validate(errors);
        if (errors.hasErrors()) {
            return errors.getAllErrors();
        }

        Item item = new Item();
        item.setItemName(itemForm.getItemName());
        item.setPrice(itemForm.getPrice());
        item.setQuantity(itemForm.getQuantity());
        item.setOpen(itemForm.getOpen());
        item.setRegions(item.getRegions());
        item.setItemType(itemForm.getItemType());
        item.setDeliveryCode(itemForm.getDeliveryCode());

        itemRepository.save(item);
        return item;
    }
}
