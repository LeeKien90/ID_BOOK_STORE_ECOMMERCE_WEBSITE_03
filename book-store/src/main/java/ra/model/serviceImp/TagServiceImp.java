package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import ra.model.entity.Tag;
import ra.model.repository.TagRepository;
import ra.model.service.TagService;

import java.util.List;

public class TagServiceImp implements TagService<Tag,Integer> {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(Integer id) {
        return tagRepository.findById(id).get();
    }

    @Override
    public Tag saveOrUpdate(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void delete(int tagId) {
        tagRepository.deleteById(tagId);
    }

    @Override
    public Tag findTagByTagName(String tagName) {
        return tagRepository.findTagByTagName(tagName);
    }
}
