// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.bars.ws.jaxrpc;


public class XsbChecksReturn extends com.rssl.phizicgate.bars.ws.jaxrpc.XsbReturn {
    protected com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbChecksDocResults documents;
    
    public XsbChecksReturn() {
    }
    
    public XsbChecksReturn(com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbExceptionItem exceptionItems, com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbChecksDocResults documents) {
        this.exceptionItems = exceptionItems;
        this.documents = documents;
    }
    
    public com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbChecksDocResults getDocuments() {
        return documents;
    }
    
    public void setDocuments(com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbChecksDocResults documents) {
        this.documents = documents;
    }
}
