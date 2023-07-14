package com.rssl.phizic.business.dictionaries.finances;

import com.csvreader.CsvReader;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.rates.Rate;
import com.rssl.phizic.business.rates.RateBuilder;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.test.SafeTaskBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import sun.nio.cs.StandardCharsets;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Загрузка справочника курсов валют из внешнего ресурса (CSV-файл)
 * ----------------------------------------------------------------
 * Структура данных в CSV-файле
 * 1 колонка - Дата начала действия курса
 * 2 колонка - Сумма, из которой конвертируют
 * 3 колонка - Сумма, в которую конвертируют
 * 4 колонка - Валюта, в которую конвертируют
 * 5 колонка - Валюта из которой конвертируют
 * ----------------------------------------------------------------
 * @author Gololobov
 * @ created 29.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class CBRatesHistoryLoaderTask extends SafeTaskBase implements RecordConstructor
{
	//Файл справочника категорий
	private static final String DEFAUL_FILE_NAME = "cbrates_history.csv";
	//Кол-во записей в пакете для сохранения в БД
	private static final int RECORD_FOR_SAVE_COUNT = 1000;
	//Разделитель
	private static final char DELIMITER = ';';
	//Валюты обнаруженные в ресурсе
	private final Map<String, Currency> currenciesByCode = new HashMap<String, Currency>();
	//Департамент
	private Department rateOffice;

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final SimpleService simpleService = new SimpleService();

	public void safeExecute() throws Exception
	{
		//Сохранение списка курсов в БД
		saveCBRatesHistory();
	}

	/**
	 * Метод создает новый объект и заполняет его данными из CSV-файла
	 * @param csvRecordIterator - итератор по CSV-ридеру
	 * @return DictionaryRecordBase - объект курс "Rate"
	 * @throws IOException
	 */
	public DictionaryRecordBase construct(CsvRecordIterator csvRecordIterator) throws IOException
	{
		try
		{
			RateBuilder builder = new RateBuilder();

			//Дата создания
			Calendar creationDate = DateUtils.truncate(Calendar.getInstance(), Calendar.DATE);
            builder.setCreationDate(creationDate);

			//Дата начала действия
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			Calendar effDate = DateHelper.toCalendar(sdf.parse(csvRecordIterator.getColumByIndex(0)));
			builder.setEffDate(effDate);

			//Валюта из которой конвертируют
			Currency fromCurrency = findCurrencyByCode(csvRecordIterator.getColumByIndex(4),this.currenciesByCode);
			builder.setFromCurrency(fromCurrency);

			//Сумма, из которой конвертируют
			BigDecimal fromValue = new BigDecimal(csvRecordIterator.getColumByIndex(1).replace(",","."));
			builder.setFromValue(fromValue);

			// Валюта, в которую конвертируют
			Currency toCurrency = findCurrencyByCode(csvRecordIterator.getColumByIndex(3),this.currenciesByCode);
			builder.setToCurrency(toCurrency);

			// Сумма, в которую конвертируют
			BigDecimal toValue = new BigDecimal(csvRecordIterator.getColumByIndex(2).replace(",","."));
			builder.setToValue(toValue);

			//Тип курса
			builder.setCurrencyRateType(CurrencyRateType.CB);

			//Департамент
			builder.setDepartment(rateOffice);

			return builder.build();
		}
		catch (NumberFormatException e)
		{
			throw new RuntimeException("Неверный формат данных. В строке " + csvRecordIterator.getCurentRecordNum(), e);
		}
		catch (BusinessException e)
		{
			throw new RuntimeException("Ошибка при загрузке валюты.",e);
		}
		catch (ParseException e)
		{
			throw new RuntimeException("Неверный формат данных. В строке "+csvRecordIterator.getCurentRecordNum(),e);
		}
	}

    private InputStream getDefaultStream ()
	{
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAUL_FILE_NAME);
	}

	/**
	 * Сохранение курсов ЦБ в БД. Только добавление нового
	 * @throws BusinessException
	 */
	private void saveCBRatesHistory() throws BusinessException
	{
		//Департамент
		DepartmentService departmentService = new DepartmentService();
		IPSConfig ipsconfig = ConfigFactory.getConfig(IPSConfig.class);
		String officeRegion = ipsconfig.getCbCurrencyOfficeRegion();
		rateOffice = departmentService.getDepartmentTBByTBNumber(officeRegion);

		//Если не нашли департамент, то это ошибка
		if (rateOffice == null)
			throw new BusinessException("Не обнаружен департамент.");

		//ридер для работы с csv-файлами
		CsvReader reader = new CsvReader(getDefaultStream(),DELIMITER,new StandardCharsets().charsetForName("windows-1251"));
		if (reader == null)
			throw new BusinessException("Не обнаружен файл ресурса: "+DEFAUL_FILE_NAME);
		try
		{
			List<Rate> listRateForSave = new LinkedList<Rate>();
			log.info("Загрузка истории изменения курса ЦБ");
			//Итератор по данным в ресурсе
			CsvRecordIterator csvRecordIterator = new CsvRecordIterator(this, reader);

			List<Rate> ratesFromSource = new LinkedList<Rate>();
			Calendar minDate = null;
			Calendar maxDate = null;
			//Определение минимальной и максимальной даты курсов из ресурса
			while (csvRecordIterator.hasNext())
			{
				Rate rate = (Rate) csvRecordIterator.next();

				minDate = minDate == null || rate.getEffDate().compareTo(minDate) < 0 ? rate.getEffDate() : minDate;
				maxDate = maxDate == null || rate.getEffDate().compareTo(maxDate) > 0 ? rate.getEffDate() : maxDate;
				ratesFromSource.add(rate);
			}

			log.info("Обнаружено записей для загрузки в базу: "+ratesFromSource.size());
			
			//Дата начала периода (c 00:00:00)
			final Calendar dateBegin = DateUtils.truncate(minDate,Calendar.DATE);
			//Дата окончания периода (до 00:00:00 следующего дня)
			Date date = DateUtils.addDays(maxDate.getTime(),1);
			final Calendar dateEnd = DateHelper.toCalendar(DateUtils.truncate(date,Calendar.DATE));

			//Загрузка из БД курсов ЦБ за период [minDate...maxDate] для обнаруженных в ресурсе валют
			Map<String,List<Rate>> rateListByCyrrency = loadRatesHistoryByCurrency(dateBegin,dateEnd,currenciesByCode);

			int recordsInsert = 0;
			//Перебирается список курсов, сформированный из ресурса (csv-ки). Для сохранения выбираются только отсутствующие в БД
			if (! CollectionUtils.isEmpty(ratesFromSource))
				for (Rate rate: ratesFromSource)
				{
					if (! isExistingRate(rate,rateListByCyrrency))
						listRateForSave.add(rate);

					//Сохранение курсов в БД пакетами по 1000 записей
					if (listRateForSave.size() == RECORD_FOR_SAVE_COUNT)
					{
						recordsInsert = recordsInsert + listRateForSave.size();
						simpleService.addList(listRateForSave);
						listRateForSave.clear();
					}					
				}
			//Сохранение оставшихся курсов в БД
			if (! CollectionUtils.isEmpty(listRateForSave))
			{
				recordsInsert = recordsInsert + listRateForSave.size();
				simpleService.addList(listRateForSave);
			}
			log.info("Загружено в базу "+recordsInsert+ " записей.");
		}
		catch (Exception e)
		{
			log.error("Ошибка при сохранениие истории изменения курса ЦБ.", e);
			throw new BusinessException(e);
		}
		finally
		{
			reader.close();
		}
	}

	/**
	 * Сравнение курса с имеющимися в базе
	 * @param rate - курс валюты
	 * @param rateByCyrrencyMap - Map<String,List<Rate>>: key - ISO-код валюты, List<Rate> - список курсов ЦБ для этой валюты
	 * @return boolean - true если такой курс есть
	 * @throws BusinessException
	 */
	private boolean isExistingRate(final Rate rate, final Map<String,List<Rate>> rateByCyrrencyMap) throws BusinessException
	{
		List<Rate> list = rateByCyrrencyMap.get(rate.getFromCurrency().getNumber());
		if (! CollectionUtils.isEmpty(list))
			for(Rate dbRate : list)
			{
				Calendar dbEffDate = DateUtils.truncate(dbRate.getEffDate(),Calendar.DATE);
				Calendar effDate = DateUtils.truncate(rate.getEffDate(),Calendar.DATE);

				if ( (dbEffDate.compareTo(effDate) == 0)&&
					 dbRate.getToCurrency().compare(rate.getToCurrency()) )
					return true;
			}
		
		return false;
	}

	/**
	 * История изменения курса ЦБ для валюты "currency" за период [minDate...maxDate]
 	 * @param currency - валюта в которую трансформируют
	 * @param minDate - начало периода
	 * @param maxDate - конец периода 
	 * @return - List<Rate> - история изменения курса
	 * @throws BusinessException
	 */
	private List<Rate> getRateByParams(final Currency currency, final Calendar minDate, final Calendar maxDate) throws BusinessException, ParseException
	{
		List<Rate> list = null;
		try
		{
			 list = HibernateExecutor.getInstance(null).execute(new HibernateAction<List<Rate>>()
				{
					public List<Rate> run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.rates.BDCurrencyRateService.geCurrencyRatePeriodOfTime");
						query.setCalendar("minDate", minDate);
						query.setCalendar("maxDate", maxDate);
						query.setString("fromCurrency", currency.getNumber());
						query.setParameter("rateType", CurrencyRateType.CB);
						query.setLong("departmentId",rateOffice.getId());

						return query.list();
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		return list;
	}

	/**
	 * Возвращает вылюту по ее буквенному коду (RUB, USD, EUR и тд.)
	 * @param currCode - буквенный код валюты
	 * @return Currency - валюта
	 * @throws BusinessException
	 */
	private Currency findCurrencyByCode(final String currCode, Map<String, Currency> currenciesByCode) throws BusinessException
	{
		try
		{
			Currency currency = currenciesByCode.get(currCode);
			if (currency != null)
				return currenciesByCode.get(currCode);

			currency = HibernateExecutor.getInstance().execute(new HibernateAction<Currency>()
			{
				public Currency run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.currencies.getCurrencyByCode");
					query.setParameter("code", currCode.toUpperCase());
					return (Currency) query.uniqueResult();
				}
			});

			//Сохраним чтобы больше не лезть в БД
			currenciesByCode.put(currCode,currency);

			return currency;
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * Выгрузка истории изменения курса валюты из БД за определенный период
	 * @param minDate - начало периода
	 * @param maxDate - окончание периода
	 * @param map - валюты, обнаруженные в СSV-ресурсе  
	 * @throws BusinessException
	 * @return Map<String,List<Rate>> - key - ISO-код валюты, value - список курсов этой валюты за заданный период  
	 */
	private Map<String,List<Rate>> loadRatesHistoryByCurrency(Calendar minDate, Calendar maxDate, Map<String, Currency> map) throws BusinessException
	{
		//Курсы валют из БД для каждой обнаруженной валюты, где она как to_Currency на определённую дату
		Map<String, List<Rate>> rateListByCyrrency = new HashMap<String, List<Rate>>();
		try
		{
			if (! CollectionUtils.isEmpty(map.values()))
				for(Currency currency: map.values())
				{
					List<Rate> list = getRateByParams(currency, minDate, maxDate);

					if (!CollectionUtils.isEmpty(list))
						rateListByCyrrency.put(currency.getNumber(),list);
				}

		    return rateListByCyrrency; 
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}
}
