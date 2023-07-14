// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.cache.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Address_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.cache.generated.Address _instance;
    private java.lang.String building;
    private java.lang.String city;
    private java.lang.String district;
    private java.lang.String flat;
    private java.lang.String homePhone;
    private java.lang.String house;
    private java.lang.String mobileOperator;
    private java.lang.String mobilePhone;
    private java.lang.String postalCode;
    private java.lang.String province;
    private java.lang.String street;
    private java.lang.String workPhone;
    private static final int myBUILDING_INDEX = 0;
    private static final int myCITY_INDEX = 1;
    private static final int myDISTRICT_INDEX = 2;
    private static final int myFLAT_INDEX = 3;
    private static final int myHOMEPHONE_INDEX = 4;
    private static final int myHOUSE_INDEX = 5;
    private static final int myMOBILEOPERATOR_INDEX = 6;
    private static final int myMOBILEPHONE_INDEX = 7;
    private static final int myPOSTALCODE_INDEX = 8;
    private static final int myPROVINCE_INDEX = 9;
    private static final int mySTREET_INDEX = 10;
    private static final int myWORKPHONE_INDEX = 11;
    
    public Address_SOAPBuilder() {
    }
    
    public void setBuilding(java.lang.String building) {
        this.building = building;
    }
    
    public void setCity(java.lang.String city) {
        this.city = city;
    }
    
    public void setDistrict(java.lang.String district) {
        this.district = district;
    }
    
    public void setFlat(java.lang.String flat) {
        this.flat = flat;
    }
    
    public void setHomePhone(java.lang.String homePhone) {
        this.homePhone = homePhone;
    }
    
    public void setHouse(java.lang.String house) {
        this.house = house;
    }
    
    public void setMobileOperator(java.lang.String mobileOperator) {
        this.mobileOperator = mobileOperator;
    }
    
    public void setMobilePhone(java.lang.String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }
    
    public void setProvince(java.lang.String province) {
        this.province = province;
    }
    
    public void setStreet(java.lang.String street) {
        this.street = street;
    }
    
    public void setWorkPhone(java.lang.String workPhone) {
        this.workPhone = workPhone;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myBUILDING_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCITY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDISTRICT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFLAT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myHOMEPHONE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myHOUSE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMOBILEOPERATOR_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMOBILEPHONE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPOSTALCODE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPROVINCE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTREET_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myWORKPHONE_INDEX:
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
                case myBUILDING_INDEX:
                    _instance.setBuilding((java.lang.String)memberValue);
                    break;
                case myCITY_INDEX:
                    _instance.setCity((java.lang.String)memberValue);
                    break;
                case myDISTRICT_INDEX:
                    _instance.setDistrict((java.lang.String)memberValue);
                    break;
                case myFLAT_INDEX:
                    _instance.setFlat((java.lang.String)memberValue);
                    break;
                case myHOMEPHONE_INDEX:
                    _instance.setHomePhone((java.lang.String)memberValue);
                    break;
                case myHOUSE_INDEX:
                    _instance.setHouse((java.lang.String)memberValue);
                    break;
                case myMOBILEOPERATOR_INDEX:
                    _instance.setMobileOperator((java.lang.String)memberValue);
                    break;
                case myMOBILEPHONE_INDEX:
                    _instance.setMobilePhone((java.lang.String)memberValue);
                    break;
                case myPOSTALCODE_INDEX:
                    _instance.setPostalCode((java.lang.String)memberValue);
                    break;
                case myPROVINCE_INDEX:
                    _instance.setProvince((java.lang.String)memberValue);
                    break;
                case mySTREET_INDEX:
                    _instance.setStreet((java.lang.String)memberValue);
                    break;
                case myWORKPHONE_INDEX:
                    _instance.setWorkPhone((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.cache.generated.Address)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
