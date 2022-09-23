package com.ll.exam.app10.app.hashtag.service;

import com.ll.exam.app10.app.article.entity.Article;
import com.ll.exam.app10.app.hashtag.entity.HashTag;
import com.ll.exam.app10.app.hashtag.repository.HashTagRepository;
import com.ll.exam.app10.app.keyword.entity.Keyword;
import com.ll.exam.app10.app.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final KeywordService keywordService;
    private final HashTagRepository hashTagRepository;

    public List<HashTag> applyHashTags(Article article, String keywordContentsStr) {

        List<HashTag> oldHashTags = hashTagRepository.findByArticle(article);
//        List<HashTag> needToDelTags = new ArrayList<>();
        if(oldHashTags.size()!=0){
            resetHashTag(article);
        }
        List<String> keywordContents = Arrays.stream(keywordContentsStr.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

/*        for(HashTag oldHashTag : oldHashTags){
            String oldKeywordContent = oldHashTag.getKeyword().getContent();
            if(!keywordContents.contains(oldKeywordContent)){
                needToDelTags.add(oldHashTag);
            }
        }*/
        List<HashTag> newHashTags = new ArrayList<>();
        keywordContents.forEach(keywordContent -> {
            newHashTags.add(saveHashTag(article, keywordContent));
        });
        return newHashTags;
    }

    private HashTag saveHashTag(Article article, String keywordContent) {
        Keyword keyword = keywordService.save(keywordContent);

        Optional<HashTag> opHashTag = hashTagRepository.findByArticleIdAndKeywordId(article.getId(), keyword.getId());

        if (opHashTag.isPresent()) {
            return opHashTag.get();
        }

        HashTag hashTag = HashTag.builder()
                .article(article)
                .keyword(keyword)
                .build();

        hashTagRepository.save(hashTag);

        return hashTag;
    }
    private void resetHashTag(Article article) {

        List<HashTag> hashTags = hashTagRepository.findByArticle(article);
        for(HashTag hashTag : hashTags){
            hashTagRepository.delete(hashTag);
        }
    }
    public List<HashTag> getHashTags(Article article){
        return hashTagRepository.findByArticle(article);
    }
}
