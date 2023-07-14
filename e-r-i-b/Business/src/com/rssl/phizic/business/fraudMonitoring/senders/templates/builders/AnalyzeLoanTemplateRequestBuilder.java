package com.rssl.phizic.business.fraudMonitoring.senders.templates.builders;

import com.rssl.phizic.business.documents.templates.impl.LoanTransferTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.AccountPayeeType;
import com.rssl.phizic.rsa.senders.types.BeneficiaryType;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.DirectionTransferFundsType;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.FROM_CARD_NUMBER_FIELD_NAME;
import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.LOAN_AGREEMENT_NUMBER_FIELD_NAME;

/**
 * Билдер запросов во ФМ для шаблона заявки погашения кредита (тип analyze)
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeLoanTemplateRequestBuilder extends AnalyzeTemplateFraudMonitoringRequestBuilderBase<LoanTransferTemplate>
{
	private LoanTransferTemplate template;

	@Override
	public AnalyzeLoanTemplateRequestBuilder append(LoanTransferTemplate template)
	{
		this.template = template;
		return this;
	}

	@Override
	protected LoanTransferTemplate getTemplateDocument()
	{
		return template;
	}

	@Override
	protected AccountPayeeType getAccountPayeeType()
	{
		return AccountPayeeType.PERSONAL_ACCOUNT;
	}

	@Override
	protected BeneficiaryType getBeneficiaryType() throws GateException
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
		return ClientDefinedEventType.LOAN_PAYMENT;
	}

	@Override
	protected AccountData createMyAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(template.getChargeOffCard());
		accountData.setInternationalAccountNumber(template.getChargeOffCard());
		accountData.setAccountName(template.getPayerName());
		return accountData;
	}

	@Override
	protected AccountData createOtherAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(template.getAgreementNumber());
		accountData.setInternationalAccountNumber(template.getAgreementNumber());
		accountData.setAccountName(template.getAgreementNumber());
		return accountData;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		return new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(FROM_CARD_NUMBER_FIELD_NAME, template.getChargeOffCard(), DataType.STRING)
				.append(LOAN_AGREEMENT_NUMBER_FIELD_NAME, template.getAgreementNumber(), DataType.STRING)
				.build();
	}
}
