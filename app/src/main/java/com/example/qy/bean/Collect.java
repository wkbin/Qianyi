package com.example.qy.bean;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 09 日 下午 1:58
 * Description: 收藏Bean
 */
public class Collect {
    public String imageUrl;
    public String title;
    public String like;

    @Override
    public String toString() {
        return "Collect{" +
                "imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", like='" + like + '\'' +
                '}';
    }
}
