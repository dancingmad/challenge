package com.irongrp.challenge;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PackagerTest {

    @Test
    void allTests() throws Exception {
        String[] results = Packager.execute(getTestfile("testfile.txt")).split("\n");
        assertEquals(4, results.length);
        assertEquals("4",results[0]);
        assertEquals("-",results[1]);
        assertEquals("2,7",results[2]);
        assertEquals("8,9",results[3]);
        int x = 10;
        int n = 0;
        n += x>0?1:0;
        List<Integer> integers = new ArrayList<>();
        integers.stream().map(
                number -> integers.stream().filter(
                        other -> Math.abs(number-other)<=1
                ).count()
        ).max(Comparator.naturalOrder());
    }

    @Test
    void given_inputWithLongValues_when_execute_then_returnItemWithLongIndex() throws Exception {
        String[] results = Packager.execute(getTestfile("boundariesInput.txt")).split("\n");
        assertEquals(1, results.length);
        assertEquals("5000000000",results[0]);
    }

    @Test
    void given_toughChoiceInput_when_execute_then_returnLowerTwoItems() throws Exception {
        String[] results = Packager.execute(getTestfile("toughChoice.txt")).split("\n");
        assertEquals(3, results.length);
        assertEquals("1",results[0]);
        assertEquals("1,4",results[1]);
        assertEquals("2,3,9,10,11,12,13,14,15",results[2]);
    }

    private String getTestfile(String filename) {
        return ClassLoader.getSystemResource(filename).getFile();
    }


}