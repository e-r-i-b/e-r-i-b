// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.test.way4.ws.mock.generated;


public class VerifyAttRsType extends com.rssl.phizic.test.way4.ws.mock.generated.CommonRsType {
    protected com.rssl.phizic.test.way4.ws.mock.generated.UserInfoType userInfo;
    protected java.lang.String token;
    protected java.lang.Integer attempts;
    
    public VerifyAttRsType() {
    }
    
    public VerifyAttRsType(java.lang.String STAN, java.lang.String status, com.rssl.phizic.test.way4.ws.mock.generated.UserInfoType userInfo, java.lang.String token, java.lang.Integer attempts) {
        this.STAN = STAN;
        this.status = status;
        this.userInfo = userInfo;
        this.token = token;
        this.attempts = attempts;
    }
    
    public com.rssl.phizic.test.way4.ws.mock.generated.UserInfoType getUserInfo() {
        return userInfo;
    }
    
    public void setUserInfo(com.rssl.phizic.test.way4.ws.mock.generated.UserInfoType userInfo) {
        this.userInfo = userInfo;
    }
    
    public java.lang.String getToken() {
        return token;
    }
    
    public void setToken(java.lang.String token) {
        this.token = token;
    }
    
    public java.lang.Integer getAttempts() {
        return attempts;
    }
    
    public void setAttempts(java.lang.Integer attempts) {
        this.attempts = attempts;
    }
}
