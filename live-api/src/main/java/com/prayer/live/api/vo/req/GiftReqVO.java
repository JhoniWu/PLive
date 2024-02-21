package com.prayer.live.api.vo.req;

/**
 * @Author idea
 * @Date: Created in 10:58 2023/8/6
 * @Description
 */
public class GiftReqVO {

    private int giftId;
    private Integer roomId;
    private Long senderUserId;
    private Long receiverId;

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public Long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "GiftReqVO{" +
                "giftId=" + giftId +
                ", roomId=" + roomId +
                ", senderUserId=" + senderUserId +
                ", receiverId=" + receiverId +
                '}';
    }
}
