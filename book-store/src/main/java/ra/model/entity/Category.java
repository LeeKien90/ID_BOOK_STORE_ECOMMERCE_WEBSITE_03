package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private int categoryId;
    @Column(name = "CategoryName", unique = true)
    private String categoryName;
    @Column(name = "CategoryStatus")
    private boolean categoryStatus;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Books> books = new ArrayList<>();
}
