// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.utils.calendar.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class EmployeeInfo_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.EmployeeInfo _instance;
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Office employeeOffice;
    private java.lang.String login;
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.PersonName personName;
    private static final int myEMPLOYEEOFFICE_INDEX = 0;
    private static final int myLOGIN_INDEX = 1;
    private static final int myPERSONNAME_INDEX = 2;
    
    public EmployeeInfo_SOAPBuilder() {
    }
    
    public void setEmployeeOffice(com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Office employeeOffice) {
        this.employeeOffice = employeeOffice;
    }
    
    public void setLogin(java.lang.String login) {
        this.login = login;
    }
    
    public void setPersonName(com.rssl.phizicgate.wsgate.services.utils.calendar.generated.PersonName personName) {
        this.personName = personName;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myEMPLOYEEOFFICE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myLOGIN_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPERSONNAME_INDEX:
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
                case myEMPLOYEEOFFICE_INDEX:
                    _instance.setEmployeeOffice((com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Office)memberValue);
                    break;
                case myLOGIN_INDEX:
                    _instance.setLogin((java.lang.String)memberValue);
                    break;
                case myPERSONNAME_INDEX:
                    _instance.setPersonName((com.rssl.phizicgate.wsgate.services.utils.calendar.generated.PersonName)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.utils.calendar.generated.EmployeeInfo)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
