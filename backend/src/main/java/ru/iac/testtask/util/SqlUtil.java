package ru.iac.testtask.util;

public class SqlUtil {

    /**
     * Returns generated ORDER BY statement or empty string
     *
     * @param alias alias of requested entity
     * @param order sql order direction (asc or desc)
     * @return sql statement depending on the validity of order
     */
    public static String getOrderByStatement(String alias, OrderByStatementUtil order) {
        if (!order.isValid()) {
            return "";
        }

        String prefix = alias.length() > 0 ? alias + "." : "";

        return " ORDER BY " + prefix + order.getColumn() + " " + order.getDirection();
    }

}
