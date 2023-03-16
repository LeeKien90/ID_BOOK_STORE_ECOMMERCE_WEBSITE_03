package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Tag;
import ra.model.repository.TagRepository;
import ra.model.service.TagService;
import ra.payload.request.TagRequest;

import java.util.List;
@Service
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
    public Tag mapTagByTagName(TagRequest tagRequest) {
        Tag tagNew = new Tag();
        tagNew.setTagName(tagRequest.getTagName());
        tagNew.setTagStatus(true);
        return tagNew;
    }
}
