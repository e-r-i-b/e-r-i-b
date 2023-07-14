package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.Amount;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.*;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.NO;
import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.OFFLINE_LOAD_FIELD_NAME;

/**
 * Базовый билдер запроса на заявки на открыти карты (тип analyze)
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeIssueCardClaimRequestBuilderBase<BD extends BusinessDocument> extends AnalyzeDocumentRequestBuilderBase<BD>
{
	@Override
	protected AccountData createOtherAccountData()
	{
		return null;
	}

	@Override
	protected AccountData createMyAccountData()
	{
		return null;
	}

	@Override
	protected AccountPayeeType getAccountPayeeType()
	{
		return null;
	}

	@Override
	protected BeneficiaryType getBeneficiaryType() throws GateException
	{
		return null;
	}

	@Override
	protected DirectionTransferFundsType getDirectionTransferFundsType()
	{
		return null;
	}

	@Override
	protected ExecutionPeriodicityType getExecutionPeriodicityType()
	{
		return null;
	}

	@Override
	protected WayTransferOfFundsType getWayTransferOfFundsType() throws GateException
	{
		return null;
	}

	@Override
	protected EventsType getEventsType()
	{
		return EventsType.REQUEST_NEW_CARD;
	}

	@Override
	protected Amount getAmount()
	{
		return null;
	}

	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		return new ClientDefinedFactBuilder()
				.append(OFFLINE_LOAD_FIELD_NAME, NO, DataType.STRING)
				.build();
	}
}
