package com.rssl.phizic.operations.loanOffer;

import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanOffer.LoanOffer;
import com.rssl.phizic.business.loanOffer.SettingLoanOfferLoad;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateActionStateless;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.files.FileHelper;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.StatelessSession;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * User: Moshenko
 * Date: 10.06.2011
 * Time: 13:44:24
 * Операция загрузки в бд предодобренных предложений по кредитному продукту клиента
 */
public class LoanOfferLoadOperation extends OfferLoanOperationBase
{
    protected static final char DELIMITER = ';';        //разделитель
    protected static final int COLUMN_COUNT = 41;        //ожидаемое колл-во параметров

    private LoanOfferReader reader =  new LoanOfferReader();
	private static final BigDecimalParser decimalParser = new BigDecimalParser();

    public void initialize(InputStream inputStream) throws BusinessException
	{
		super.initialize(inputStream,reader,DELIMITER,COLUMN_COUNT);
	}

    /**
     * обновляем предложения по предодобренным кредитным продукам
     */

	/**
	 * обновляем предложения по предодобренным кредитным картам
	 */
	public void updateLoanOffer() throws BusinessException
	{
		long startTime = System.currentTimeMillis();
		//удаляем все предыдущие предложения

		offersService.removeAllLoanOffer();

		//пропускаем заголовок.
		reader.getRowValueSource();
		do
		{
			try
			{
				HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
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
			}
			catch (Exception e)
			{
				throw new BusinessException(e);
			}
		}
		while (reader.hasNext());

		LoanOfferLoadResults loadResult = new LoanOfferLoadResults();
		loadResult.setAllCount(Long.valueOf(getTotalCount()));
		loadResult.setLoadCount(Long.valueOf(getLoadCount()));

		loadResult.setLoadCommonErr(getStringError(commonErrors,"ce"));
		loadResult.setLoadOfferErr(getStringError(personsErrors, "le"));
		try
		{
			simpleService.addOrUpdate(loadResult);
		}
		catch (Exception e)
		{
			log.error("Ошибка при попытке сохранить результаты загрузки кредитных предложений. ", e);
		}
		System.out.println("Общее время загрузки:" + String.valueOf(System.currentTimeMillis() - startTime));
	}


    private void dataProcessing(MapValuesSource source, Session session) throws BusinessException
    {
        try
        {
            Map<String, Object> result = updateLoanOfferInfo(source);
            //если клиент не найден, или другие ошибки ,переходим к кледующей записи
            if (result.isEmpty())
                return;
                //загружаем предложение для клиента
                LoanOffer loanOffer = new LoanOffer();
                loanOffer.setFirstName((String)result.get(Constants.FIRST_NAME));
                loanOffer.setSurName((String)result.get(Constants.SUR_NAME));
                loanOffer.setPatrName((String)result.get(Constants.PATR_NAME));
                loanOffer.setBirthDay((Calendar)result.get(Constants.BIRTH_DATE));
                loanOffer.setProductName((String)result.get(Constants.PRODUCT_NAME));
                String productCode =(String)result.get(Constants.PRODUCT_CODE);
                if (!StringHelper.isEmpty(productCode))
	                loanOffer.setProductCode(productCode);
                String subProductCode =(String)result.get(Constants.SUB_PRODUCT_CODE);
                if (!StringHelper.isEmpty(subProductCode))
	                loanOffer.setSubProductCode(subProductCode);
                loanOffer.setProductName((String)result.get(Constants.PRODUCT_NAME));
                loanOffer.setPercentRate((decimalParser.parse((String)result.get(Constants.PRACENT_RATE))).doubleValue());
                loanOffer.setEndDate((Calendar)result.get(Constants.END_DATE));

                loanOffer.setPasportNumber(((String)result.get(Constants.PASPORT_NUMBER)));
                loanOffer.setPasportSeries(((String)result.get(Constants.PASPORT_SERIES)));
                String terbank = (String) result.get(Constants.TERBANK);
                if (!StringHelper.isEmpty(terbank))
                loanOffer.setTerbank(new Long((terbank)));
                loanOffer.setMonth6(((BigDecimal)result.get(Constants.MONTH6)).longValue());
                loanOffer.setMonth12(((BigDecimal)result.get(Constants.MONTH12)).longValue());
                loanOffer.setMonth18(((BigDecimal)result.get(Constants.MONTH18)).longValue());
                loanOffer.setMonth24(((BigDecimal)result.get(Constants.MONTH24)).longValue());
                loanOffer.setMonth36(((BigDecimal)result.get(Constants.MONTH36)).longValue());
                loanOffer.setMonth48(((BigDecimal)result.get(Constants.MONTH48)).longValue());
                loanOffer.setMonth60(((BigDecimal)result.get(Constants.MONTH60)).longValue());
                loanOffer.setDuration((Long) result.get(Constants.DURATION));

                BigDecimal maxLimitValue = (BigDecimal) result.get(Constants.MAX_LIMIT_VALUE);
                Currency currency = (Currency) result.get(Constants.CURRENCY);
                loanOffer.setMaxLimit(new Money(maxLimitValue,currency));
	            loanOffer.setCampaignMemberId(((String)result.get(Constants.CAMPAIGN_MEMBER_ID)));

                session.save(loanOffer);
	            loadCount++;
        }
        catch (Exception e)
        {
            commonErrors.add("Ошибка во время обработки записи №" + reader.getCurrentRecord() + ": " + e.getMessage());
        }
    }

