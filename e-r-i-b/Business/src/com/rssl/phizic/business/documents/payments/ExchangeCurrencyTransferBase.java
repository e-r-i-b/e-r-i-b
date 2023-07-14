package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.DebugLogFactory;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author krenev
 * @ created 27.08.2010
 * @ $Author$
 * @ $Revision$
 * Базовый класс, предоставляющий утилитные функции для поддержки конверсионных операций
 */
public abstract class ExchangeCurrencyTransferBase extends AbstractAccountsTransfer
{
	private static final ProfileService profileService = new ProfileService();
	private static final String CLEAR_CURRENCY_RATE_ERROR_MWSSAGE = "При очистке кеша для курса CurrencyRate(%s0, %s0, %s0) произошла ошибка.";
	//Курс продажи банком клиенту для валюты списания
	private static final String DEBET_SALE_RATE_FROM_ATTRIBUTE_NAME = "debet-sale-rate-from";
	private static final String DEBET_SALE_RATE_TO_ATTRIBUTE_NAME = "debet-sale-rate-to";
	//Курс покупки банком у клиента для валюты списания
	private static final String DEBET_BUY_RATE_FROM_ATTRIBUTE_NAME = "debet-buy-rate-from";
	private static final String DEBET_BUY_RATE_TO_ATTRIBUTE_NAME = "debet-buy-rate-to";
	//Курс продажи банком клиенту для валюты зачисления
	private static final String CREDIT_SALE_RATE_FROM_ATTRIBUTE_NAME = "credit-sale-rate-from";
	private static final String CREDIT_SALE_RATE_TO_ATTRIBUTE_NAME = "credit-sale-rate-to";
	//Курс покупки банком у клиента для валюты зачисления
	private static final String CREDIT_BUY_RATE_FROM_ATTRIBUTE_NAME = "credit-buy-rate-from";
	private static final String CREDIT_BUY_RATE_TO_ATTRIBUTE_NAME = "credit-buy-rate-to";
	//Кросскурс операции.
	private static final String CONVERTION_RATE_ATTRIBUTE_NAME = "convertion-rate";
	//Обычный курс операции
	protected static final String STANDART_CONVERTION_RATE_ATTRIBUTE_NAME = "standart-convertion-rate";
	//Выгода от льготного курса
	private static final String GAIN_ATTRIBUTE_NAME = "gain";

	private static final String CONVERTION_RATE_CHANGED_ATTRIBUTE_NAME = "recalculated-amount-changed";

	// Значение суммы/массы, введенное клиентом. Сохраняется в отдельном поле для операций с ОМС.
	private static final String VALUE_OF_EXACT_AMMOUNT_ATTRIBUTE_NAME = "value-of-exact-amount";

	/*Идентификатор операции*/
	public static final String OPER_UID = "oper-uid";
	/*Дата передачи сообщения*/
	public static final String OPER_TIME = "oper-time";

	private static final String TARIF_PLAN_CODE_TYPE = "tarif-plan-code-type";
	private static final String IMA_PREMIER_SHOW_MSG = "premier-show-msg";

