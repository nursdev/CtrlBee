package kz.ctrlbee.controller;


import kz.ctrlbee.model.dto.PostCreateDTO;
import kz.ctrlbee.model.dto.PostViewDTO;
import kz.ctrlbee.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostViewDTO> createPost(@ModelAttribute PostCreateDTO postDTO, Principal principal) throws IOException {
        return new ResponseEntity<>(postService.createPost(UUID.fromString(principal.getName()), postDTO), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<PostViewDTO>> getPosts(Principal principal) throws IOException {
        return ResponseEntity.ok(postService.getPosts(UUID.fromString(principal.getName())));
    }
}
