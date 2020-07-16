package com.evostar.model;

public enum EntityType {
    ENTITY_QUESTION(1, "QUESTION"),//给问题的互动
    ENTITY_ANSWER(2, "ANSWER"),//给回答的互动
    ENTITY_COMMENT(3, "COMMENT"),//给评论的互动
    ENTITY_FOLLOWED_QUESTION(4, "FOLLOWED_QUESTION_"),//关注问题，以问题祝中心
    ENTITY_FOLLOWED_USER(5, "FOLLOWED_USER_"),//关注人
    ENTITY_FOLLOW_QUESTION(6, "FOLLOW_QUESTION_"),
    ENTITY_FOLLOW_USER(7, "FOLLOW_USER_");

    int type;
    String key;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
    public void setKey(String key){
        this.key = key;
    }
    public String getKey(){
        return key;
    }

    EntityType(int type, String key){
        this.type = type;
        this.key = key;
    }
}
