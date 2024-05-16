package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable Long itemId) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

/*

    @PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam Integer price,
            @RequestParam Integer quantity,
            Model model) {
        Item item = new Item();
        item.setName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        model.addAttribute("item", item);
        return "basic/item";
    }

*/

/*
    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, Model model) {
        Item savedItem = itemRepository.save(item);
        Long savedItemId = savedItem.getId();
//      model.addAttribute("item", item); //자동 추가, 생략가능
        return "redirect:/basic/items/"+savedItemId;
    }
*/

    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

//      model.addAttribute("item", item); //자동 추가, 생략가능
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String addItem(Model model, @PathVariable Long itemId) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editItem(@ModelAttribute Item item, @PathVariable Long itemId) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }
    /**
     * Test용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 20));
        itemRepository.save(new Item("itemB", 20000, 10));
    }
}
