// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.bars.ws.jaxrpc;


public class XsbRemoteClientNameResult extends com.rssl.phizicgate.bars.ws.jaxrpc.XsbDocResults {
    protected javax.xml.soap.SOAPElement DOldNameBeg;
    protected javax.xml.soap.SOAPElement DOldNameEnd;
    protected java.lang.String SFName;
    protected java.lang.String SFNameOld;
    protected java.lang.String SInn;
    protected java.lang.String SSName;
    protected java.lang.String SSNameOld;
    
    public XsbRemoteClientNameResult() {
    }
    
    public XsbRemoteClientNameResult(com.rssl.phizicgate.bars.ws.jaxrpc.XsbDocID docID, com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbExceptionItem exceptionItems, javax.xml.soap.SOAPElement DOldNameBeg, javax.xml.soap.SOAPElement DOldNameEnd, java.lang.String SFName, java.lang.String SFNameOld, java.lang.String SInn, java.lang.String SSName, java.lang.String SSNameOld) {
        this.docID = docID;
        this.exceptionItems = exceptionItems;
        this.DOldNameBeg = DOldNameBeg;
        this.DOldNameEnd = DOldNameEnd;
        this.SFName = SFName;
        this.SFNameOld = SFNameOld;
        this.SInn = SInn;
        this.SSName = SSName;
        this.SSNameOld = SSNameOld;
    }
    
    public javax.xml.soap.SOAPElement getDOldNameBeg() {
        return DOldNameBeg;
    }
    
    public void setDOldNameBeg(javax.xml.soap.SOAPElement DOldNameBeg) {
        this.DOldNameBeg = DOldNameBeg;
    }
    
    public javax.xml.soap.SOAPElement getDOldNameEnd() {
        return DOldNameEnd;
    }
    
    public void setDOldNameEnd(javax.xml.soap.SOAPElement DOldNameEnd) {
        this.DOldNameEnd = DOldNameEnd;
    }
    
    public java.lang.String getSFName() {
        return SFName;
    }
    
    public void setSFName(java.lang.String SFName) {
        this.SFName = SFName;
    }
    
    public java.lang.String getSFNameOld() {
        return SFNameOld;
    }
    
    public void setSFNameOld(java.lang.String SFNameOld) {
        this.SFNameOld = SFNameOld;
    }
    
    public java.lang.String getSInn() {
        return SInn;
    }
    
    public void setSInn(java.lang.String SInn) {
        this.SInn = SInn;
    }
    
    public java.lang.String getSSName() {
        return SSName;
    }
    
    public void setSSName(java.lang.String SSName) {
        this.SSName = SSName;
    }
    
    public java.lang.String getSSNameOld() {
        return SSNameOld;
    }
    
    public void setSSNameOld(java.lang.String SSNameOld) {
        this.SSNameOld = SSNameOld;
    }
}
