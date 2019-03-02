package com.example.qy.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 02 日 上午 11:52
 * Description: 承载城市数据类
 */
public class CityBean implements IPickerViewData {
    private String province;
    private List<String> city_list;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<String> getCity_list() {
        return city_list;
    }

    public void setCity_list(List<String> city_list) {
        this.city_list = city_list;
    }

    @Override
    public String getPickerViewText() {
        return this.province;
    }
}
