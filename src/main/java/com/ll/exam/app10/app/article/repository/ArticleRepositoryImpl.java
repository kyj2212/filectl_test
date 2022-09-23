package com.ll.exam.app10.app.article.repository;

import com.ll.exam.app10.app.article.entity.Article;
import com.ll.exam.app10.app.article.entity.QArticle;
import com.ll.exam.app10.app.hashtag.entity.HashTag;
import com.ll.exam.app10.app.hashtag.entity.QHashTag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import static com.ll.exam.app10.app.article.entity.QArticle.article;
import static com.ll.exam.app10.app.hashtag.entity.QHashTag.hashTag;
import static com.ll.exam.app10.app.keyword.entity.QKeyword.keyword;
import java.util.List;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Article> getArticlesByUsernameAndKeyword(String username, String keywordContent) {
        return jpaQueryFactory
                .select(article)
                .from(article)
                .leftJoin(hashTag)
                .on(hashTag.article.eq(article))
                .where(hashTag.keyword.content.eq(keywordContent).and(article.author.username.eq(username)))
                .orderBy(article.createDate.desc())
                .fetch();
    }

    @Override
    public List<Article> getArticlesByUsername(String username) {
        return  jpaQueryFactory
                .select(article)
                .from(article)
                .where(article.author.username.eq(username))
                .fetch();
    }

    @Override
    public List<Article> getArticlesByKeyword(String keywordContent) {
        return  jpaQueryFactory
                .select(article)
                .from(article)
                .leftJoin(hashTag)
                .on(hashTag.article.id.eq(article.id))
                .where(hashTag.keyword.content.eq(keywordContent))
                .fetch();
    }
}
