// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.documents.generated;


public class ErrorMessage {
    protected java.lang.String errorType;
    protected java.lang.String message;
    protected java.lang.String regExp;
    protected java.lang.String system;
    
    public ErrorMessage() {
    }
    
    public ErrorMessage(java.lang.String errorType, java.lang.String message, java.lang.String regExp, java.lang.String system) {
        this.errorType = errorType;
        this.message = message;
        this.regExp = regExp;
        this.system = system;
    }
    
    public java.lang.String getErrorType() {
        return errorType;
    }
    
    public void setErrorType(java.lang.String errorType) {
        this.errorType = errorType;
    }
    
    public java.lang.String getMessage() {
        return message;
    }
    
    public void setMessage(java.lang.String message) {
        this.message = message;
    }
    
    public java.lang.String getRegExp() {
        return regExp;
    }
    
    public void setRegExp(java.lang.String regExp) {
        this.regExp = regExp;
    }
    
    public java.lang.String getSystem() {
        return system;
    }
    
    public void setSystem(java.lang.String system) {
        this.system = system;
    }
}
