package hello.itemservice.web;

import hello.itemservice.domain.item.*;
import hello.itemservice.domain.item.form.ItemForm;
import hello.itemservice.domain.item.form.SaveCheck;
import hello.itemservice.domain.item.form.UpdateCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/v3/items")
@RequiredArgsConstructor
public class ItemControllerV3 {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("DAEGU", "대구");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public DeliveryCode[] deliveryCodes() {
        return DeliveryCode.values();
    }

    @GetMapping

    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "v3/item";
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);

        ItemForm itemForm = new ItemForm();
        itemForm.setItemName(item.getItemName());
        itemForm.setPrice(item.getPrice());
        itemForm.setQuantity(item.getQuantity());
        itemForm.setOpen(item.getOpen());
        itemForm.setRegions(item.getRegions());
        itemForm.setItemType(item.getItemType());
        itemForm.setDeliveryCode(item.getDeliveryCode());

        model.addAttribute("itemForm", itemForm);
        return "v3/editForm";
    }

//    @PostMapping("/{itemId}/edit")
    public String updateItem(
            @PathVariable("itemId") Long itemId,
            @Valid @ModelAttribute ItemForm itemForm,
            Errors errors
    ) {
        itemForm.validate(errors);
        if (errors.hasErrors()) {
            return "v3/editForm";
        }

        itemRepository.update(itemId, itemForm);
        return "redirect:/v3/items/{itemId}";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem2(
            @PathVariable("itemId") Long itemId,
            @Validated(UpdateCheck.class) @ModelAttribute ItemForm itemForm,
            Errors errors
    ) {
        itemForm.validate(errors);
        if (errors.hasErrors()) {
            return "v3/editForm";
        }

        itemRepository.update(itemId, itemForm);
        return "redirect:/v3/items/{itemId}";
    }

    @GetMapping("/add")
    public String postItemForm(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "v3/addForm";
    }

//    @PostMapping("/add")
    public String postItem(
            @Valid @ModelAttribute ItemForm itemForm,
            Errors errors,
            RedirectAttributes redirectAttributes
    ) {
        itemForm.validate(errors);

        if (errors.hasErrors()) {
            return "v3/addForm";
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
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/v3/items/{itemId}";
    }

    @PostMapping("/add")
    public String postItem2(
            @Validated(SaveCheck.class) @ModelAttribute ItemForm itemForm,
            Errors errors,
            RedirectAttributes redirectAttributes
    ) {
        itemForm.validate(errors);

        if (errors.hasErrors()) {
            return "v3/addForm";
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
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/v3/items/{itemId}";
    }
}
