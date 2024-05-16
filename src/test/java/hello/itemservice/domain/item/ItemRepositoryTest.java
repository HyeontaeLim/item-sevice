package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        Item item = new Item("itemA", 20000, 30);
        Item savedItem = itemRepository.save(item);
        Item findItem = itemRepository.findById(savedItem.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        Item item1 = new Item("item1", 20000, 30);
        Item item2 = new Item("item2", 30000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> items = itemRepository.findAll();

        assertThat(items.size()).isEqualTo(2);
        assertThat(items).contains(item1, item2);
    }

    @Test
    void update() {
        Item item1 = new Item("item1", 20000, 30);
        Item item2 = new Item("item2", 30000, 20);
        itemRepository.save(item1);

        itemRepository.update(item1.getId(), item2);

        Item findItem = itemRepository.findById(item1.getId());
        assertThat(findItem.getName()).isEqualTo("item2");
        assertThat(findItem.getPrice()).isEqualTo(30000);
        assertThat(findItem.getQuantity()).isEqualTo(20);
    }
}