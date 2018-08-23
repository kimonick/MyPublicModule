package com.kimonik.mypublicmodule;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testMatrix(){
        List<String> list=mock(List.class);
        when(list.get(0)).thenReturn("first");
        list.add("one");
        System.out.println(list.get(2));
        list.clear();
        verify(list).add("one");
        verify(list).clear();

    }
}