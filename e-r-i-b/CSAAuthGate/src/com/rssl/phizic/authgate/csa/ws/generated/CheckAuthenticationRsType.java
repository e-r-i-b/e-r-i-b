// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.authgate.csa.ws.generated;


public class CheckAuthenticationRsType {
    protected com.rssl.phizic.authgate.csa.ws.generated.StatusType status;
    protected java.lang.String service;
    protected com.rssl.phizic.authgate.csa.ws.generated.ArrayOfParamListTypeParam authData;
    
    public CheckAuthenticationRsType() {
    }
    
    public CheckAuthenticationRsType(com.rssl.phizic.authgate.csa.ws.generated.StatusType status, java.lang.String service, com.rssl.phizic.authgate.csa.ws.generated.ArrayOfParamListTypeParam authData) {
        this.status = status;
        this.service = service;
        this.authData = authData;
    }
    
    public com.rssl.phizic.authgate.csa.ws.generated.StatusType getStatus() {
        return status;
    }
    
    public void setStatus(com.rssl.phizic.authgate.csa.ws.generated.StatusType status) {
        this.status = status;
    }
    
    public java.lang.String getService() {
        return service;
    }
    
    public void setService(java.lang.String service) {
        this.service = service;
    }
    
    public com.rssl.phizic.authgate.csa.ws.generated.ArrayOfParamListTypeParam getAuthData() {
        return authData;
    }
    
    public void setAuthData(com.rssl.phizic.authgate.csa.ws.generated.ArrayOfParamListTypeParam authData) {
        this.authData = authData;
    }
}