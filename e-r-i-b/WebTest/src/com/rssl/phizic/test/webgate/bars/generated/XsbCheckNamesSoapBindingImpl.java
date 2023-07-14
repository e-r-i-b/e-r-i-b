/**
 * XsbCheckNamesSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.bars.generated;

public class XsbCheckNamesSoapBindingImpl implements com.rssl.phizic.test.webgate.bars.generated.XsbCheckNames_PortType{
	private static int countForException = 0;

    public com.rssl.phizic.test.webgate.bars.generated.XsbChecksReturn checkRemoteClientName(java.lang.String xsbDocument, com.rssl.phizic.test.webgate.bars.generated.XsbParameter[] parameters) throws java.rmi.RemoteException {
        return null;
    }

    public com.rssl.phizic.test.webgate.bars.generated.XsbRemoteClientNameReturn readRemoteClientName(java.lang.String xsbDocument, com.rssl.phizic.test.webgate.bars.generated.XsbParameter[] parameters) throws java.rmi.RemoteException
    {
		XsbRemoteClientNameReturn nameReturn = new XsbRemoteClientNameReturn();
	    if (countForException < 10)
	    {
		    XsbRemoteClientNameResult[] clientNameResult = new XsbRemoteClientNameResult[1];
		    XsbRemoteClientNameResult result = new XsbRemoteClientNameResult();
		    result.setSSName("Иван Иванович");
		    result.setSInn("6666666661");
		    clientNameResult[0] = result;
		    nameReturn.setDocuments(clientNameResult);
		    countForException++;
	    }
	    else
	    {
		    XsbExceptionItem[] exceptionItems = new XsbExceptionItem[1];
		    XsbExceptionItem exceptionItem = new XsbExceptionItem();
		    exceptionItem.setExcName("001");
		    exceptionItem.setExcMessage("Отказываем каждый 10й запрос");
		    exceptionItems[0] = exceptionItem;
		    nameReturn.setExceptionItems(exceptionItems);
		    countForException = 0;
	    }
	    return nameReturn;
    }

    public com.rssl.phizic.test.webgate.bars.generated.XsbRemoteClientNameExtendedReturn readRemoteClientNameExtended(java.lang.String xsbDocument, com.rssl.phizic.test.webgate.bars.generated.XsbParameter[] parameters) throws java.rmi.RemoteException {
        return null;
    }

}