	private static final Log debugLog = DebugLogFactory.getLog(Constants.LOG_MODULE_WEB);

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		//обновляем информацию о сумме списания/зачисления
		storeAmountInfo(document);
		//сохраняем инфу для конверсионной операции.
		calcConvertionRates();
	}

	/**
	 * Обновить информацию о сумме списания/зачисления. Требуется только для открытия вклада
	 * @param document
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	protected void storeAmountInfo(Document document) throws DocumentException, DocumentLogicException
	{
		//обновляем в наследниках, если требуется
	}

	/**
	 * пересчитать и сохранить курсы конверсии в платеже
	 * Внимание!!! дергать метод осмысленно(только там где нужно).
	 * происходит пересчет курсов на текущий
	 * если при пересчете курсы(любые) изменились, взводится флаг isRatesChanged(например, для показа пользователю)
	 * @return были ли изменены курсы.
	 */
	public boolean calcConvertionRates() throws DocumentLogicException, DocumentException
	{
		InputSumType inputSumType = getInputSumType();
		if (isLongOffer() || !needRates() || inputSumType == null)
		{
			//даже не пытаемся получать курсы для операций
			return false;
		}

		try
		{
			Currency chargeOffCurrency = getChargeOffCurrency();
			Currency destinationCurrency = getDestinationCurrency();
			Currency nationalCurrency = getNationalCurrency();
			CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);
			Office office = getOffice();

			BusinessSettingsConfig businessSettingsConfig = ConfigFactory.getConfig(BusinessSettingsConfig.class);
			String tarifPlanCodeType = getTarifPlanCodeType();
			if (!businessSettingsConfig.getClearRateCacheUseJMS())
			{
				//перед пересчетом сумм чистим кэш, что бы минимизировать вероятность того, что сумма будет рассчитана
				//по курсу, отличному от курса полученному в currencyRateService.getRate() из-за кэширования
				clearCurrencyRateCache(new CurrencyRate(chargeOffCurrency, BigDecimal.ONE, destinationCurrency, BigDecimal.ONE, CurrencyRateType.BUY_REMOTE, tarifPlanCodeType), office);

				// очищаем кэш курса покупки/продажи банком у клиента для валюты списания, чтобы в запросе уходили актуальные курсы
				clearCurrencyRateCache(new CurrencyRate(chargeOffCurrency, BigDecimal.ONE, nationalCurrency, BigDecimal.ONE, CurrencyRateType.SALE_REMOTE, tarifPlanCodeType), office);
				clearCurrencyRateCache(new CurrencyRate(chargeOffCurrency, BigDecimal.ONE, nationalCurrency, BigDecimal.ONE, CurrencyRateType.BUY_REMOTE, tarifPlanCodeType), office);

				// очищаем кэш курса покупки/продажи банком у клиента для валюты зачисления, чтобы в запросе уходили актуальные курсы
				clearCurrencyRateCache(new CurrencyRate(destinationCurrency, BigDecimal.ONE, nationalCurrency, BigDecimal.ONE, CurrencyRateType.BUY_REMOTE, tarifPlanCodeType), office);
				clearCurrencyRateCache(new CurrencyRate(destinationCurrency, BigDecimal.ONE, nationalCurrency, BigDecimal.ONE, CurrencyRateType.SALE_REMOTE, tarifPlanCodeType), office);
			}
			//Курс валют клиента для его тарифного плана
			CurrencyRate currencyRate = currencyRateService.getRate(chargeOffCurrency, destinationCurrency, CurrencyRateType.BUY_REMOTE, office,
					tarifPlanCodeType);
			//Обычный курс валют
			CurrencyRate standartCurrencyRate = null;
			if (!TariffPlanHelper.isUnknownTariffPlan(tarifPlanCodeType))
				standartCurrencyRate = currencyRateService.getRate(chargeOffCurrency, destinationCurrency, CurrencyRateType.BUY_REMOTE, office,
						TariffPlanHelper.getUnknownTariffPlanCode());

			if (needSumUpdate())
			{
				if (InputSumType.CHARGEOFF == inputSumType)
				{
					CurrencyRate convertionRate = currencyRateService.convert(getChargeOffAmount(), destinationCurrency, office, tarifPlanCodeType);
					if (convertionRate != null)
					{
						if (getDestinationResourceType() == ResourceType.IM_ACCOUNT)
						{
							setDestinationAmount(roundAmount(convertionRate.getToValue(), destinationCurrency));
							//сохраняем сумму введённую пользователем
							setValueOfExactAmount(getChargeOffAmount());
							// Пересчет введенной суммы в рублях для уменьшения погрешности конверсии
							setChargeOffAmount(new Money(CurrencyUtils.reverseConvert(getDestinationAmount().getDecimal(), currencyRate), chargeOffCurrency));
						}
						else if(getChargeOffResourceType() == ResourceType.ACCOUNT && (getDestinationResourceType() == ResourceType.ACCOUNT || getDestinationResourceType() == ResourceType.CARD))
						{
							//Сумма зачисления округляется отбрасыванием цифр начиная со 2-го разряда после запятой (по задаче "Исправление конверсионной ошибки")
							setDestinationAmount(MoneyUtil.roundDestinationAmount(convertionRate.getToValue(), destinationCurrency));
						}
						else
							setDestinationAmount(new Money(convertionRate.getToValue(), destinationCurrency));
					}
					else
					{
						//если курсов нет, то передаем только одну сумму, вторую не расчитываем
						setDestinationAmount(null);
					}
				}
				if (InputSumType.DESTINATION == inputSumType)
				{
					CurrencyRate convertionRate = currencyRateService.convert(chargeOffCurrency, getDestinationAmount(), office, tarifPlanCodeType);
					if (convertionRate != null)
					{
						if (getChargeOffResourceType() == ResourceType.IM_ACCOUNT)
						{
							setChargeOffAmount(roundAmount(convertionRate.getFromValue(), chargeOffCurrency));
							// Пересчет введенной суммы в рублях для уменьшения погрешности конверсии
							setDestinationAmount(new Money(CurrencyUtils.convert(getChargeOffAmount().getDecimal(), currencyRate), destinationCurrency));
						}
						else if(getChargeOffResourceType() == ResourceType.ACCOUNT && (getDestinationResourceType() == ResourceType.ACCOUNT || getDestinationResourceType() == ResourceType.CARD))
						{
							// адаптируем полученный курс к входной сумме
							CurrencyRate rate = CurrencyUtils.getToCurrencyRateForAccountPayment(getDestinationAmount().getDecimal(), currencyRate);
							setChargeOffAmount(MoneyUtil.roundChargeOffAmount(rate.getFromValue(), chargeOffCurrency));
						}
						else
							setChargeOffAmount(new Money(convertionRate.getFromValue(), chargeOffCurrency));
					}
					else
					{
						//если курсов нет, то передаем только одну сумму, вторую не расчитываем
						setChargeOffAmount(null);
					}
				}
			}

			//всегда банк покупает валюту списания за валюту зачисления и курс соответсвенно получаем этот.
			setConvertionRate(currencyRate);
			//Обычный курс валют
			setStandartConvertionRate(standartCurrencyRate);
			//Курс продажи банком клиенту для валюты списания.
			setDebetSaleRate(currencyRateService.getRate(chargeOffCurrency, nationalCurrency,  CurrencyRateType.SALE_REMOTE, office, tarifPlanCodeType));
			//Курс покупки банком у клиента для валюты списания.
			setDebetBuyRate(currencyRateService.getRate(chargeOffCurrency,  nationalCurrency, CurrencyRateType.BUY_REMOTE, office, tarifPlanCodeType));
			//Курс продажи банком клиенту для валюты зачисления.
			setCreditSaleRate(currencyRateService.getRate(destinationCurrency, nationalCurrency, CurrencyRateType.SALE_REMOTE, office, tarifPlanCodeType));
			//Курса покупки банком у клиента для валюты зачисления.
			setCreditBuyRate(currencyRateService.getRate(destinationCurrency, nationalCurrency, CurrencyRateType.BUY_REMOTE, office, tarifPlanCodeType));
			//Тарифный план
			setTarifPlanCodeType(tarifPlanCodeType);

			boolean showPremierMsg = checkPremierCourse(chargeOffCurrency, destinationCurrency, office, tarifPlanCodeType);
			setPremierShowMsg(String.valueOf(showPremierMsg));
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		return isRateChanged();
	}

	/**
	 * Проверить, отображать ли клиенту сообщение об использовании льготного курса
	 * @param currFrom - валюта списания
	 * @param currTo - валюта зачисления
	 * @param office - id офиса
	 * @param tarifPlanCodeType - тарифный план клиента
	 * @return true, если сообщение о льготном курсе должно быть отображено
	 * @throws GateLogicException
	 * @throws GateException
	 * @throws DocumentException
	 */
	private boolean checkPremierCourse(Currency currFrom, Currency currTo, Office office, String tarifPlanCodeType) throws GateLogicException, GateException, DocumentException
	{
		//Только если у клиента задан тарифный план анализируем информацию о курсах
		if (TariffPlanHelper.isUnknownTariffPlan(tarifPlanCodeType))
			return false;
		CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);
		CurrencyRateType rateType = (StringHelper.equals(currFrom.getCode(), "RUB")) ? CurrencyRateType.SALE_REMOTE : CurrencyRateType.BUY_REMOTE;
		CurrencyRate rate = currencyRateService.getRate(currFrom, currTo, rateType, office, tarifPlanCodeType);

		if (rate != null)
		{
			BigDecimal rateFactor = rate.getFactor();

			//Если валюта операции из списка, то сообщение отображается
			Set<String> currencyShowMsgSet = CurrencyUtils.getCurrencyShowPremierMsgSet();
			if (currencyShowMsgSet.contains(rate.getFromCurrency().getCode().toUpperCase()) ||
					currencyShowMsgSet.contains(rate.getToCurrency().getCode().toUpperCase()))
				return true;
			//Иначе сравнивается курс валют текущего тарифа клиента ("Сбербанк Премьер") с курсом обычного тарифа ("отсутствует")
			else
			{
				CurrencyRate rateUnknown = currencyRateService.getRate(currFrom, currTo, rateType, office, TariffPlanHelper.getUnknownTariffPlanCode());
				if (rateUnknown != null)
					return rateFactor.compareTo(rateUnknown.getFactor()) != 0 ? true : false;
			}
		}
		return false;
	}

	/**
	 * нужны ли курсы для данной операции
	 * @return да/нет
	 */
	protected abstract boolean needRates() throws DocumentException;

	/**
	 *
	 * @return является ли платеж конверсионным
	 * @throws DocumentException
	 */
	public boolean isConvertion() throws DocumentException
	{
		try
		{
			Currency chargeOffCurrency = getChargeOffCurrency();
			Currency destinationCurrency = getDestinationCurrency();
			if (chargeOffCurrency == null || destinationCurrency == null)
			{
				//одной из валют нет - не конверсия
				return false;
			}
			return !chargeOffCurrency.compare(destinationCurrency);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * @return Курс продажи банком клиенту для валюты списания
	 */
	public CurrencyRate getDebetSaleRate()
	{
		BigDecimal fromAmount = (BigDecimal) getNullSaveAttributeValue(DEBET_SALE_RATE_FROM_ATTRIBUTE_NAME);
		BigDecimal toAmount = (BigDecimal) getNullSaveAttributeValue(DEBET_SALE_RATE_TO_ATTRIBUTE_NAME);
		if (fromAmount == null && toAmount == null)
		{
			return null;//курсов нет.
		}
		try
		{
			Currency fromCurrency = getChargeOffCurrency();
			Currency toCurrency = getNationalCurrency();
			
			return new CurrencyRate(fromCurrency, fromAmount, toCurrency, toAmount, CurrencyRateType.SALE_REMOTE, getTarifPlanCodeType() );
		}
		catch (DocumentException e)
		{
			throw new RuntimeException(e);
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * устанавливаем курс продажи банком клиенту для валюты списания
	 * @param rate курс
	 */
	private void setDebetSaleRate(CurrencyRate rate) throws DocumentException
	{
		if (rate == null)
		{
			removeAttribute(DEBET_SALE_RATE_FROM_ATTRIBUTE_NAME);
			removeAttribute(DEBET_SALE_RATE_TO_ATTRIBUTE_NAME);
			return;
		}
		//страхуемся)) ибо конверсия - это жесть.
		if (rate.getType() != CurrencyRateType.SALE_REMOTE)
		{
			throw new IllegalArgumentException("должен быть курс продажи");
		}
		if (!compareChargeOffCurrency(rate.getFromCurrency()))
		{
			throw new IllegalArgumentException("не совпадает валюта списания курса ");
		}
		if (!rate.getToCurrency().compare(getNationalCurrency()))
		{
			throw new IllegalArgumentException("не совпадает валюта зачисления курса ");
		}

		setNullSaveAttributeDecimalValue(DEBET_SALE_RATE_FROM_ATTRIBUTE_NAME, rate.getFromValue());
		setNullSaveAttributeDecimalValue(DEBET_SALE_RATE_TO_ATTRIBUTE_NAME, rate.getToValue());
	}

	protected boolean compareChargeOffCurrency(Currency currency) throws DocumentException
	{
		try
		{
			return currency.compare(getChargeOffCurrency());
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	protected boolean compareDestinationCurrency(Currency currency) throws DocumentException
	{
		try
		{
			return currency.compare(getDestinationCurrency());
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * @return Курс покупки банком клиенту для валюты списания
	 */
	public CurrencyRate getDebetBuyRate()
	{
		BigDecimal fromAmount = (BigDecimal) getNullSaveAttributeValue(DEBET_BUY_RATE_FROM_ATTRIBUTE_NAME);
		BigDecimal toAmount = (BigDecimal) getNullSaveAttributeValue(DEBET_BUY_RATE_TO_ATTRIBUTE_NAME);
		if (fromAmount == null && toAmount == null)
		{
			return null;//курсов нет.
		}
		try
		{
			Currency fromCurrency = getNationalCurrency();
			Currency toCurrency = getChargeOffCurrency();
			return new CurrencyRate(fromCurrency, fromAmount, toCurrency, toAmount, CurrencyRateType.BUY_REMOTE, getTarifPlanCodeType());
		}
		catch (DocumentException e)
		{
			throw new RuntimeException(e);
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * устанавливаем курс покупки банком клиенту для валюты списания
	 * @param rate курс
	 */
	private void setDebetBuyRate(CurrencyRate rate) throws DocumentException
	{
		if (rate == null)
		{
			removeAttribute(DEBET_BUY_RATE_FROM_ATTRIBUTE_NAME);
			removeAttribute(DEBET_BUY_RATE_TO_ATTRIBUTE_NAME);
			return;
		}
		//страхуемся)) ибо конверсия - это жесть.
		if (rate.getType()!=CurrencyRateType.BUY_REMOTE)
		{
			throw new IllegalArgumentException("должен быть курс покупки");
		}
		if (!compareChargeOffCurrency(rate.getFromCurrency()))
		{
			throw new IllegalArgumentException("не совпадает валюта списания курса ");
		}
		if (!rate.getToCurrency().compare(getNationalCurrency()))
		{
			throw new IllegalArgumentException("не совпадает валюта зачисления курса ");
		}

		setNullSaveAttributeDecimalValue(DEBET_BUY_RATE_FROM_ATTRIBUTE_NAME, rate.getFromValue());
		setNullSaveAttributeDecimalValue(DEBET_BUY_RATE_TO_ATTRIBUTE_NAME, rate.getToValue());
	}

	/**
	 * @return Курс продажи банком клиенту для валюты зачисления
	 */
	public CurrencyRate getCreditSaleRate()
	{
		BigDecimal fromAmount = (BigDecimal) getNullSaveAttributeValue(CREDIT_SALE_RATE_FROM_ATTRIBUTE_NAME);
		BigDecimal toAmount = (BigDecimal) getNullSaveAttributeValue(CREDIT_SALE_RATE_TO_ATTRIBUTE_NAME);
		if (fromAmount == null && toAmount == null)
		{
			return null;//курсов нет.
		}
		try
		{
			Currency fromCurrency = getDestinationCurrency();
			Currency toCurrency = getNationalCurrency();
			return new CurrencyRate(fromCurrency, fromAmount, toCurrency, toAmount, CurrencyRateType.SALE_REMOTE, getTarifPlanCodeType());
		}
		catch (DocumentException e)
		{
			throw new RuntimeException(e);
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * устанавливаем курс  клиенту для валюты зачисления
	 * @param rate курс
	 */
	private void setCreditSaleRate(CurrencyRate rate) throws DocumentException
	{
		if (rate == null)
		{
			removeAttribute(CREDIT_SALE_RATE_FROM_ATTRIBUTE_NAME);
			removeAttribute(CREDIT_SALE_RATE_TO_ATTRIBUTE_NAME);
			return;
		}
		//страхуемся)) ибо конверсия - это жесть.
		if (rate.getType() != CurrencyRateType.SALE_REMOTE)
		{
			throw new IllegalArgumentException("должен быть курс продажи банком");
		}
		if (!compareDestinationCurrency(rate.getFromCurrency()))
		{
			throw new IllegalArgumentException("не совпадает валюта списания курса ");
		}
		if (!rate.getToCurrency().compare(getNationalCurrency()))
		{
			throw new IllegalArgumentException("не совпадает валюта зачисления курса ");
		}

		setNullSaveAttributeDecimalValue(CREDIT_SALE_RATE_FROM_ATTRIBUTE_NAME, rate.getFromValue());
		setNullSaveAttributeDecimalValue(CREDIT_SALE_RATE_TO_ATTRIBUTE_NAME, rate.getToValue());
	}

	/**
	 * @return Курс покупки банком у клиента для валюты зачисления
	 */
	public CurrencyRate getCreditBuyRate()
	{
		BigDecimal fromAmount = (BigDecimal) getNullSaveAttributeValue(CREDIT_BUY_RATE_FROM_ATTRIBUTE_NAME);
		BigDecimal toAmount = (BigDecimal) getNullSaveAttributeValue(CREDIT_BUY_RATE_TO_ATTRIBUTE_NAME);
		if (fromAmount == null && toAmount == null)
		{
			return null;//курсов нет.
		}
		try
		{
			Currency fromCurrency = getDestinationCurrency();
			Currency toCurrency = getNationalCurrency();
			return new CurrencyRate(fromCurrency, fromAmount, toCurrency, toAmount, CurrencyRateType.BUY_REMOTE, getTarifPlanCodeType());
		}
		catch (DocumentException e)
		{
			throw new RuntimeException(e);
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * устанавливаем курс покупки банком клиенту для валюты зачисления
	 * @param rate курс
	 */
	private void setCreditBuyRate(CurrencyRate rate) throws DocumentException
	{
		if (rate == null)
		{
			removeAttribute(CREDIT_BUY_RATE_FROM_ATTRIBUTE_NAME);
			removeAttribute(CREDIT_BUY_RATE_TO_ATTRIBUTE_NAME);
			return;
		}
		//страхуемся)) ибо конверсия - это жесть.
		if (rate.getType() != CurrencyRateType.BUY_REMOTE)
		{
			throw new IllegalArgumentException("должен быть курс продажи банком");
		}
		if (!compareDestinationCurrency(rate.getFromCurrency()))
		{
			throw new IllegalArgumentException("не совпадает валюта списания курса ");
		}
		if (!rate.getToCurrency().compare(getNationalCurrency()))
		{
			throw new IllegalArgumentException("не совпадает валюта зачисления курса ");
		}

		setNullSaveAttributeDecimalValue(CREDIT_BUY_RATE_FROM_ATTRIBUTE_NAME, rate.getFromValue());
		setNullSaveAttributeDecimalValue(CREDIT_BUY_RATE_TO_ATTRIBUTE_NAME, rate.getToValue());
	}

	/**
	 * @return Курс конверсии из валюты списания в валюту зачисления
	 */
	public BigDecimal getConvertionRate()
	{
		return (BigDecimal) getNullSaveAttributeValue(CONVERTION_RATE_ATTRIBUTE_NAME);
	}

	/**
	 * Установить кроскурс операции
	 * @param rate курс
	 * @throws DocumentException
	 */
	private void setConvertionRate(CurrencyRate rate) throws DocumentException
	{
		BigDecimal oldRate = getConvertionRate();
		if (rate == null)
		{
			removeAttribute(CONVERTION_RATE_ATTRIBUTE_NAME);
			if (oldRate != null)
			{
				setRateChanged(true);
			}
			return;
		}
		//страхуемся)) ибо конверсия - это жесть.
		if (!compareChargeOffCurrency(rate.getFromCurrency()))
		{
			throw new IllegalArgumentException("не совпадает валюта списания курса ");
		}
		if (!compareDestinationCurrency(rate.getToCurrency()))
		{
			throw new IllegalArgumentException("не совпадает валюта зачисления курса ");
		}
		Currency nationalCurrency = getNationalCurrency();
		BigDecimal newRate;
		if (rate.getFromCurrency().compare(nationalCurrency))
		{
			newRate = rate.getReverseFactor();
		}
		else
		{
			newRate = rate.getFactor();
		}

		setNullSaveAttributeDecimalValue(CONVERTION_RATE_ATTRIBUTE_NAME, newRate);
		setRateChanged(oldRate == null || newRate.compareTo(oldRate) != 0);
	}

	/**
	 * @return Стандартный курс конверсии из валюты списания в валюту зачисления
	 */
	public BigDecimal getStandartConvertionRate()
	{
		return (BigDecimal) getNullSaveAttributeValue(STANDART_CONVERTION_RATE_ATTRIBUTE_NAME);
	}

	/**
	 * Установить стандартный курс операции
	 * @param standartRate курс
	 * @throws DocumentException
	 */
	private void setStandartConvertionRate(CurrencyRate standartRate) throws DocumentException
	{
		if (standartRate == null)
			return;
		if (!compareChargeOffCurrency(standartRate.getFromCurrency()))
		{
			throw new IllegalArgumentException("не совпадает валюта списания курса ");
		}
		if (!compareDestinationCurrency(standartRate.getToCurrency()))
		{
			throw new IllegalArgumentException("не совпадает валюта зачисления курса ");
		}
		Currency nationalCurrency = getNationalCurrency();
		BigDecimal newStandartRate = standartRate.getFromCurrency().compare(nationalCurrency) ?
				standartRate.getReverseFactor() : standartRate.getFactor();

		setNullSaveAttributeDecimalValue(STANDART_CONVERTION_RATE_ATTRIBUTE_NAME, newStandartRate);
	}

	/**
	 * @return Выгода от льготного курса
	 */
	public BigDecimal getGain()
	{
		return (BigDecimal) getNullSaveAttributeValue(GAIN_ATTRIBUTE_NAME);
	}

	/**
	 * Установка значения выгоды от льготного курса
	 * @param gain
	 */
	private void setGain(BigDecimal gain)
	{
		setNullSaveAttributeDecimalValue(GAIN_ATTRIBUTE_NAME, gain);
	}

	/**
	 * Установить признак измененных курсов
	 * @param flag изменились/нет
	 */
	private void setRateChanged(boolean flag)
	{
		setNullSaveAttributeBooleanValue(CONVERTION_RATE_CHANGED_ATTRIBUTE_NAME, flag);
	}

	/**
	 * @return изменены ли курсы
	 */
	public boolean isRateChanged()
	{
		Boolean changed = (Boolean) getNullSaveAttributeValue(CONVERTION_RATE_CHANGED_ATTRIBUTE_NAME);
		return (changed == null) ? false : changed;
	}

	/**
	 * @return нужно ли пересчитывать сумму списания/зачисления в соответствии с курсом конверсии
	 */
	protected boolean needSumUpdate()
	{
		return true;
	}

	private void clearCurrencyRateCache(CurrencyRate currencyRate, Office office)
	{
		CacheService cacheService = GateSingleton.getFactory().service(CacheService.class);
		StringBuilder builderMsg = new StringBuilder();
		try
		{
			
			Application application = LogThreadContext.getApplication();
			builderMsg.append("|from="+ currencyRate.getFromCurrency().getNumber());
			builderMsg.append("; to="+ currencyRate.getToCurrency().getNumber());
			builderMsg.append("; region="+office.getCode().getFields().get("region"));
			builderMsg.append("; rateType="+currencyRate.getType().getId());
			builderMsg.append("; tarifPlanCodeType="+currencyRate.getTarifPlanCodeType());
			builderMsg.append("; application="+application != null ? application.name() : "");
			builderMsg.append("|");

			debugLog.trace("CurrencyRateDebug: ExchangeCurrencyTransferBase.clearCurrencyRateCache: Очистка кеша курсов валют. Параметры: "+builderMsg.toString());
			cacheService.clearCurrencyRateCache(currencyRate, office);
		}
		catch (InactiveExternalSystemException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			debugLog.trace("CurrencyRateDebug: ExchangeCurrencyTransferBase.clearCurrencyRateCache: Ошибка при очистке кеша для курса: "+builderMsg.toString());
			//не падаем при очистке кеша курсов валют
			String message = String.format(CLEAR_CURRENCY_RATE_ERROR_MWSSAGE, currencyRate.getFromCurrency().getCode(), currencyRate.getToCurrency().getCode(), currencyRate.getType().name());
			log.error(message, e);
		}
	}

	protected Money roundAmount(BigDecimal decimal, Currency currency)
	{
		return MoneyUtil.roundForCurrency(decimal, currency);
	}

	/**
	 * @return Значение суммы/массы, введенное клиентом. Для операций с ОМС getExactAmount() может вернуть не то,
	 * что вводил клиент.
	 */
	public BigDecimal getValueOfExactAmount()
	{
		BigDecimal exactAmountValue = (BigDecimal) getNullSaveAttributeValue(VALUE_OF_EXACT_AMMOUNT_ATTRIBUTE_NAME);
		return (exactAmountValue == null) ? getExactAmount().getDecimal() : exactAmountValue;
	}

	private void setValueOfExactAmount(Money exactAmount)
	{
		addAttribute(createAttribute(ExtendedAttribute.DECIMAL_TYPE, VALUE_OF_EXACT_AMMOUNT_ATTRIBUTE_NAME, exactAmount.getDecimal()));
	}

	public String getTarifPlanCodeType() throws DocumentException
	{
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			Profile profile = profileService.findByLogin(documentOwner.getLogin());
			return profile.getTariffPlanCode();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private void setTarifPlanCodeType(String tarifPlanCodeType)
	{
		setNullSaveAttributeStringValue(TARIF_PLAN_CODE_TYPE, tarifPlanCodeType);
	}

	public String getPremierShowMsg()
	{
		return getNullSaveAttributeStringValue(IMA_PREMIER_SHOW_MSG);
	}

	private void setPremierShowMsg(String premierShowMsg)
	{
		setNullSaveAttributeStringValue(IMA_PREMIER_SHOW_MSG, premierShowMsg);
	}
}
