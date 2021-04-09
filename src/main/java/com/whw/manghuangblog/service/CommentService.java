package com.whw.manghuangblog.service;

import com.whw.manghuangblog.po.Blog;
import com.whw.manghuangblog.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
