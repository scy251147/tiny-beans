package org.tiny.beans.core;

import lombok.extern.slf4j.Slf4j;
import org.tiny.beans.sdk.annotation.BeanScan;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shichaoyang
 * @Description: bean扫描器
 */
@Slf4j
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

    //扫描包下的类文件集合
    private List<String> classFiles = new ArrayList<>();

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
        ClassLoader classLoader = BeanManager.class.getClassLoader();
        URL resource = classLoader.getResource(path);

        //将文件加载到classloader
        File file = new File(resource.getFile());
        List<String> classFiles = findClassFiles(file);
        for (String classFile : classFiles) {
            try {
                Class<?> clazz = classLoader.loadClass(classFile);
                classList.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return classList;
    }

    /**
     * 递归遍历获取.class文件
     *
     * @param file
     * @return
     */
    private List<String> findClassFiles(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                String absoltePath = f.getAbsolutePath();
                //寻找类文件，其他文件忽略
                if (absoltePath.endsWith(STOP_WORDS)) {
                    absoltePath = absoltePath.substring(absoltePath.indexOf(START_WORDS), absoltePath.indexOf(STOP_WORDS));
                    absoltePath = absoltePath.replace("\\", ".");
                    classFiles.add(absoltePath);
                }
            } else {
                findClassFiles(f);
            }
        }
        return classFiles;
    }
}
