package hello.itemservice.web;

import hello.itemservice.domain.item.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/v2/items")
@RequiredArgsConstructor
public class ItemControllerV2 {

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
        return "v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "v2/item";
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, @ModelAttribute ItemUpdateDto updateParam) {
        System.out.println("updateParam.getItemName() = " + updateParam.getItemName());
        itemRepository.update(itemId, updateParam);

        return "redirect:/v2/items/{itemId}";
    }

    @GetMapping("/add")
    public String postItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "v2/addForm";
    }

//    @PostMapping("/add")
    public String postItemV1(
            @ModelAttribute("item") Item item,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        // Validation logics
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", "상품이름은 필수입니다."));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000_000) {
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() <= 0 || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
        }

        // Multi field validation logic
        if (item.getPrice() != null && item.getQuantity() != null) {
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000) {
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000을 넘어야 합니다."));
            }
        }

        if (bindingResult.hasErrors()) {
            return "v2/addForm";
        }

        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String postItemV2(
            @ModelAttribute Item item,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null,"상품이름은 필수입니다."));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000_000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() <= 0 || item.getQuantity() > 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999 까지 허용합니다."));
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int result = item.getPrice() * item.getQuantity();
            if (result < 10_000) {
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000을 넘어야 합니다."));
            }
        }

        if (bindingResult.hasErrors()) {
            return "v2/addForm";
        }

        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/v2/items/{itemId}";
    }

    @PostMapping("/add")
    public String postItemV3(
            @ModelAttribute Item item,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null,null));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000_000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000_000}, null));
        }
        if (item.getQuantity() == null || item.getQuantity() <= 0 || item.getQuantity() > 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int result = item.getPrice() * item.getQuantity();
            if (result < 10_000) {
                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMain"}, new Object[]{1000, result}, null));
            }
        }

        if (bindingResult.hasErrors()) {
            return "v2/addForm";
        }

        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/v2/items/{itemId}";
    }
}
