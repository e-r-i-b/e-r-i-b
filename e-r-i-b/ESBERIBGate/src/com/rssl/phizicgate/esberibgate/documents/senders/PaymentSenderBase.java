package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.PostConfirmCalcCommission;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 16.07.2010
 * @ $Author$
 * @ $Revision$
 * Базовый сендер для платежек шины(запрос XferAddRq)
 * Сендре может работать в 2 режимах: длительное поручение и обычный платеж
 * Определяется параметорм is-long-offer-mode
 */
public abstract class PaymentSenderBase extends DocumentSenderBase
{
	protected static final String REMAIND_IN_RECIP_MESSAGE = "Данный автоплатеж можно создать только для операций внутри одного территориального банка Сбербанка. " +
			"Пожалуйста, выберите другой тип суммы или укажите другой счет зачисления.";
	protected static final String OVER_DRAFT_MESSAGE = "Данный автоплатеж можно создать только для операций внутри одного территориального банка Сбербанка. " +
			"Пожалуйста, выберите другой счет зачисления.";
	public static final String IS_LONG_OFFER_MODE_PARAMETER_NAME = "is-long-offer-mode";
	private static Map<String, String> assignee = new HashMap<String, String>();    //правоприемники

	static
	{
		TBSAXSource tbsaxSource = new TBSAXSource();
		assignee  = tbsaxSource.getSource();
	}

	private boolean isLongOfferMode;

	public PaymentSenderBase(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		Object parameterValue = params.get(IS_LONG_OFFER_MODE_PARAMETER_NAME);
		isLongOfferMode = (parameterValue != null) && Boolean.valueOf((String) parameterValue);
		super.setParameters(params);
	}

	/**
	 * @return признак того, что сендер работает в режиме длительного поручения
	 */

	public boolean isLongOfferMode()
	{
		return isLongOfferMode;
	}

	/**
	 * сформировать запрос на исполнение документа.
	 * Для платежей это всегда XferAddRq.
	 * Заполянеяем заголовки и общие поля.
	 * @param document данные о документе
	 * @return запрос
	 */
	public IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - AbstractTransfer, а пришел " + document.getClass());
		}

		AbstractTransfer transfer = (AbstractTransfer) document;

		XferAddRq_Type xferAddRq = new XferAddRq_Type();
//заполянем ключевые поля
		xferAddRq.setRqUID(PaymentsRequestHelper.generateUUID());
		xferAddRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		xferAddRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		xferAddRq.setSPName(SPName_Type.BP_ERIB);
		xferAddRq.setOperName(getOperationName(transfer));
		xferAddRq.setBankInfo(paymentsRequestHelper.createAuthBankInfo(transfer.getInternalOwnerId()));
//получаем тело сообщения
		XferInfo_Type xferInfo = createBody(transfer);
//заполняем значения микроопераций
		fillCommissionWriteDownOperation(transfer, xferInfo);
//заполяем сумму при необходимости
		fillAmount(transfer, xferInfo);
		xferAddRq.setXferInfo(xferInfo);
