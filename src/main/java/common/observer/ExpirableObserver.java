package common.observer;

import java.util.List;

public interface ExpirableObserver<T> {
    void onExpired(List<T> expiredEntities);
}
