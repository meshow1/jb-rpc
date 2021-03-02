package com.jiangbo.jbrpc.codec;

import junit.framework.TestCase;

public class JSONEncoderTest extends TestCase {

    public void testEncoder() {
        Encoder encoder = new JSONEncoder();
        TestBean bean = new TestBean();
        bean.setName("将");
        bean.setAge(24);
        byte[] bytes = encoder.encoder(bean);
        assertNotNull(bytes);


    }
}