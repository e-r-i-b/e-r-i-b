// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.authgate.csa.ws.generated;


public class PrepareSessionRsType {
    protected com.rssl.phizic.authgate.csa.ws.generated.StatusType status;
    protected java.lang.String authToken;
    
    public PrepareSessionRsType() {
    }
    
    public PrepareSessionRsType(com.rssl.phizic.authgate.csa.ws.generated.StatusType status, java.lang.String authToken) {
        this.status = status;
        this.authToken = authToken;
    }
    
    public com.rssl.phizic.authgate.csa.ws.generated.StatusType getStatus() {
        return status;
    }
    
    public void setStatus(com.rssl.phizic.authgate.csa.ws.generated.StatusType status) {
        this.status = status;
    }
    
    public java.lang.String getAuthToken() {
        return authToken;
    }
    
    public void setAuthToken(java.lang.String authToken) {
        this.authToken = authToken;
    }
}