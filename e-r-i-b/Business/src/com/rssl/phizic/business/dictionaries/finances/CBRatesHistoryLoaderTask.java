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
 * �������� ����������� ������ ����� �� �������� ������� (CSV-����)
 * ----------------------------------------------------------------
 * ��������� ������ � CSV-�����
 * 1 ������� - ���� ������ �������� �����
 * 2 ������� - �����, �� ������� ������������
 * 3 ������� - �����, � ������� ������������
 * 4 ������� - ������, � ������� ������������
 * 5 ������� - ������ �� ������� ������������
 * ----------------------------------------------------------------
 * @author Gololobov
 * @ created 29.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class CBRatesHistoryLoaderTask extends SafeTaskBase implements RecordConstructor
{
	//���� ����������� ���������
	private static final String DEFAUL_FILE_NAME = "cbrates_history.csv";
	//���-�� ������� � ������ ��� ���������� � ��
	private static final int RECORD_FOR_SAVE_COUNT = 1000;
	//�����������
	private static final char DELIMITER = ';';
	//������ ������������ � �������
	private final Map<String, Currency> currenciesByCode = new HashMap<String, Currency>();
	//�����������
	private Department rateOffice;

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final SimpleService simpleService = new SimpleService();

	public void safeExecute() throws Exception
	{
		//���������� ������ ������ � ��
		saveCBRatesHistory();
	}

	/**
	 * ����� ������� ����� ������ � ��������� ��� ������� �� CSV-�����
	 * @param csvRecordIterator - �������� �� CSV-������
	 * @return DictionaryRecordBase - ������ ���� "Rate"
	 * @throws IOException
	 */
	public DictionaryRecordBase construct(CsvRecordIterator csvRecordIterator) throws IOException
	{
		try
		{
			RateBuilder builder = new RateBuilder();

			//���� ��������
			Calendar creationDate = DateUtils.truncate(Calendar.getInstance(), Calendar.DATE);
            builder.setCreationDate(creationDate);

			//���� ������ ��������
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			Calendar effDate = DateHelper.toCalendar(sdf.parse(csvRecordIterator.getColumByIndex(0)));
			builder.setEffDate(effDate);

			//������ �� ������� ������������
			Currency fromCurrency = findCurrencyByCode(csvRecordIterator.getColumByIndex(4),this.currenciesByCode);
			builder.setFromCurrency(fromCurrency);

			//�����, �� ������� ������������
			BigDecimal fromValue = new BigDecimal(csvRecordIterator.getColumByIndex(1).replace(",","."));
			builder.setFromValue(fromValue);

			// ������, � ������� ������������
			Currency toCurrency = findCurrencyByCode(csvRecordIterator.getColumByIndex(3),this.currenciesByCode);
			builder.setToCurrency(toCurrency);

			// �����, � ������� ������������
			BigDecimal toValue = new BigDecimal(csvRecordIterator.getColumByIndex(2).replace(",","."));
			builder.setToValue(toValue);

			//��� �����
			builder.setCurrencyRateType(CurrencyRateType.CB);

			//�����������
			builder.setDepartment(rateOffice);

			return builder.build();
		}
		catch (NumberFormatException e)
		{
			throw new RuntimeException("�������� ������ ������. � ������ " + csvRecordIterator.getCurentRecordNum(), e);
		}
		catch (BusinessException e)
		{
			throw new RuntimeException("������ ��� �������� ������.",e);
		}
		catch (ParseException e)
		{
			throw new RuntimeException("�������� ������ ������. � ������ "+csvRecordIterator.getCurentRecordNum(),e);
		}
	}

    private InputStream getDefaultStream ()
	{
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAUL_FILE_NAME);
	}

	/**
	 * ���������� ������ �� � ��. ������ ���������� ������
	 * @throws BusinessException
	 */
	private void saveCBRatesHistory() throws BusinessException
	{
		//�����������
		DepartmentService departmentService = new DepartmentService();
		IPSConfig ipsconfig = ConfigFactory.getConfig(IPSConfig.class);
		String officeRegion = ipsconfig.getCbCurrencyOfficeRegion();
		rateOffice = departmentService.getDepartmentTBByTBNumber(officeRegion);

		//���� �� ����� �����������, �� ��� ������
		if (rateOffice == null)
			throw new BusinessException("�� ��������� �����������.");

		//����� ��� ������ � csv-�������
		CsvReader reader = new CsvReader(getDefaultStream(),DELIMITER,new StandardCharsets().charsetForName("windows-1251"));
		if (reader == null)
			throw new BusinessException("�� ��������� ���� �������: "+DEFAUL_FILE_NAME);
		try
		{
			List<Rate> listRateForSave = new LinkedList<Rate>();
			log.info("�������� ������� ��������� ����� ��");
			//�������� �� ������ � �������
			CsvRecordIterator csvRecordIterator = new CsvRecordIterator(this, reader);

			List<Rate> ratesFromSource = new LinkedList<Rate>();
			Calendar minDate = null;
			Calendar maxDate = null;
			//����������� ����������� � ������������ ���� ������ �� �������
			while (csvRecordIterator.hasNext())
			{
				Rate rate = (Rate) csvRecordIterator.next();

				minDate = minDate == null || rate.getEffDate().compareTo(minDate) < 0 ? rate.getEffDate() : minDate;
				maxDate = maxDate == null || rate.getEffDate().compareTo(maxDate) > 0 ? rate.getEffDate() : maxDate;
				ratesFromSource.add(rate);
			}

			log.info("���������� ������� ��� �������� � ����: "+ratesFromSource.size());
			
			//���� ������ ������� (c 00:00:00)
			final Calendar dateBegin = DateUtils.truncate(minDate,Calendar.DATE);
			//���� ��������� ������� (�� 00:00:00 ���������� ���)
			Date date = DateUtils.addDays(maxDate.getTime(),1);
			final Calendar dateEnd = DateHelper.toCalendar(DateUtils.truncate(date,Calendar.DATE));

			//�������� �� �� ������ �� �� ������ [minDate...maxDate] ��� ������������ � ������� �����
			Map<String,List<Rate>> rateListByCyrrency = loadRatesHistoryByCurrency(dateBegin,dateEnd,currenciesByCode);

			int recordsInsert = 0;
			//������������ ������ ������, �������������� �� ������� (csv-��). ��� ���������� ���������� ������ ������������� � ��
			if (! CollectionUtils.isEmpty(ratesFromSource))
				for (Rate rate: ratesFromSource)
				{
					if (! isExistingRate(rate,rateListByCyrrency))
						listRateForSave.add(rate);

					//���������� ������ � �� �������� �� 1000 �������
					if (listRateForSave.size() == RECORD_FOR_SAVE_COUNT)
					{
						recordsInsert = recordsInsert + listRateForSave.size();
						simpleService.addList(listRateForSave);
						listRateForSave.clear();
					}					
				}
			//���������� ���������� ������ � ��
			if (! CollectionUtils.isEmpty(listRateForSave))
			{
				recordsInsert = recordsInsert + listRateForSave.size();
				simpleService.addList(listRateForSave);
			}
			log.info("��������� � ���� "+recordsInsert+ " �������.");
		}
		catch (Exception e)
		{
			log.error("������ ��� ����������� ������� ��������� ����� ��.", e);
			throw new BusinessException(e);
		}
		finally
		{
			reader.close();
		}
	}

	/**
	 * ��������� ����� � ���������� � ����
	 * @param rate - ���� ������
	 * @param rateByCyrrencyMap - Map<String,List<Rate>>: key - ISO-��� ������, List<Rate> - ������ ������ �� ��� ���� ������
	 * @return boolean - true ���� ����� ���� ����
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
	 * ������� ��������� ����� �� ��� ������ "currency" �� ������ [minDate...maxDate]
 	 * @param currency - ������ � ������� ��������������
	 * @param minDate - ������ �������
	 * @param maxDate - ����� ������� 
	 * @return - List<Rate> - ������� ��������� �����
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
	 * ���������� ������ �� �� ���������� ���� (RUB, USD, EUR � ��.)
	 * @param currCode - ��������� ��� ������
	 * @return Currency - ������
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

			//�������� ����� ������ �� ����� � ��
			currenciesByCode.put(currCode,currency);

			return currency;
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������� ��������� ����� ������ �� �� �� ������������ ������
	 * @param minDate - ������ �������
	 * @param maxDate - ��������� �������
	 * @param map - ������, ������������ � �SV-�������  
	 * @throws BusinessException
	 * @return Map<String,List<Rate>> - key - ISO-��� ������, value - ������ ������ ���� ������ �� �������� ������  
	 */
	private Map<String,List<Rate>> loadRatesHistoryByCurrency(Calendar minDate, Calendar maxDate, Map<String, Currency> map) throws BusinessException
	{
		//����� ����� �� �� ��� ������ ������������ ������, ��� ��� ��� to_Currency �� ����������� ����
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
