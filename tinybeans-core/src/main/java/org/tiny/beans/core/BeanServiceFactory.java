package org.tiny.beans.core;

/**
 * @author shichaoyang
 * @Description: 服务工厂
 * @date 2021-03-31 9:53
 */
public class BeanServiceFactory {

    public BeanServiceFactory(){

    }

    private static volatile BeanScanService beanScanService;

    private static volatile BeanCreateService beanCreateService;

    private static volatile BeanParseService beanParseService;

    private static Object beanScanLock = new Object();

    private static Object beanCreateLock = new Object();

    private static Object beanParseLock = new Object();

    protected BeanScanService getBeanScanServiceInstance(){
        if(beanScanService == null){
            synchronized (beanScanLock){
                if (beanScanService == null) {
                    return new BeanScanService();
                }
            }
        }
    }


    


}
