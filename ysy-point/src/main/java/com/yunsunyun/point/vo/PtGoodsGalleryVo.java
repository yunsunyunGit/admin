package com.yunsunyun.point.vo;

import com.yunsunyun.point.po.PtGoodsGallery;

//后台修改时用
public class PtGoodsGalleryVo extends PtGoodsGallery {
    private  String reallyPath;
    private  String imageName;

    public String getReallyPath() {
        return reallyPath;
    }

    public void setReallyPath(String reallyPath) {
        this.reallyPath = reallyPath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
