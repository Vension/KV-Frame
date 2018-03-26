package com.vension.customview.indexable_layout;

/**
 * Created by YoKey on 16/10/6.
 */
class EntityWrapper<T> {
    static final int TYPE_TITLE = Integer.MAX_VALUE - 1;
    static final int TYPE_CONTENT = Integer.MAX_VALUE;

    static final int TYPE_HEADER = 1;
    static final int TYPE_FOOTER = 2;

    private String index;
    private String indexTitle;
    private String pinyin;
    private String indexByField;
    private T data;
    private int originalPosition = -1;
    private int itemType = TYPE_CONTENT;
    private int headerFooterType;

    EntityWrapper() {
    }

    EntityWrapper(String index, int itemType) {
        this.index = index;
        this.indexTitle = index;
        this.pinyin = index;
        this.itemType = itemType;
    }

    String getIndex() {
        return index;
    }

    void setIndex(String index) {
        this.index = index;
    }

    String getIndexTitle() {
        return indexTitle;
    }

    void setIndexTitle(String indexTitle) {
        this.indexTitle = indexTitle;
    }

    String getPinyin() {
        return pinyin;
    }

    void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    String getIndexByField() {
        return indexByField;
    }

    void setIndexByField(String indexByField) {
        this.indexByField = indexByField;
    }

    T getData() {
        return data;
    }

    void setData(T data) {
        this.data = data;
    }

    int getOriginalPosition() {
        return originalPosition;
    }

    void setOriginalPosition(int originalPosition) {
        this.originalPosition = originalPosition;
    }

    int getItemType() {
        return itemType;
    }

    void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getHeaderFooterType() {
        return headerFooterType;
    }

    void setHeaderFooterType(int headerFooterType) {
        this.headerFooterType = headerFooterType;
    }
}