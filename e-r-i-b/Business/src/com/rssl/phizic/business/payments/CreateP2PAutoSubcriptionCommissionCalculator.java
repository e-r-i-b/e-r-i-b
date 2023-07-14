package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.IndicateFormFieldsException;
import com.rssl.phizic.business.documents.payments.EditP2PAutoTransferClaim;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateFieldIncorrectException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.esberibgate.autopayments.P2PAutoSubscriptionCommissionListener;
import com.rssl.phizicgate.esberibgate.autopayments.P2PAutoSubscriptionCommissionCalculator;
import com.rssl.phizicgate.esberibgate.documents.senders.CardToCardAutoSubscriptionCommissionCalculator;

/**
 * Подсчет комиссии P2P автоплатежей через WAY4.
 *
 * @author bogdanov
 * @ created 07.05.15
 * @ $Author$
 * @ $Revision$
 */
public class CreateP2PAutoSubcriptionCommissionCalculator extends CardToCardAutoSubscriptionCommissionCalculator implements P2PAutoSubscriptionCommissionListener
{
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		if (!ConfigFactory.getConfig(PaymentsConfig.class).isP2pAutoPayCommissionViaWAY4())
		{
			super.calcCommission(transfer);
			return;
		}

		AutoSubscriptionClaim claim = (AutoSubscriptionClaim) transfer;
		if (claim.getState().getCode().equals("INITIAL") && !(claim instanceof EditP2PAutoTransferClaim))
		{
			claim.setCommission(null);
			return;
		}

		try
		{
			P2PAutoSubscriptionCommissionCalculator requestSender = new P2PAutoSubscriptionCommissionCalculator(GateSingleton.getFactory(), this);
			requestSender.calcCommission(claim);
		}
		catch (GateFieldIncorrectException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			LOG.error("Ошибка при получении комиссии P2P автоплатежа ", e);
			super.calcCommission(transfer);
			return;
		}
	}

	public void onCommission(Long status, String statusDescription, Money commission, AutoSubscriptionClaim claim) throws GateLogicException, GateException
	{
		if (status == 0L)
		{
			claim.setCommission(commission);
			return;
		}
		else if (status == -623L || status == -601L)
		{
			throw new GateFieldIncorrectException("fromResource", true, "Совершение операции по указанной карте списания недоступно. Выберите другую карту или обратитесь в Контактный центр Сбербанка");
		}
		else if ((-102L <= status && status <= -100L) || (status == -105L))
		{
			CreateP2PAutoSubcriptionCommissionCalculator.super.calcCommission(claim);
			return;
		}

		throw new GateException(statusDescription);
	}
}
