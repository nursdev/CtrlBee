package kz.ctrlbee.model.dto;

import kz.ctrlbee.model.entity.Post;
import kz.ctrlbee.model.util.FileManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PostViewDTO {

    private UUID id;
    private String description;
    private byte[] media;

    public PostViewDTO(Post post) throws IOException {
        this.id = post.getId();
        this.description = post.getDescription();
        this.media = FileManager.getFile(post.getMediaPath());
    }
}
