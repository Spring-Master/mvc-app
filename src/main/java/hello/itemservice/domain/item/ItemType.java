package hello.itemservice.domain.item;

import java.util.Random;

public enum ItemType {

    BOOK("도서"), FOOD("음식"), ETC("기타");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public static ItemType getRandom() {
        Random random = new Random();
        int ordinal = random.nextInt(values().length);
        return values()[ordinal];
    }
}
