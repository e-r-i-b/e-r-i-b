// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.bars.ws.jaxrpc;


public class XsbRemoteClientNameReturn extends com.rssl.phizicgate.bars.ws.jaxrpc.XsbReturn {
    protected com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbRemoteClientNameResult documents;
    
    public XsbRemoteClientNameReturn() {
    }
    
    public XsbRemoteClientNameReturn(com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbExceptionItem exceptionItems, com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbRemoteClientNameResult documents) {
        this.exceptionItems = exceptionItems;
        this.documents = documents;
    }
    
    public com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbRemoteClientNameResult getDocuments() {
        return documents;
    }
    
    public void setDocuments(com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbRemoteClientNameResult documents) {
        this.documents = documents;
    }
}
