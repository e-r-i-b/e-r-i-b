package com.rssl.phizic.web.gate.services.documents;

import com.rssl.phizic.web.gate.services.documents.types.StateUpdateInfo;
import com.rssl.phizic.web.gate.services.documents.types.GateDocument;

/**
 * @author egorova
 * @ created 28.05.2009
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentUpdater_PortType extends java.rmi.Remote
{
	StateUpdateInfo execute(GateDocument document) throws java.rmi.RemoteException;
}
