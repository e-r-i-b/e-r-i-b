package com.rssl.phizicgate.iqwave.autopayment;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.config.SpecificGateConfig;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem;
import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItemState;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizicgate.iqwave.types.AutoPaymentImpl;
import com.rssl.phizicgate.iqwave.types.ScheduleItemImpl;
import com.rssl.phizicgate.iqwave.utils.AutoPaymentCompositeId;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.xml.transform.TransformerException;

/**
 * @author osminin
 * @ created 02.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentResponseSerializer
{
	private static final String ROUTES_FILE_NAME = "iqwReceiverRoutesCodes.csv";
	private static final String DELIMITER = ";";
	private static final String DELIMITER_CODE_SERVICE = "@";

	private static Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);
	private final Map<String, Pair<String, String>> routesMap = new HashMap<String, Pair<String, String>>(); // маршруты получателей платежей

	private static String lastReceiversRoutesFilePath;

	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * Заполнение списка автоплатежей
	 * @param response ответ на запрос списка платежей
	 * @return список платежей
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<AutoPayment> fillAutoPayments(Document response) throws GateException, GateLogicException
	{
		try
		{
			final List<AutoPayment> autoPayments = new ArrayList<AutoPayment>();

			XmlHelper.foreach(response.getDocumentElement(), Constants.AUTO_PAY_ITEM_TEG, new ForeachElementAction()
			{
				public void execute(Element element) throws GateException, GateLogicException
				{
					String paymentType = XmlHelper.getSimpleElementValue(element, Constants.AUTO_PAY_TYPE_TEG);
					if("0".equals(paymentType) || "3".equals(paymentType) || "1".equals(paymentType))
					{
						try
						{
							autoPayments.add(fillAutoPayment(element));
						}
						catch (Exception e)
						{
							log.warn("Невозможно получить информацию по автоплатежу " + XmlHelper.getSimpleElementValue(element, Constants.AUTO_PAY_FRIENDLY_NAME_TEG) + ".", e);
						}
					}
					else
					{
						log.info("Неизвестный тип автоплатежа AutoPayType = " + paymentType);
					}
				}
			});
			return autoPayments;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Заполнение графика исполнения
	 * @param response ответ на запрос графика исполнения
	 * @return график исполнения автоплатежа
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<ScheduleItem> fillSceduleItems(Document response) throws GateException, GateLogicException
	{
		try
		{
			final List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();
			XmlHelper.foreach(response.getDocumentElement(), Constants.AUTO_PAYMENTS_TAG, new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					ScheduleItemImpl scheduleItem = new ScheduleItemImpl();

					//Устанавливаем дату транзакции
					String paymentDate = XmlHelper.getSimpleElementValue(element, Constants.PAYMENT_DATE_TAG);
					scheduleItem.setDate(parseDateTime(paymentDate));

					//Устанавливаем сумму транзакции
					Element paymentAmount = XmlHelper.selectSingleNode(element, Constants.PAYMENT_AMOUNT_TAG);
					String amount = XmlHelper.getSimpleElementValue(paymentAmount, Constants.SUMMA_TEG);
					String curr = XmlHelper.getSimpleElementValue(paymentAmount, Constants.CURR_ISO_TEG);
					scheduleItem.setAmount(getMoney(amount, curr));

					//устанавливаем статус: 1-исполнен; 4,5 - отказан
					String paymentStatus = XmlHelper.getSimpleElementValue(element, Constants.PAYMENT_STATUS_TAG);
					if (paymentStatus.equals("1"))
						scheduleItem.setState(ScheduleItemState.SUCCESS);
					else if (paymentStatus.equals("4") || paymentStatus.equals("5"))
						scheduleItem.setState(ScheduleItemState.FAIL);
					else
						throw new GateException("Статус с кодом " + paymentStatus + " не поддерживается.");

					scheduleItems.add(scheduleItem);
				}
			});

			return scheduleItems;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Заполнение осоновных данных автоплатежа
	 */
	public AutoPayment fillAutoPayment(Element element) throws GateException, GateLogicException
	{
		try
		{
			AutoPaymentImpl payment = new AutoPaymentImpl();

			//устанавливаем номер карты
			String cardNumber = XmlHelper.getSimpleElementValue(element, Constants.AUTO_PAY_CARD_NO_TEG);
			payment.setCardNumber(cardNumber);

			// формируем и устанавливаем externalId - номер карты^номер лицевого счета/телефона^код сервиса(маршрута)
			String telNumber = XmlHelper.getSimpleElementValue(element, Constants.AUTO_PAY_TEL_NO_TEG);
			Element providerIdTeg = XmlHelper.selectSingleNode(element,Constants.AUTO_PAY_PROVIDER_ID_TEG);
			String providerId = XmlHelper.getSimpleElementValue(providerIdTeg, Constants.DIG_CODE_TEG);
			AutoPaymentCompositeId compositeId = new AutoPaymentCompositeId(cardNumber, telNumber, providerId);
			payment.setExternalId(compositeId.toString());

			//устанавливаем в качестве номера платежа номер лицевого счета
			payment.setRequisite(telNumber);

			Pair<String, String> pair = getRoutes().get(providerId);

			if(pair == null || StringHelper.isEmpty(pair.getFirst()))
				throw new GateException("Не найдена запись или код сервиса в справочнике кодов сервисов получателей (iqwReceiverRoutesCodes.csv)");
			//устанавливаем код сервиса(маршрута)
			payment.setCodeService(pair.getFirst() + DELIMITER_CODE_SERVICE + providerId);
			//устанавливаем имя получателя
			payment.setReceiverName(pair.getSecond());

			String paymentType = XmlHelper.getSimpleElementValue(element, Constants.AUTO_PAY_TYPE_TEG);

			//устанавливаем сумму обязательного платежа
			Element amount = XmlHelper.selectSingleNode(element, Constants.AUTO_PAY_AMOUNT_TEG);
			String amountValue = XmlHelper.getSimpleElementValue(amount, Constants.SUMMA_TEG);
			String amountCurr = XmlHelper.getSimpleElementValue(amount, Constants.CURR_ISO_TEG);
			payment.setAmount(getMoney(amountValue, amountCurr));

			//пороговый автоплатеж
			if (paymentType.equals("0"))
			{
				//устанавливаем пороговый лимит (обязателен для порогового платежа)
				payment.setFloorLimit(getMoney(XmlHelper.selectSingleNode(element, Constants.AUTO_PAY_FLOOR_LIMIT_TEG)));
				payment.setExecutionEventType(ExecutionEventType.REDUSE_OF_BALANCE);
				// добавляем период макимальной сумма по платежам, если есть
				String totalMaxAmountPeriod = XmlHelper.getSimpleElementValue(element, Constants.AUTO_PAY_TOTAL_PERIOD_TAG);
				if(totalMaxAmountPeriod != null)
					payment.setTotalAmountPeriod(getTotalAmountPeriod(totalMaxAmountPeriod));
				// добавляем лимит макимальной сумма по платежам, если есть
				Element totalMaxAmountElement = XmlHelper.selectSingleNode(element, Constants.AUTO_PAY_TOTAL_AMOUNT_TAG);
				if(totalMaxAmountElement != null)
				{
					payment.setTotalAmountLimit(getMoney(
							XmlHelper.getSimpleElementValue(totalMaxAmountElement, Constants.SUMMA_TEG),
							XmlHelper.getSimpleElementValue(totalMaxAmountElement, Constants.CURR_ISO_TEG)));
				}

			}
			// автоплатеж по счету
			else if(paymentType.equals("1"))
			{
				// устанавливаем максимальную сумму платежа для автоплатежа «по счёту» (с валютой).
				payment.setFloorLimit(getMoney(XmlHelper.selectSingleNode(element, Constants.AUTO_PAY_MAX_SUM_TEG)));
				payment.setExecutionEventType(ExecutionEventType.BY_INVOICE);
			}
			//регулярный автоплатеж
			else if (paymentType.equals("3"))
			{
				//устанавливаем дату первого исполнения автоплатежа
				payment.setStartDate(DateHelper.parseCalendar(XmlHelper.getSimpleElementValue(element, Constants.AUTO_PAY_CYCLE_DATE_TEG)));

				//устанавливаем период исполнения (обязательно для регулярного платежа)
				String cyclePeriod = XmlHelper.getSimpleElementValue(element, Constants.AUTO_PAY_CYCLE_PERIOD_TEG);
				ExecutionEventType eventType;
				if (cyclePeriod.equals("1")) {
				    eventType = ExecutionEventType.ONCE_IN_MONTH;
				} else if (cyclePeriod.equals("2")){
					eventType = ExecutionEventType.ONCE_IN_QUARTER;
				} else {
					eventType = ExecutionEventType.ONCE_IN_YEAR;
				}
				payment.setExecutionEventType(eventType);
			}

			//устанавливаем дату оформления заявки на автоплатеж
			String createDate = XmlHelper.getSimpleElementValue(element, Constants.AUTO_PAY_CREATE_DATE_TEG);
			payment.setDateAccepted(parseDateTime(createDate));

			//усианавливаем мнемоническое имя
			String friendlyName = XmlHelper.getSimpleElementValue(element, Constants.AUTO_PAY_FRIENDLY_NAME_TEG);
			if(StringHelper.isEmpty(friendlyName))
				friendlyName = telNumber + " " + amountValue + " " + amountCurr;
			payment.setFriendlyName(friendlyName);

			//устанавливаем состояние платежа
			payment.setReportStatus(getStatus(element));

			return payment;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
	}

	private TotalAmountPeriod getTotalAmountPeriod(String gateValue)
	{
		if("1".equals(gateValue))
			return TotalAmountPeriod.IN_MONTH;
		if("2".equals(gateValue))
			return TotalAmountPeriod.IN_QUARTER;
		if("3".equals(gateValue))
			return TotalAmountPeriod.IN_YEAR;
		if("4".equals(gateValue))
			return TotalAmountPeriod.IN_WEEK;
		if("5".equals(gateValue))
			return TotalAmountPeriod.IN_TENDAY;
		if("9".equals(gateValue))
			return TotalAmountPeriod.IN_DAY;

		throw new IllegalArgumentException("Неизвестный тип периода для расчёта лимитов");
	}

	/**
	 * Получить состояние автоплатежа
	 */
	public AutoPaymentStatus getStatus(Element element)
	{
		String status = XmlHelper.getSimpleElementValue(element, Constants.AUTO_PAY_REPORT_STATUS_TEG);
		return StringHelper.isEmpty(status) ? null : AutoPaymentStatus.fromValue(Long.parseLong(status));
	}

	/**
	 * Получить сумма по элементу SumCurr
	 * @param sumCurr элемент с кодом и значением суммы
	 * @return сумма
	 */
	private Money getMoney(Element sumCurr) throws GateLogicException, GateException
	{
		String amount = XmlHelper.getSimpleElementValue(sumCurr, Constants.SUMMA_TEG);
		String currency = XmlHelper.getSimpleElementValue(sumCurr, Constants.CURR_ISO_TEG);
		return getMoney(amount, currency);
	}

	/**
	 * Получить сумму
	 * @param amount размер суммы
	 * @param currCode код валюты
	 * @return сумма
	 */
	private Money getMoney(String amount, String currCode) throws GateException, GateLogicException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency currency = currencyService.findByAlphabeticCode(currCode);
		return new Money(new BigDecimal(amount), currency);
	}

	/**
	 * Привести строковую дату к календарю
	 */
	private Calendar parseDateTime(String date)
	{
		return XMLDatatypeHelper.parseDateTime(date);
	}

	/**
	 * Получить маршруты получателей платежей (соответствие кода сервису имени получателя)
	 * @return маршруты
	 * @throws GateException
	 */
	private Map<String, Pair<String, String>> getRoutes() throws GateException
	{
		lock.readLock().lock();
		try
		{
			if (!(routesMap.isEmpty() || !StringHelper.equals(lastReceiversRoutesFilePath, ConfigFactory.getConfig(SpecificGateConfig.class).getReceiversRoutesDictionaryPath())))
				return routesMap;
		}
		finally
		{
			lock.readLock().unlock();
		}

		return initRoutes();
	}

	/**
	 * Заполнить мап маршрутов данными из файла
	 * @throws GateException
	 */
	private Map<String, Pair<String, String>> initRoutes() throws GateException
	{
		lock.writeLock().lock();
		BufferedReader reader = null;
		try
		{
			lastReceiversRoutesFilePath = ConfigFactory.getConfig(SpecificGateConfig.class).getReceiversRoutesDictionaryPath();
			File file = new File(lastReceiversRoutesFilePath, ROUTES_FILE_NAME);
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null)
			{
				String[] codes = line.split(DELIMITER, 3);
				routesMap.put(codes[0], new Pair<String, String>(codes[1],  codes[2]));
			}
			return routesMap;
		}
		catch (IOException e)
		{
			throw new GateException(e);
		}
		finally
		{
			lock.writeLock().unlock();
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				throw new GateException(e);
			}
		}
	}

	/**
	 * @param responce ответ, из которого происходит формирование
	 * @return Получить список разрешенных типов автоплатежей
	 */
	public List<ExecutionEventType> fillAllowedAutoPayTypes(Document responce) throws GateException
	{
		List<ExecutionEventType> result = new ArrayList<ExecutionEventType>();

		String value = XmlHelper.getSimpleElementValue(responce.getDocumentElement(), Constants.AUTO_PAY_REPORT_PAYMENT_TYPE);
		// любой, кроме «по выставленному счёту» (т.е. разрешены: пороговый или регулярный)
		if("0".equals(value))
		{
			result.add(ExecutionEventType.ONCE_IN_MONTH);
			result.add(ExecutionEventType.ONCE_IN_QUARTER);
			result.add(ExecutionEventType.ONCE_IN_YEAR);
			result.add(ExecutionEventType.REDUSE_OF_BALANCE);
		}
		// только «по выставленному счёту»;
		else if("1".equals(value))
		{
			result.add(ExecutionEventType.BY_INVOICE);
		}
		// любой (т.е. разрешены: пороговый, по счёту, регулярный)
		else if("2".equals(value))
		{
			result.add(ExecutionEventType.ONCE_IN_MONTH);
			result.add(ExecutionEventType.ONCE_IN_QUARTER);
			result.add(ExecutionEventType.ONCE_IN_YEAR);
			result.add(ExecutionEventType.REDUSE_OF_BALANCE);
			result.add(ExecutionEventType.BY_INVOICE);
		}

		return result;
	}
}
