package common.publisher;

import common.observer.ExpirableObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ExpirableService <T>{
    private final Supplier<List<T>> supplier;
    private final List<ExpirableObserver<T>> observers = new ArrayList<>();

    public ExpirableService(Supplier<List<T>> supplier) {
        this.supplier = supplier;
    }

    public void addObserver(ExpirableObserver<T> observer) {
        observers.add(observer);
    }

    public void removeObserver(ExpirableObserver<T> observer) {
        observers.remove(observer);
    }

    public List<T> notifyExpired(ExpirableChecker<T> checker) {
        List<T> all = supplier.get();
        List<T> expired = all.stream()
                .filter(checker::isExpired)
                .toList();
        for (ExpirableObserver<T> observer : observers) {
            observer.onExpired(expired);
        }
        return expired;
    }
}
