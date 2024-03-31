package team.ftft.project4242.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.PostMajor;
import team.ftft.project4242.domain.PostType;
import team.ftft.project4242.dto.PostRequestDto;
import team.ftft.project4242.repository.PostMajorRepository;
import team.ftft.project4242.repository.PostRepository;
import team.ftft.project4242.repository.PostTypeRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostTypeRepository postTypeRepository;
    private final PostMajorRepository postMajorRepository;

    public PostService(PostRepository postRepository, PostTypeRepository postTypeRepository, PostMajorRepository postMajorRepository) {
        this.postRepository = postRepository;
        this.postTypeRepository = postTypeRepository;
        this.postMajorRepository = postMajorRepository;
    }

    public Post save(PostRequestDto request) {
        System.out.println(request.getType_id() + ", " + request.getMajor_id());
        PostType postType = postTypeRepository.findById(request.getType_id())
                .orElseThrow(() -> new IllegalArgumentException("not found type id"));
        PostMajor postMajor = postMajorRepository.findById(request.getMajor_id())
                .orElseThrow(() -> new IllegalArgumentException("not found major id"));
        System.out.println(postType.getType_nm() + ", " + postMajor.getMajor_nm());
        return postRepository.save(request.toEntity(postType, postMajor));
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
        post.update(request.getTitle(), request.getContent());

        return post;
    }

    public void disablePostById(Long id) {
        postRepository.disablePostById(id);
    }

    public List<Post> findTypePostAll(Long type_id) {
        return postRepository.findTypePostAll(type_id);
    }

    public List<Post> findMajorPostAll(Long major_id) {
        return postRepository.findMajorPostAll(major_id);
    }
}
