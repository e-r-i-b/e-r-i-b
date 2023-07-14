package com.rssl.phizicgate.wsgate.services.registration.generated;

import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
import com.sun.xml.rpc.encoding.DeserializationException;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

/**
 * @author: Pakhomova
 * @created: 01.06.2009
 * @ $Author$
 * @ $Revision$
 */

public class ClientDocument_SOAPBuilder implements SOAPInstanceBuilder
{
    private com.rssl.phizicgate.wsgate.services.registration.generated.ClientDocument _instance;
    private boolean docIdentify;
    private java.lang.String docIssueBy;
    private java.lang.String docIssueByCode;
    private java.util.Calendar docIssueDate;
    private java.lang.String docNumber;
    private java.lang.String docSeries;
    private java.util.Calendar docTimeUpDate;
    private java.lang.String docTypeName;
    private java.lang.String documentType;
    private java.lang.Long paperKind;
    private java.lang.Long personId;
    private static final int myDOCIDENTIFY_INDEX = 0;
    private static final int myDOCISSUEBY_INDEX = 1;
    private static final int myDOCISSUEBYCODE_INDEX = 2;
    private static final int myDOCISSUEDATE_INDEX = 3;
    private static final int myDOCNUMBER_INDEX = 4;
    private static final int myDOCSERIES_INDEX = 5;
    private static final int myDOCTIMEUPDATE_INDEX = 6;
    private static final int myDOCTYPENAME_INDEX = 7;
    private static final int myDOCUMENTTYPE_INDEX = 8;
    private static final int myPAPERKIND_INDEX = 9;
    private static final int myPERSONID_INDEX = 10;

    public ClientDocument_SOAPBuilder() {
    }

    public void setDocIdentify(boolean docIdentify) {
        this.docIdentify = docIdentify;
    }

    public void setDocIssueBy(java.lang.String docIssueBy) {
        this.docIssueBy = docIssueBy;
    }

    public void setDocIssueByCode(java.lang.String docIssueByCode) {
        this.docIssueByCode = docIssueByCode;
    }

    public void setDocIssueDate(java.util.Calendar docIssueDate) {
        this.docIssueDate = docIssueDate;
    }

    public void setDocNumber(java.lang.String docNumber) {
        this.docNumber = docNumber;
    }

    public void setDocSeries(java.lang.String docSeries) {
        this.docSeries = docSeries;
    }

    public void setDocTimeUpDate(java.util.Calendar docTimeUpDate) {
        this.docTimeUpDate = docTimeUpDate;
    }

    public void setDocTypeName(java.lang.String docTypeName) {
        this.docTypeName = docTypeName;
    }

    public void setDocumentType(java.lang.String documentType) {
        this.documentType = documentType;
    }

    public void setPaperKind(java.lang.Long paperKind) {
        this.paperKind = paperKind;
    }

    public void setPersonId(java.lang.Long personId) {
        this.personId = personId;
    }

    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myDOCISSUEBY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDOCISSUEBYCODE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDOCNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDOCSERIES_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDOCTYPENAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDOCUMENTTYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPAPERKIND_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPERSONID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void construct() {
    }

    public void setMember(int index, java.lang.Object memberValue) {
        try {
            switch(index) {
                case myDOCISSUEBY_INDEX:
                    _instance.setDocIssueBy((java.lang.String)memberValue);
                    break;
                case myDOCISSUEBYCODE_INDEX:
                    _instance.setDocIssueByCode((java.lang.String)memberValue);
                    break;
                case myDOCNUMBER_INDEX:
                    _instance.setDocNumber((java.lang.String)memberValue);
                    break;
                case myDOCSERIES_INDEX:
                    _instance.setDocSeries((java.lang.String)memberValue);
                    break;
                case myDOCTYPENAME_INDEX:
                    _instance.setDocTypeName((java.lang.String)memberValue);
                    break;
                case myDOCUMENTTYPE_INDEX:
                    _instance.setDocumentType((java.lang.String)memberValue);
                    break;
                case myPAPERKIND_INDEX:
                    _instance.setPaperKind((java.lang.Long)memberValue);
                    break;
                case myPERSONID_INDEX:
                    _instance.setPersonId((java.lang.Long)memberValue);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException();
            }
        }
        catch (java.lang.RuntimeException e) {
            throw e;
        }
        catch (java.lang.Exception e) {
            throw new DeserializationException(new LocalizableExceptionAdapter(e));
        }
    }

    public void initialize() {
    }

    public void setInstance(java.lang.Object instance) {
        _instance = (com.rssl.phizicgate.wsgate.services.registration.generated.ClientDocument)instance;
    }

    public java.lang.Object getInstance() {
        return _instance;
    }
}
