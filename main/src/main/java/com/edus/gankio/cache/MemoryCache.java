package com.edus.gankio.cache;

import java.util.List;

public class MemoryCache<Data> {

    private List<Data> dataList;
    private int pageIndex;
    private int pageSize;
    private boolean hasMore;

    public MemoryCache(int pageSize){
        this.pageSize = pageSize;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
