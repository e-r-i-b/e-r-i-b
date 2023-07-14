package com.rssl.phizic.operations.loanOffer;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanCardOffer.LoanCardOffer;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.loanOffer.SettingLoanCardOfferLoad;
import com.rssl.phizic.business.pereodicalTask.PereodicalTaskResult;
import com.rssl.phizic.business.pereodicalTask.unload.DirInteractionPereodicalTask;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.files.FileHelper;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * User: Moshenko
 * Date: 16.06.2011
 * Time: 9:22:40
 */
public class LoanCardOfferLoadOperation extends OfferLoanOperationBase
{
	private static final CounterService counterService = new CounterService();

	private static final char DELIMITER = '\t';        //разделитель
	private static final int COLUMN_COUNT = 21;        //ожидаемое колл-во параметров

    private static final String REPORT_NAME = "PR_CRED_CARD_DOWNLOAD_PROTOCOL_";        //ожидаемое колл-во параметров
	private List<Pair<String,String>> errorsToReport = new ArrayList<Pair<String,String>>();  //Ошибки которые пойдут в отчет.
	private LoanCardOfferReader reader =  new LoanCardOfferReader();
	private PereodicalTaskResult result;              //результат выполнения загрузки
	private CardOfferLoadLoggerBean reportBean;
	private boolean isAvto = false;
	private InputStream stream;
	private Currency currency;

    public void initialize(InputStream inputStream) throws BusinessException
	{
		currency = getCurrency();
		super.initialize(inputStream,reader,DELIMITER,COLUMN_COUNT);
	}

	public void initialize(DirInteractionPereodicalTask backroundTask) throws BusinessException
	{
		isAvto = true;
		Calendar startTime = Calendar.getInstance();
		result = new PereodicalTaskResult();
		result.setStartDate(startTime);
		reportBean = new CardOfferLoadLoggerBean();
		reportBean.setStartTime(startTime);
		currency = getCurrency();

		try
		{
			reportBean.setFileName(REPORT_NAME + DateHelper.formatToDateToString(Calendar.getInstance()) + counterService.getNext(Counters.CARD_OFFER_LOAD_OPERATION).toString());
		}
		catch (CounterException e)
		{
			//Если попали сюда то файла с отчетом не будет.
			throw new BusinessException(e);
		}
	}

	public PereodicalTaskResult execute() throws BusinessException
	{
		try
		{
			String path = getPathToAutoFile();
			File file = new File(path);
			stream = new FileInputStream(file);
			reportBean.setSize(file.length());
			super.initialize(stream,reader,DELIMITER,COLUMN_COUNT);
			long startTimeMs = System.currentTimeMillis();
			reportBean.setStatus(CardOfferLoadState.OK);
			updateLoanCardOffer();
			reportBean.setLoadTime(System.currentTimeMillis() - startTimeMs);
		}
		catch(FileNotFoundException ex)
		{
			reportBean.setStatus(CardOfferLoadState.FILE_NOT_FOUND);
			commonErrors.add("Ошибка при попытки выполнения автоматической загрузки карточных предложений, файл не найден.");
			log.error(ex.getMessage());
		}
		finally
		{
			try
			{
				if (stream != null)
					stream.close();
			}
			catch (IOException ignored) {}
		}

		result.setEndDate(Calendar.getInstance());
		reportBean.setEndTime(Calendar.getInstance());
		reportBean.setTotalCount(totalCount);
		reportBean.setLoadCount(loadCount);
		reportBean.setErrorString(errorsToReport);
		CardOfferLoadLogger loggerReport = new CardOfferLoadLogger();
		try
		{
			loggerReport.write(reportBean);
		}
		catch (IOException e)
		{
			log.error("Ошибка при попытке записи отчета о загрузке предодбренных карточных предложений",e);
		}
		return this.result;
	}

	/**
	 * обновляем предложения по предодобренным кредитным картам
	 */
	public void updateLoanCardOffer() throws BusinessException
	{
		//удаляем все предыдущие предложения
		offersService.removeAllLoanCardOffer();
		do
		{
			try
			{
				HibernateExecutor hibernateExecutor = HibernateExecutor.getInstance();
				hibernateExecutor.execute(new HibernateAction<Void>()
				{
					public Void run(Session session) throws BusinessException
					{
						int batchCount = 0;
						for (MapValuesSource source = reader.getRowValueSource(); reader.hasNext(); source = reader.getRowValueSource())
						{
							dataProcessing(source, session);
							totalCount++;
							batchCount++;
							if (batchCount == BATCH_SIZE)
								break;
						}
						session.flush();
						session.clear();
						return null;
					}

					;
				});
			}
			catch (HibernateException ex)
			{
				//если словили he то был ролбек, значит уменьшаем счетчик загруженных заявок на размер пачки
				loadCount = loadCount - BATCH_SIZE;
				log.info(ex.getMessage());
				if (isAvto)
					reportBean.setStatus(CardOfferLoadState.BD_ERROR);
			}
			catch (Exception e)
			{
				if (isAvto)
					reportBean.setStatus(CardOfferLoadState.LOAD_ERROR);
				throw new BusinessException(e);
			}
		}
		while (reader.hasNext());
	}
	private void dataProcessing(MapValuesSource source, Session session)
	{

		try
		{
			Map<String, Object> result = updateLoanCardOfferInfo(source);
			//если клиент не найден, или другие ошибки ,переходим к кледующей записи
			if (result.isEmpty())
				return;

			BigDecimal creditLimit = (BigDecimal) result.get("creditLimit");

			LoanCardOffer loanCardOffer = new LoanCardOffer();
			loanCardOffer.setLoadDate(Calendar.getInstance());
			loanCardOffer.setFirstName((String)  result.get("name"));
			loanCardOffer.setSurName((String)  result.get("surname"));
			loanCardOffer.setPatrName((String)  result.get("patrName"));
			loanCardOffer.setBirthDay((Calendar)  result.get("birthDate"));
			loanCardOffer.setOfferType(LoanCardOfferType.valueOf(((BigInteger) result.get("offerType")).intValue()));
			loanCardOffer.setMaxLimit(new Money(creditLimit, currency));
			loanCardOffer.setSeriesAndNumber((String) result.get("seriesAndNumber"));

			if (result.get("idWay") != null)
				loanCardOffer.setIdWay(((BigInteger) result.get("idWay")).longValue());


			loanCardOffer.setOsb(((BigInteger) result.get(Constants.OSB)).longValue());

			if (result.get("vsp") != null)
				loanCardOffer.setVsp(((BigInteger) result.get("vsp")).longValue());

			if (result.get("cardNumber") != null)
				loanCardOffer.setCardNumber((String) result.get("cardNumber"));

            if (result.get(Constants.TERBANK) != null)
                loanCardOffer.setTerbank(((BigInteger) result.get(Constants.TERBANK)).longValue());
			else
				loanCardOffer.setTerbank(null);

			session.save(loanCardOffer);
			///////////////////////////////////////////////////////////////////////////////////
			loadCount++;
		}
		catch (Exception e)
		{
			addCommonErr("Ошибка во время обработки записи №" + reader.getCurrentRecord());
			log.info(e.getMessage());
		}
	}

