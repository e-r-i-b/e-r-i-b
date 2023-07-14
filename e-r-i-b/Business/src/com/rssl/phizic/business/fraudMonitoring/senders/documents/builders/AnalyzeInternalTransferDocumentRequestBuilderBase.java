package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.ClientDefinedFactConstants;
import com.rssl.phizic.rsa.senders.types.*;

import java.math.BigDecimal;

/**
 * Базовый класс билдеров запросов перевода между моими счетами картами
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeInternalTransferDocumentRequestBuilderBase extends AnalyzeDocumentRequestBuilderBase<InternalTransfer>
{
	@Override
	protected AccountPayeeType getAccountPayeeType()
	{
		return AccountPayeeType.PERSONAL_ACCOUNT;
	}

	@Override
	protected BeneficiaryType getBeneficiaryType()
	{
		return BeneficiaryType.SAME_BANK;
	}

	@Override
	protected DirectionTransferFundsType getDirectionTransferFundsType()
	{
		return DirectionTransferFundsType.ME_TO_ME;
	}

	@Override
	protected ExecutionPeriodicityType getExecutionPeriodicityType()
	{
		return ExecutionPeriodicityType.IMMEDIATE;
	}

	@Override
	protected WayTransferOfFundsType getWayTransferOfFundsType()
	{
		return WayTransferOfFundsType.INTERNAL;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType()
	{
		return ClientDefinedEventType.INTERNAL_PAYMENT;
	}

	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		try
		{
			InternalTransfer internalTransfer = getBusinessDocument();
			BigDecimal rate = internalTransfer.getConvertionRate();

			if (internalTransfer.isConvertion() && rate != null)
			{
				return new ClientDefinedFactBuilder()
						.append(super.createClientDefinedAttributeList())
						.append(ClientDefinedFactConstants.COURSE_ATTRIBUTE_NAME, rate.toString(), DataType.STRING)
						.build();
			}
			return super.createClientDefinedAttributeList();
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}
}
