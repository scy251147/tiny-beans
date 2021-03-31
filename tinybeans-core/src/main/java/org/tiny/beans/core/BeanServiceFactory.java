package org.tiny.beans.core;

import org.tiny.beans.core.model.BeanContext;

/**
 * @author shichaoyang
 * @Description: 服务工厂
 * @date 2021-03-31 9:53
 */
public class BeanServiceFactory {

    /**
     * 带参构造
     * @param beanContext
     */
    public BeanServiceFactory(BeanContext beanContext){
        this.beanContext = beanContext;
    }

    //上下文对象
    private BeanContext beanContext;

    //扫描bean服务
    private static volatile BeanScanService beanScanService;

    //创建bean服务
    private static volatile BeanCreateService beanCreateService;

    //解析bean服务
    private static volatile BeanParseService beanParseService;

    //扫描bean服务实例锁
    private static Object beanScanLock = new Object();

    //创建bean服务实例锁
    private static Object beanCreateLock = new Object();

    //解析bean服务实例锁
    private static Object beanParseLock = new Object();

    /**
     * 创建beanscan服务实例
     * @return
     */
    protected BeanScanService getBeanScanServiceInstance() {
        if (beanScanService == null) {
            synchronized (beanScanLock) {
                if (beanScanService == null) {
                    beanScanService = new BeanScanService(beanContext.getConfigClass());
                }
            }
        }
        return beanScanService;
    }

    /**
     * 创建beanparse服务实例
     * @return
     */
    protected  BeanParseService getBeanParseServiceInstance() {
        if (beanParseService == null) {
            synchronized (beanParseLock) {
                if (beanParseService == null) {
                    beanParseService = new BeanParseService(beanContext);
                }
            }
        }
        return beanParseService;
    }

    /**
     * 创建beancreate服务实例
     * @return
     */
    protected BeanCreateService getBeanCreateServiceIntance() {
        if (beanCreateService == null) {
            synchronized (beanCreateLock) {
                if (beanCreateService == null) {
                    beanCreateService = new BeanCreateService(beanContext);
                }
            }
        }
        return beanCreateService;
    }
}
