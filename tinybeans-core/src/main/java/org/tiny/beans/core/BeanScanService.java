package org.tiny.beans.core;

import lombok.extern.slf4j.Slf4j;
import org.tiny.beans.core.exception.TbException;
import org.tiny.beans.core.exception.TbIOException;
import org.tiny.beans.core.model.BeanContext;
import org.tiny.beans.sdk.annotation.BeanScan;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shichaoyang
 * @Description: bean扫描器
 */
@Slf4j
public class BeanScanService {

    /**
     * 带参构造
     *
     * @param beanContext
     */
    public BeanScanService(BeanContext beanContext) {
        this.beanContext = beanContext;
    }

    //上下文对象
    private BeanContext beanContext;

    //文件路径开始字符 TODO 如果用户的包不是org开头的呢？
    private static final String START_WORDS = "org";

    //文件路径结束字符
    private static final String STOP_WORDS = ".class";

    //扫描包下的类文件集合
    private List<String> classFiles = new ArrayList<>();

    /**
     * bean扫描并加载
     */
    protected void scan() throws TbIOException, TbException {

        //加载配置文件并放到上下文
        Map<String, String> configPool = loadPackageConfig();
        beanContext.setConfigPool(configPool);

        //加载类文件并放到上下文
        List<Class> classes = loadPackageClass();
        beanContext.setClassPool(classes);
    }

    /**
     * 加载包中的配置文件
     * @return
     */
    private Map<String, String> loadPackageConfig() throws TbIOException, TbException {
        Map<String, String> configPool = new ConcurrentHashMap<>();
        BeanScan beanScanAnnotation = (BeanScan) beanContext.getConfigClass().getAnnotation(BeanScan.class);
        String packageConfig = beanScanAnnotation.packageConfig();
        //TODO biz
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = ClassLoader.getSystemResourceAsStream(packageConfig);
            properties.load(inputStream);
            Enumeration enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String strKey = (String) enumeration.nextElement();
                String strVal = properties.getProperty(strKey);
                System.out.println(strKey + "=" + strVal);
                if (!beanContext.getConfigPool().containsKey(strKey)) {
                    beanContext.getConfigPool().put(strKey, strVal);
                }else{
                    throw new TbException("found duplicate config node", null);
                }
            }
        } catch (IOException e) {
            log.error("exception when handle config file", e);
            throw new TbIOException("exception when handling config file", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("exception when close config file stream", e);
                }
            }
        }
        return configPool;
    }

    /**
     * 加载包中的类文件
     * @return
     */
    private List<Class> loadPackageClass(){
        List<Class> classList = new ArrayList<>();
        BeanScan beanScanAnnotation = (BeanScan) beanContext.getConfigClass().getAnnotation(BeanScan.class);

        //扫描包路径
        String path = beanScanAnnotation.packagePath();
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
