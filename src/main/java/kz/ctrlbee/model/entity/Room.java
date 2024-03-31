package kz.ctrlbee.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rooms")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User owner;

    @ManyToMany
    private List<User> members = new ArrayList<>();
}
