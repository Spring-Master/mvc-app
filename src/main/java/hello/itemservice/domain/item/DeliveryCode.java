package hello.itemservice.domain.item;

import java.util.Random;

public enum DeliveryCode {

    FAST("빠른 배송"), NORMAL("일반 배송"), SLOW("느린 배송");

    private final String description;

    DeliveryCode(String description) {
        this.description = description;
    }

    public static DeliveryCode getRandom() {
        Random random = new Random();
        int ordinal = random.nextInt(values().length);
        return values()[ordinal];
    }
}
