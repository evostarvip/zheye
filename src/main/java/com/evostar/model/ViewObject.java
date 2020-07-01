package com.evostar.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

@ApiModel(value = "有点复杂，包括question对象和user对象，外加一个followCount(关注人数)的变量")
public class ViewObject {
    @ApiModelProperty(value = "[{\"data\":{\"followCount\":0,\"question\":{\"id\":1,\"title\":\"今天您核酸了吗？\",\"content\":\"一加一在什么情况下等于三？答，在算错的情况下的等于三！\",\"created_date\":\"2020-07-01 23:10:28\",\"user_id\":1,\"comment_count\":0},\"user\":{\"id\":1,\"name\":\"peter\",\"password\":null,\"salt\":null,\"head_url\":\"http://images.nowcoder.com/head/129t.png\",\"token\":null}}}]")
    public Map<String, Object> data = new HashMap<String, Object>();
    public void set(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }
}