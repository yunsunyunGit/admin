package com.yunsunyun.point.vo;

import java.util.List;

public class GoodsGalleryVo {
    private Long id;
    private String name;
    private Double point;
    private Double referencePrice;
    private String imageUrl;
    private List<GalleryVo> detailPics;
    private List<GalleryVo> carouselPics;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public Double getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(Double referencePrice) {
        this.referencePrice = referencePrice;
    }

    public List<GalleryVo> getDetailPics() {
        return detailPics;
    }

    public void setDetailPics(List<GalleryVo> detailPics) {
        this.detailPics = detailPics;
    }

    public List<GalleryVo> getCarouselPics() {
        return carouselPics;
    }

    public void setCarouselPics(List<GalleryVo> carouselPics) {
        this.carouselPics = carouselPics;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
