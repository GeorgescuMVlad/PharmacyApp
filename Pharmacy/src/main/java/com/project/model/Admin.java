package com.project.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int id;

    String mail;

    String password;

    String name;

    boolean role;

    public boolean getRole() {
        return this.role;
    }

}
