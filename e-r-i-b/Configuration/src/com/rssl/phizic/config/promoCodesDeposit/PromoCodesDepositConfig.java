package com.rssl.phizic.config.promoCodesDeposit;

import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Конфиг для работы с настройками промо - кодов для открытия вкладов
 *
 * @ author: Gololobov
 * @ created: 18.12.14
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodesDepositConfig extends Config
{
	public static final String PROMOCODES_PROPERTIES_PREFIX = "com.rssl.iccs.promocodes";
	public static final String ACCESSIBLE_SYMBOLS = "com.rssl.iccs.promocodes.accessibleSymbols";
	public static final String MIN_COUNT_SYMBOLS = "com.rssl.iccs.promocodes.minCountSymbols";
	public static final String MAX_COUNT_SYMBOLS = "com.rssl.iccs.promocodes.maxCountSymbols";
	public static final String MAX_UNSUCCESSFULL_ITERATIONS = "com.rssl.iccs.promocodes.maxUnsuccessfullIteration";
	public static final String BLOCKING_TIMEMINUTES = "com.rssl.iccs.promocodes.blockingTimeMinutes";

	public static final String MAX_CLIENT_PROMO_CODE_STORAGE_PERIOD_YEARS = "com.rssl.iccs.client.promocodes.max.storage.time.years";
	public static final String MAX_PROMO_PERIOD_YEARS = "com.rssl.iccs.client.promocodes.period.years";

	private static final String PROMO_CODES_MESSAGE = "com.rssl.iccs.promocodes.message.%s";
	private static final String PROMO_CODES_MESSAGE_COUNT = String.format(PROMO_CODES_MESSAGE, "count");
	public static final String PROMO_CODES_MSG = "MSG0%s";
	public static final String PROMO_CODES_MESSAGE_TITLE = PROMO_CODES_MESSAGE + ".title";
	public static final String PROMO_CODES_MESSAGE_TEXT = PROMO_CODES_MESSAGE + ".text";
	public static final String PROMO_CODES_MESSAGE_EVENT = PROMO_CODES_MESSAGE + ".event";
	public static final String PROMO_CODES_MESSAGE_TYPE = PROMO_CODES_MESSAGE + ".type";

	private int maxClientPromoCodeStoragePeriod;           //Максимальное время хранения промокода
	private int maxPromoPeriod;              //Максимальный срок промо акции
	private String accessibleSymbols;           //Разрешенные символы
	private int minCountSymbols;                //Минимальное количество символов в промо-коде
	private int maxCountSymbols;                //Максимальное количество символов в промо-коде
	private Integer maxUnsuccessfulIteration;   //Лимит неудачных попыток
	private Integer blockingTimeMinutes;        //Время блокировки
	//Мапа с сообщениями промо - кодов. Key - номер сообщения, Value - текст сообщения.
	private Map<String, PromoCodesMessage> promoCodesMessagesMap        = new HashMap<String, PromoCodesMessage>();
	private Map<String, PromoCodesMessage> defaultPromoCodesMessagesMap = new HashMap<String, PromoCodesMessage>();

	public PromoCodesDepositConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		accessibleSymbols = getProperty(ACCESSIBLE_SYMBOLS);
		minCountSymbols = getIntProperty(MIN_COUNT_SYMBOLS);
		maxCountSymbols = getIntProperty(MAX_COUNT_SYMBOLS);
        maxClientPromoCodeStoragePeriod = getIntProperty(MAX_CLIENT_PROMO_CODE_STORAGE_PERIOD_YEARS);
        maxPromoPeriod = getIntProperty(MAX_PROMO_PERIOD_YEARS);
		maxUnsuccessfulIteration = getIntProperty(MAX_UNSUCCESSFULL_ITERATIONS);
		blockingTimeMinutes = getIntProperty(BLOCKING_TIMEMINUTES);

		readPromoCodesMessages(getReader(),        promoCodesMessagesMap);
		readPromoCodesMessages(getDefaultReader(), defaultPromoCodesMessagesMap);
	}

	/**
	 * Загрузка сообщений промо - кодов
	 * @return
	 * @param reader
	 * @param promoCodesMessagesMap
	 */
	private void readPromoCodesMessages(PropertyReader reader, Map<String, PromoCodesMessage> promoCodesMessagesMap)
	{
		if (reader == null || promoCodesMessagesMap == null)
		{
			return;
		}

		//Кол-во сообщений на событие
		Integer count = reader.getIntProperty(PROMO_CODES_MESSAGE_COUNT);
		if (count == null || count.equals(0))
			return;

		for (long i = 1; i < count + 1; i++)
		{
			String promoCodeNumber = String.format(PROMO_CODES_MSG, i);

			PromoCodesMessage promoCodesMessage = new PromoCodesMessage();
			promoCodesMessage.setNumber(promoCodeNumber);
			promoCodesMessage.setTitle(reader.getProperty(String.format(PROMO_CODES_MESSAGE_TITLE, promoCodeNumber)));
			promoCodesMessage.setText(reader.getProperty(String.format(PROMO_CODES_MESSAGE_TEXT, promoCodeNumber)));
			promoCodesMessage.setEvent(reader.getProperty(String.format(PROMO_CODES_MESSAGE_EVENT, promoCodeNumber)));

			String eventMessageType = reader.getProperty(String.format(PROMO_CODES_MESSAGE_TYPE, promoCodeNumber));
			promoCodesMessage.setType(PromoCodesMessageType.getPromoCodesMessageType(eventMessageType));

			promoCodesMessagesMap.put(promoCodeNumber, promoCodesMessage);
		}
	}

	public String getAccessibleSymbols()
	{
		return accessibleSymbols;
	}

	public void setAccessibleSymbols(String accessibleSymbols)
	{
		this.accessibleSymbols = accessibleSymbols;
	}

	public int getMinCountSymbols()
	{
		return minCountSymbols;
	}

	public void setMinCountSymbols(int minCountSymbols)
	{
		this.minCountSymbols = minCountSymbols;
	}

	public int getMaxCountSymbols()
	{
		return maxCountSymbols;
	}

	public void setMaxCountSymbols(int maxCountSymbols)
	{
		this.maxCountSymbols = maxCountSymbols;
	}

	public Integer getMaxUnsuccessfulIteration()
	{
		return maxUnsuccessfulIteration;
	}

	public void setMaxUnsuccessfulIteration(Integer maxUnsuccessfulIteration)
	{
		this.maxUnsuccessfulIteration = maxUnsuccessfulIteration;
	}

	public Integer getBlockingTimeMinutes()
	{
		return blockingTimeMinutes;
	}

	public void setBlockingTimeMinutes(Integer blockingTimeMinutes)
	{
		this.blockingTimeMinutes = blockingTimeMinutes;
	}

	public Map<String, PromoCodesMessage> getPromoCodesMessagesMap()
	{
		return promoCodesMessagesMap;
	}

	/**
	 * Возвращает мапу с необходимыми для сохранения данными по настройкам промо - кодов.
	 * @return
	 */
	public Map<String, String> getPromoCodeSettingsForSaveMap()
	{
		Map<String, String> promoCodesSettingsMap = new HashMap<String, String>();
		promoCodesSettingsMap.put(ACCESSIBLE_SYMBOLS, accessibleSymbols);
		promoCodesSettingsMap.put(MIN_COUNT_SYMBOLS, String.valueOf(minCountSymbols));
		promoCodesSettingsMap.put(MAX_COUNT_SYMBOLS, String.valueOf(maxCountSymbols));
		promoCodesSettingsMap.put(MAX_UNSUCCESSFULL_ITERATIONS, String.valueOf(maxUnsuccessfulIteration));
		promoCodesSettingsMap.put(BLOCKING_TIMEMINUTES, String.valueOf(blockingTimeMinutes));
		for (String key : promoCodesMessagesMap.keySet())
		{
			PromoCodesMessage promoCodesMessage = promoCodesMessagesMap.get(key);
			//Заголовок сообщения
			promoCodesSettingsMap.put(String.format(PROMO_CODES_MESSAGE_TITLE, key), promoCodesMessage.getTitle());
			//Текст сообщения
			promoCodesSettingsMap.put(String.format(PROMO_CODES_MESSAGE_TEXT, key), promoCodesMessage.getText());
		}
		return promoCodesSettingsMap;
	}

	/**
	 * Возвращает строку, содержащую допустимые символы для подставления в Regexp валидатор
	 * @return
	 */
	public String getAccessibleSymbolsForRegexpValidator()
	{
		if (StringHelper.isEmpty(accessibleSymbols))
			return null;

		StringBuffer accessibleSymbolsForRegexp = new StringBuffer();
		List<String> accessibleSymbolsList = new ArrayList<String>(Arrays.asList(StringUtils.split(accessibleSymbols, ",")));
		for (String accessibleSymbolsItem : accessibleSymbolsList)
			accessibleSymbolsForRegexp.append(accessibleSymbolsItem);

		return "["+accessibleSymbolsForRegexp.toString()+"]*";
	}

    public int getMaxClientPromoCodeStoragePeriod()
    {
        return maxClientPromoCodeStoragePeriod;
    }

    public void setMaxClientPromoCodeStoragePeriod(int maxClientPromoCodeStoragePeriod)
    {
        this.maxClientPromoCodeStoragePeriod = maxClientPromoCodeStoragePeriod;
    }

    public int getMaxPromoPeriod()
    {
        return maxPromoPeriod;
    }

    public void setMaxPromoPeriod(int maxPromoPeriod)
    {
        this.maxPromoPeriod = maxPromoPeriod;
	}

	public Map<String, PromoCodesMessage> getDefaultPromoCodesMessagesMap()
	{
		return Collections.unmodifiableMap(defaultPromoCodesMessagesMap);
	}

	protected PropertyReader getDefaultReader()
	{
		PropertyReader currentReader = getReader();

		if (currentReader instanceof CompositePropertyReader)
		{
			for (PropertyReader reader : ((CompositePropertyReader) currentReader).getReaders())
			{
				if (reader instanceof ResourcePropertyReader)
				{
					return reader;
				}
			}
		}

		if (currentReader instanceof ResourcePropertyReader)
		{
			return currentReader;
		}

		return null;
	}
}
