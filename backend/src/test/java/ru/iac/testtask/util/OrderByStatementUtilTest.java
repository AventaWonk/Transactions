package ru.iac.testtask.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderByStatementUtilTest {

    private static List<String> sortableColumns =  Arrays.asList(
            "name",
            "product",
            "address"
    );

    @Test
    void isValid() {
        OrderByStatementUtil invalidColAndDirection= new OrderByStatementUtil(sortableColumns, "3", "4");
        assertFalse(invalidColAndDirection.isValid());

        OrderByStatementUtil invalidDirection = new OrderByStatementUtil(sortableColumns, "name", "d");
        assertFalse(invalidDirection.isValid());

        OrderByStatementUtil invalidColumn = new OrderByStatementUtil(sortableColumns, "3", "asc");
        assertFalse(invalidColumn.isValid());

        OrderByStatementUtil validAscOrder = new OrderByStatementUtil(sortableColumns, "address", "asc");
        assertTrue(validAscOrder.isValid());
        assertEquals("address", validAscOrder.getColumn());
        assertEquals("asc", validAscOrder.getDirection());

        OrderByStatementUtil validDescOrder = new OrderByStatementUtil(sortableColumns, "name", "desc");
        assertTrue(validDescOrder.isValid());
        assertEquals("name", validDescOrder.getColumn());
        assertEquals("desc", validDescOrder.getDirection());
    }

}