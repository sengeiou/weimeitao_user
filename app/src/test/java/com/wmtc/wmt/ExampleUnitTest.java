package com.wmtc.wmt;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        int[] arr = {1, 2, 3, 4, 5, 1, 2, 3};
        Map<Integer, Integer> integerIntegerMap = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            integerIntegerMap.put(arr[i], arr[i]);
        }
        System.out.println(integerIntegerMap.toString());
    }
}