package com.ll.exam.app10;

import com.ll.exam.app10.app.article.entity.Article;
import com.ll.exam.app10.app.article.service.ArticleService;
import com.ll.exam.app10.app.hashtag.entity.HashTag;
import com.ll.exam.app10.app.hashtag.service.HashTagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ArticleTests {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private HashTagService hashTagService;

    @Test
    @DisplayName("1번 게시물에는 키워드가 2개 존재한다.")
    public void t1(){
        Article article = articleService.getArticleById(1L);
        List<HashTag> hashTags = hashTagService.getHashTags(article);
        hashTags.forEach(h -> System.out.println(h.getKeyword().getContent()));
        assertThat(hashTags.size()).isEqualTo(2);
    }
    @Test
    @DisplayName("1번 게시물의 해시태그를 수정하면 기존 해시태그 중 몇개는 지워질 수 있다.")
    public void t2(){
        String keywordContentsStr = "#해시태그2 #자바 #해시태그4";
        Article article = articleService.getArticleById(1L);
        List<HashTag> hashTags = hashTagService.applyHashTags(article,keywordContentsStr);
        hashTags.forEach(h -> System.out.println(h.getKeyword().getContent()));
        assertThat(hashTags.size()).isEqualTo(3);
    }

}
