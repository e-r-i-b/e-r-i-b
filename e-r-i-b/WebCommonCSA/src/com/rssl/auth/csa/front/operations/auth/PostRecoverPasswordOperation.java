package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.ChangePasswordInitializationData;
import com.rssl.phizic.rsa.senders.initialization.SenderInitializationByEventData;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.EventsType;
import org.w3c.dom.Document;

/**
 * @author niculichev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class PostRecoverPasswordOperation extends InterchangeCSABackOperationBase
{
	private String password;
	private RecoverPasswordOperationInfo info;

	public void initialize(OperationInfo info, String password)
	{
		this.info = (RecoverPasswordOperationInfo) info;
		this.password = password;
	}

	protected Document doRequest() throws BackLogicException, BackException
	{
		// если нет информации о коннекторе, значит логин гостевой
		if(info.getConnectorType() == null)
		{
			return CSABackRequestHelper.sendFinishGuestRecoverPasswordRq(info.getOUID(), password);
		}
		else
		{
			return CSABackRequestHelper.sendFinishRecoverPasswordRq(info.getOUID(), password);
		}
	}

	protected void processResponce(Document responce) throws FrontLogicException, FrontException
	{
		//Логирование входа клиента если он привлечен промоутером
		updatePromoClientLog(null, info.getOUID());
	}
}
