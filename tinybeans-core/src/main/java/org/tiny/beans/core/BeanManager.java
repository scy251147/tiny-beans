package org.tiny.beans.core;

import lombok.extern.slf4j.Slf4j;
import org.tiny.beans.core.model.BeanContext;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author shichaoyang
 * @Description: bean管理
 */
@Slf4j
public class BeanManager {

    /**
     * 带参构造
     *
     * @param configClass
     */
    public BeanManager(Class configClass) {
        //锁止
        if (initFlag.compareAndSet(false, true)) {
            //初始化上下文
            BeanContext beanContext = new BeanContext();
            beanContext.setConfigClass(configClass);

            //实例化服务工厂
            beanServiceFactory = new BeanServiceFactory(beanContext);

            //创建bean扫描服务实例 并 执行扫描操作
            BeanScanService beanScanService = beanServiceFactory.getBeanScanServiceInstance();
            beanScanService.scan();

            //创建bean解析服务实例 并 执行解析操作
            BeanParseService beanParseService = beanServiceFactory.getBeanParseServiceInstance();
            beanParseService.parse();

            //创建bean生成服务实例 并 执行创建操作
            BeanCreateService beanCreateService = beanServiceFactory.getBeanCreateServiceIntance();
            beanCreateService.createSingletonBean();
        }
    }

    //并发锁
    private static AtomicBoolean initFlag = new AtomicBoolean();

    //bean相关服务创建工厂
    private static BeanServiceFactory beanServiceFactory;

    /**
     * 获取bean对象
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        return beanServiceFactory.getBeanCreateServiceIntance().getBean(beanName);
    }
}