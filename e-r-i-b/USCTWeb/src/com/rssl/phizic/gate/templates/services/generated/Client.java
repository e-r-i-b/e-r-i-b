// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.gate.templates.services.generated;


public class Client {
    protected java.util.Calendar birthDay;
    protected java.util.List documents;
    protected java.lang.String firstName;
    protected com.rssl.phizic.gate.templates.services.generated.Office office;
    protected java.lang.String patrName;
    protected java.lang.String surName;
    
    public Client() {
    }
    
    public Client(java.util.Calendar birthDay, java.util.List documents, java.lang.String firstName, com.rssl.phizic.gate.templates.services.generated.Office office, java.lang.String patrName, java.lang.String surName) {
        this.birthDay = birthDay;
        this.documents = documents;
        this.firstName = firstName;
        this.office = office;
        this.patrName = patrName;
        this.surName = surName;
    }
    
    public java.util.Calendar getBirthDay() {
        return birthDay;
    }
    
    public void setBirthDay(java.util.Calendar birthDay) {
        this.birthDay = birthDay;
    }
    
    public java.util.List getDocuments() {
        return documents;
    }
    
    public void setDocuments(java.util.List documents) {
        this.documents = documents;
    }
    
    public java.lang.String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }
    
    public com.rssl.phizic.gate.templates.services.generated.Office getOffice() {
        return office;
    }
    
    public void setOffice(com.rssl.phizic.gate.templates.services.generated.Office office) {
        this.office = office;
    }
    
    public java.lang.String getPatrName() {
        return patrName;
    }
    
    public void setPatrName(java.lang.String patrName) {
        this.patrName = patrName;
    }
    
    public java.lang.String getSurName() {
        return surName;
    }
    
    public void setSurName(java.lang.String surName) {
        this.surName = surName;
    }
}
