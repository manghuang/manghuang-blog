package com.whw.manghuangblog.service;

import com.whw.manghuangblog.dao.CommentRepository;
import com.whw.manghuangblog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, Sort.by(Sort.Direction.ASC, "createTime"));

        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentView = new ArrayList<>();
        for (Comment comment:
             comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentView.add(c);
        }
        combineChildren(commentView);
        return commentView;
    }

    private void combineChildren(List<Comment> commentView) {
        for (Comment comment : commentView) {
            List<Comment> ans = dfs(comment);
            comment.setReplyComments(ans);
        }
    }

    private List<Comment> dfs(Comment comment) {
        List<Comment> ans = new ArrayList<>();
        if(comment.getReplyComments().size() == 0){
            return ans;
        }
        List<Comment> replyComments = comment.getReplyComments();
        for (Comment replyComment : replyComments) {
            ans.add(replyComment);
            ans.addAll(dfs(replyComment));
        }
        return ans;
    }
}
