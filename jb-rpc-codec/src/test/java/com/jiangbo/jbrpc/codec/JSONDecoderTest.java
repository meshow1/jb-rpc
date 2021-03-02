package com.jiangbo.jbrpc.codec;

import junit.framework.TestCase;

public class JSONDecoderTest extends TestCase {

    public void testDecode() {
        Encoder encoder = new JSONEncoder();
        TestBean bean = new TestBean();
        bean.setName("将");
        bean.setAge(24);
        byte[] bytes = encoder.encoder(bean);

        Decoder decoder = new JSONDecoder();
        assertEquals(decoder.decode(bytes, TestBean.class), bean);
    }
}