package com.evostar.service;

import com.evostar.dao.EsDAO;
import com.evostar.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsService {
    @Qualifier("elasticsearchTemplate")
    @Autowired
    private ElasticsearchRestTemplate template;
    @Autowired
    private EsDAO esDAO;

    public void add(Question question){
        template.save(question);
    }

    public List<Question> selectByTitleLike(String search, Pageable pageable){
        return esDAO.findQuestionsByTitleContains(search,pageable);
    }

    public void deleteByTitle(String title){
        esDAO.deleteQuestionsByTitleContaining(title);
    }
}
