package ra.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagId")
    private int tagId;
    @Column(name = "tagName")
    private String tagName;
    @Column(name = "tagStatus")
    private String tagStatus;
    @OneToMany(mappedBy = "tag")
    private List<TagBooks> tagBooks = new ArrayList<>();
}
