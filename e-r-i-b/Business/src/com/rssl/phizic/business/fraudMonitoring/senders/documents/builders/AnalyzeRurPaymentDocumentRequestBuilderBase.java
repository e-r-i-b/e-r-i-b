package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;
import com.rssl.phizic.business.receptiontimes.TimeMatching;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.*;
import com.rssl.phizic.utils.StringHelper;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Базовый класс билдеров запросов перевода частному лицу
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeRurPaymentDocumentRequestBuilderBase extends AnalyzeDocumentRequestBuilderBase<RurPayment>
{
	private static final ReceptionTimeService receptionTimeService = new ReceptionTimeService();

	protected AccountPayeeType getAccountPayeeType()
	{
		return AccountPayeeType.BILLER;
	}

	protected DirectionTransferFundsType getDirectionTransferFundsType()
	{
		return DirectionTransferFundsType.ME_TO_YOU;
	}

	protected ExecutionPeriodicityType getExecutionPeriodicityType()
	{
		try
		{
			GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);
			if (receptionTimeService.getWorkTimeInfo(getBusinessDocument()).isWorkTimeNow() == TimeMatching.RIGHT_NOW || gateInfoService.isDelayedPaymentNeedSend(getBusinessDocument().getOffice()))
			{
				return ExecutionPeriodicityType.IMMEDIATE;
			}
			return ExecutionPeriodicityType.SCHEDULED;
		}
		catch (Exception e)
		{
			log.error(e);
			return ExecutionPeriodicityType.IMMEDIATE;
		}
	}

	protected WayTransferOfFundsType getWayTransferOfFundsType()
	{
		return getBusinessDocument().isOurBank() ? WayTransferOfFundsType.INTERNAL : WayTransferOfFundsType.WIRE;
	}

	protected ClientDefinedEventType getClientDefinedEventType()
	{
		return ClientDefinedEventType.RUR_PAYMENT;
	}

	protected AccountData createMyAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(getBusinessDocument().getChargeOffAccount());
		accountData.setInternationalAccountNumber(getBusinessDocument().getChargeOffAccount());
		return accountData;
	}

	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		RurPayment payment = getBusinessDocument();
		return new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(GROUND_FIELD_NAME, payment.getGround(), DataType.STRING)
				.append(RECEIVER_BANK_BIC_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(payment.getReceiverBIC()), DataType.STRING)
				.append(RECEIVER_BANK_NAME_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(payment.getReceiverBankName()), DataType.STRING)
				.append(RECEIVER_BANK_CORR_ACCOUNT_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(payment.getReceiverCorAccount()), DataType.STRING)
				.append(RECEIVER_INN_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(payment.getReceiverINN()), DataType.STRING)
				.append(RECEIVER_FIO_FIELD_NAME, StringHelper.getEmptyIfNull(payment.getReceiverName()), DataType.STRING)
				.append(getBICReceiverAccountClientDefinedFact(payment.getReceiverBIC(), payment.getReceiverAccount()))
				.append(getClientBICReceiverAccountClientDefinedFact(getDocumentOwner().getId(), payment.getReceiverBIC(), payment.getReceiverAccount()))
				.build();
	}
}
