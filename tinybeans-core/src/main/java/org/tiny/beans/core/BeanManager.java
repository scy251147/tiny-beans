package org.tiny.beans.core;

import org.tiny.beans.core.model.BeanContext;
import java.util.List;

/**
 * @author shichaoyang
 * @Description: bean管理
 * @date 2021-03-31 13:35
 */
public class BeanManager {

    public BeanManager(Class configClass) {

        //初始化上下文
        this.beanContext = new BeanContext();
        this.beanContext.setConfigClass(configClass);

        //实例化服务工厂
        this.beanServiceFactory = new BeanServiceFactory(beanContext);

        //创建bean扫描服务实例
        BeanScanService beanScanService = beanServiceFactory.getBeanScanServiceInstance();

        //创建bean解析服务实例
        BeanParseService beanParseService = beanServiceFactory.getBeanParseServiceInstance();

        //执行扫描操作
        List<Class> classes = beanScanService.scan();

        //执行解析操作
        beanParseService.parse(classes);

    }

    //bean上下文
    private BeanContext beanContext;

    //bean相关服务生成
    private BeanServiceFactory beanServiceFactory;


}
