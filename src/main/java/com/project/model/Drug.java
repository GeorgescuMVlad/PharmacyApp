package com.project.model;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Drug {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    String name;

    String type;

    String price;

    String description;

    @ManyToMany(mappedBy = "drugs",  fetch=FetchType.EAGER)
    private Set<User> users = new HashSet<>();

}