	private Currency getCurrency() throws BusinessException
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			return currencyService.findByAlphabeticCode("RUB");
		}
		catch (GateException e)
		{
			throw new BusinessException("Ошибка при получении валюты", e);
		}
	}

    private Map<String,Object> updateLoanCardOfferInfo(MapValuesSource source) throws BusinessException
	{
        FormProcessor formProcessor = new FormProcessor(source, LoanCardOfferForm.getForm(), new StringErrorCollector(), DefaultValidationStrategy.getInstance());
        Map<String,Object> result = new HashMap<String,Object>(8);

        if (!reader.countTest())
        {
	        addCommonErr("ошибка в формате строки: количество параметров не соответствует ожидаемому (строка № " + reader.getCurrentRecord() + ")");
            return result;
        }

        if  (!formProcessor.process())
        {
	        addCommonErr("ошибка в формате файла: " + formProcessor.getErrors().toString() + "(строка № " + reader.getCurrentRecord() + ")");
           return result;
        }
		else
        {
            Map userInfo = formProcessor.getResult();

            String surname = ((String) userInfo.get("surname")).toUpperCase();
            String name = ((String) userInfo.get("name")).toUpperCase();
            String patrName = ((String) userInfo.get("patrName")).toUpperCase();

            Calendar birthDate = Calendar.getInstance();
            birthDate.setTime((Date) userInfo.get(Constants.BIRTH_DATE));

			String osb = userInfo.get(Constants.OSB)  == null ? null : userInfo.get(Constants.OSB).toString();
			if (osb == null)
			{
				addPersonErr("Не указано ОСБ клиента.");
				return result;
			}

            BigDecimal creditLimit = ((BigDecimal)userInfo.get("creditLimit"));

		    String cardNumber= null;
			if (userInfo.get("cardNumber") != null)
                     cardNumber = userInfo.get("cardNumber").toString();

			result.put("surname",surname);
			result.put("name",name);
			result.put("patrName",patrName);
			result.put(Constants.BIRTH_DATE,birthDate);
			result.put("creditLimit",creditLimit);
			result.put("cardNumber",cardNumber);
			result.put("seriesAndNumber", userInfo.get("seriesAndNumber"));
			result.put("idWay", userInfo.get("idWay"));
			result.put(Constants.TERBANK, userInfo.get(Constants.TERBANK));
			result.put(Constants.OSB, userInfo.get(Constants.OSB));
			result.put("vsp", userInfo.get("vsp"));

			BigInteger  offerType = (BigInteger) userInfo.get("offerType");
			if (offerType==null)
				addPersonErr(surname + " " + name + " " + patrName + " " + DateHelper.formatDateWithStringMonth(birthDate) + " " + "не установлен тип предложения");
			else
			result.put("offerType",offerType);

        }
        return result;
    }


	public String getPathToFile() throws BusinessException
	{
		SettingLoanCardOfferLoad settings = getSetting();
		if (StringHelper.isEmpty(settings.getFileName()) || StringHelper.isEmpty(settings.getDirectory()))
			return null;
		return FileHelper.normalizePath(settings.getDirectory() + File.separator + settings.getFileName());
	}

	private String getPathToAutoFile() throws BusinessException
	{
		SettingLoanCardOfferLoad settings = getSetting();
		return FileHelper.normalizePath(settings.getAutomaticLoadDirectory() + File.separator + settings.getAutomaticLoadFileName());
	}

	private SettingLoanCardOfferLoad getSetting() throws BusinessException
	{
		List<SettingLoanCardOfferLoad> settings = simpleService.getAll(SettingLoanCardOfferLoad.class);
		if (settings == null || settings.isEmpty())
			return new SettingLoanCardOfferLoad();
		return settings.get(0);
	}

	private void addCommonErr(String msg)
	{
		commonErrors.add(msg);
		addRepportErr(msg);
	}

	private void addPersonErr(String msg)
	{
	  	personsErrors.add(msg);
		addRepportErr(msg);
	}

	private void addRepportErr(String msg)
	{
		Pair<String,String> reportErr = new Pair<String, String>();
		reportErr.setFirst(reader.getRawRecord());
		reportErr.setSecond(msg);
		errorsToReport.add(reportErr);
		if (isAvto)
			reportBean.setStatus(CardOfferLoadState.PARTLY_ERROR);
	}
}
