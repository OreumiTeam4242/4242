package team.ftft.project4242.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.PostRequestDto;
import team.ftft.project4242.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(PostRequestDto request) {
        return postRepository.save(request.toEntity());
    }

    public List<Post> findAllAble() {
        return postRepository.findAllAble();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found id : " + id));
    }

    @Transactional
    public Post update(Long id, PostRequestDto request) {
        Post post = findById(id);
        post.update(request.getTitle(), request.getContent(), request.getUpdatedAt());

        return post;
    }

    public void disablePostById(Long id) {
        postRepository.disablePostById(id);
    }

    public List<Post> findStudyPostAll() {
        return postRepository.findStudyPostAll();
    }

    public List<Post> findProjectPostAll() {
        return postRepository.findProjectPostAll();
    }
}
