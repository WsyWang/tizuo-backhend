package com.wsy.tizuobackend.model.jsonobject;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionOption implements Serializable {

    private static final long serialVersionUID = 4269554975714512618L;

    private String a;

    private String b;

    private String c;

    private String d;

    private String t;

    private String f;
}
