package com.evostar.utils;

import com.evostar.ApplicationTests;
import com.evostar.dao.EsDAO;
import com.evostar.model.Question;
import com.evostar.service.EsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import java.util.List;

public class ExAppTest extends ApplicationTests {

    @Qualifier("elasticsearchTemplate")
    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private EsService esService;

    @Autowired
    private EsDAO esDAO;

    @Test
    public void createIndex(){
        Question question = new Question();
        question.setTitle("你好");
        question.setContent("看霍建华客家话客家话客家话看");
        question.setId(2);
        template.save(question);
    }

    @Test
    public void getData(){
        Pageable pageable = PageRequest.of(0, 10);
        List<Question> list = esService.selectByTitleLike("l", pageable);
        System.out.println(list);
    }
    @Test
    public void getDataById(){
        List<Question> list = esDAO.findQuestionsById(1);
        System.out.println(list);
    }

    @Test
    public void deleteAll(){
        esService.deleteByTitle("你");
    }
}
