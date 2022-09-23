package com.ll.exam.app10.app.article.repository;

import com.ll.exam.app10.app.article.entity.Article;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<Article> getArticlesByUsernameAndKeyword(String username, String keyword);
    List<Article> getArticlesByUsername(String username);

    List<Article> getArticlesByKeyword(String keyword);
}
