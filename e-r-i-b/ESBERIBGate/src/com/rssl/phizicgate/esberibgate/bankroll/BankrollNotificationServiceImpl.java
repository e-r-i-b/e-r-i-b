package com.rssl.phizicgate.esberibgate.bankroll;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.BankrollNotificationService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.BankAcctStmtImgInqRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.Status_Type;

import java.util.Calendar;

/**
 * @author egorova
 * @ created 08.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class BankrollNotificationServiceImpl extends AbstractService implements BankrollNotificationService
{
	private BankrollRequestHelper requestHelper = new BankrollRequestHelper(getFactory());

	public BankrollNotificationServiceImpl(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public void sendCardReport(Card card, String address, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		Long clientLoginId = getLoginIdCardId(card.getId());
		String cbCode = requestHelper.getRbTbBrch(clientLoginId);
		IFXRq_Type ifxRq = requestHelper.createBankAcctStmtImgInqRq(card, address, fromDate, toDate,cbCode);
		IFXRs_Type ifxRs = getRequest(ifxRq);
		Status_Type status = ifxRs.getBankAcctStmtImgInqRs().getStatus();
		if (status.getStatusCode() != 0)
			ESBERIBExceptionStatisticHelper.throwErrorResponse(status, BankAcctStmtImgInqRs_Type.class, ifxRq);
	}

	public Long getLoginIdCardId(String cardId) throws GateException
	{
		String[] strings = cardId.trim().split("\\^");
		if (strings.length > 1)
		{
             try
			{
				return Long.parseLong(strings[3]);
			}
			catch (NumberFormatException e)
			{
				throw new GateException(e.getMessage());
			}
		}
		else
		    return null;
	}
}