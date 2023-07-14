package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.CASNSICardProduct;
import com.rssl.phizic.business.ext.sbrf.dictionaries.ProductUploadMode;
import com.rssl.phizic.business.ext.sbrf.dictionaries.SynchronizeSettingsUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.MinMax;
import com.rssl.phizic.config.*;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.ArrayUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Mescheryakova
 * @ created 28.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class CASNSICardProductReplicaSource extends XmlReplicaSourceBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private List<CASNSICardProduct> source = new ArrayList<CASNSICardProduct>();
	private SynchronizeResultImpl results;

	protected void clearData ()
	{
		source.clear();
		results = new SynchronizeResultImpl();
	}

	protected InputStream getDefaultStream()
	{
		return getResourceReader(com.rssl.phizic.business.Constants.DEFAULT_FILE_NAME);
	}

	protected XMLFilter getDefaultFilter () throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new SaxFilter(super.chainXMLReader(xmlReader));
	}

	public void initialize(GateFactory factory) throws GateException {}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();

		return source.iterator();
	}

	public SynchronizeResult getSynchronizeResult()
	{
		return results;
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private Boolean cardProductSection = false;
		private Boolean currencyCardProductSection = false;

		private Map<Long, Map<Long, TempCASNSICardProduct>> cardProductInfo = new HashMap<Long, Map<Long, TempCASNSICardProduct>>();
		private Map<String, Currency> currencyCache = new HashMap<String, Currency>();

		private Boolean currentCardProductKind = false;
		private Boolean currentCardProductSubKind = false;
		private Long subKind;
		private Long kind;
		private Map<Long, String[]> allowedCardProductKinds = new HashMap<Long, String[]>();

		private static final String CURRENCY_TABLE_NAME = "qvkl_val";
		private static final String DICTIONARY_NAME = "qvb";		

		private static final String FILE_CONFIG_NAME = "iccs.properties";

		public SaxFilter ( XMLReader parent )
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			try
			{
				fillCardProductKindValues();       // получение диапазона видов карт
			}
			catch(BusinessException e)
			{
				throw new SAXException(e);
			}
			
			if(qName.equalsIgnoreCase("table") && DICTIONARY_NAME.equalsIgnoreCase(attributes.getValue("name")))
			{
				cardProductSection = true;   // зашли в таблицу карточных продуктов
			}
			if(qName.equalsIgnoreCase("table") && CURRENCY_TABLE_NAME.equalsIgnoreCase(attributes.getValue("name")))
			{
				currencyCardProductSection = true;  // зашли в таблицу соответствия карточных продуктов и валют
			}
			// Парсинг видов карточных продуктов
			else if (cardProductSection)
			{
				parseCardProducts(qName, attributes);
			}
			// Парсинг валют карточных продуктов
			else if (currencyCardProductSection)
			{
				parseCurrencyInfo(qName, attributes);
			}

			if (!cardProductSection)
			{
				super.startElement(uri, localName, qName, attributes);
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (qName.equalsIgnoreCase("table"))
			{
				cardProductSection = false;
				currencyCardProductSection = false;
			}
			else if (qName.equalsIgnoreCase("package"))
			{
				for (Long kindKey : new ArrayList<Long>(cardProductInfo.keySet()))
				{
					for (Long subKindKey : new ArrayList<Long>(cardProductInfo.get(kindKey).keySet()))
					{

						TempCASNSICardProduct subKindValue = cardProductInfo.get(kindKey).get(subKindKey);
						// если по продукту чего-то не хватает, то значит  нет записи для данного подвида в файле (только валюты)
						if (subKindValue != null && !StringHelper.isEmpty(subKindValue.getName()) && subKindValue.getDate() != null)
						{

							if (subKindValue.getCurrencies().isEmpty())
								saveResults(SynchronizeResultStatus.FAIL, "CUR_NO", kindKey + "-" + subKindKey, "Для указанного вида карты отсутствует валюта в справочнике ЦАС НСИ");
							else
							{
								for (Currency currency : subKindValue.getCurrencies())
								{
									CASNSICardProduct casnsiCardProduct = new CASNSICardProduct();
									casnsiCardProduct.setProductId(kindKey);
									casnsiCardProduct.setProductSubId(subKindKey);
									casnsiCardProduct.setName(subKindValue.getName());
									casnsiCardProduct.setStopOpenDeposit(subKindValue.getDate());
									casnsiCardProduct.setCurrency(currency);

									source.add(casnsiCardProduct);
									saveResults(SynchronizeResultStatus.SUCCESS, "Карта с видом=" + casnsiCardProduct.getProductId() + " с подвидом=" + casnsiCardProduct.getProductSubId() + " с кодом валюты=" + casnsiCardProduct.getCurrency().getCode() + " обработана", casnsiCardProduct.getSynchKey(), "Карта обработана");
								}
							}
						}
					}
				}
			}

			if (cardProductSection || currencyCardProductSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					clearParams();
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}

		/**
		 * Обнуление всех вспомогательных данных для чтения файла
		 */
		private void clearParams()
		{
			currentCardProductKind = false;
			currentCardProductSubKind = false;
			kind = null;
			subKind = null;
		}

		/**
		 * Получить из настроек минимальное и максимальное значения диапазона видов карточных продуктов
		 */
		private void fillCardProductKindValues() throws BusinessException
		{

			// настройки уже были считаны
			if (!allowedCardProductKinds.isEmpty())
				return;

			// получаем режим загрузки карт: полный/неполный
			String mode = readLoadMode();

			// если режим полный, то получаем диапазон допустимых значений из конфигурационного файла
			if (mode.equals(ProductUploadMode.ALL.name()))
			{
				allowedCardProductKinds.putAll(readConfigFileAllMode());
			}
			else
			{
				allowedCardProductKinds.putAll(readConfigFileOptionallyMode());
			}
		}

		/**
		 * Считать метод загрузки
		 */
		private String readLoadMode()
		{
			return ConfigFactory.getConfig(CardsConfig.class).getCardProductMode();
		}

		/**
		 * Читает из конф. файла информацию о диапазоне допустимых видов загружаемых карт для полного режима
		 * @return - список чисел - допустимых значений видов карт
		 * @throws BusinessException
		 */
		private Map<Long, String[]> readConfigFileAllMode() throws BusinessException
		{
			MinMax<Long> minMaxCardProductKinds = SynchronizeSettingsUtil.getMinMaxCardProductKindValues();
			Map<Long, String[]> results = new HashMap<Long, String[]>();

			// разрешенные виды карт для загрузки - это все числа от минимального до максимального
			for (Long i = minMaxCardProductKinds.getMin(); i <= minMaxCardProductKinds.getMax(); i++)
				results.put(i, null);

			return results;
		}

		/**
		 * Читает из конф. файла информацию о диапазоне допустимых видов загружаемых карт для частичного режима
		 * @return - список чисел - допустимых значений видов карт и подвидов
		 * @throws BusinessException
		 */
		private Map<Long, String[]> readConfigFileOptionallyMode() throws BusinessException
		{
			MinMax<Long> minMaxCardProductKinds = SynchronizeSettingsUtil.getMinMaxCardProductKindValues();
			// считать заданные в конф. файле виды и подвиды загружаемых карт
			Map<Long, String[]> loadInfo = getKindAndSubkindLoadingCardProduct();

			// пробегаемся во всем ключам и удаляем все элементы, не подходящие диапазону карточных продуктов
			List<Long> keys = new ArrayList<Long>(loadInfo.keySet());
			for (Long key : keys)
			{
				if (key < minMaxCardProductKinds.getMin() || key > minMaxCardProductKinds.getMax())
					loadInfo.remove(key);
			}

			return loadInfo;
		}

		/**
		 * Возвращает структуру, содержащую в себе список разрешенных к загрузке видов карт
		 * и соответствующих им подвидов
		 */
		private Map<Long, String[]> getKindAndSubkindLoadingCardProduct()
		{
			Map<Long, String[]> loadInfo = new HashMap<Long, String[]>();

			String unformatLoadCardProductInfo = ConfigFactory.getConfig(CardsConfig.class).getCardProductUsedKinds();

			// отделяем каждый вид с соответствующими ему подвивами
			String[] kindWithSubKinds = unformatLoadCardProductInfo.split(";");
			// вычленяем из каждой строки вид и соответствующие ему подвиды
			for (int i = 0; i < kindWithSubKinds.length; i++)
			{
				String[] parseKindAndSubKinds = kindWithSubKinds[i].split("\\.");
				if (parseKindAndSubKinds != null && parseKindAndSubKinds.length > 0)
				{
					String[] subKinds = null;
					if (parseKindAndSubKinds.length >= 2)
					{
						subKinds = 	parseKindAndSubKinds[1].split(",");
					}

					loadInfo.put(Long.parseLong(parseKindAndSubKinds[0]), subKinds);
				}
			}
			return loadInfo;
		}

		/**
		 * Сохранение результатов и логирование
		 * @param resultStatus  - статус сообщения: ошибка или нет
		 * @param message - текст ошибки
		 * @param kind - ключ карты (в некоторохы случаях только вид-подвид, когда валюту еще не удалось считать)
		 * @param logMessage - сообщение для логов
		 */
		private void saveResults(SynchronizeResultStatus resultStatus, String message, Comparable kind, String logMessage)
		{
			SynchronizeResultRecord record = new SynchronizeResultRecord();

			record.setStatus(resultStatus);
			List<String> errorDescriptions = new ArrayList<String>();
			errorDescriptions.add(message);
			record.setErrorDescriptions(errorDescriptions);
			record.setSynchKey(kind);

			if (SynchronizeResultStatus.FAIL.equals(resultStatus))
			{
				log.error(kind + ": " + logMessage);
			}
			else if (SynchronizeResultStatus.SUCCESS.equals(resultStatus))
			{
				log.info(kind + ": " + logMessage);
			}
			
			results.setDictionaryType(DictionaryType.CARD);
			results.addResultRecord(record);
		}

		/**
		 * Парсинг видов карточных продуктов
		 * @param qName - название тега
		 * @param attributes - аттрибуты тега
		 * @throws SAXException - ошибки парсинга
		 */
		private void parseCardProducts(String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("field"))
			{
				String name  = attributes.getValue("name");
				String value = attributes.getValue("value");

				// парсинг данных о виде карточного продукта
				if ("QDTN1".equalsIgnoreCase(name))
				{
					parseQDTN1(value);
					return;
				}

				if (!currentCardProductKind) // вид еще не был определен, парсить дальше нет смысла
					return;

				// парсим подвид карточного продукта
				if ("QDTSUB".equalsIgnoreCase(name))
				{
					parseQDTSUB(value);
					return;
				}

				if (!currentCardProductSubKind) // подвида нет, парсить дальше нет смысла
					return;

				// парсим название карточного продукта
				if ("QDN".equalsIgnoreCase(name))
				{
					cardProductInfo.get(kind).get(subKind).setName(value);
					return;
				}

				// парсим дату прекращения открытия вкладов
				if("QOPEND".equals(name))
				{
					parseQOPEND(value);
					return;
				}
			}
		}

		/**
		 * Парсинг вида карточного продукта
		 * @param value - значение вида
		 */
		private void parseQDTN1(String value)
		{
			Long qdtn1 = Long.parseLong(value);
			List<Long> allowedKinds = new ArrayList<Long>(allowedCardProductKinds.keySet());
			if (!allowedKinds.contains(qdtn1))
				return;
			
			kind =  qdtn1;
			currentCardProductKind = true;
		}

		/**
		 * Парсинг подвида карточного продукта
		 * @param value - значение подвида
		 */
		private void parseQDTSUB(String value)
		{
			String[] subkinds = allowedCardProductKinds.get(kind);
			if (subkinds == null || ArrayUtils.contains(subkinds, value))
			{
				subKind = Long.parseLong(value);
				Map<Long, TempCASNSICardProduct> tempInfo = new HashMap<Long, TempCASNSICardProduct>();
				tempInfo.put(subKind, new TempCASNSICardProduct());
				if (cardProductInfo.get(kind) == null)
					cardProductInfo.put(kind, tempInfo);
				else if (cardProductInfo.get(kind).get(subKind) == null)
					cardProductInfo.get(kind).put(subKind, new TempCASNSICardProduct());

				currentCardProductSubKind = true;
			}
		}

		/**
		 * Парсинг даты прекращения открытия вкладов
		 * @param value -  значение даты
		 * @throws SAXException
		 */
		private void parseQOPEND(String value) throws SAXException
		{
			if (value == null)
			{
			    saveResults(SynchronizeResultStatus.FAIL, "DT_CARD", kind + "-" + subKind, "Дата прекращения открытия вкладов");
			    return;
			}

			DateFormat formatter = new SimpleDateFormat("yyyy.mm.dd");
			try
			{
        	     Date date = formatter.parse(value);

        	     Calendar calendar=Calendar.getInstance();
        	     calendar.setTime(date);
				cardProductInfo.get(kind).get(subKind).setDate(calendar);
			}
			catch(ParseException e)
			{
				throw new SAXException("Ошибка парсинга даты " + value, e);
			}
		}

		/**
		 * Разбирает информацию о валютах для карточных продуктов
		 * @param qName - название тега
		 * @param attributes  - атрибуты тега
		 * @throws SAXException - ошибка парсинга
		 */
		private void parseCurrencyInfo(String qName, Attributes attributes) throws SAXException
		{
				if (qName.equalsIgnoreCase("field"))
				{
						String name  = attributes.getValue("name");
						String value = attributes.getValue("value");

						// парсинг вида продукта, к кот. принадлежит валюта
						if ("QVKL_T_QDTN1".equalsIgnoreCase(name))
						{
							parseQVKL_T_QDTN1(value);
							return;
						}

						if (!currentCardProductKind)
							return;

						// парсинг подвида продукта, к кот. принадлежит валюта
						if ("QVKL_T_QDTSUB".equalsIgnoreCase(name))
						{
							parseQVKL_T_QDTSUB(value);
							return;
						}

						if (!currentCardProductSubKind)
							return;

					    if ("QVKL_V".equalsIgnoreCase(name))
						{
							parseQVKL_V(value);
							return;
						}
				}
		}

		/**
		 * Парсиг вид продукта, к кот. привязана валюта
		 * @param value - значение вида продукта
		 */
		private void parseQVKL_T_QDTN1(String value)
		{
			Long qdtn1 = Long.parseLong(value);
			List<Long> allowedKinds = new ArrayList<Long>(allowedCardProductKinds.keySet());
			if (!allowedKinds.contains(qdtn1))
				return;

			kind =  qdtn1;
			currentCardProductKind = true;

		}

		/**
		 * Парсинг подвида продукта, к кот. привязана валюта
		 * @param value - значение подвида продукта
		 */
		private void parseQVKL_T_QDTSUB(String value)
		{
			String[] subkinds = allowedCardProductKinds.get(kind);
			if (subkinds == null || ArrayUtils.contains(subkinds, value))
			{
				subKind = Long.parseLong(value);
				Map<Long, TempCASNSICardProduct> tempInfo = new HashMap<Long, TempCASNSICardProduct>();
				tempInfo.put(subKind, new TempCASNSICardProduct());
				if (cardProductInfo.get(kind) == null)
					cardProductInfo.put(kind, tempInfo);
				else if (cardProductInfo.get(kind).get(subKind) == null)
					cardProductInfo.get(kind).putAll(tempInfo);

				currentCardProductSubKind = true;
			}
		}

		/**
		 * Парсинг кода валюты
		 * @param value - значение кода валюты
		 * @throws SAXException - ошибка парсинга
		 */
		private void parseQVKL_V(String value) throws SAXException
		{
			Currency currency = currencyCache.get(value);
			if (currency == null)
				currency = getCurrencyFromDB(value);
			
			if (currency != null)
				cardProductInfo.get(kind).get(subKind).addCurrencies(currency);
		}

		/**
		 * Попытка найти валюту по ее коду в нашей бд
		 * @param codeCurrency  - код валюты
		 * @return  найденная валюта или null
		 * @throws SAXException
		 */
		private Currency getCurrencyFromDB(String codeCurrency) throws SAXException
		{
			// Ищем в БД
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			try
			{
				Currency currency = currencyService.findByNumericCode(codeCurrency);
				if (currency == null)
				{
				  saveResults(SynchronizeResultStatus.FAIL, "CUR_CARD", kind + "-" + currentCardProductSubKind, "Для указанного в файле кода валюты нет соответствующей валюты в справочнике системы");
				}
				else
				{
					currencyCache.put(codeCurrency, currency);
				}

				return currency;
			}
			catch(GateException e)
			{
				throw new SAXException("Ошибка получения валюты", e);
			}
		}
	}
}
