// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.documents.update.generated;


public class WriteDownOperation {
    protected com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Money curAmount;
    protected java.lang.String operationName;
    protected java.lang.String turnOver;
    
    public WriteDownOperation() {
    }
    
    public WriteDownOperation(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Money curAmount, java.lang.String operationName, java.lang.String turnOver) {
        this.curAmount = curAmount;
        this.operationName = operationName;
        this.turnOver = turnOver;
    }
    
    public com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Money getCurAmount() {
        return curAmount;
    }
    
    public void setCurAmount(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Money curAmount) {
        this.curAmount = curAmount;
    }
    
    public java.lang.String getOperationName() {
        return operationName;
    }
    
    public void setOperationName(java.lang.String operationName) {
        this.operationName = operationName;
    }
    
    public java.lang.String getTurnOver() {
        return turnOver;
    }
    
    public void setTurnOver(java.lang.String turnOver) {
        this.turnOver = turnOver;
    }
}
