// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.bars.ws.jaxrpc;


public class XsbDocID {
    protected java.util.Calendar date;
    protected java.lang.String docSubType;
    protected java.lang.String docType;
    protected int number;
    protected java.lang.String originator;
    
    public XsbDocID() {
    }
    
    public XsbDocID(java.util.Calendar date, java.lang.String docSubType, java.lang.String docType, int number, java.lang.String originator) {
        this.date = date;
        this.docSubType = docSubType;
        this.docType = docType;
        this.number = number;
        this.originator = originator;
    }
    
    public java.util.Calendar getDate() {
        return date;
    }
    
    public void setDate(java.util.Calendar date) {
        this.date = date;
    }
    
    public java.lang.String getDocSubType() {
        return docSubType;
    }
    
    public void setDocSubType(java.lang.String docSubType) {
        this.docSubType = docSubType;
    }
    
    public java.lang.String getDocType() {
        return docType;
    }
    
    public void setDocType(java.lang.String docType) {
        this.docType = docType;
    }
    
    public int getNumber() {
        return number;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    public java.lang.String getOriginator() {
        return originator;
    }
    
    public void setOriginator(java.lang.String originator) {
        this.originator = originator;
    }
}
