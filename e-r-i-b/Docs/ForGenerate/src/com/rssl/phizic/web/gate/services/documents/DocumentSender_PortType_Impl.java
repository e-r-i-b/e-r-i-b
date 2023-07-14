package com.rssl.phizic.web.gate.services.documents;

import com.rssl.phizic.web.gate.services.documents.types.GateDocument;

import java.util.Map;
import java.rmi.RemoteException;

/**
 * @author egorova
 * @ created 28.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class DocumentSender_PortType_Impl implements DocumentSender_PortType
{
	public void setParameters(Map<String, ?> params) throws RemoteException
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void send(GateDocument document) throws RemoteException
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void prepare(GateDocument document) throws RemoteException
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void rollback(GateDocument document) throws RemoteException
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void confirm(GateDocument document) throws RemoteException
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void validate(GateDocument document) throws RemoteException
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
