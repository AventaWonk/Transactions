package ru.iac.testtask.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaginationUtilTest {

    @Test
    void calculatePageCount() {
        assertEquals(24, PaginationUtil.calculatePageCount(234, 10));
        assertEquals(1, PaginationUtil.calculatePageCount(1, 1));
        assertEquals(1, PaginationUtil.calculatePageCount(1, 10));
        assertEquals(4, PaginationUtil.calculatePageCount(11, 3));
    }

    @Test
    void calculateOffset() {
        assertEquals(0, PaginationUtil.calculateOffset(1, 5));
        assertEquals(110, PaginationUtil.calculateOffset(11, 11));
        assertEquals(1, PaginationUtil.calculateOffset(2, 1));
    }
}