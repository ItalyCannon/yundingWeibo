package com.yundingweibo.domain;

import java.util.List;

/**
 * @author 杜奕明
 * @date 2019/2/21 11:57
 */
public class PageBean<T> {
    private int pageCode;
    private int totalRecord;
    private int pageSize;
    private List<T> beanList;

    public int getPageCode() {
        return pageCode;
    }

    public void setPageCode(int pageCode) {
        this.pageCode = pageCode;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<T> beanList) {
        this.beanList = beanList;
    }

    public int getTotalPage() {
        if (totalRecord <= pageSize) {
            return 1;
        }
        if (totalRecord % pageSize != 0) {
            return totalRecord / pageSize + 1;
        } else {
            return totalRecord / pageSize;
        }

    }
}
