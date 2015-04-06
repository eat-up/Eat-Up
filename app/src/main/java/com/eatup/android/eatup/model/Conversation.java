package com.eatup.android.eatup.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Ariel on 4/5/15.
 */
@ParseClassName("Conversation")
public class Conversation extends ParseObject {
    public String getGroupId() {
        return getString("userId");
    }

    public String getConversation() {
        return getString("body");
    }

    public void setGroupId(String groupId) {
        put("groupId", groupId);
    }

    public void setConversation(String[] conversation) {
        put("conversation", conversation);
    }
}
