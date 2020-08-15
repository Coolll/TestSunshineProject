package com.myp.sections.User.service;

import com.myp.baseFile.BaseService;

import java.util.ArrayList;

public class UserService extends BaseService {

    public Object testLogMethod() {
        ArrayList list = new ArrayList();
        list.add("Good");
        list.add("Nice");
        list.add("Kayle");
        list.add("Hello");
        try {
            String string = list.get(4).toString();

        } catch (Exception e) {

            StackTraceElement element = Thread.currentThread().getStackTrace()[1];
            dealException(e, element.getFileName(), element.getLineNumber());
        }
        return "测试12345678";
    }
}