//дополняем инфой по параметрам ПД при необходимости
		if (isLongOfferMode)
		{
			xferAddRq.setRegular(paymentsRequestHelper.getRegular((LongOffer) transfer));
		}
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setXferAddRq(xferAddRq);
		return ifxRq;
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		Status_Type statusType = ifxRs.getXferAddRs().getStatus();
		long statusCode = statusType.getStatusCode();
		BackRefCommissionTBSettingService commissionTBSerivice = getFactory().service(BackRefCommissionTBSettingService.class);
		//таймаут
		if (statusCode == UNKNOW_DOCUMENT_STATE_ERROR_CODE)
		{
			throw new GateTimeOutException(statusType.getStatusDesc());
		}
		if(statusCode == -433 && commissionTBSerivice.isCalcCommissionSupport(document))
		{
			//это рассчет комиссии.
			fillCommissions(document, ifxRs);
			throw new PostConfirmCalcCommission();
		}
		if (statusCode != 0)
		{
			//Все ошибки пользовательские. Если описание ошибки не пришло, то выдаем сообщение по умолчанию
			throwGateLogicException(statusType, XferAddRs_Type.class);
		}
		if (document instanceof SynchronizableDocument)
		{

			SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
			synchronizableDocument.setExternalId(ifxRs.getXferAddRs().getRqUID());
		}
	}

	/**
	 * Добавление в тело запроса информации о микрооперациях(комиссиях)
	 * @param transfer данные платежа
	 * @param xferInfo запрос
	 */
	protected void fillCommissionWriteDownOperation(AbstractTransfer transfer, XferInfo_Type xferInfo) throws GateException
	{
		//если подразделение не поддерживает расчет комиссий в цод - ничего не заполняем.
		if(!getFactory().service(BackRefCommissionTBSettingService.class).isCalcCommissionSupport(transfer))
			return;

		xferInfo.setSrcLayoutInfo(getSrcLayoutInfo(transfer));
	}

	protected SrcLayoutInfo_Type getSrcLayoutInfo(AbstractTransfer transfer) throws GateException
	{
		SrcLayoutInfo_Type scrLayoutInfo = new SrcLayoutInfo_Type();

		List<WriteDownOperation> writeDownOperations = transfer.getWriteDownOperations();
		scrLayoutInfo.setIsCalcOperation(writeDownOperations == null || writeDownOperations.isEmpty());
		if (writeDownOperations != null && !writeDownOperations.isEmpty())
		{
			WriteDownOperation_Type[] operations = new WriteDownOperation_Type[writeDownOperations.size()];
			int i = 0;
			for(WriteDownOperation op: writeDownOperations)
			{
				WriteDownOperation_Type operation = new WriteDownOperation_Type();
				operation.setCurAmt(op.getCurAmount().getDecimal());
				operation.setOperationName(op.getOperationName());
				operation.setTurnover(Turnover_Type.fromString(op.getTurnOver()));
				operations[i] = operation; i++;
			}
			scrLayoutInfo.setWriteDownOperation(operations);
		}
		return scrLayoutInfo;
	}

	/**
	 * Заполнение платежа данными микроопераций списания(комиссий)
	 * @param document - платеж
	 * @param ifxRs - ответ
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected void fillCommissions(GateDocument document, IFXRs_Type ifxRs) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Базовый метод получения микроопераций списания из ответа
	 * @param ifxRs - ответ
	 * @return микроперации комиссий.
	 */
	protected SrcLayoutInfo_Type getSrcLayoutInfo(IFXRs_Type ifxRs)
	{
		return ifxRs.getXferAddRs().getSrcLayoutInfo();
	}

	/**
	 * заполнить сумму в запросе. в реализации учитываются ПД(и переопределяющим это делать):
	 * Сумма обязательна при совершении платежа, а также при создании длительного поручения
	 * с кодами в поле <SummaKindCode>:
	 *      1)FIXED_SUMMA,
	 *      2)REMAIND_OVER_SUMMA
	 *      3)FIXED_SUMMA_IN_RECIP_CURR
	 * @param transfer данные платежа
	 * @param xferInfo запрос
	 * @throws GateException
	 */
	protected void fillAmount(AbstractTransfer transfer, XferInfo_Type xferInfo) throws GateException
	{
		Money amount = null;
		if (isLongOfferMode)
		{
			LongOffer longOffer = (LongOffer) transfer;
			SumType sumType = longOffer.getSumType();
			if (sumType != SumType.FIXED_SUMMA && sumType != SumType.REMAIND_OVER_SUMMA && sumType != SumType.FIXED_SUMMA_IN_RECIP_CURR)
			{
				//пропускаем.
				return;
			}
			amount = longOffer.getAmount();
		}
		else
		{
			if(transfer.getInputSumType() == InputSumType.CHARGEOFF)
				amount = transfer.getChargeOffAmount();
			else if (transfer.getInputSumType() == InputSumType.DESTINATION)
				amount = transfer.getDestinationAmount();
			else
				throw new GateException("не задан тип суммы платежа");
		}
		if (amount == null)
			throw new GateException("не задана сумма платежа");

		fillAmount(xferInfo, amount);
	}

	/**
	 * заполнить в сообщении сумму(по умолчанию складывается в поля CurAmt и AcctCur)
	 * Реализации могут изменить место положения суммы
	 * @param xferInfo сообщение
	 * @param amount сумма, не может быть null
	 */
	protected void fillAmount(XferInfo_Type xferInfo, Money amount)
	{
		xferInfo.setCurAmt(amount.getDecimal());
		xferInfo.setAcctCur(amount.getCurrency().getCode());
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - AbstractTransfer, а пришел " + document.getClass());
		}

		AbstractTransfer transfer = (AbstractTransfer) document;
		//проверяем совпадение режима сендера и типа документа
		if (isLongOfferMode ^ LongOffer.class.isAssignableFrom(document.getType()))
		{
			throw new GateException("несовпадение тип документа и режима валидатора:\n тип документа =" + document.getType() + "; isLongOfferMode =" + isLongOfferMode);
		}
		if (!isLongOfferMode)
		{
			return;
		}
		//проверим, что задан процент: Поле обязательно, если <SummaKindCode> = PERCENT_OF_REMAIND.
		LongOffer longOffer = (LongOffer) document;
		if (longOffer.getSumType() == SumType.PERCENT_OF_REMAIND && longOffer.getPercent() == null)
		{
			//где-то косяк в бизнесе - падаем
			throw new GateException(" Не соблюдается условие на Percent : Поле обязательно, если <SummaKindCode> = PERCENT_OF_REMAIND");
		}
		//проверяем, что задана сумма:Сумма обязательна при совершении платежа, а также при создании длительного поручения с кодами
		//1)FIXED_SUMMA,
		//2)REMAIND_OVER_SUMMA
		//3)FIXED_SUMMA_IN_RECIP_CURR
		//в поле <SummaKindCode>.
		SumType sumType = longOffer.getSumType();
		if ((sumType == SumType.FIXED_SUMMA || sumType == SumType.REMAIND_OVER_SUMMA||sumType == SumType.FIXED_SUMMA_IN_RECIP_CURR) && longOffer.getAmount() == null)
		{
			//где-то косяк в бизнесе - падаем
			throw new GateException("сумма отсутсвует для типа суммы :" + sumType);
		}
	}

	/**
	 * Заполнить тело сообщения без суммы
	 * @param document документ
	 * @return тело сообщения
	 */
	protected abstract XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException;

	/**
	 * Получить тип операции
	 * @param document документ
	 * @return тип операции
	 */
	protected abstract OperName_Type getOperationName(AbstractTransfer document);

	/**
	 * Заполнить структуру информация по карте
	 * @param card карта
	 * @param owner владелец
	 * @param expireDate дата закрытия
	 * @return CardAcctId_Type
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public CardAcctId_Type createCardAcctId(Card card, Client owner, Calendar expireDate) throws GateLogicException, GateException
	{
		CardAcctId_Type cardAcctId = super.createCardAcctId(card, owner, expireDate);
		if (isLongOfferMode)
		{
			String primaryAccountNumber = null;
			try {
				Account account = GroupResultHelper.getOneResult(getFactory().service(BankrollService.class).getCardPrimaryAccount(card));
				primaryAccountNumber = account.getNumber();
			}
			catch (SystemException e)
			{
				throw new GateException(e);
			}
			catch (LogicException e)
			{
				throw new GateLogicException(e);
			}
			if (StringHelper.isEmpty(primaryAccountNumber))
			{
				// Для длительных поручений обязательна передача СКС
				GateLogicException e = new GateLogicException("По данной карте невозможно выполнить операцию. Пожалуйста, выберите другую карту");
				Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
				log.error("Не найден СКС для карты № " + card.getNumber(),e);
				throw e;
			}

			cardAcctId.setAcctId(primaryAccountNumber);
		}
		return cardAcctId;
	}

	/**
	 * Проверяем принадлжедат ли офис источника списания и счет получателя 1 ТБ.
	 * @param office офис источника списания
	 * @param receiverAccount счет получателя. Важно: передаваться должны только сберовские счета
	 * те перед вызовом обязательно проверять является ли банк получаетеля отделением сбера
	 * @return true - принадлежат
	 */
	public static boolean isSameTB(Office office, String receiverAccount) throws GateException
	{
		return BackRefInfoRequestHelper.isSameTB(office, receiverAccount);
	}

	@Override
	public void prepare(GateDocument document) throws GateException, GateLogicException {}
}
