/**
 * SendSMSServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.ermb.smstransport.generated;

import com.rssl.phizic.common.types.ermb.generated.SendSMSRsType;
import com.rssl.phizic.common.types.ermb.generated.SendSMSRqType;
import com.rssl.phizic.utils.RandomGUID;

import java.util.Random;

public class SendSMSServiceSoapBindingImpl implements com.rssl.phizic.test.ermb.smstransport.generated.SendSMSService{
    public SendSMSRsType sendSMS(SendSMSRqType parameters) throws java.rmi.RemoteException {
        return new SendSMSRsType(new RandomGUID().getStringValue(), getStatus());
    }

    public SendSMSRsType sendSMSWithIMSI(SendSMSRqType parameters) throws java.rmi.RemoteException {
        return new SendSMSRsType(new RandomGUID().getStringValue(), getStatus());
    }

	private long getStatus()
	{
		Random rand = new Random();
		int i = rand.nextInt(10);
		if (i <= 7)
			return 0;
		return 1;
	}
}
