package com.training.periodical.entity;

/**
 * Subsctiption entity.
 */
public class Subscription extends Entity {

    private Long userId;

    private Long magazineId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(Long magazineId) {
        this.magazineId = magazineId;
    }


    @Override
    public String toString() {
        return "Order [userId=" + userId + ", magazineId="
                + magazineId + "]";
    }

}
