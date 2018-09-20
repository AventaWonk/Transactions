package ru.iac.testtask.util;

import java.util.List;

public class OrderByStatementUtil {

    private boolean isOrderValid;
    private boolean isColumnValid;
    private List<String> sortableColumns;
    private String direction;
    private String column;

    public OrderByStatementUtil(List<String> sortableColumns, String column, String direction) {
        this.sortableColumns = sortableColumns;
        this.column = column;
        this.direction = direction;

        this.isColumnValid = OrderByStatementUtil.checkColumnValidity(sortableColumns, column);
        this.isOrderValid = OrderByStatementUtil.checkOrderValidity(direction);
    }

    /**
     * Returns validity of specified ordering direction and column
     *
     * @return result of order and column validation
     */
    public boolean isValid() {
        return this.isColumnValid && this.isOrderValid;
    }

    public String getDirection() {
        return direction;
    }

    public OrderByStatementUtil setDirection(String direction) {
        this.direction = direction;
        this.isOrderValid = OrderByStatementUtil.checkOrderValidity(direction);

        return this;
    }

    public String getColumn() {
        return column;
    }

    public OrderByStatementUtil setColumn(String column) {
        this.column = column;
        this.isColumnValid = OrderByStatementUtil.checkColumnValidity(sortableColumns, column);

        return this;
    }

    protected static boolean checkColumnValidity(List<String> availableColumns, String column) {
        return availableColumns.contains(column);
    }

    protected static boolean checkOrderValidity(String direction) {
        return direction.equals("asc") || direction.equals("desc");
    }

}
