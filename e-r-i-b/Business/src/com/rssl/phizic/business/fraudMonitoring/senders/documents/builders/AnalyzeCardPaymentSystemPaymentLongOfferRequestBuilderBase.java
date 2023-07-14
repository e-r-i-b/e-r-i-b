package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.AccountPayeeType;
import com.rssl.phizic.rsa.senders.types.BeneficiaryType;
import com.rssl.phizic.rsa.senders.types.DirectionTransferFundsType;
import com.rssl.phizic.rsa.senders.types.WayTransferOfFundsType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Базовый класс билдеров запросов для заявко создания/редактирования автоплатежей с карт в БС
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeCardPaymentSystemPaymentLongOfferRequestBuilderBase<BD extends RurPayment> extends AnalyzeAutoSubscriptionClaimRequestBuilderBase<BD>
{
	@Override
	protected AccountPayeeType getAccountPayeeType()
	{
		return AccountPayeeType.BILLER;
	}

	@Override
	protected BeneficiaryType getBeneficiaryType() throws GateException
	{
		return getBeneficiaryType(getBusinessDocument().getReceiverBank());
	}

	@Override
	protected DirectionTransferFundsType getDirectionTransferFundsType()
	{
		return DirectionTransferFundsType.ME_TO_YOU;
	}

	@Override
	protected WayTransferOfFundsType getWayTransferOfFundsType() throws GateException
	{
		return getWayTransferOfFundsType(getBusinessDocument().getReceiverBank());
	}

	@Override
	protected AccountData createMyAccountData()
	{
		return createMyAccountData(getBusinessDocument());
	}

	protected AccountData createMyAccountData(AbstractAccountsTransfer document)
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
		return createOtherAccountData(getBusinessDocument());
	}

	protected AccountData createOtherAccountData(RurPayment document) throws GateException
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
		RurPayment transfer = getBusinessDocument();
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(createAutoPaymentData(transfer))
				.append(GROUND_FIELD_NAME, StringHelper.getEmptyIfNull(transfer.getGround()), DataType.STRING)
				.append(FROM_CARD_NUMBER_FIELD_NAME, transfer.getChargeOffCard(), DataType.STRING)
				.append(TO_ACCOUNT_NUMBER_FIELD_NAME, transfer.getReceiverAccount(), DataType.STRING);

		ResidentBank residentBank = transfer.getReceiverBank();
		if (residentBank != null)
		{
			builder.append(RECEIVER_BANK_BIC_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(residentBank.getBIC()), DataType.STRING);
			builder.append(RECEIVER_BANK_CORR_ACCOUNT_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(residentBank.getAccount()), DataType.STRING);
			builder.append(RECEIVER_BANK_NAME_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(residentBank.getName()), DataType.STRING);
			builder.append(RECEIVER_INN_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(transfer.getReceiverINN()), DataType.STRING);
			builder.append(getBICReceiverAccountClientDefinedFact(residentBank.getBIC(), transfer.getReceiverAccount()));
			builder.append(getClientBICReceiverAccountClientDefinedFact(getDocumentOwner().getId(), residentBank.getBIC(), transfer.getReceiverAccount()));
		}

		if (StringHelper.isNotEmpty(transfer.getReceiverAccount()))
		{
			builder.append(RECEIVER_BAL_ACCOUNT_FIELD_NAME, transfer.getReceiverAccount().substring(0, 5), DataType.STRING);
		}

		try
		{
			List<Field> fields = getBusinessDocument().getExtendedFields();
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
