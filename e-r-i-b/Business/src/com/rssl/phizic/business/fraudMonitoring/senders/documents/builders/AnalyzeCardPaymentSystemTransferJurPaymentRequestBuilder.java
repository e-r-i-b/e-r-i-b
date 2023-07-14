package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.*;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Билдер запросов для перевода с карты по произвольным реквизитам (тип analyze)
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCardPaymentSystemTransferJurPaymentRequestBuilder extends AnalyzeDocumentRequestBuilderBase<JurPayment>
{
	private JurPayment document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(JurPayment document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected JurPayment getBusinessDocument()
	{
		return document;
	}

	@Override
	protected AccountPayeeType getAccountPayeeType()
	{
		return AccountPayeeType.BILLER;
	}

	@Override
	protected BeneficiaryType getBeneficiaryType() throws GateException
	{
		return getBeneficiaryType(document.getReceiverBank());
	}

	@Override
	protected DirectionTransferFundsType getDirectionTransferFundsType()
	{
		return DirectionTransferFundsType.ME_TO_YOU;
	}

	@Override
	protected ExecutionPeriodicityType getExecutionPeriodicityType()
	{
		return ExecutionPeriodicityType.IMMEDIATE;
	}

	@Override
	protected WayTransferOfFundsType getWayTransferOfFundsType() throws GateException
	{
		return getWayTransferOfFundsType(document.getReceiverBank());
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType() throws GateException
	{
		try
		{
			if (DocumentHelper.isExternalPayment(document))
			{
				return ClientDefinedEventType.EXTERNAL_PROVIDER_PAYMENT;
			}

			return ClientDefinedEventType.RUR_PAY_JUR_SB;
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	@Override
	protected AccountData createMyAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(document.getChargeOffCard());
		accountData.setInternationalAccountNumber(document.getChargeOffCard());
		accountData.setAccountName(document.getPayerName());
		return accountData;
	}

	@Override
	protected AccountData createOtherAccountData() throws GateException
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(getOtherAccountNumber(document));
		accountData.setInternationalAccountNumber(accountData.getAccountNumber());
		accountData.setAccountName(document.getReceiverName());
		return accountData;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(GROUND_FIELD_NAME, StringHelper.getEmptyIfNull(document.getGround()), DataType.STRING)
				.append(FROM_CARD_NUMBER_FIELD_NAME, document.getChargeOffCard(), DataType.STRING)
				.append(TO_ACCOUNT_NUMBER_FIELD_NAME, document.getReceiverAccount(), DataType.STRING);


		ResidentBank residentBank = document.getReceiverBank();
		if (residentBank != null)
		{
			builder.append(RECEIVER_BANK_BIC_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(residentBank.getBIC()), DataType.STRING);
			builder.append(RECEIVER_BANK_CORR_ACCOUNT_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(residentBank.getAccount()), DataType.STRING);
			builder.append(RECEIVER_BANK_NAME_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(residentBank.getName()), DataType.STRING);
			builder.append(RECEIVER_INN_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(document.getReceiverINN()), DataType.STRING);
			builder.append(getBICReceiverAccountClientDefinedFact(residentBank.getBIC(), document.getReceiverAccount()));
			builder.append(getClientBICReceiverAccountClientDefinedFact(getDocumentOwner().getId(), residentBank.getBIC(), document.getReceiverAccount()));
		}

		if (StringHelper.isNotEmpty(document.getReceiverAccount()))
		{
			builder.append(RECEIVER_BAL_ACCOUNT_FIELD_NAME, document.getReceiverAccount().substring(0, 5), DataType.STRING);
		}

		try
		{
			List<Field> fields = document.getExtendedFields();
			if (CollectionUtils.isNotEmpty(fields))
			{
				for (Field field : fields)
				{
					builder.append(field.getName(), getExtendedFieldValue(field), DataType.STRING);
				}
			}
		}
		catch (DocumentException e)
		{
			log.error(e);
		}
		return builder.build();
	}

}
