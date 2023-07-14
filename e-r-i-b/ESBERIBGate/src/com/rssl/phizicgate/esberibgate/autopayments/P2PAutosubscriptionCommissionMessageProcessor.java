package com.rssl.phizicgate.esberibgate.autopayments;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.*;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

import java.util.Calendar;

/**
 * Обработчик сообщения о комиссии для автоплатежа p2p.
 *
 * @author bogdanov
 * @ created 10.06.15
 * @ $Author$
 * @ $Revision$
 */

public class P2PAutosubscriptionCommissionMessageProcessor extends OnlineMessageProcessorBase<CalcCardToCardTransferCommissionRs>
{
	private CalcCardToCardTransferCommissionRq request;
	private final BackRefInfoRequestHelper requestHelper;
	private final AutoSubscriptionClaim claim;
	private final P2PAutoSubscriptionCommissionListener commissionListener;

	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 * @param claim документ
	 * @param serviceName имя сервиса
	 * @param commissionListener слушатель комиссии
	 */
	public P2PAutosubscriptionCommissionMessageProcessor(GateFactory factory, AutoSubscriptionClaim claim, String serviceName, P2PAutoSubscriptionCommissionListener commissionListener)
	{
		super(ESBSegment.federal, serviceName);
		requestHelper = new BackRefInfoRequestHelper(factory);
		this.claim = claim;
		this.commissionListener = commissionListener;
	}

	@Override
	protected Object initialize() throws GateException, GateLogicException
	{
		request = createGetCommissionForAutoPay(claim);
		return request;
	}

	@Override
	protected String getResponseId(CalcCardToCardTransferCommissionRs response)
	{
		return response.getRqUID();
	}

	@Override
	protected String getResponseErrorCode(CalcCardToCardTransferCommissionRs response)
	{
		return String.valueOf(response.getStatus().getStatusCode());
	}

	@Override
	protected String getResponseErrorMessage(CalcCardToCardTransferCommissionRs response)
	{
		return response.getStatus().getStatusDesc();
	}

	@Override
	protected String getRequestId()
	{
		return request.getRqUID();
	}

	@Override
	protected String getRequestSystemId()
	{
		return request.getSPName().value();
	}

	@Override
	protected String getRequestMessageType()
	{
		return CalcCardToCardTransferCommissionRq.class.getSimpleName();
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return Request.SKIP_MONITORING;
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<CalcCardToCardTransferCommissionRs>> request, Response<CalcCardToCardTransferCommissionRs> response) throws GateException, GateLogicException
	{
		CalcCardToCardTransferCommissionRs responce = response.getResponse();
		long status = Long.parseLong(response.getErrorCode());
		String statusDesc = response.getErrorMessage();
		Money commission = null;
		if (status != 0)
		{
			log.warn("Вернулось сообщение с ошибкой. Ошибка номер " + response.getErrorCode() + ". " + response.getErrorMessage());
		}
		else
		{
			Currency currency = GateSingleton.getFactory().service(CurrencyService.class).findByNumericCode(responce.getCommissionCur());
			commission = new Money(responce.getCommission(), currency);
		}

		commissionListener.onCommission(status, statusDesc, commission, claim);
	}

	/**
	 * Выполняет запрос на получении комиссии для перевода P2P карта-карта.
	 *
	 * @param autoSub
	 * @return
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public CalcCardToCardTransferCommissionRq createGetCommissionForAutoPay(AutoSubscription autoSub) throws GateLogicException, GateException
	{
		CalcCardToCardTransferCommissionRq calcCardToCardTransferCommissionRq = new CalcCardToCardTransferCommissionRq();
		calcCardToCardTransferCommissionRq.setRqUID(RequestHelper.generateUUID());
		calcCardToCardTransferCommissionRq.setRqTm(RequestHelper.generateRqTm());
		calcCardToCardTransferCommissionRq.setOperUID(RequestHelper.generateOUUID());
		calcCardToCardTransferCommissionRq.setSPName(SPNameType.BP_ERIB);
		calcCardToCardTransferCommissionRq.setBankInfo(RequestHelper.makeBankInfo(requestHelper.getRbTbBrch((AutoSubscriptionClaim) autoSub)));
		calcCardToCardTransferCommissionRq.setRRN(RrnGenerator.generateAutopayment(Calendar.getInstance()).rrn);
		SourceType sourceType = new SourceType();
		sourceType.setContractNumber(ConfigFactory.getConfig(DocumentConfig.class).getP2pAutoPayCommissionContractNumber());
		calcCardToCardTransferCommissionRq.setSource(sourceType);

		CardAcctIdType from = new CardAcctIdType();
		from.setCardNum(autoSub.getCardNumber());
		calcCardToCardTransferCommissionRq.setCardAcctIdFrom(from);

		CardAcctIdType to = new CardAcctIdType();
		to.setCardNum(autoSub.getReceiverCard());
		calcCardToCardTransferCommissionRq.setCardAcctIdTo(to);

		if (autoSub.getAmount() == null)
			throw new GateException("Должна быть установлена сумма автоплатежа.");
		calcCardToCardTransferCommissionRq.setCurAmt(autoSub.getAmount().getDecimal());
		calcCardToCardTransferCommissionRq.setAcctCur("RUR");

		return calcCardToCardTransferCommissionRq;
	}
}
