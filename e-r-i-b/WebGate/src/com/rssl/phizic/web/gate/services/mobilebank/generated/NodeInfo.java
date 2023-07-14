// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;


public class NodeInfo {
    protected boolean adminAvailable;
    protected com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo dictionaryMQ;
    protected com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo ermbQueueMQ;
    protected boolean existingUsersAllowed;
    protected boolean guestAvailable;
    protected java.lang.String hostname;
    protected java.lang.Long id;
    protected java.lang.String listenerHost;
    protected com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo mbkRegistrationMQ;
    protected com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo multiNodeDataMQ;
    protected java.lang.String name;
    protected boolean newUsersAllowed;
    protected com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo smsMQ;
    protected boolean temporaryUsersAllowed;
    protected boolean usersTransferAllowed;
    
    public NodeInfo() {
    }
    
    public NodeInfo(boolean adminAvailable, com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo dictionaryMQ, com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo ermbQueueMQ, boolean existingUsersAllowed, boolean guestAvailable, java.lang.String hostname, java.lang.Long id, java.lang.String listenerHost, com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo mbkRegistrationMQ, com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo multiNodeDataMQ, java.lang.String name, boolean newUsersAllowed, com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo smsMQ, boolean temporaryUsersAllowed, boolean usersTransferAllowed) {
        this.adminAvailable = adminAvailable;
        this.dictionaryMQ = dictionaryMQ;
        this.ermbQueueMQ = ermbQueueMQ;
        this.existingUsersAllowed = existingUsersAllowed;
        this.guestAvailable = guestAvailable;
        this.hostname = hostname;
        this.id = id;
        this.listenerHost = listenerHost;
        this.mbkRegistrationMQ = mbkRegistrationMQ;
        this.multiNodeDataMQ = multiNodeDataMQ;
        this.name = name;
        this.newUsersAllowed = newUsersAllowed;
        this.smsMQ = smsMQ;
        this.temporaryUsersAllowed = temporaryUsersAllowed;
        this.usersTransferAllowed = usersTransferAllowed;
    }
    
    public boolean isAdminAvailable() {
        return adminAvailable;
    }
    
    public void setAdminAvailable(boolean adminAvailable) {
        this.adminAvailable = adminAvailable;
    }
    
    public com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo getDictionaryMQ() {
        return dictionaryMQ;
    }
    
    public void setDictionaryMQ(com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo dictionaryMQ) {
        this.dictionaryMQ = dictionaryMQ;
    }
    
    public com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo getErmbQueueMQ() {
        return ermbQueueMQ;
    }
    
    public void setErmbQueueMQ(com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo ermbQueueMQ) {
        this.ermbQueueMQ = ermbQueueMQ;
    }
    
    public boolean isExistingUsersAllowed() {
        return existingUsersAllowed;
    }
    
    public void setExistingUsersAllowed(boolean existingUsersAllowed) {
        this.existingUsersAllowed = existingUsersAllowed;
    }
    
    public boolean isGuestAvailable() {
        return guestAvailable;
    }
    
    public void setGuestAvailable(boolean guestAvailable) {
        this.guestAvailable = guestAvailable;
    }
    
    public java.lang.String getHostname() {
        return hostname;
    }
    
    public void setHostname(java.lang.String hostname) {
        this.hostname = hostname;
    }
    
    public java.lang.Long getId() {
        return id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
    public java.lang.String getListenerHost() {
        return listenerHost;
    }
    
    public void setListenerHost(java.lang.String listenerHost) {
        this.listenerHost = listenerHost;
    }
    
    public com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo getMbkRegistrationMQ() {
        return mbkRegistrationMQ;
    }
    
    public void setMbkRegistrationMQ(com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo mbkRegistrationMQ) {
        this.mbkRegistrationMQ = mbkRegistrationMQ;
    }
    
    public com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo getMultiNodeDataMQ() {
        return multiNodeDataMQ;
    }
    
    public void setMultiNodeDataMQ(com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo multiNodeDataMQ) {
        this.multiNodeDataMQ = multiNodeDataMQ;
    }
    
    public java.lang.String getName() {
        return name;
    }
    
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public boolean isNewUsersAllowed() {
        return newUsersAllowed;
    }
    
    public void setNewUsersAllowed(boolean newUsersAllowed) {
        this.newUsersAllowed = newUsersAllowed;
    }
    
    public com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo getSmsMQ() {
        return smsMQ;
    }
    
    public void setSmsMQ(com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo smsMQ) {
        this.smsMQ = smsMQ;
    }
    
    public boolean isTemporaryUsersAllowed() {
        return temporaryUsersAllowed;
    }
    
    public void setTemporaryUsersAllowed(boolean temporaryUsersAllowed) {
        this.temporaryUsersAllowed = temporaryUsersAllowed;
    }
    
    public boolean isUsersTransferAllowed() {
        return usersTransferAllowed;
    }
    
    public void setUsersTransferAllowed(boolean usersTransferAllowed) {
        this.usersTransferAllowed = usersTransferAllowed;
    }
}