package com.eproe.monitor.was.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextUtil
        implements ApplicationContextAware
{
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static Object getBean(String beanName)
    {
        return context.getBean(beanName);
    }
}