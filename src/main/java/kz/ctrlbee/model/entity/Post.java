package kz.ctrlbee.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "posts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Post {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "media_path")
    private String mediaPath;

    @Column(name = "description")
    private String description;
}
