// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.dictionaries.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Currency_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.currency.generated.Currency _instance;
    private String code;
    private String externalId;
    private String name;
    private String number;
    private static final int myCODE_INDEX = 0;
    private static final int myEXTERNALID_INDEX = 1;
    private static final int myNAME_INDEX = 2;
    private static final int myNUMBER_INDEX = 3;

    public Currency_SOAPBuilder() {
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCODE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myEXTERNALID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void construct() {
    }

    public void setMember(int index, Object memberValue) {
        try {
            switch(index) {
                case myCODE_INDEX:
                    _instance.setCode((String)memberValue);
                    break;
                case myEXTERNALID_INDEX:
                    _instance.setExternalId((String)memberValue);
                    break;
                case myNAME_INDEX:
                    _instance.setName((String)memberValue);
                    break;
                case myNUMBER_INDEX:
                    _instance.setNumber((String)memberValue);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new DeserializationException(new LocalizableExceptionAdapter(e));
        }
    }

    public void initialize() {
    }

    public void setInstance(Object instance) {
        _instance = (com.rssl.phizicgate.wsgate.services.currency.generated.Currency)instance;
    }

    public Object getInstance() {
        return _instance;
    }
}