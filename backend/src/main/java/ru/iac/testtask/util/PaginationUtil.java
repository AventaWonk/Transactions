package ru.iac.testtask.util;

public class PaginationUtil {

    /**
     * Returns total number of pages by per page limit and
     * total entity count
     *
     * @param total count of all available entities
     * @param limit maximum count of entities per page
     * @return count of available pages
     */
    public static int calculatePageCount(int total, int limit) {
        return (int) Math.ceil(total / (float) limit);
    }

    public static int calculateOffset(int page, int limit) {
        return (page - 1) * limit;
    }

}
