package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.responses.ConfirmationInformation;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Операция получения данных о способах подтверждения операций клиентом
 */

public class GetConfirmationInfoOperation extends InterchangeCSABackOperationBase
{
	private ConfirmableOperationInfo operationInfo;

	/**
	 * инициализация операции
	 * @param operationInfo информация об операции
	 */
	public void initialize(ConfirmableOperationInfo operationInfo)
	{
		this.operationInfo = operationInfo;
	}

	protected Document doRequest() throws BackLogicException, BackException
	{
		return CSABackRequestHelper.sendGetConfirmationInfoRq(operationInfo.getAuthToken());
	}

	protected void processResponce(Document responce) throws FrontLogicException
	{
		try
		{
			ConfirmationInformation confirmationInformation = CSABackResponseSerializer.getConfirmationInformation(responce);
			operationInfo.setPreferredConfirmType(confirmationInformation.getPreferredConfirmType());
			List<String> cardConfirmationSourceList = confirmationInformation.getCardConfirmationSource();
			Map<String, String> cardConfirmationSource = new HashMap<String, String>();
			int i = 0;
			for (String cardNumbers : cardConfirmationSourceList)
				cardConfirmationSource.put(String.valueOf(i++), cardNumbers);

			operationInfo.setCardConfirmationSource(cardConfirmationSource);
			operationInfo.setPushAllowed(confirmationInformation.isPushAllowed());
		}
		catch (Exception e)
		{
			throw new FrontLogicException("Ошибка получения информации о способах подтверждения.", e);
		}
	}
}
