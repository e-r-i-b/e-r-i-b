// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.documents.generated;


public class DocumentService_update_ResponseStruct {
    protected com.rssl.phizicgate.wsgate.services.documents.generated.StateUpdateInfo result;
    protected com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument gateDocument_1;
    
    public DocumentService_update_ResponseStruct() {
    }
    
    public DocumentService_update_ResponseStruct(com.rssl.phizicgate.wsgate.services.documents.generated.StateUpdateInfo result, com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument gateDocument_1) {
        this.result = result;
        this.gateDocument_1 = gateDocument_1;
    }
    
    public com.rssl.phizicgate.wsgate.services.documents.generated.StateUpdateInfo getResult() {
        return result;
    }
    
    public void setResult(com.rssl.phizicgate.wsgate.services.documents.generated.StateUpdateInfo result) {
        this.result = result;
    }
    
    public com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument getGateDocument_1() {
        return gateDocument_1;
    }
    
    public void setGateDocument_1(com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument gateDocument_1) {
        this.gateDocument_1 = gateDocument_1;
    }
}
