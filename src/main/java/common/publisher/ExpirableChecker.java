package common.publisher;

public interface ExpirableChecker<T> {
    boolean isExpired(T entity);
}
