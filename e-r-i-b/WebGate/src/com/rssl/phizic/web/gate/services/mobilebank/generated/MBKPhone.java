// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;


public class MBKPhone {
    protected boolean added;
    protected long id;
    protected java.util.Calendar lastUpdateTime;
    protected java.lang.String phone;
    
    public MBKPhone() {
    }
    
    public MBKPhone(boolean added, long id, java.util.Calendar lastUpdateTime, java.lang.String phone) {
        this.added = added;
        this.id = id;
        this.lastUpdateTime = lastUpdateTime;
        this.phone = phone;
    }
    
    public boolean isAdded() {
        return added;
    }
    
    public void setAdded(boolean added) {
        this.added = added;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public java.util.Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }
    
    public void setLastUpdateTime(java.util.Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    
    public java.lang.String getPhone() {
        return phone;
    }
    
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }
}
