package com.example.mycooking.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by liaozhihua on 2016/9/9.
 */
public class PickerViewData implements IPickerViewData {
    private String content;

    public PickerViewData(String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getPickerViewText() {
        return content;
    }
}
