// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.auth.csa.back.integration.ipas.generated;


public class VerifyPasswordRqType extends com.rssl.auth.csa.back.integration.ipas.generated.CommonRqType {
    protected java.lang.String userId;
    protected java.lang.String password;
    
    public VerifyPasswordRqType() {
    }
    
    public VerifyPasswordRqType(java.lang.String STAN, java.lang.String userId, java.lang.String password) {
        this.STAN = STAN;
        this.userId = userId;
        this.password = password;
    }
    
    public java.lang.String getUserId() {
        return userId;
    }
    
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    public java.lang.String getPassword() {
        return password;
    }
    
    public void setPassword(java.lang.String password) {
        this.password = password;
    }
}
