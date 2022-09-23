package com.ll.exam.app10.app.hashtag.repository;

import com.ll.exam.app10.app.article.entity.Article;
import com.ll.exam.app10.app.hashtag.entity.HashTag;
import com.ll.exam.app10.app.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface HashTagRepository extends JpaRepository<HashTag,Long> {

    Optional<HashTag> findByArticleIdAndKeywordId(Long articleId, Long keywordId);

    List<HashTag> findByArticle(Article article);

}
