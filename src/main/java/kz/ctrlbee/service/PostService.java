package kz.ctrlbee.service;


import kz.ctrlbee.model.dto.PostCreateDTO;
import kz.ctrlbee.model.dto.PostViewDTO;
import kz.ctrlbee.model.entity.Post;
import kz.ctrlbee.model.entity.User;
import kz.ctrlbee.model.util.FileManager;
import kz.ctrlbee.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public PostViewDTO createPost(UUID userId, PostCreateDTO postDTO) throws IOException {
        var user = userService.findById(userId);

        String subFolder = "/post";
        MultipartFile image = postDTO.getMedia();
        String hashImage = FileManager.hashFile(image.getInputStream());
        String path = FileManager.saveFile(
                image.getInputStream(),
                subFolder.concat("/".concat(hashImage.substring(0, 12))),
                hashImage.substring(2)
        );


        var post = Post.builder()
                .description(postDTO.getDescription())
                .mediaPath(path)
                .build();

        var savedPost = postRepository.save(post);
        user.getPosts().add(savedPost);
        return new PostViewDTO(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostViewDTO> getPosts(UUID userId) throws IOException {
        var user = userService.findById(userId);

        List<PostViewDTO> postViewDTOS = new ArrayList<>();
        for (Post post : user.getPosts()) {
            postViewDTOS.add(new PostViewDTO(post));
        }
        return postViewDTOS;
    }
}
