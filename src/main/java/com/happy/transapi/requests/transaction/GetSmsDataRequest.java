
package com.happy.transapi.requests.transaction;

import com.google.gson.annotations.Expose;

public class GetSmsDataRequest {

    @Expose
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
