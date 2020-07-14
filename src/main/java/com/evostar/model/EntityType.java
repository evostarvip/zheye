package com.evostar.model;

public enum EntityType {
    ENTITY_QUESTION(1, "QUESTION"),//给问题的互动
    ENTITY_ANSWER(2, "ANSWER"),//给回答的互动
    ENTITY_COMMENT(3, "COMMENT");//给评论的互动

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
