package ra.model.service;

import ra.model.entity.Tag;
import ra.payload.request.TagRequest;

public interface TagService<T,V> extends StoreBookService<T,V>{
    Tag mapTagByTagName(TagRequest tagRequest);
}
