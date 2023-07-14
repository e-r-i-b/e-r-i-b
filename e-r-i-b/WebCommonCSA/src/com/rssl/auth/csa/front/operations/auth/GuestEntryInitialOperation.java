package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author tisov
 * @ created 24.12.14
 * @ $Author$
 * @ $Revision$
 * [Гостевой вход] Операция по инициализации гостевого входа
 */
public class GuestEntryInitialOperation extends InterchangeCSABackOperationBase
{
	private String phoneNumber;
	private GuestEntryOperationInfo info;


	public GuestEntryInitialOperation(GuestEntryOperationInfo info)
	{
		this.info = info;
		this.phoneNumber = info.getPhoneNumber();
	}

	@Override
	protected Document doRequest() throws BackLogicException, BackException
	{
		try
		{
			return CSABackRequestHelper.sendGuestEntryInitialRq(phoneNumber);
		}
		catch (NoMoreAttemptsCodeConfirmException ignored)
		{
			info.setValid(false);
			updateStubConfirmParams();
			return null;
		}

	}

	protected void processResponce(Document responce) throws FrontLogicException, FrontException
	{
		try
		{
			info.setConfirmParams(CSABackResponseSerializer.getConfirmParameters(responce));
			info.setOUID(CSABackResponseSerializer.getOUID(responce));
		}
		catch (TransformerException e)
		{
			throw new FrontException(e);
		}
	}


	private void updateStubConfirmParams()
	{
		Map<String, Object> confirmParams = new HashMap<String, Object>();
		MobileApiConfig config = ConfigFactory.getConfig(MobileApiConfig.class);
		confirmParams.put(CSAResponseConstants.ATTEMPTS_CONFIRM_PARAM_NAME, Long.valueOf(config.getConfirmationAttemptsCount()));
		confirmParams.put(CSAResponseConstants.TIMEOUT_CONFIRM_PARAM_NAME, Long.valueOf(config.getConfirmationTimeout()));
		confirmParams.put(StubConfirmOperationBase.TIME_RECEIVE_ANSWER_CONFIRM_PARAM_NAME, System.currentTimeMillis());
		info.setConfirmParams(confirmParams);
	}
}
