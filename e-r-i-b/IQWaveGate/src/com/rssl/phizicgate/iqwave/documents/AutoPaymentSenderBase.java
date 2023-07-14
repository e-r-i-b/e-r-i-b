package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.AbstractBillingPayment;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;

/**
 * Ѕазовый сендре дл€ регистрации, редатировани€ и отмены автоплатежа
 * @author niculichev
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract  class AutoPaymentSenderBase extends IQWaveAbstractDocumentSender
{
	protected AutoPaymentSenderBase(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * @return <им€ запроса на исполнение платежа, им€ запроса присылаемое ¬— на исполнение документа>
	 */
	protected abstract Pair<String, String> getExecutionMessageName();

	/**
	 * послать документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractBillingPayment))
			throw new GateException("Ќе корректный тип, ожидаетс€ наследник AbstractBillingPayment");

		AbstractBillingPayment payment = (AbstractBillingPayment) document;

		if (!(document instanceof AutoPayment))
			throw new GateException("Ќе корректный тип, ожидаетс€ наследник AutoPayment");

		AutoPayment autoPayment = (AutoPayment) document;

		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(getExecutionMessageName().getFirst());

		fillExecutionMessage(message, autoPayment);
		Document response = serviceFacade.sendOnlineMessage(message, null);
		//устанавливаем внешний идентификатор
		String externalId = getExternalId(response);
		payment.setExternalId(externalId);

		addOfflineDocumentInfo(payment);
	}

	/**
	 * валидаци€ платежа
	 * @param document  платеж
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void validate(GateDocument document) throws GateException, GateLogicException
	{
	}

	/**
	 * «аполнить сообщение на исполение платежа
	 * @param message сообщение
	 * @param payment платеж
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	protected void fillExecutionMessage(GateMessage message, AutoPayment payment) throws GateException, GateLogicException
	{
		message.addParameter(Constants.AUTO_PAY_CARD_NO_TEG, payment.getCardNumber());
		message.addParameter(Constants.AUTO_PAY_TEL_NO_TEG, payment.getRequisite());
		RequestHelper.appendRouteCode(message,Constants.AUTO_PAY_PROVIDER_ID_TEG, Long.valueOf(payment.getCodeService()));

		final ExecutionEventType executionEventType = payment.getExecutionEventType();
		boolean isRegular = isReqularType(executionEventType);
		if (isRegular)
		{
			message.addParameter(Constants.AUTO_PAY_TYPE_TEG, 3);
		}
		else if (executionEventType == ExecutionEventType.REDUSE_OF_BALANCE)
		{
			message.addParameter(Constants.AUTO_PAY_TYPE_TEG, 0);
		}
		else if (executionEventType == ExecutionEventType.BY_INVOICE)
		{
			message.addParameter(Constants.AUTO_PAY_TYPE_TEG, 1);
		}
		else
		{
			throw new GateException("Ќеизвестный тип автоплатежа " + executionEventType);
		}

		if (isRegular || executionEventType == ExecutionEventType.REDUSE_OF_BALANCE)
		{
			RequestHelper.appendAmount(message, Constants.AUTO_PAY_AMOUNT_TEG, payment.getAmount());
		}

		if(isRegular)
		{
			message.addParameter(Constants.AUTO_PAY_CYCLE_DATE_TEG, (DateHelper.formatDateToStringWithPoint(payment.getStartDate())));
			message.addParameter(Constants.AUTO_PAY_CYCLE_PERIOD_TEG, getPeriodFormat(payment.getExecutionEventType()));
		}
		else if (executionEventType == ExecutionEventType.REDUSE_OF_BALANCE)
		{
			RequestHelper.appendAmount(message, Constants.AUTO_PAY_FLOOR_LIMIT_TEG, payment.getFloorLimit());
		}
		else if (executionEventType == ExecutionEventType.BY_INVOICE)
		{
			RequestHelper.appendAmount(message, Constants.AUTO_PAY_MAX_SUM_TEG, payment.getFloorLimit());
		}
		String name = payment.getFriendlyName();
		// ≈сли им€ не задано, то по умолчанию будет им€, сложенное из "номера телефона" и "суммы пополнени€" или "лимита пополнени€" (дл€ автоплатежа по выставленному счету).
		if(StringHelper.isEmpty(name))
		{
			if (executionEventType == ExecutionEventType.BY_INVOICE)
			{
				name = payment.getRequisite() + payment.getFloorLimit().getDecimal();
			}
			else
			{
				name = payment.getRequisite() + payment.getAmount().getDecimal();
			}
		}

		message.addParameter(Constants.AUTO_PAY_FRIENDLY_NAME_TEG, name);

		// только дл€ соблюдени€ пор€дка, было бы хорошо написать в общем ифе выше
		if(executionEventType == ExecutionEventType.REDUSE_OF_BALANCE && payment.getTotalAmountLimit() != null)
		{
			RequestHelper.appendAmount(message, Constants.AUTO_PAY_TOTAL_AMOUNT_TAG, payment.getTotalAmountLimit());
			message.addParameter(Constants.AUTO_PAY_TOTAL_PERIOD_TAG, getTotalAmountPeriodFormat(payment.getTotalAmountPeriod()));
		}
	}

	/**
	 * ѕолучение периода исполнени€ дл€ регул€рного автоплатежа в формате IQWave
	 * @param type тип
	 * @return 1 - раз в мес€ц, 2 - ежеквартально, 3 - раз в год.
	 */
	private long getPeriodFormat(ExecutionEventType type) throws GateException
	{
		switch(type)
		{
			// раз в мес€ц
			case ONCE_IN_MONTH : return 1;
			//раз в квартал
			case ONCE_IN_QUARTER : return 2;
			//раз в год
			case ONCE_IN_YEAR : return 3;

			default:
				throw new GateException("Ќеверный тип регул€рного автоплатежа");
		}
	}

	/**
	 * ќпределение типа автоплатежа(–егул€рный или пороговый)
	 * @param type тип
	 * @return 1 - регул€рный, 2 - пороговый
	 * @throws GateException
	 */
	private boolean isReqularType(ExecutionEventType type) throws GateException
	{
		if(type == ExecutionEventType.ONCE_IN_MONTH || type == ExecutionEventType.ONCE_IN_QUARTER || type == ExecutionEventType.ONCE_IN_YEAR)
			return true;
		else if (type == ExecutionEventType.REDUSE_OF_BALANCE || type == ExecutionEventType.BY_INVOICE)
			return false;

		throw new GateException("Ќеверный тип автоплатежа");
	}

	/**
	 * ѕолучение периода подсчета общей суммы в соответствии с интеграцией
	 * @param totalAmountPeriod период в гейтовом формате
	 * @return период в формате дл€ iqw
	 * @throws GateException
	 */
	private int getTotalAmountPeriodFormat(TotalAmountPeriod totalAmountPeriod) throws GateException
	{
		switch (totalAmountPeriod)
		{
			case IN_DAY:
				return 9;
			case IN_WEEK:
				return 4;
			case IN_TENDAY:
				return 5;
			case IN_MONTH:
				return 1;
			case IN_QUARTER:
				return 2;
			case IN_YEAR:
				return 3;
			default:
				throw new GateException("Ќеверно указан период дл€ подсчета общей суммы списани€");
		}
	}

	@Override
	protected String getConfirmRequestName(GateDocument document)
	{
		return getExecutionMessageName().getSecond();
	}
}
