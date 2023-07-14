package com.rssl.phizic.business.fraudMonitoring.senders.templates.builders;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.impl.IndividualTransferTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.AccountPayeeType;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.DirectionTransferFundsType;
import com.rssl.phizic.utils.StringHelper;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.RECEIVER_FIO_FIELD_NAME;
import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.RECEIVER_INN_ATTRIBUTE_NAME;

/**
 * Билдер запросов во ФМ для шаблонов оплаты ЧЛ
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeRurPaymentTemplateRequestBuilderBase extends AnalyzeTemplateFraudMonitoringRequestBuilderBase<IndividualTransferTemplate>
{
	protected AccountPayeeType getAccountPayeeType()
	{
		return AccountPayeeType.BILLER;
	}

	protected DirectionTransferFundsType getDirectionTransferFundsType()
	{
		return DirectionTransferFundsType.ME_TO_YOU;
	}

	protected ClientDefinedEventType getClientDefinedEventType()
	{
		return ClientDefinedEventType.RUR_PAYMENT;
	}

	protected AccountData createMyAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(getTemplateDocument().getChargeOffAccount());
		accountData.setInternationalAccountNumber(getTemplateDocument().getChargeOffAccount());
		return accountData;
	}

	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		TemplateDocument template = getTemplateDocument();
		return new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(RECEIVER_INN_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(template.getReceiverINN()), DataType.STRING)
				.append(RECEIVER_FIO_FIELD_NAME, StringHelper.getEmptyIfNull(template.getReceiverName()), DataType.STRING)
				.build();
	}
}
