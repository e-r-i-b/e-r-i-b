package com.rssl.phizic.business.fraudMonitoring.senders.templates.builders;

import com.rssl.phizic.business.documents.templates.impl.InternalTransferTemplate;
import com.rssl.phizic.rsa.senders.types.AccountPayeeType;
import com.rssl.phizic.rsa.senders.types.BeneficiaryType;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.DirectionTransferFundsType;

/**
 * Базовый класс билдеров шаблонов документов между моими счетами/картами
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeInternalTransferTemplateRequestBuilderBase<TD extends InternalTransferTemplate> extends AnalyzeTemplateFraudMonitoringRequestBuilderBase<TD>
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
	protected ClientDefinedEventType getClientDefinedEventType()
	{
		return ClientDefinedEventType.INTERNAL_PAYMENT;
	}

}
