// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.authgate.csa.ws.generated;


public class SetAliasRqType {
    protected java.lang.String SID;
    protected java.lang.String userId;
    protected java.lang.String alias;
    protected java.lang.String newAlias;
    
    public SetAliasRqType() {
    }
    
    public SetAliasRqType(java.lang.String SID, java.lang.String userId, java.lang.String alias, java.lang.String newAlias) {
        this.SID = SID;
        this.userId = userId;
        this.alias = alias;
        this.newAlias = newAlias;
    }
    
    public java.lang.String getSID() {
        return SID;
    }
    
    public void setSID(java.lang.String SID) {
        this.SID = SID;
    }
    
    public java.lang.String getUserId() {
        return userId;
    }
    
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    public java.lang.String getAlias() {
        return alias;
    }
    
    public void setAlias(java.lang.String alias) {
        this.alias = alias;
    }
    
    public java.lang.String getNewAlias() {
        return newAlias;
    }
    
    public void setNewAlias(java.lang.String newAlias) {
        this.newAlias = newAlias;
    }
}
