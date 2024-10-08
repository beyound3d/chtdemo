package com.enychat.model;

public class ChatTextModel {
    private String MemberId;
    private String mMemberIdTo;
    private String TextMessage;
    private String msgPostID;
    private String msgType;

    public ChatTextModel(String memberId, String mMemberIdTo, String textMessage, String msgPostID, String msgType) {
        MemberId = memberId;
        this.mMemberIdTo = mMemberIdTo;
        TextMessage = textMessage;
        this.msgPostID = msgPostID;
        this.msgType = msgType;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getmMemberIdTo() {
        return mMemberIdTo;
    }

    public void setmMemberIdTo(String mMemberIdTo) {
        this.mMemberIdTo = mMemberIdTo;
    }

    public String getTextMessage() {
        return TextMessage;
    }

    public void setTextMessage(String textMessage) {
        TextMessage = textMessage;
    }

    public String getMsgPostID() {
        return msgPostID;
    }

    public void setMsgPostID(String msgPostID) {
        this.msgPostID = msgPostID;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
