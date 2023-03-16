package ra.model.service;

import ra.model.entity.Star;
import ra.payload.request.StarRequest;

public interface StarService<T,V> extends StoreBookService<T,V>{
    Star findByBookIdAndUserId(Integer bookId, Integer userId);
    Star mapRequestToStar(Integer userId, StarRequest startRequest);
}
