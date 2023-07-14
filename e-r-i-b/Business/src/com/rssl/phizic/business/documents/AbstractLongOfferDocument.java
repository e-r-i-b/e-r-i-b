package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.longoffer.*;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.ChannelType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.BooleanUtils;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author krenev
 * @ created 21.09.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractLongOfferDocument extends AbstractPaymentDocument implements LongOffer
{
	public static final String AUTOSUB_TYPE_ATTRIBUTE_NAME                          = "auto-sub-type";

	protected static final String LONG_OFFER_NUMBER_ATTRIBUTE_NAME    = "long-offer-number";
	protected static final String LONG_OFFER_START_DATE_ATTRIBUTE_NAME    = "long-offer-start-date";
	protected static final String LONG_OFFER_END_DATE_ATTRIBUTE_NAME  = "long-offer-end-date";
	protected static final String LONG_OFFER_EXECUTION_EVENT_TYPE_ATTRIBUTE_NAME = "long-offer-event-type";
	protected static final String LONG_OFFER_SUM_TYPE_ATTRIBUTE_NAME  = "long-offer-sum-type";
	protected static final String LONG_OFFER_PAY_DAY_ATTRIBUTE_NAME   = "long-offer-pay-day";
	protected static final String LONG_OFFER_PRIORITY_ATTRIBUTE_NAME  = "long-offer-priority";
	protected static final String LONG_OFFER_PERCENT_ATTRIBUTE_NAME   = "long-offer-percent";
	protected static final String LONG_OFFER_START_DATE_CHANGED_ATTRIBUTE_NAME    = "is-long-offer-start-date-changed";
	protected static final String LONG_OFFER_FIRST_PAYMENT_DATE_ATTRIBUTE_NAME    = "long-offer-first-payment-date";

	private static final String AUTOPAY_CLIENT_TOTAL_AMOUNT_LIMIT_ATTRIBUTE_NAME    = "autopay-client-total-amount-limit";
	private static final String AUTOPAY_CLIENT_TOTAL_AMOUNT_CURR_ATTRIBUTE_NAME     = "autopay-client-total-amount-currency";
	private static final String AUTOPAY_TOTAL_AMOUNT_LIMIT_ATTRIBUTE_NAME           = "autopay-total-amount-limit";
	private static final String AUTOPAY_TOTAL_AMOUNT_CURR_ATTRIBUTE_NAME            = "autopay-total-amount-currency";
	private static final String AUTOPAY_TOTAL_AMOUNT_PERIOD_ATTRIBUTE_NAME          = "autopay-total-amount-period";
	private static final String IS_AUTOPAY_TOTAL_AMOUNT_ATTRIBUTE_NAME              = "is-autopay-total-amount-limit";
	private static final String IS_ALREADY_SHOW_INACTIVE_MBK_WARNING_ATTRIBUTE_NAME = "is-already-show-inactive-mbk-warning";
	private static final String OFFER_EXTERNAL_ID_ATTRIBUTE                         = "offer-external-id";

	private static final int[][] QUARTERLY_PERIOD  = {{0, 3, 6, 9}, {1, 4, 7, 10}, {2, 5, 8, 11}};        //ежеквартально
	private static final int[][] HALFYEAR_PERIOD   = {{0, 6}, {1, 7}, {2, 8}, {3, 9}, {4, 10}, {5, 11}};  //раз в полгода

	private boolean longOffer;

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		calcStartDate(getStartDate());
	}

	/**
	 * @return является ли документ длительным поручением
	 */
	public boolean isLongOffer()
	{
		return longOffer;
	}

	public void setLongOffer(boolean longOffer)
	{
		this.longOffer = longOffer;
	}

	/**
	 * Определяем нужно ли заполнять поле сумма для данного ДП/автоплатежа
	 * @return true - ввод суммы обязателен
	 */
	public boolean needInputAmount()
	{
		SumType sumType = getSumType();
		return sumType == SumType.FIXED_SUMMA || sumType == SumType.REMAIND_OVER_SUMMA
				|| sumType == SumType.FIXED_SUMMA_IN_RECIP_CURR || sumType == SumType.REMAIND_IN_RECIP
					|| sumType == SumType.CREDIT_MANUAL;
	}

	public String getNumber()
	{
		return getNullSaveAttributeStringValue(LONG_OFFER_NUMBER_ATTRIBUTE_NAME);
	}

	/**
	 * Установить номер документа
	 * @param number номер документа
	 */
	public void setNumber(String number)
	{
		setNullSaveAttributeStringValue(LONG_OFFER_NUMBER_ATTRIBUTE_NAME, number);
	}

	public Calendar getStartDate()
	{
		return getNullSaveAttributeCalendarValue(LONG_OFFER_START_DATE_ATTRIBUTE_NAME);
	}

	/**
	 * Установить дату начала действия
	 * @param date дата начала дейсвия
	 */
	public void setStartDate(Calendar date)
	{
		setNullSaveAttributeCalendarValue(LONG_OFFER_START_DATE_ATTRIBUTE_NAME, date);
	}

	/**
	 * @return дата ближайшего платежа
	 */
	public Calendar getFirstPaymentDate()
	{
		return getNullSaveAttributeCalendarValue(LONG_OFFER_FIRST_PAYMENT_DATE_ATTRIBUTE_NAME);
	}

	/**
	 * Установить дату ближайшего платежа
	 * @param date дата ближайшего платежа
	 */
	public void setFirstPaymentDate(Calendar date)
	{
		setNullSaveAttributeCalendarValue(LONG_OFFER_FIRST_PAYMENT_DATE_ATTRIBUTE_NAME, date);
	}

	public Calendar getEndDate()
	{
		return getNullSaveAttributeCalendarValue(LONG_OFFER_END_DATE_ATTRIBUTE_NAME);
	}

	public void setEndDate(Calendar endDate)
	{
		setNullSaveAttributeCalendarValue(LONG_OFFER_END_DATE_ATTRIBUTE_NAME, endDate);
	}

	public ExecutionEventType getExecutionEventType()
	{
		return getNullSaveAttributeEnumValue(ExecutionEventType.class, LONG_OFFER_EXECUTION_EVENT_TYPE_ATTRIBUTE_NAME);
	}

	public void setExecutionEventType(ExecutionEventType eventType)
	{
		setNullSaveAttributeEnumValue(LONG_OFFER_EXECUTION_EVENT_TYPE_ATTRIBUTE_NAME, eventType);
	}

	public void setExecutionEventType(String eventType)
	{
		setExecutionEventType(eventType == null ? null : ExecutionEventType.valueOf(eventType));
	}

	public SumType getSumType()
	{
		return getNullSaveAttributeEnumValue(SumType.class, LONG_OFFER_SUM_TYPE_ATTRIBUTE_NAME);
	}

	public Money getAmount()
	{
		if (isLongOffer())
		{
			SumType sumType = getSumType();
			if (SumType.RUR_SUMMA == sumType)
				return getChargeOffAmount();

			if (SumType.FIXED_SUMMA == sumType)
				return getChargeOffAmount();

			if (SumType.FIXED_SUMMA_IN_RECIP_CURR == sumType)
				return getDestinationAmount();

			if (SumType.REMAIND_OVER_SUMMA == sumType)
				return getChargeOffAmount();

			if (SumType.REMAIND_IN_RECIP == sumType)
				return getDestinationAmount();
		}

		return null;
	}

	public BigDecimal getPercent()
	{
		return (BigDecimal) getNullSaveAttributeValue(LONG_OFFER_PERCENT_ATTRIBUTE_NAME);
	}

	/**
	 * Установить процент
	 * @param percent процент от суммы
	 */
	public void setPercent(BigDecimal percent)
	{
		setNullSaveAttributeDecimalValue(LONG_OFFER_PERCENT_ATTRIBUTE_NAME, percent);
	}

	public Long getPayDay()
	{
		return (Long) getNullSaveAttributeValue(LONG_OFFER_PAY_DAY_ATTRIBUTE_NAME);
	}

	/**
	 * Установить день месяца выполнения автоплатежа
	 * @param calendar дата выполнения автоплатежа
	 */
	public void setPayDay(Calendar calendar)
	{
		setNullSaveAttributeLongValue(LONG_OFFER_PAY_DAY_ATTRIBUTE_NAME, Long.valueOf(calendar.get(Calendar.DATE)));
	}

	public Long getPriority()
	{
		return (Long) getNullSaveAttributeValue(LONG_OFFER_PRIORITY_ATTRIBUTE_NAME);
	}

	/**
	 * Пересчитать и сохранить ближайшую дату платежа
	 * Внимание!!! дергать метод осмысленно(только там где нужно).
	 * @param date - дата начала действия поручения
	 * @return true/false
	 */
	public boolean calcStartDate(Calendar date) throws DocumentException, DocumentLogicException
	{
		// для автоплатежей дата ближашего платежа равна дате начала
		if(this instanceof AutoPayment || this instanceof CardPaymentSystemPaymentLongOffer)
			return false;

		Calendar firstPaymentDate = getFirstPaymentDate();
		if (firstPaymentDate == null)
			return false;

		//если расчетная дата ближайшего платежа больше установленной,
		//то меняем дату ближайшего платежа и подкрашиваем ее на форме
		Calendar calcDate = getImmediateDate(date, firstPaymentDate);
		boolean isChanged = DateHelper.clearTime(firstPaymentDate).compareTo(DateHelper.clearTime(calcDate)) < 0;

		if (isChanged)
			setFirstPaymentDate(calcDate);
		                                                           
		setStartDateChanged(isChanged);

		return isChanged;
	}

	private Calendar getImmediateDate(Calendar date, Calendar startDate) throws DocumentException, DocumentLogicException
	{
		//для типа переодичности "по событию" возвращаем дату ближайшего платежа, которая пришла
		if (!isPeriodic())
			return date;

		ExecutionEventType eventType = getExecutionEventType();
		int payDay = getPaymentDay();
		int currentYear  = date.get(Calendar.YEAR);         //текущий год
		int currentMonth = date.get(Calendar.MONTH);        //текущий месяц
		int currentDay   = date.get(Calendar.DATE);         //текущий день
		int startMonth   = startDate.get(Calendar.MONTH);   //месяц выбранный клиентом (для определения периода)

		int nextMonth = 0;

		if (ExecutionEventType.ONCE_IN_MONTH == eventType)
		{
			//если день платежа в этом месяце
			if (payDay >= currentDay)
				return newActualTime(currentYear, currentMonth, payDay);

			//устанавливаем месяц платежа
			nextMonth = (currentMonth == 11) ? 0 : currentMonth + 1;

			if (nextMonth > currentMonth)
				return newActualTime(currentYear, nextMonth, payDay);

			if (nextMonth < currentMonth)
				return newActualTime(currentYear + 1, nextMonth, payDay);
		}
		else if (ExecutionEventType.ONCE_IN_QUARTER == eventType || ExecutionEventType.ONCE_IN_HALFYEAR == eventType)
		{
			int[] period = new int[0];

			if (ExecutionEventType.ONCE_IN_QUARTER == eventType)
				period = QUARTERLY_PERIOD[(startMonth % 3)];
			if (ExecutionEventType.ONCE_IN_HALFYEAR == eventType)
				period = HALFYEAR_PERIOD[(startMonth % 6)];

			for (int i = 0; i < period.length; i++)
			{
				int month = period[i];

				if (currentMonth == month)
				{
					//если дата платежа в этом месяце
					if (payDay >= currentDay)
						return newActualTime(currentYear, currentMonth, payDay);
				}
				//если в этом периоде есть еще месяцы для платежа
				if (currentMonth < month)
				{
					nextMonth = month;
					break;
				}
				//если последняя итерация и след. месяц не найден
				if (i == period.length)
					nextMonth = period[0];
			}

			if (nextMonth > currentMonth)
				return newActualTime(currentYear, nextMonth, payDay);

			if (nextMonth < currentMonth)
				return newActualTime(currentYear + 1, nextMonth, payDay);
		}
		else if (ExecutionEventType.ONCE_IN_YEAR == eventType)
		{
			if (startMonth > currentMonth)
				return newActualTime(currentYear, startMonth, payDay);

			if (startMonth == currentMonth && payDay >= currentDay)
				return newActualTime(currentYear, startMonth, payDay);

			return newActualTime(currentYear + 1, startMonth, payDay);
		}
		throw new IllegalStateException("некорректный тип события");
	}

	/**
	 * @return true - значит платеж выполняется периодически, не по событию
	 */
	public boolean isPeriodic()
	{
		return ExecutionEventType.isPeriodic(getExecutionEventType());
	}

	/**
	 * Установить флаг изменения ближайшей даты платежа
	 * @param flag (true/false)
	 */
	private void setStartDateChanged(boolean flag)
	{
		setNullSaveAttributeBooleanValue(LONG_OFFER_START_DATE_CHANGED_ATTRIBUTE_NAME, flag);
	}

	protected int getPaymentDay() throws DocumentLogicException
    {
		int payDay = getPayDay().intValue();
		if (getPayDay() > 31)
			throw new DocumentLogicException("Вы неправильно указали дату платежа. Пожалуйста, введите число от 1 до 28.");

		return payDay;
	}

	private Calendar newTime(int year, int month, int day)
	{
		Calendar time = DateHelper.getCurrentDate();
		time.set(Calendar.YEAR,  year);
		time.set(Calendar.MONTH, month);
		time.set(Calendar.DATE,  day);
		return time;
	}

	private Calendar newActualTime(int year, int month, int day)
	{
		int maxDaysInMonth = getMaxDaysInMonth(year, month);
		return newTime(year, month, day > maxDaysInMonth ? maxDaysInMonth : day);
	}

	private int getMaxDaysInMonth(int year, int month)
	{
		return 32 - newTime(year, month, 32).get(Calendar.DATE);
	}

	public String getFriendlyName()
	{
		return getNullSaveAttributeStringValue(JurPayment.AUTOSUB_FRIENDLY_NAME);
	}

	public Money getFloorLimit()
	{
		return null;
	}

	/**
	 * @return Значение максимальной суммы платежей в определенный период веденное клиентом при создании автоплатежа
	 */
	public Money getClientTotalAmountLimit()
	{
		String currencyText = getNullSaveAttributeStringValue(AUTOPAY_CLIENT_TOTAL_AMOUNT_CURR_ATTRIBUTE_NAME);
		String amountText = getNullSaveAttributeStringValue(AUTOPAY_CLIENT_TOTAL_AMOUNT_LIMIT_ATTRIBUTE_NAME);
		return createMoney(amountText, currencyText);
	}

	/**
	 * Установить значение максимальной суммы платежей в определенный период введенное клиентом
	 * @param totalAmountLimit значение
	 */
	public void setClientTotalAmountLimit(Money totalAmountLimit)
	{
		if (totalAmountLimit != null)
		{
			setNullSaveAttributeStringValue(AUTOPAY_CLIENT_TOTAL_AMOUNT_CURR_ATTRIBUTE_NAME, totalAmountLimit.getCurrency().getCode());
			setNullSaveAttributeDecimalValue(AUTOPAY_CLIENT_TOTAL_AMOUNT_LIMIT_ATTRIBUTE_NAME, totalAmountLimit.getDecimal());
		}
		else
		{
			setNullSaveAttributeStringValue(AUTOPAY_CLIENT_TOTAL_AMOUNT_CURR_ATTRIBUTE_NAME, null);
			setNullSaveAttributeDecimalValue(AUTOPAY_CLIENT_TOTAL_AMOUNT_LIMIT_ATTRIBUTE_NAME, null);
		}
	}

	/**
	 * Установить значение максимальной суммы платежей в определенный период в рамках автоплатежа для гейта
	 * @param totalAmountLimit значение
	 */
	public void setTotalAmountLimit(Money totalAmountLimit)
	{
		if (totalAmountLimit != null)
		{
			setNullSaveAttributeStringValue(AUTOPAY_TOTAL_AMOUNT_CURR_ATTRIBUTE_NAME, totalAmountLimit.getCurrency().getCode());
			setNullSaveAttributeDecimalValue(AUTOPAY_TOTAL_AMOUNT_LIMIT_ATTRIBUTE_NAME, totalAmountLimit.getDecimal());
		}
		else
		{
			setNullSaveAttributeDecimalValue(AUTOPAY_TOTAL_AMOUNT_LIMIT_ATTRIBUTE_NAME, null);
			setNullSaveAttributeStringValue(AUTOPAY_TOTAL_AMOUNT_CURR_ATTRIBUTE_NAME, null);
		}
	}

	/**
	 * @return Значение максимальной суммы платежей в определенный период в рамках автоплатежа для гейта
	 */
	public Money getTotalAmountLimit()
	{
		String currencyText = getNullSaveAttributeStringValue(AUTOPAY_TOTAL_AMOUNT_CURR_ATTRIBUTE_NAME);
		String amountText = getNullSaveAttributeStringValue(AUTOPAY_TOTAL_AMOUNT_LIMIT_ATTRIBUTE_NAME);
		return createMoney(amountText, currencyText);
	}

	public TotalAmountPeriod getTotalAmountPeriod()
	{
		return getNullSaveAttributeEnumValue(TotalAmountPeriod.class, AUTOPAY_TOTAL_AMOUNT_PERIOD_ATTRIBUTE_NAME);
	}

	/**
	 * Установить период, за который считается общая сумма списания, для ограничения по лимиту
	 * @param value значение периода
	 */
	public void setTotalAmountPeriod(TotalAmountPeriod value)
	{
		setNullSaveAttributeEnumValue(AUTOPAY_TOTAL_AMOUNT_PERIOD_ATTRIBUTE_NAME, value);
	}

	/**
	 * Для веб-сервиса
	 * @param value значение ввиде стринга
	 */
	public void setTotalAmountPeriod(String value)
	{
		setTotalAmountPeriod(StringHelper.isEmpty(value) ? null : TotalAmountPeriod.valueOf(value));
	}

	/**
	 * @return Поддерживает ли автоплатеж в установку максимальной суммы платежей в определенный период(true = поддерживает)
	 */
	public Boolean isSupportTotalAmount()
	{
		return (Boolean) getNullSaveAttributeValue(IS_AUTOPAY_TOTAL_AMOUNT_ATTRIBUTE_NAME);
	}

	public String getCardNumber()
	{
		return getChargeOffCard();
	}

	public String getAccountNumber()
	{
		return getChargeOffAccount();
	}

	public boolean isConnectChargeOffResourceToMobileBank()
	{
		return BooleanUtils.toBoolean(isConnectChargeOffToMobilBank());
	}

	/**
	 * Установить флаг подключения к МБ
	 * @param value значение
	 */
	public void setConnectChargeOffResourceToMobileBank(boolean value)
	{
		setNullSaveAttributeBooleanValue(JurPayment.CHARGEOFF_CONNECT_TO_MOBILBANK, value);
	}

	public void setConnectChargeOffToMobilBank(Boolean isConnect)
	{
		setNullSaveAttributeBooleanValue(JurPayment.CHARGEOFF_CONNECT_TO_MOBILBANK, isConnect);
	}

	/**
	 * Подключен ли карта списания к мобильному банку
	 * @return true - подключена, false - не подключена, null - ресурс списания не является картой
	 */
	public Boolean isConnectChargeOffToMobilBank()
	{
		return (Boolean) getNullSaveAttributeValue(JurPayment.CHARGEOFF_CONNECT_TO_MOBILBANK);
	}

	public Boolean isExecutionNow()
	{
		if(!isLongOffer())
		{
			return null;
		}

		return Boolean.valueOf(getNullSaveAttributeStringValue(JurPayment.AUTOSUB_EXECUTE_NOW));
	}

	/**
	 * @return Дата и время создания подписки
	 */
	public Calendar getCreateDate()
	{
		return getAdmissionDate();
	}

	/**
	 * @return Необходимость подтверждения операции
	 */
	public boolean isNeedConfirmation()
	{
		// Если документ создан в мобильном приложении или с помощью УС, клиент так или иначе подтвердит его
		// в доверенном канале
		if (getCreationType() == CreationType.mobile || getCreationType() == CreationType.atm)
		{
			return false;
		}

		//если услуга мобильный банк подлкючена и
		//документ не подтверждался ни по смс, ни по чеку, ни по вводу пин-кода пластиковой карты.
		return isConnectChargeOffResourceToMobileBank() && !isConfirmed();
	}

	protected boolean isConfirmed()
	{
		ConfirmStrategyType strategyType = getConfirmStrategyType();
		return strategyType == ConfirmStrategyType.sms || strategyType == ConfirmStrategyType.card || strategyType == ConfirmStrategyType.push || strategyType == ConfirmStrategyType.plasticCardClient;
	}

	/**
	 * @return Канал создания подписки(IB - интернет банк, VSP - ВСП, US - устройство самообслуживания)
	 */
	public ChannelType getChannelType()
	{
		return getNullSaveAttributeEnumValue(ChannelType.class, JurPayment.AUTOSUB_CHANNEL_TYPE);
	}

	/**
	 * Установить канал создания подписки
	 * @param channelType канал (IB - интернет банк, VSP - ВСП, US - устройство самообслуживания)
	 */
	public void setChannelType(ChannelType channelType)
	{
		setNullSaveAttributeStringValue(JurPayment.AUTOSUB_CHANNEL_TYPE, channelType == null ? null : channelType.name());
	}

	public Money getMaxSumWritePerMonth()
	{
		return null;
	}

	/**
	 * Причина нахождения автоплатежа в данном состоянии
	 * @return причина нахождения автоплатежа в данном состоянии
	 */
	public String getReasonDescription()
	{
		return null;
	}

	/**
	 * @return дата следующего платежа
	 */
	public Calendar getNextPayDate()
	{
		return getNullSaveAttributeCalendarValue(JurPayment.AUTOSUB_NEXT_PAY_DATE);
	}

	/**
	 * Установить дату следующего платежа
	 * @param nextPayDate дата
	 */
	public void setNextPayDate(Calendar nextPayDate)
	{
		setNullSaveAttributeCalendarValue(JurPayment.AUTOSUB_NEXT_PAY_DATE, nextPayDate);
	}

	/**
	 * Статус автоплатежа.
	 *
	 * @return статус.
	 */
	public AutoPayStatusType getAutoPayStatusType()
	{
		return null;
	}

	public void setAutoPayStatusType(AutoPayStatusType status)
	{}

	/**
	 * @return Тип автоплатежа
	 */
	public AutoSubType getAutoSubType()
	{
		String autoSubType = getNullSaveAttributeStringValue(AUTOSUB_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(autoSubType))
		{
			return null;
		}

		return AutoSubType.valueOf(autoSubType);
	}

	/**
	 * Получить расшифровку по периодичности исполнения
	 * @return периодичность исполнения
	 */
	public String getStartExecutionDetail()
	{
		if (isPeriodic())
		{
			return PeriodicExecutionEventTypeWrapper.getExecutionDetail(getExecutionEventType(), getStartDate());
		}
		return getExecutionEventType().getDescription();
	}

	/**
	 * Получить расшифровку по периодичности исполнения
	 * @return периодичность исполнения
	 */
	public String getNextExecutionDetail()
	{
		if (isPeriodic())
		{
			return PeriodicExecutionEventTypeWrapper.getExecutionDetail(getExecutionEventType(), getNextPayDate());
		}
		return getExecutionEventType().getDescription();
	}

	/**
	 * @return показывалось ли сообщение о неактивности МБК
	 */
	public boolean isAlreadyShowInactiveMBKWarning()
	{
		return getNullSaveAttributeBooleanValue(IS_ALREADY_SHOW_INACTIVE_MBK_WARNING_ATTRIBUTE_NAME);
	}

	/**
	 * Установить флаг показа сообщения о неактивности МБК
	 * @param value значени
	 */
	public void setAlreadyShowInactiveMBKWarning(boolean value)
	{
		setNullSaveAttributeBooleanValue(IS_ALREADY_SHOW_INACTIVE_MBK_WARNING_ATTRIBUTE_NAME, value);
	}

	/**
	 * @return Внешний идентификатор подписки, по которой создается заявка
	 */
	public String getLongOfferExternalId()
	{
		return getNullSaveAttributeStringValue(OFFER_EXTERNAL_ID_ATTRIBUTE);
	}

	/**
	 * Установить внешний идентификатор подписки, по которой создается заявка
	 * @param externalId внешний идентификатор подписки
	 */
	public void setLongOfferExternalId(String externalId)
	{
		setNullSaveAttributeStringValue(OFFER_EXTERNAL_ID_ATTRIBUTE, externalId);
	}
}
