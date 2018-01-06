package com.victory.oauth2;

import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * Created by ajkx
 * Date: 2017/8/29.
 * Time:15:59
 */
public class Oauth2SubjectFactory extends DefaultWebSubjectFactory{
    @Override
    public Subject createSubject(SubjectContext context) {
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
