package com.sky.search.publisher.app;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.zookeeper.config.ZookeeperPropertySource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;


public class ZooApplication {

    @Value("${kumar.name}")
    private String name;

    @Autowired
    private TestBean testBean;

    private CuratorFramework getCuratorFramework(String namespace) {
        return CuratorFrameworkFactory.builder().namespace(namespace).connectString(getZookeeperHosts())
                .retryPolicy(new RetryOneTime(500)).build();
    }

    private String getZookeeperHosts() {
        return "localhost:2181";
    }

    public static void main(String... args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(ZooApplication.class);
        TestBean zooApplication = context.getBean(TestBean.class);
        zooApplication.printName();
    }

    private void printName(){
        testBean.printName();
    }
    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();

        CuratorFramework curatorFramework = getCuratorFramework("");
        curatorFramework.start();
        ZookeeperPropertySource zookeeperProperty = new ZookeeperPropertySource("kumar", curatorFramework);
        for (String s : zookeeperProperty.getPropertyNames()) {
            System.out.println(s + ": " + zookeeperProperty.getProperty(s));
        }
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addFirst(zookeeperProperty);
        ppc.setPropertySources(propertySources);
        return ppc;
    }
}
