package com.ll.exam.app10.app.article.service;

import com.ll.exam.app10.app.article.entity.Article;
import com.ll.exam.app10.app.article.repository.ArticleRepository;
import com.ll.exam.app10.app.gen.entity.GenFile;
import com.ll.exam.app10.app.gen.service.GenFileService;
import com.ll.exam.app10.app.hashtag.entity.HashTag;
import com.ll.exam.app10.app.hashtag.service.HashTagService;
import com.ll.exam.app10.app.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final GenFileService genFileService;

    private final HashTagService hashTagService;

    public Article write(Long authorId, String subject, String content) {
        return write(new Member(authorId), subject, content);
    }
    public Article write(Long authorId, String subject, String content,String hashTagsStr) {
        return write(new Member(authorId), subject, content,hashTagsStr);
    }
    public Article write(Member author, String subject, String content) {
        return write(author, subject, content, "");
    }

    public Article write(Member author, String subject, String content, String hashTagsStr) {
        Article article = Article
                .builder()
                .author(author)
                .subject(subject)
                .content(content)
                .build();

        articleRepository.save(article);

        hashTagService.applyHashTags(article, hashTagsStr);

        return article;
    }
    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }
    public void addGenFileByUrl(Article article, String typeCode, String type2Code, int fileNo, String url) {
        genFileService.addGenFileByUrl("article", article.getId(), typeCode, type2Code, fileNo, url);
    }
    public Article getForPrintArticleById(Long id) {
        Article article = getArticleById(id);
        List<HashTag> hashTags = hashTagService.getHashTags(article);
        Map<String, GenFile> genFileMap = genFileService.getRelGenFileMap(article);
        article.getExtra().put("age__name__33", 22);
        article.getExtra().put("genFileMap", genFileMap);
        article.getExtra().put("hashTags",hashTags);
        return article;
    }

    public void modify(Article article, String subject, String content) {
        article.setSubject(subject);
        article.setContent(content);
        articleRepository.save(article);
    }
    public List<Article> getArticles(String username){
        return articleRepository.findByAuthorUsername(username);
    }
    public List<Article> getForPrintArticles(List<Article> articles){
        for(Article article: articles){
            List<HashTag> hashTags = hashTagService.getHashTags(article);
            article.getExtra().put("hashTags",hashTags);
        }
        return articles;
    }
    public List<Article> getForPrintArticlesByUsername(String username) {
        List<Article> articles = articleRepository.getArticlesByUsername(username);
        return getForPrintArticles(articles);
    }
    public List<Article> getForPrintArticlesByKeyword(String username, String keyword) {
        List<Article> articles = articleRepository.getArticlesByUsernameAndKeyword(username, keyword);
        return getForPrintArticles(articles);
    }
}
