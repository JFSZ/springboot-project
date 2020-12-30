package com.zz.springbootproject;

import com.zz.springbootproject.module.job.entity.ScheduleJobEntity;
import com.zz.springbootproject.module.job.utils.ScheduleUtils;
import com.zz.springbootproject.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.function.Consumer;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringbootProjectApplicationTests {
    private Consumer c = System.out::println;

    @Autowired
    private Scheduler scheduler;

    @Test
    public void test() {
        ScheduleJobEntity scheduleJobEntity = new ScheduleJobEntity();
        scheduleJobEntity.setBeanName("myTask");
        scheduleJobEntity.setJobId(123L);
        scheduleJobEntity.setParams("Tom");
        scheduleJobEntity.setCronExpression("0/10 * * * * ?");
        ScheduleUtils.runJob(scheduler, scheduleJobEntity);
        while (true) {

        }
    }

    @Autowired
    private RedisUtils redisUtils;

    //测试redis缓存
    @Test
    public void test1() {
        Object hget = redisUtils.hget("sysConfig", "test");
        System.out.println(hget);
    }

    @Test
    public void test2(){
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource(""));
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MyConfig.class);
        applicationContext.refresh();
        MyConfig myConfig = (MyConfig)applicationContext.getBean("myConfig");
        myConfig.doWork();
    }

    @Test
    public void test3(){
        boolean b = redisUtils.lSet("test", Collections.singletonList("Tom").add("Jack"));
        System.out.println(b);
    }


}
class ReplaceTest{
    private Consumer c = System.out::println;
    public void doWork(){
        c.accept("ReplaceTest 方法");
    }
}

class ReplaceMethod implements MethodReplacer{

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println("替换方法开始。。。");
        return obj;
    }
}






class Fruit{
    public Fruit(){
        System.out.println("fruit");
    }
}

class Apple extends Fruit{
    public Apple(){
        System.out.println("apple");
    }
}

class Bananer extends Fruit{
    public Bananer() {
        System.out.println("bananer");
    }
}

@Component("myConfig")
@Slf4j
abstract class MyConfig{

    @Bean("apple")
    @Scope("prototype")
    public Apple getApple(){
        return new Apple();
    }
    @Bean("bananer")
    public Bananer getBananer(){
        return new Bananer();
    }
    public void doWork(){
        Fruit fruit1 = getFruit1();
        Fruit fruit11 = getFruit11();
        Fruit fruit2 = getFruit2();
        Fruit fruit21 = getFruit21();
        log.info("Fruit1's hashcode is : {}",fruit1.hashCode());
        log.info("Fruit1's hashcode is : {}",fruit11.hashCode());
        log.info("Fruit2's hashcode is : {}",fruit2.hashCode());
        log.info("Fruit2's hashcode is : {}",fruit21.hashCode());

    }
    @Lookup("apple")
    protected abstract Fruit getFruit1();

    @Lookup("apple")
    protected abstract Fruit getFruit11();

    @Lookup("bananer")
    protected abstract Fruit getFruit2();
    @Lookup("bananer")
    protected abstract Fruit getFruit21();

}

@Component
@Scope("prototype")
abstract class FruitPlate{
     protected abstract Fruit getFruit();
}