// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.bars.ws.jaxrpc;


public class XsbResult {
    protected int errorId;
    protected java.lang.String fullMsg;
    protected int status;
    protected java.lang.String refType;
    protected int procCode;
    protected int priority;
    
    public XsbResult() {
    }
    
    public XsbResult(int errorId, java.lang.String fullMsg, int status, java.lang.String refType, int procCode, int priority) {
        this.errorId = errorId;
        this.fullMsg = fullMsg;
        this.status = status;
        this.refType = refType;
        this.procCode = procCode;
        this.priority = priority;
    }
    
    public int getErrorId() {
        return errorId;
    }
    
    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }
    
    public java.lang.String getFullMsg() {
        return fullMsg;
    }
    
    public void setFullMsg(java.lang.String fullMsg) {
        this.fullMsg = fullMsg;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public java.lang.String getRefType() {
        return refType;
    }
    
    public void setRefType(java.lang.String refType) {
        this.refType = refType;
    }
    
    public int getProcCode() {
        return procCode;
    }
    
    public void setProcCode(int procCode) {
        this.procCode = procCode;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
