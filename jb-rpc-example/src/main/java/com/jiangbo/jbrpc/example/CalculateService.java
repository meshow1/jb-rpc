package com.jiangbo.jbrpc.example;

/**
 * 远程分布式计算器样例
 */
public interface CalculateService {
    int add(int a, int b);
    int minus(int a, int b);
}
