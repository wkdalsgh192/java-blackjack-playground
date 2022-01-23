package blackjack.model;

import java.util.function.Function;

public enum CardNumber {
    A(1, t -> t + 10 > 21 ? t + 1 : t + 10),
    TWO(2, t -> t + 2),
    THREE(3, t -> t + 3),
    FOUR(4, t -> t + 4),
    FIVE(5, t -> t + 5),
    SIX(6, t -> t + 6),
    SEVEN(7, t -> t + 7),
    EIGHT(8, t -> t + 8),
    NINE(9, t -> t + 9),
    TEN(10, t -> t + 10),
    J(10, t -> t + 10),
    Q(10, t -> t + 10),
    K(10, t -> t + 10);

    private Integer value;
    private Function<Integer, Integer> function;

    CardNumber(Integer value, Function<Integer, Integer> function) {
        this.value = value;
        this.function = function;
    }

    public int apply(int currentSum) {
        return function.apply(currentSum);
    }

    public int getNumericValue() {
        return value;
    }

    @Override
    public String toString() {
        if (this == A || this == J || this == Q || this == K) return this.name();
        return String.valueOf(this.getNumericValue());
    }
}
