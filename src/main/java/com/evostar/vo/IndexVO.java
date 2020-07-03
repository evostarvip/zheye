package com.evostar.vo;

import com.evostar.model.Question;
import com.evostar.model.User;
import lombok.Data;


@Data
public class IndexVO {

    private Question question;

    private int followCount;

    private User user;
}
