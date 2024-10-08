package com.enychat.model;

public class UserObjectModel {
    private String mMemberId;
    private String mName;
    private String mAboutUs;
    private String mNumber;
    private String mPic;

    public UserObjectModel(String mMemberId, String mName, String mAboutUs, String mNumber, String mPic) {
        this.mMemberId = mMemberId;
        this.mName = mName;
        this.mAboutUs = mAboutUs;
        this.mNumber = mNumber;
        this.mPic = mPic;
    }

    public String getmMemberId() {
        return mMemberId;
    }

    public String getmName() {
        return mName;
    }

    public String getmAboutUs() {
        return mAboutUs;
    }

    public String getmNumber() {
        return mNumber;
    }

    public String getmPic() {
        return mPic;
    }
}
