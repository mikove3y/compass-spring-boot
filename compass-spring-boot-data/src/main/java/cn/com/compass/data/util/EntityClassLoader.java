package cn.com.compass.data.util;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/5/2 22:13
 */
public class EntityClassLoader extends ClassLoader{

    private ClassLoader parent;

    public EntityClassLoader(ClassLoader parent) {
        this.parent = parent;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> clazz = this.loadClass(name, false);
        // 包装一下
        try {
            clazz = LogicDeleteUtil.addDeleteAnnotation2DomainClass(clazz);
        } catch (Exception e) {
            throw new ClassNotFoundException(e.getMessage(),e);
        }
        return clazz;
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = this.findLoadedClass(name);
        if (null != parent)
            clazz = parent.loadClass(name);
        if (null == clazz)
            this.findSystemClass(name);
        if (null == clazz)
            throw new ClassNotFoundException();
        if (resolve)
            this.resolveClass(clazz);
        return clazz;
    }
}
