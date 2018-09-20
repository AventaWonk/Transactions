package ru.iac.testtask.util;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SqlUtilTest {

    @Test
    void getOrderByStatement() {
        OrderByStatementUtil orderUtil = mock(OrderByStatementUtil.class);
        when(orderUtil.getDirection()).thenReturn("asc");
        when(orderUtil.getColumn()).thenReturn("name");
        when(orderUtil.isValid()).thenReturn(false).thenReturn(true).thenReturn(true);

        String emptyOrderResult = SqlUtil.getOrderByStatement("", orderUtil);
        assertEquals("", emptyOrderResult);

        String orderResultWithoutAlias = SqlUtil.getOrderByStatement("", orderUtil);
        assertEquals(" ORDER BY name asc", orderResultWithoutAlias);

        String orderResultWithAlias = SqlUtil.getOrderByStatement("s", orderUtil);
        assertEquals(" ORDER BY s.name asc", orderResultWithAlias);
    }
}