package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import org.w3c.dom.Document;

/**
 * Операция выбора логина при актуализации информации о входе
 * @author niculichev
 * @ created 21.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class ChoiceLoginsOperation extends LoginOperationBase
{
	private String connectorGuid;

	public void initialize(OperationInfo operationInfo, String connectorGuid)
	{
		super.initialize(operationInfo);
		this.connectorGuid = connectorGuid;
	}

	public Document doRequest() throws BackLogicException, BackException
	{
		return CSABackRequestHelper.sendActualizeLogonInfoRq(operationInfo.getOUID(), connectorGuid);
	}
}
