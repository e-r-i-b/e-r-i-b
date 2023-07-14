package com.rssl.phizic.web.gate.services.documents;

import com.rssl.phizic.web.gate.services.documents.types.GateDocument;

import java.util.Map;
import java.rmi.Remote;

/**
 * @author egorova
 * @ created 28.05.2009
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentSender_PortType extends Remote
{
	void setParameters(Map<String,?> params) throws java.rmi.RemoteException;

	void send(GateDocument document) throws java.rmi.RemoteException;

	void prepare(GateDocument document) throws java.rmi.RemoteException;

	void rollback(GateDocument document) throws java.rmi.RemoteException;

	void confirm(GateDocument document) throws java.rmi.RemoteException;

	void validate(GateDocument document) throws java.rmi.RemoteException;
}
