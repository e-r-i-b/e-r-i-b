package com.rssl.phizicgate.wsgate.services.clients.generated;

/**
 * @author: Pakhomova
 * @created: 01.06.2009
 * @ $Author$
 * @ $Revision$
 */

public class ClientDocument {
    protected boolean docIdentify;
    protected java.lang.String docIssueBy;
    protected java.lang.String docIssueByCode;
    protected java.util.Calendar docIssueDate;
    protected java.lang.String docNumber;
    protected java.lang.String docSeries;
    protected java.util.Calendar docTimeUpDate;
    protected java.lang.String docTypeName;
    protected java.lang.String documentType;
    protected java.lang.Long paperKind;
    protected java.lang.Long personId;

    public ClientDocument() {
    }

    public ClientDocument(boolean docIdentify, java.lang.String docIssueBy, java.lang.String docIssueByCode, java.util.Calendar docIssueDate, java.lang.String docNumber, java.lang.String docSeries, java.util.Calendar docTimeUpDate, java.lang.String docTypeName, java.lang.String documentType, java.lang.Long paperKind, java.lang.Long personId) {
        this.docIdentify = docIdentify;
        this.docIssueBy = docIssueBy;
        this.docIssueByCode = docIssueByCode;
        this.docIssueDate = docIssueDate;
        this.docNumber = docNumber;
        this.docSeries = docSeries;
        this.docTimeUpDate = docTimeUpDate;
        this.docTypeName = docTypeName;
        this.documentType = documentType;
        this.paperKind = paperKind;
        this.personId = personId;
    }

    public boolean isDocIdentify() {
        return docIdentify;
    }

    public void setDocIdentify(boolean docIdentify) {
        this.docIdentify = docIdentify;
    }

    public java.lang.String getDocIssueBy() {
        return docIssueBy;
    }

    public void setDocIssueBy(java.lang.String docIssueBy) {
        this.docIssueBy = docIssueBy;
    }

    public java.lang.String getDocIssueByCode() {
        return docIssueByCode;
    }

    public void setDocIssueByCode(java.lang.String docIssueByCode) {
        this.docIssueByCode = docIssueByCode;
    }

    public java.util.Calendar getDocIssueDate() {
        return docIssueDate;
    }

    public void setDocIssueDate(java.util.Calendar docIssueDate) {
        this.docIssueDate = docIssueDate;
    }

    public java.lang.String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(java.lang.String docNumber) {
        this.docNumber = docNumber;
    }

    public java.lang.String getDocSeries() {
        return docSeries;
    }

    public void setDocSeries(java.lang.String docSeries) {
        this.docSeries = docSeries;
    }

    public java.util.Calendar getDocTimeUpDate() {
        return docTimeUpDate;
    }

    public void setDocTimeUpDate(java.util.Calendar docTimeUpDate) {
        this.docTimeUpDate = docTimeUpDate;
    }

    public java.lang.String getDocTypeName() {
        return docTypeName;
    }

    public void setDocTypeName(java.lang.String docTypeName) {
        this.docTypeName = docTypeName;
    }

    public java.lang.String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(java.lang.String documentType) {
        this.documentType = documentType;
    }

    public java.lang.Long getPaperKind() {
        return paperKind;
    }

    public void setPaperKind(java.lang.Long paperKind) {
        this.paperKind = paperKind;
    }

    public java.lang.Long getPersonId() {
        return personId;
    }

    public void setPersonId(java.lang.Long personId) {
        this.personId = personId;
    }
}

