package org.tiny.beans.core;

import org.tiny.beans.core.model.BeanContext;
import org.tiny.beans.core.parse.*;
import org.tiny.beans.sdk.annotation.Bean;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shichaoyang
 * @Description: bean解析annotation并处理
 */
public class BeanParseService {

    public BeanParseService(BeanContext beanContext) {
        this.beanContext = beanContext;
    }

    //上下文对象
    private BeanContext beanContext;

    /**
     * 类解并放到池子中
     */
    protected void parse() {
        for (Class clazz : beanContext.getClassPool()) {
            if (clazz.isAnnotationPresent(Bean.class)) {

                List<BeanParse> beanParseList = new ArrayList<>();
                beanParseList.add(new BeanInitAnnotationParse());
                beanParseList.add(new BeanPostAnnotationParse());
                beanParseList.add(new BeanScopeAnnotationParse());
                beanParseList.add(new BeanValueAnnotationParse());

                for (BeanParse beanParse : beanParseList) {
                    beanParse.parse(clazz, beanContext);
                }
            }
        }
    }
}
