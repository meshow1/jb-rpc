package com.jiangbo.jbrpc.server;

import com.jiangbo.jbrpc.Request;
import com.jiangbo.jbrpc.ServiceDescriptor;
import com.jiangbo.jbrpc.common.utils.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;

public class ServiceManagerTest extends TestClass {
    ServiceManager sm;

    @Before
    public void init() {
        sm = new ServiceManager();

        TestClass bean = new TestClass();
        sm.registery(TestInterface.class, bean);
    }

    @Test
    public void testRegistery() {
        TestClass bean = new TestClass();
        sm.registery(TestInterface.class, bean);
    }

    @Test
    public void testLookup() {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestInterface.class);
        ServiceDescriptor sdp = ServiceDescriptor.from(TestInterface.class, publicMethods[0]);


        Request request = new Request();
        request.setService(sdp);
        ServiceInstance sis = sm.lookup(request);

        assertNotNull(sis);

    }
}