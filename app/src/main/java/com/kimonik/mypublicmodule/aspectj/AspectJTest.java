package com.kimonik.mypublicmodule.aspectj;

import android.util.Log;
import android.view.View;

import com.kimonik.utilsmodule.mvp.Contract;
import com.kimonik.utilsmodule.utils.LUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * * ===============================================================
 * name:             AspectJTest
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/7/23
 * method:
 * <p>
 * <p>
 * description：
 * history：https://www.jianshu.com/p/f90e04bcb326
 * *==================================================================
 */
@Aspect
public class AspectJTest {
    final String TAG = AspectJTest.class.getSimpleName();

//    /**
//     * 在指定方法之前执行
//     * @param joinPoint  切点
//     * @throws Throwable  异常
//     */
//    @Before("execution(* *..HomeActivity.initView(..))")
//    public void method(JoinPoint joinPoint) throws Throwable {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        String className = joinPoint.getThis().getClass().getSimpleName();
//
//        Log.e(TAG, "class切点插入:" + className);
//        Log.e(TAG, "method切点插入:" + methodSignature.getName());
//    }
//    /**
//     * 在指定方法之后执行
//     * @param joinPoint  切点
//     */
//    @After("execution(* *..HomeActivity.initView(..))")
//    public void method1(JoinPoint joinPoint){
//        Log.e(TAG, "class切点插入:方法执行结束后");
//    }


//    /**
//     * 不能与@before和@after同时使用
//     *不是用joinPoint.proceed();时原方法不会被执行
//     * @param joinPoint  切点
//     */
//    @Around("execution(* *..HomeActivity.initView(..))")
//    public void method2(ProceedingJoinPoint joinPoint) throws Throwable {
//        Log.e(TAG, "class切点插入:原方法不会被执行");
//        //添加该方法后原方法才会被执行
//        joinPoint.proceed();
//    }
//android.support.v7.app
    //ceshi(..)中的..表示任意长度任意类型的参数
    //   *：匹配任何数量字符；

    //    ..：匹配任何数量字符的重复，如在类型模式中匹配任何数量子包；
    // 而在方法参数模式中匹配任何数量参数。
    //       +：匹配指定类型的子类型；仅能作为后缀放在类型模式后边。

    /**
     * aspectj语法
     * https://blog.csdn.net/kjfcpua/article/details/7513273
     java.lang.String    匹配String类型；
     java.*.String       匹配java包下的任何“一级子包”下的String类型；
     如匹配java.lang.String，但不匹配java.lang.ss.String
     java..*            匹配java包及任何子包下的任何类型;
     如匹配java.lang.String、java.lang.annotation.Annotation
     java.lang.*ing      匹配任何java.lang包下的以ing结尾的类型；
     java.lang.Number+  匹配java.lang包下的任何Number的自类型；
     如匹配java.lang.Integer，也匹配java.math.BigInteger
     */
    @Around("execution(* *..HomeActivity.ceshi(..))")
    public void method2(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] objects = joinPoint.getArgs();
        for (int i = 0; i < objects.length; i++) {
            //执行方法时传入的参数列表
            Log.e("TAG", "method2: ???" + objects[i]);
        }
        //传入新的参数替代原来的参数
        joinPoint.proceed(new Object[]{"这是修改后的参数土豆金服"});
        Log.e("TAG", "method2: 拦截系统方法后");

    }

    @Around("execution(* android.app.Activity.onCreate(..))")
    public void method3(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();
        LUtils.getMethodTrace(getClass());

        LUtils.e(AspectJTest.class,"logflag---dispatchTouchEvent方法一致性");

    }

}
