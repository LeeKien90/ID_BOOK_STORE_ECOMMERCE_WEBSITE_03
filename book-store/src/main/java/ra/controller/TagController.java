package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Tag;
import ra.model.service.TagService;
import ra.payload.request.TagRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/createTag")
    public ResponseEntity<?> createNewTag(@RequestBody TagRequest tagRequest){
        try {
            Tag result = (Tag) tagService.saveOrUpdate(tagService.mapTagByTagName(tagRequest));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateTag/{tagId}")
    public ResponseEntity<?> updateTag(@PathVariable int tagId, @RequestBody Tag tag){
        try {
            Tag tagUpdate = (Tag) tagService.findById(tagId);
            tagUpdate.setTagName(tag.getTagName());
            tagUpdate.setTagStatus(tag.isTagStatus());
            Tag result = (Tag) tagService.saveOrUpdate(tagUpdate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("/deleteTag/{tagId}")
    public ResponseEntity<?> delete(@PathVariable int tagId){
        try {
            Tag tag = (Tag) tagService.findById(tagId);
            tag.setTagStatus(false);
            Tag result = (Tag) tagService.saveOrUpdate(tag);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