	private String getStringError(List<String> errors,String tegName)
	{
		StringBuilder builder = new StringBuilder();
		for (String err:errors)
		{
			builder.append("<" + tegName + ">");
			builder.append(err);
			builder.append("</" + tegName + ">");
		}
		return builder.toString();
	}
    
    private Map<String,Object> updateLoanOfferInfo(MapValuesSource source) throws BusinessException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

        FormProcessor formProcessor = new FormProcessor(source, LoanOfferForm.FORM, new StringErrorCollector(), DefaultValidationStrategy.getInstance());
        Map<String,Object> result = new HashMap <String,Object>();

        BigDecimal maxLimitValue = BigDecimal.ZERO;
		BigDecimal month6 = BigDecimal.ZERO;
	    BigDecimal month12 = BigDecimal.ZERO;
	    BigDecimal month18 = BigDecimal.ZERO;
	    BigDecimal month24 = BigDecimal.ZERO;
	    BigDecimal month36 = BigDecimal.ZERO;
	    BigDecimal month48 = BigDecimal.ZERO;
	    BigDecimal month60 = BigDecimal.ZERO;
        Long duration = 0L;

		if (!reader.countTest())
        {
            commonErrors.add("ошибка в формате строки: количество параметров не соответствует ожидаемому (строка № "+reader.getCurrentRecord()+")");
            return result;
        }
        if  (!formProcessor.process())
        {
	       String errorString = formProcessor.getErrors().toString();
           commonErrors.add("ошибка в формате файла:\"" +errorString.substring(1,errorString.length()-1)+"\"(строка № "+reader.getCurrentRecord()+")");
           return result;
        }
            Map userInfo = formProcessor.getResult();
            //тут храним уже полученые, в ходе парсинга валюты
            Map<Object, Currency> currencyMap = new HashMap<Object, Currency>();
            String[] fio = ((String) userInfo.get(Constants.FIO)).split(" ");

            Calendar birthDate = DateHelper.toCalendar((Date) userInfo.get(Constants.BIRTH_DATE));

            String series = ((String) userInfo.get(Constants.SERIES));
            String pasportNumber = ((String) userInfo.get(Constants.PASPORT_NUMBER));
            String terbank = ((String) userInfo.get(Constants.TERBANK));

            //ищем максимальный лимит, и сохраняем его объем и сумму.
            BigDecimal maxLimit = BigDecimal.ZERO;
            if (userInfo.get(Constants.MONTH6) != null)
            {
                maxLimit = ((BigDecimal)userInfo.get(Constants.MONTH6));
                month6 = maxLimit;
                if   (maxLimitValue.longValue() < maxLimit.longValue())
                {
				   maxLimitValue = maxLimit;
				   duration = 6l;
                }

            }

            if (userInfo.get(Constants.MONTH12) != null )
            {
                maxLimit = ((BigDecimal) userInfo.get(Constants.MONTH12));
                month12 = maxLimit;
                if (maxLimitValue.longValue() < maxLimit.longValue())
                {
					maxLimitValue = maxLimit;
					duration = 12L;
                }
            }

            if (userInfo.get(Constants.MONTH18) != null )
            {
                maxLimit = ((BigDecimal) userInfo.get(Constants.MONTH18));
                month18 = (BigDecimal)userInfo.get(Constants.MONTH18);
                if (maxLimitValue.longValue() < maxLimit.longValue())
                {
					maxLimitValue = maxLimit;
					duration = 16L;
                }
            }

            if (userInfo.get(Constants.MONTH24) != null )
            {
                maxLimit = ((BigDecimal) userInfo.get(Constants.MONTH24));
                month24 = maxLimit;
                if (maxLimitValue.longValue() < maxLimit.longValue())
                {
					maxLimitValue = maxLimit;
					duration = 24L;
                }
            }

            if (userInfo.get(Constants.MONTH36) != null )
            {
                maxLimit = ((BigDecimal) userInfo.get(Constants.MONTH36));
                month36 = maxLimit;
	            if (maxLimitValue.longValue() < maxLimit.longValue())
	            {
					maxLimitValue = maxLimit;
					duration = 36L;
	            }
            }

