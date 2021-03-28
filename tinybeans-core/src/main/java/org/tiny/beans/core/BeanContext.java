package org.tiny.beans.core;

import org.tiny.beans.sdk.*;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanContext {

    public BeanContext(Class configClass) {
        //设置配置类
        this.configClass = configClass;

        //扫描并加载
        List<Class> classes = scan();

        //类解析并放到池子中
        for (Class clazz : classes) {
            if (clazz.isAnnotationPresent(Bean.class)) {

                //如果实现了beanpost接口
                if(BeanPost.class.isAssignableFrom(clazz)){
                    try {
                        BeanPost o = (BeanPost) clazz.getDeclaredConstructor().newInstance();
                        beanPosts.add(o);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }

                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setBeanClass(clazz);

                Bean beanAnnotation = (Bean) clazz.getAnnotation(Bean.class);
                String beanName = beanAnnotation.value();

                //1. 解析原型模式/单例模式
                //原型
                if (clazz.isAnnotationPresent(Scope.class)) {
                    Scope scopeAnnotation = (Scope) clazz.getAnnotation(Scope.class);
                    beanDefinition.setScope(scopeAnnotation.value());
                }
                //单例
                else {
                    beanDefinition.setScope("singleton");
                }
                beanDefinitionMap.put(beanName, beanDefinition);

                //2. 解析懒加载模式
                //TODO
            }
        }

        //2. 实例化bean
        for(String beanName: beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            //创建单例bean
            if (beanDefinition.getScope().equals("singleton")) {
                if (!singletonObjectPool.containsKey(beanName)) {
                    Object bean = createBean(beanName, beanDefinition);
                    singletonObjectPool.put(beanName, bean);
                }
            }
        }

    }

    private Class configClass;

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private Map<String, Object> singletonObjectPool = new ConcurrentHashMap<>();

    private List<BeanPost> beanPosts = new ArrayList<>();

    /**
     * bean扫描并加载
     */
    private List<Class> scan() {
        List<Class> classList = new ArrayList<>();
        //扫描包路径
        BeanScan beanScanAnnotation = (BeanScan) configClass.getAnnotation(BeanScan.class);
        String path = beanScanAnnotation.value();
        path = path.replace(".","/");

        //利用类加载器获取路径
        ClassLoader classLoader = BeanContext.class.getClassLoader();
        URL resource = classLoader.getResource(path);

        //得到文件夹并加载到classloader中
        File file = new File(resource.getFile());
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String absoltePath = f.getAbsolutePath();
                absoltePath = absoltePath.substring(absoltePath.indexOf("org"), absoltePath.indexOf("class"));
                absoltePath = absoltePath.replace("\\", ".");
                try {
                    Class<?> clazz = classLoader.loadClass(absoltePath);
                    //这里需要判断下annotation是否存在
                    classList.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classList;
    }

    public Object createBean(String beanName, BeanDefinition beanDefinition) {
        try {
            //1. 类实例化
            Class beanClass = beanDefinition.getBeanClass();
            Object object = beanClass.getDeclaredConstructor().newInstance();

            //2. 属性填充, 将userService中的user对象填充进去
            Field[] declaredFields = beanClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if(declaredField.isAnnotationPresent(Inject.class)) {
                    Object bean = getBean(declaredField.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(object, bean);
                }
            }

            //3. 初始化前处理
            for (BeanPost beanPost : beanPosts) {
                object = beanPost.postProcessBeforeInitialization(beanPost, beanName);
            }

            //4. 用户自定义初始化
            if(object instanceof BeanInit) {
                ((BeanInit) (object)).afterPropertiesSet();
            }

            //5. 初始化后处理
            for (BeanPost beanPost : beanPosts) {
                object = beanPost.postProcessAfterInitialization(beanPost, beanName);
            }
            return object;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        //原型模式，每次都需要创建bean
        if (beanDefinition.getScope().equals("prototype")) {
            Object bean = createBean(beanName, beanDefinition);
            return bean;
        }
        //单例模式，直接返回原有bean
        else if (beanDefinition.getScope().equals("singleton")) {
            Object object = singletonObjectPool.get(beanName);
            if (object == null) {
                object = createBean(beanName, beanDefinition);
                singletonObjectPool.put(beanName, beanDefinition);
            }
            return object;
        }
        return null;
    }

}
