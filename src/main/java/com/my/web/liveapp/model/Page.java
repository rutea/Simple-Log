package com.my.web.liveapp.model;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public final static int DEF_PAGESIZE = 30;

    private int startIndex = 0;

    private int pageSize = DEF_PAGESIZE;

    private int totalCount;

    private List<T> items;

    public Page(List<T> items, int totalCount, int startIndex) {
        setPageSize(DEF_PAGESIZE);
        setTotalCount(totalCount);
        setItems(items);
        setStartIndex(startIndex);
    }

    public Page(List<T> items, int totalCount, int startIndex, int pageSize) {
        setPageSize(pageSize);
        setTotalCount(totalCount);
        setItems(items);
        setStartIndex(startIndex);
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPageSize() {
        return pageSize;
    }

    private void setPageSize(int pageSize) {
        if (pageSize < 5) {
            this.pageSize = 5;
        } else if (pageSize > 200) {
            this.pageSize = 200;
        } else {
            this.pageSize = pageSize;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    private void setTotalCount(int totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
        } else {
            this.totalCount = 0;
        }
    }

    public int getStartIndex() {
        return startIndex;
    }

    private void setStartIndex(int startIndex) {
        if ((totalCount <= 0) || (startIndex < 0)) {
            this.startIndex = 0;
        } else if (startIndex >= totalCount) {
            int pageCount = totalCount / pageSize;
            if (totalCount % pageSize > 0)
                pageCount++;
            this.startIndex = pageSize * (pageCount - 1);
        } else {
            this.startIndex = startIndex;
        }
    }

    public int getNextIndex() {
        int nextIndex = getStartIndex() + pageSize;
        if (nextIndex >= totalCount)
            return getStartIndex();
        else
            return nextIndex;
    }

    public int getPrevIndex() {
        int prevIndex = getStartIndex() - pageSize;
        if (prevIndex < 0)
            return 0;
        else
            return prevIndex;
    }

    public boolean isFirstPage() {
        return (getPrevIndex() == 0);
    }

    public boolean isLastPage() {
        return (getNextIndex() == getStartIndex());
    }

}
