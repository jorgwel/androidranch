/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author jorgwel
 */
public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String time;
    private boolean mSolved;

    public Crime() {
        // Generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
    }
    
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
    
    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }
    public void setSolved(boolean solved) {
        mSolved = solved;
    }
    
    @Override
    public String toString() {
        return mTitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
}
