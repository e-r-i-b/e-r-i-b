// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.auth.csa.back.integration.erib.generated;


public class ConfirmationInformation {
    protected java.lang.String confirmStrategyType;
    protected java.util.List confirmationSources;
    protected boolean pushAllowed;
    
    public ConfirmationInformation() {
    }
    
    public ConfirmationInformation(java.lang.String confirmStrategyType, java.util.List confirmationSources, boolean pushAllowed) {
        this.confirmStrategyType = confirmStrategyType;
        this.confirmationSources = confirmationSources;
        this.pushAllowed = pushAllowed;
    }
    
    public java.lang.String getConfirmStrategyType() {
        return confirmStrategyType;
    }
    
    public void setConfirmStrategyType(java.lang.String confirmStrategyType) {
        this.confirmStrategyType = confirmStrategyType;
    }
    
    public java.util.List getConfirmationSources() {
        return confirmationSources;
    }
    
    public void setConfirmationSources(java.util.List confirmationSources) {
        this.confirmationSources = confirmationSources;
    }
    
    public boolean isPushAllowed() {
        return pushAllowed;
    }
    
    public void setPushAllowed(boolean pushAllowed) {
        this.pushAllowed = pushAllowed;
    }
}
