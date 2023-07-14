// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.cache.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class AutoPayment_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.cache.generated.AutoPayment _instance;
    private com.rssl.phizicgate.wsgate.services.cache.generated.Money amount;
    private java.lang.String cardNumber;
    private java.lang.String codeService;
    private java.util.Calendar dateCreated;
    private java.util.Calendar endDate;
    private java.lang.String executionEventType;
    private java.lang.String externalId;
    private com.rssl.phizicgate.wsgate.services.cache.generated.Money floorLimit;
    private java.lang.String friendlyName;
    private java.lang.String getNumber;
    private com.rssl.phizicgate.wsgate.services.cache.generated.Office office;
    private java.lang.Long payDay;
    private java.math.BigDecimal percent;
    private java.lang.Long priority;
    private java.lang.String receiverName;
    private java.lang.String reportStatus;
    private java.lang.String requisite;
    private java.util.Calendar startDate;
    private java.lang.String sumType;
    private com.rssl.phizicgate.wsgate.services.cache.generated.Money totalAmountLimit;
    private java.lang.String totalAmountPeriod;
    private java.lang.String type;
    private static final int myAMOUNT_INDEX = 0;
    private static final int myCARDNUMBER_INDEX = 1;
    private static final int myCODESERVICE_INDEX = 2;
    private static final int myDATECREATED_INDEX = 3;
    private static final int myENDDATE_INDEX = 4;
    private static final int myEXECUTIONEVENTTYPE_INDEX = 5;
    private static final int myEXTERNALID_INDEX = 6;
    private static final int myFLOORLIMIT_INDEX = 7;
    private static final int myFRIENDLYNAME_INDEX = 8;
    private static final int myGETNUMBER_INDEX = 9;
    private static final int myOFFICE_INDEX = 10;
    private static final int myPAYDAY_INDEX = 11;
    private static final int myPERCENT_INDEX = 12;
    private static final int myPRIORITY_INDEX = 13;
    private static final int myRECEIVERNAME_INDEX = 14;
    private static final int myREPORTSTATUS_INDEX = 15;
    private static final int myREQUISITE_INDEX = 16;
    private static final int mySTARTDATE_INDEX = 17;
    private static final int mySUMTYPE_INDEX = 18;
    private static final int myTOTALAMOUNTLIMIT_INDEX = 19;
    private static final int myTOTALAMOUNTPERIOD_INDEX = 20;
    private static final int myTYPE_INDEX = 21;
    
    public AutoPayment_SOAPBuilder() {
    }
    
    public void setAmount(com.rssl.phizicgate.wsgate.services.cache.generated.Money amount) {
        this.amount = amount;
    }
    
    public void setCardNumber(java.lang.String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public void setCodeService(java.lang.String codeService) {
        this.codeService = codeService;
    }
    
    public void setDateCreated(java.util.Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public void setEndDate(java.util.Calendar endDate) {
        this.endDate = endDate;
    }
    
    public void setExecutionEventType(java.lang.String executionEventType) {
        this.executionEventType = executionEventType;
    }
    
    public void setExternalId(java.lang.String externalId) {
        this.externalId = externalId;
    }
    
    public void setFloorLimit(com.rssl.phizicgate.wsgate.services.cache.generated.Money floorLimit) {
        this.floorLimit = floorLimit;
    }
    
    public void setFriendlyName(java.lang.String friendlyName) {
        this.friendlyName = friendlyName;
    }
    
    public void setGetNumber(java.lang.String getNumber) {
        this.getNumber = getNumber;
    }
    
    public void setOffice(com.rssl.phizicgate.wsgate.services.cache.generated.Office office) {
        this.office = office;
    }
    
    public void setPayDay(java.lang.Long payDay) {
        this.payDay = payDay;
    }
    
    public void setPercent(java.math.BigDecimal percent) {
        this.percent = percent;
    }
    
    public void setPriority(java.lang.Long priority) {
        this.priority = priority;
    }
    
    public void setReceiverName(java.lang.String receiverName) {
        this.receiverName = receiverName;
    }
    
    public void setReportStatus(java.lang.String reportStatus) {
        this.reportStatus = reportStatus;
    }
    
    public void setRequisite(java.lang.String requisite) {
        this.requisite = requisite;
    }
    
    public void setStartDate(java.util.Calendar startDate) {
        this.startDate = startDate;
    }
    
    public void setSumType(java.lang.String sumType) {
        this.sumType = sumType;
    }
    
    public void setTotalAmountLimit(com.rssl.phizicgate.wsgate.services.cache.generated.Money totalAmountLimit) {
        this.totalAmountLimit = totalAmountLimit;
    }
    
    public void setTotalAmountPeriod(java.lang.String totalAmountPeriod) {
        this.totalAmountPeriod = totalAmountPeriod;
    }
    
    public void setType(java.lang.String type) {
        this.type = type;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myAMOUNT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCARDNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCODESERVICE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myEXECUTIONEVENTTYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myEXTERNALID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFLOORLIMIT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFRIENDLYNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myGETNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myOFFICE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPAYDAY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPRIORITY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myRECEIVERNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myREPORTSTATUS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myREQUISITE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySUMTYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTOTALAMOUNTLIMIT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTOTALAMOUNTPERIOD_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTYPE_INDEX:
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
                case myAMOUNT_INDEX:
                    _instance.setAmount((com.rssl.phizicgate.wsgate.services.cache.generated.Money)memberValue);
                    break;
                case myCARDNUMBER_INDEX:
                    _instance.setCardNumber((java.lang.String)memberValue);
                    break;
                case myCODESERVICE_INDEX:
                    _instance.setCodeService((java.lang.String)memberValue);
                    break;
                case myEXECUTIONEVENTTYPE_INDEX:
                    _instance.setExecutionEventType((java.lang.String)memberValue);
                    break;
                case myEXTERNALID_INDEX:
                    _instance.setExternalId((java.lang.String)memberValue);
                    break;
                case myFLOORLIMIT_INDEX:
                    _instance.setFloorLimit((com.rssl.phizicgate.wsgate.services.cache.generated.Money)memberValue);
                    break;
                case myFRIENDLYNAME_INDEX:
                    _instance.setFriendlyName((java.lang.String)memberValue);
                    break;
                case myGETNUMBER_INDEX:
                    _instance.setGetNumber((java.lang.String)memberValue);
                    break;
                case myOFFICE_INDEX:
                    _instance.setOffice((com.rssl.phizicgate.wsgate.services.cache.generated.Office)memberValue);
                    break;
                case myPAYDAY_INDEX:
                    _instance.setPayDay((java.lang.Long)memberValue);
                    break;
                case myPRIORITY_INDEX:
                    _instance.setPriority((java.lang.Long)memberValue);
                    break;
                case myRECEIVERNAME_INDEX:
                    _instance.setReceiverName((java.lang.String)memberValue);
                    break;
                case myREPORTSTATUS_INDEX:
                    _instance.setReportStatus((java.lang.String)memberValue);
                    break;
                case myREQUISITE_INDEX:
                    _instance.setRequisite((java.lang.String)memberValue);
                    break;
                case mySUMTYPE_INDEX:
                    _instance.setSumType((java.lang.String)memberValue);
                    break;
                case myTOTALAMOUNTLIMIT_INDEX:
                    _instance.setTotalAmountLimit((com.rssl.phizicgate.wsgate.services.cache.generated.Money)memberValue);
                    break;
                case myTOTALAMOUNTPERIOD_INDEX:
                    _instance.setTotalAmountPeriod((java.lang.String)memberValue);
                    break;
                case myTYPE_INDEX:
                    _instance.setType((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.cache.generated.AutoPayment)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
