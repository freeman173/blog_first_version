package com.example.myadmin.service;


import com.example.myadmin.vo.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public interface ArticleService {


    Result getArtilces(Map<String, Object> map);

    Result deleteArticleById(Long articleId);

    Result getPieData();

    Result getLineData();

    Result getBarData();
}
