/**
 * WebBankServiceIFBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.sofia.axis;

import com.rssl.phizic.test.webgate.sofia.common.MessageHelper;

public class WebBankServiceIFBindingImpl implements com.rssl.phizic.test.webgate.sofia.axis.generated.WebBankServiceIF{
    public java.lang.String sendMessage(java.lang.String message) throws java.rmi.RemoteException {
      return new MessageHelper().getCurentMessage(message);
    }

}
