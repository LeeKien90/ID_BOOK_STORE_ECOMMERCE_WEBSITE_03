package ra.model.service;

import ra.model.entity.Tag;

public interface TagService<T,V> extends StoreBookService<T,V>{
    void delete(int tagId);
    Tag findTagByTagName(String tagName);
}
