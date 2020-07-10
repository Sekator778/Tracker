package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemTracker implements Store {
    /**
     * Массив для хранения заявок.
     */
    private final List<Item> items = new ArrayList<>();

    @Override
    public void init() {

    }

    /**
     * Метод добавления заявки в хранилище
     *
     * @param item новая заявка
     */
    @Override
    public Item add(Item item) {
        item.setId(this.generateId());
        items.add(item);
        return item;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     *
     * @return Уникальный ключ.
     */
    private Integer generateId() {
        Random rm = new Random();
        Random rand = new Random(System.currentTimeMillis());
// loop starts here
        double randomNumber = Math.floor(10 * rand.nextDouble());
// If you want an integer up to inputParam1 as it seems, you can do:
        int randomInt = (int) randomNumber;
//        Integer i = Math.toIntExact((int) rm.nextLong() + System.currentTimeMillis());
        return randomInt;
    }

    /**
     * Метод возвращает новый масив.
     * Который состоит из старого за исключением null ячеек
     *
     * @return цельный масив
     */
    public List<Item> findAll() {
        return items;
    }

    /**
     * @param name имя итема итему
     * @return возвращаем масив елементов у которых одинаковое имя
     */
    public List<Item> findByName(String name) {
        List<Item> newItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(name)) {
                newItems.add(item);
            }
        }
        return newItems;
    }

    /**
     * @param id уникальный ключ у кажого итема
     * @return получаем итем
     */
    @Override
    public Item findById(int id) {
        int index = id;
        if (index != -1) {
            return items.get(index);
        }
        return null;
    }

    /**
     * @param id уникальный ключ у кажого итема
     * @return возвращает индекс елемента в масиве итемов items
     */
    private int indexOf(int id) {
        int rsl = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    /**
     * @param item новый итем с новым айди
     * @return пока не ясно что тестить
     */
    public boolean replace(int index, Item item) {
        if (index == -1) {
            return false;
        }
        item.setId(index);
        items.set(index, item);
        return true;
    }

    @Override
    public boolean delete(int id) {
        int index = id;
        if (index == -1) {
            return false;
        }
        items.remove(index);
        return true;
    }

    @Override
    public void close() {
    }
}