// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.auth.csa.back.integration.ipas.generated;


public class VerifyRsType extends com.rssl.auth.csa.back.integration.ipas.generated.CommonRsType {
    protected com.rssl.auth.csa.back.integration.ipas.generated.UserInfoType userInfo;
    protected java.lang.String token;
    
    public VerifyRsType() {
    }
    
    public VerifyRsType(java.lang.String STAN, java.lang.String status, com.rssl.auth.csa.back.integration.ipas.generated.UserInfoType userInfo, java.lang.String token) {
        this.STAN = STAN;
        this.status = status;
        this.userInfo = userInfo;
        this.token = token;
    }
    
    public com.rssl.auth.csa.back.integration.ipas.generated.UserInfoType getUserInfo() {
        return userInfo;
    }
    
    public void setUserInfo(com.rssl.auth.csa.back.integration.ipas.generated.UserInfoType userInfo) {
        this.userInfo = userInfo;
    }
    
    public java.lang.String getToken() {
        return token;
    }
    
    public void setToken(java.lang.String token) {
        this.token = token;
    }
}