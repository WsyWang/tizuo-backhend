package com.wsy.tizuobackend.model.enums;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

public enum UserAccessEnum {
    ADMIN("管理员", "admin"),
    STUDENT("学生", "student"),
    TEACHER("教师", "teacher"),
    BAN("被封号", "ban"),
    NORMAL("正常", "normal")
    ;


    private final String text;

    private final String value;

    UserAccessEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    public static UserAccessEnum getEnumByValue(String value) {
        if (StrUtil.isEmpty(value)) {
            return null;
        }
        for (UserAccessEnum userEnum : UserAccessEnum.values()) {
            if (userEnum.value.equals(value)) {
                return userEnum;
            }
        }
        return null;
    }

    public static List<UserAccessEnum> getEnumByValue(String[] value) {
        List<UserAccessEnum> userAccessEnums = new ArrayList<>();
        for (UserAccessEnum userAccessEnum : UserAccessEnum.values()) {
            for (String enumValue : value) {
                if (userAccessEnum.value.equals(enumValue)) {
                    userAccessEnums.add(userAccessEnum);
                }
            }
        }
        return userAccessEnums;
    }
}
