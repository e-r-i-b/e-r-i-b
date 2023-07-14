package com.rssl.phizicgate.rsV51.senders;

import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.TarifPlanCodeType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.CurrencyExchangeTransfer;
import com.rssl.phizicgate.rsV51.bankroll.AccountImpl;
import com.rssl.phizicgate.rsV51.demand.ExpandedPaymentDemand;
import com.rssl.phizicgate.rsV51.demand.PaymentDemandBase;
import com.rssl.phizicgate.rsV51.demand.Remittee;

import java.math.BigDecimal;

/**
 * @author Krenev
 * @ created 17.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyExchangeTransferSender extends AbstractPaymentSender
{
	private static final String PARAMETR_GROUND = "ground";
	/**
	 * создать заявку
	 * @return заявка
	 */
	protected PaymentDemandBase createDemand()
	{
		return new ExpandedPaymentDemand();
	}

	/**
	 *
	 * @param transfer платеж
	 * @return true, если банк покупает валюту (клиент продает) за рубли
	 */
	private boolean isPurchaseForRubles(CurrencyExchangeTransfer transfer)
	{
        //==проверяем соблюдение интерфейса
		assert (transfer.getDestinationAmount() != null && transfer.getChargeOffAmount() != null);

		return "RUR".equals(transfer.getDestinationAmount().getCurrency().getCode())
				|| "RUB".equals(transfer.getDestinationAmount().getCurrency().getCode());
	}

	/**
	 *
	 * @param transfer платеж
	 * @return true, если банк продает валюту (клиент покупает) за рубли
	 */
	private boolean isSaleForRubles(CurrencyExchangeTransfer transfer)
	{
        //==проверяем соблюдение интерфейса
		assert (transfer.getDestinationAmount() != null && transfer.getChargeOffAmount() != null);

		return "RUR".equals(transfer.getChargeOffAmount().getCurrency().getCode())
				|| "RUB".equals(transfer.getChargeOffAmount().getCurrency().getCode());
	}

    protected Remittee createReceiver(PaymentDemandBase demand) throws GateLogicException, GateException
	{
		Remittee receiver = new Remittee();
		receiver.setGround(demand.getGround());

		return receiver;
	}
	/**
	 * послать документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	protected void fillDemand(PaymentDemandBase demand, GateDocument document) throws GateLogicException, GateException
	{
		if (!(document instanceof CurrencyExchangeTransfer))
			throw new GateException("Неверный тип платежа, должен быть - CurrencyExchangeTransfer.");
		CurrencyExchangeTransfer payment = (CurrencyExchangeTransfer) document;
		super.fillDemand(demand, payment);
		ExpandedPaymentDemand expandedPaymentDemand = (ExpandedPaymentDemand) demand;

		Long clientLongId = Long.valueOf(document.getClientInfo().getExternalOwnerId());//Long.valueOf(client.getId());
		AccountImpl chargeOffAccount = getAccount(payment.getChargeOffAccount(), clientLongId);
		AccountImpl destinationAccount = getAccount(payment.getDestinationAccount(), clientLongId);

		expandedPaymentDemand.setGround(getDocumentGround());
		expandedPaymentDemand.setReceiverAccount(payment.getDestinationAccount());

		CurrencyRateService courseService = GateSingleton.getFactory().service(CurrencyRateService.class);
		Money payerAmount = payment.getChargeOffAmount();
		Money receiverAmount = payment.getDestinationAmount();
		CurrencyRate CBRate=null;
		Office office = payment.getOffice();
		double rateForRetail = 0;

		if (isSaleForRubles(payment))
		{
			CurrencyRate rate = courseService.convert(destinationAccount.getCurrency(), payerAmount, office, TarifPlanCodeType.UNKNOWN);
			rateForRetail = rate.getToValue().doubleValue();
			CBRate=courseService.getRate(destinationAccount.getCurrency(), chargeOffAccount.getCurrency(), CurrencyRateType.CB, office, TarifPlanCodeType.UNKNOWN);
		}
		else
		{
			//конверсия валюты или когда банк покупает валюту за рубли (клиент продает валюту)
			CurrencyRate rate = courseService.convert(receiverAmount, chargeOffAccount.getCurrency(), office, TarifPlanCodeType.UNKNOWN);
			rateForRetail = rate.getFromValue().doubleValue();
			CBRate=courseService.getRate(destinationAccount.getCurrency(), chargeOffAccount.getCurrency(), CurrencyRateType.CB, office, TarifPlanCodeType.UNKNOWN);
		}
		expandedPaymentDemand.setCurrencyCode(Long.valueOf(payerAmount.getCurrency().getExternalId()));
		expandedPaymentDemand.setSummaOut(payerAmount.getDecimal());

		expandedPaymentDemand.setReceiverCurrencyCode(Long.valueOf(receiverAmount.getCurrency().getExternalId()));
		expandedPaymentDemand.setCurrencyBought(receiverAmount.getDecimal());
		expandedPaymentDemand.setOperationRest(new BigDecimal(rateForRetail));
		expandedPaymentDemand.setOperationCBRest(CBRate.getToValue());

		AccountImpl account = getAccount(expandedPaymentDemand.getReceiverAccount(), expandedPaymentDemand.getClientCode());
		expandedPaymentDemand.setReceiverOffice( account.getBranch() );
		expandedPaymentDemand.setReceiverCorAccount(account.getDescription());

		Remittee receiver = createReceiver(expandedPaymentDemand);
		expandedPaymentDemand .setDestination(receiver);
	}


	/**
	* Получить описание основания платежа из настроек
	* Domain: Text
	* @return основание
	*/
	private String getDocumentGround()
	{
		return (String) getParameter(PARAMETR_GROUND);
	}
}