            if (userInfo.get(Constants.MONTH48) != null )
            {
                maxLimit = ((BigDecimal) userInfo.get(Constants.MONTH48));
                month48 = maxLimit;
                if (maxLimitValue.longValue() < maxLimit.longValue())
                {
					maxLimitValue = maxLimit;
					duration = 48L;
                }
            }


            if (userInfo.get(Constants.MONTH60) != null)
            {
                maxLimit = ((BigDecimal) userInfo.get(Constants.MONTH60));
                month60 = maxLimit;
                if (maxLimitValue.longValue() < maxLimit.longValue())
                {
					maxLimitValue = maxLimit;
					duration = 60L;
                }
            }

            //если суммы не указанны(0)
            if (maxLimit.longValue()==0)
            {
                personsErrors.add(getEmptyErrorMessage(fio,birthDate,"сумма предложения"));
                return result;
            }

            //если валюту еще не нашли, то производим её поиск
            if (!currencyMap.containsKey(userInfo.get(Constants.CURRENCY)))
            {
                try
                {
	                Currency currency = currencyService.findByAlphabeticCode((String) userInfo.get(Constants.CURRENCY));
	                currencyMap.put(userInfo.get(Constants.CURRENCY), currency);
                }
                catch (GateException e)
                {
                   commonErrors.add("ошибка при получении валюты кредита ( строка №"+reader.getCurrentRecord()+"): " + e.getMessage());
                   return result;
                }
            }

            String percentRate = (String)userInfo.get(Constants.PRACENT_RATE);

            if (percentRate==null)
            {
	           personsErrors.add(getEmptyErrorMessage(fio,birthDate,"процентрная ставка"));
               return result;
            }

            if (userInfo.get(Constants.END_DATE) == null)
            {
	            personsErrors.add(getEmptyErrorMessage(fio,birthDate,"дата окончания действия предложения"));
	            return result;
            }
            Calendar endDate = DateHelper.toCalendar((Date) userInfo.get(Constants.END_DATE));

            String productName =  (String)userInfo.get(Constants.PRODUCT_NAME);
            if (StringHelper.isEmpty(productName))
            {
	          personsErrors.add(getEmptyErrorMessage(fio,birthDate,"имя продукта"));
              return result;
            }

            String productCode =  (String) userInfo.get(Constants.PRODUCT_CODE);
            String subProductCode = (String) userInfo.get(Constants.SUB_PRODUCT_CODE);
			String campaignMemberId =  (String)userInfo.get(Constants.CAMPAIGN_MEMBER_ID);

            //заполняем результирующий набор данных
            result.put(Constants.MONTH6,month6);
            result.put(Constants.MONTH12,month12);
            result.put(Constants.MONTH18,month18);
            result.put(Constants.MONTH24,month24);
            result.put(Constants.MONTH36,month36);
            result.put(Constants.MONTH48,month48);
            result.put(Constants.MONTH60,month60);
			result.put(Constants.SUR_NAME,fio[0]);
			result.put(Constants.FIRST_NAME,fio[1]);
            result.put(Constants.PATR_NAME,fio[2]);
            result.put(Constants.BIRTH_DATE,birthDate);
            result.put(Constants.CURRENCY,currencyMap.get(userInfo.get(Constants.CURRENCY)));
            result.put(Constants.TERBANK,terbank);
            result.put(Constants.PASPORT_SERIES,series);
            result.put(Constants.PASPORT_NUMBER,pasportNumber);
            result.put(Constants.PRACENT_RATE,percentRate);
            result.put(Constants.END_DATE,endDate);
            result.put(Constants.PRODUCT_NAME,productName);
            result.put(Constants.PRODUCT_CODE,productCode);
            result.put(Constants.SUB_PRODUCT_CODE,subProductCode);
            result.put(Constants.MAX_LIMIT_VALUE,maxLimitValue);
            result.put(Constants.DURATION,duration);
            result.put(Constants.END_DATE,endDate);
			result.put(Constants.CAMPAIGN_MEMBER_ID,campaignMemberId);

        return result;
	}

	public String getPathToFile() throws BusinessException
	{
		List<SettingLoanOfferLoad> settings = simpleService.getAll(SettingLoanOfferLoad.class);
		if (settings == null || settings.isEmpty())
			return "";
		else
			return FileHelper.normalizePath(settings.get(0).getDirectory() + File.separator + settings.get(0).getFileName());
	}

	private String getEmptyErrorMessage(String[] fio, Calendar birthDate ,String emptyFieldName)
	{
		return fio[0] + " " + fio[1] + " " + fio[2] + " " +  DateHelper.formatDateWithStringMonth(birthDate) + " для данного клиента не указанно поле " + emptyFieldName;
	}
}
