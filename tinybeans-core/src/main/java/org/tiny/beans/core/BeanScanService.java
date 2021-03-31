package org.tiny.beans.core;

import org.tiny.beans.core.bak.BeanContexter;
import org.tiny.beans.sdk.annotation.BeanScan;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shichaoyang
 * @Description: bean扫描器
 * @date 2021-03-29 11:27
 */
public class BeanScanService {

    /**
     * 带参构造
     *
     * @param configClass
     */
    public BeanScanService(Class<?> configClass) {
        this.configClass = configClass;
    }

    //配置文件
    private Class configClass;

    //文件路径开始字符
    private static final String START_WORDS = "org";

    //文件路径结束字符
    private static final String STOP_WORDS = ".class";

    /**
     * bean扫描并加载
     */
    protected List<Class> scan() {
        List<Class> classList = new ArrayList<>();

        //扫描包路径
        BeanScan beanScanAnnotation = (BeanScan) configClass.getAnnotation(BeanScan.class);
        String path = beanScanAnnotation.value();
        path = path.replace(".", "/");

        //利用类加载器获取路径
        ClassLoader classLoader = BeanContexter.class.getClassLoader();
        URL resource = classLoader.getResource(path);

        //得到文件夹并加载到classloader中
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String absoltePath = f.getAbsolutePath();
                //寻找类文件，其他文件忽略
                if (absoltePath.endsWith(STOP_WORDS)) {
                    absoltePath = absoltePath.substring(absoltePath.indexOf(START_WORDS), absoltePath.indexOf(STOP_WORDS));
                    absoltePath = absoltePath.replace("\\", ".");
                    try {
                        Class<?> clazz = classLoader.loadClass(absoltePath);
                        classList.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classList;
    }
}
