package com.example.reddit_clone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.reddit_clone.dto.CommentRequest;
import com.example.reddit_clone.mapper.CommentMapper;
import com.example.reddit_clone.models.Comment;
import com.example.reddit_clone.models.NotificationEmail;
import com.example.reddit_clone.models.Post;
import com.example.reddit_clone.models.User;
import com.example.reddit_clone.repository.CommentRepo;
import com.example.reddit_clone.repository.PostRepository;
import com.example.reddit_clone.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService; 
    private final CommentMapper commentMapper;
    private final CommentRepo commentRepo;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    /**
     * - Get the post from the postid
     * - get current user 
     * - Map dto to comment
     * - save to database
     * @param newComment
     */
    public void save(CommentRequest newComment) { 
        System.out.println("✅ CommentService.save()");
        Post post = postRepository
            .findById(newComment.getPostId())
            .orElseThrow(() -> new IllegalStateException("Unable to find post id for comment"));
        User user = authService.getCurrentUser();
        // Map DTO to Comment
        Comment dtoConversion = commentMapper.map(newComment, post, user);
        // Save to database
        System.out.println("✅ Saving comment to comment repository");
        commentRepo.save(dtoConversion);

        System.out.println("✅ Send Notification to author post");
        var message = mailContentBuilder
            .build(post.getUser().getUsername() + "posted a comment on your post" + post.getUrl());
        sendCommentNotification(message, post.getUser());
    }

    /**
     * 
     * @param message
     * @param user
     */
    private void sendCommentNotification(String message, User user) {
        System.out.println("🛫 Sending message to user!");
        mailService.sendMail(new NotificationEmail(
            // Subject
            user.getUsername() + "Commented on your post",
            user.getEmail(),
            message
        ));
    }

    /**
     * Find comments by post
     * @param postId
     * @return
     */
    public List<CommentRequest> getAllCommentsForPost(Long postId) {
        System.out.println("✅ CommentService.getAllCommentsForPost()");
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalStateException("Unabled to get the post"));
        return commentRepo
            .findByPost(post)
            .stream()
            .map(commentMapper::commentToDto)
            .collect(Collectors.toList());
    }
    /**
     * 
     * @param username
     * @return
     */
    public List<CommentRequest> getAllCommentsForUsername(String username) {
        System.out.println("✅ CommentService.getAllCommentsForUsername()");
        User user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new IllegalStateException("Unable to find user by username"));
        return commentRepo
            .findByUser(user)
            .stream()
            .map(commentMapper::commentToDto)
            .collect(Collectors.toList());
    }   
}
