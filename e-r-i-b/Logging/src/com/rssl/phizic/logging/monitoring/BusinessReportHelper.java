package com.rssl.phizic.logging.monitoring;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.reports.BusinessReportConfig;
import com.rssl.phizic.utils.DateHelper;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author koptyaev
 * @ created 29.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class BusinessReportHelper
{
	/**
	 * Возвращает мапу параметров из конфига BusinessReportConfig
	 * @return мапа параметров
	 */
	public static Map<String,Object> getParamsFromConfig()
	{
		BusinessReportConfig config = ConfigFactory.getConfig(BusinessReportConfig.class);
		HashMap<String, Object> params = new HashMap<String, Object>();
		Calendar to = DateHelper.getCurrentDate();
		Calendar from = (Calendar) to.clone();
		from.add(Calendar.DAY_OF_YEAR, - MonitoringReportService.DEFAULT_REPORT_BUILD_PERIOD);

		params.put( BusinessReportConfig.TO_PERIOD,                          to);
		params.put( BusinessReportConfig.FROM_PERIOD,                        from);
		params.put( BusinessReportConfig.REPORTS_BLOCK_KEY,                  config.getBlockNumber());
		params.put( BusinessReportConfig.REPORTS_CHANNEL_KEY,                config.getChannel());
		params.put( BusinessReportConfig.REPORTS_PAYMENT_ON_KEY,             config.isPaymentOn());
		params.put( BusinessReportConfig.REPORTS_TRANSFER_ON_KEY,            config.isTransferOn());
		params.put( BusinessReportConfig.REPORTS_OPEN_ACCOUNT_ON_KEY,        config.isAccountOpenOn());
		params.put( BusinessReportConfig.REPORTS_DEVIATION_ALL_KEY,          config.getDeviationAll());
		params.put( BusinessReportConfig.REPORTS_DEVIATION_OPEN_ACCOUNT_KEY, config.getDeviationOpenAccount());
		params.put( BusinessReportConfig.MAIL_RECEIVERS_KEY,                 config.getMailReceivers());
		params.put( BusinessReportConfig.MAIL_THEME_KEY,                     config.getMailTheme());
		params.put( BusinessReportConfig.MAIL_TEXT_KEY,                      config.getMailText());
		params.put( BusinessReportConfig.SEND_ON_KEY,                        config.isSendOn());

		return params;
	}

	/**
	 * Ребилд мапы для того, чтобы нужные поля были в нужном виде
	 * @param parameters мапа с формпроцессора
	 * @return пересобранная мапа
	 */
	public static Map<String,Object> getParamsFromForm(Map<String,Object> parameters)
	{
		Map<String,Object> rebuildedParams = new HashMap<String, Object>( parameters.size() );
		rebuildedParams.put(BusinessReportConfig.REPORTS_BLOCK_KEY,                     "all".equals(parameters.get(BusinessReportConfig.REPORTS_BLOCK_KEY)) ? -1 : Integer.parseInt((String) parameters.get(BusinessReportConfig.REPORTS_BLOCK_KEY)));
		rebuildedParams.put(BusinessReportConfig.REPORTS_CHANNEL_KEY,                   parameters.get( BusinessReportConfig.REPORTS_CHANNEL_KEY));
		rebuildedParams.put(BusinessReportConfig.REPORTS_PAYMENT_ON_KEY,                parameters.get( BusinessReportConfig.REPORTS_PAYMENT_ON_KEY));
		rebuildedParams.put(BusinessReportConfig.REPORTS_TRANSFER_ON_KEY,               parameters.get( BusinessReportConfig.REPORTS_TRANSFER_ON_KEY));
		rebuildedParams.put(BusinessReportConfig.REPORTS_OPEN_ACCOUNT_ON_KEY,           parameters.get( BusinessReportConfig.REPORTS_OPEN_ACCOUNT_ON_KEY));
		rebuildedParams.put(BusinessReportConfig.REPORTS_DEVIATION_ALL_KEY,             getIntValueFromBigInteger((BigInteger)parameters.get( BusinessReportConfig.REPORTS_DEVIATION_ALL_KEY)));
		rebuildedParams.put(BusinessReportConfig.REPORTS_DEVIATION_OPEN_ACCOUNT_KEY,    getIntValueFromBigInteger((BigInteger)parameters.get( BusinessReportConfig.REPORTS_DEVIATION_OPEN_ACCOUNT_KEY)));
		rebuildedParams.put(BusinessReportConfig.MAIL_RECEIVERS_KEY,                    parameters.get( BusinessReportConfig.MAIL_RECEIVERS_KEY));
		rebuildedParams.put(BusinessReportConfig.MAIL_THEME_KEY,                        parameters.get( BusinessReportConfig.MAIL_THEME_KEY));
		rebuildedParams.put(BusinessReportConfig.MAIL_TEXT_KEY,                         parameters.get( BusinessReportConfig.MAIL_TEXT_KEY));
		rebuildedParams.put(BusinessReportConfig.SEND_ON_KEY,                           parameters.get( BusinessReportConfig.SEND_ON_KEY));
		rebuildedParams.put(BusinessReportConfig.TO_PERIOD,                             DateHelper.toCalendar((Date) parameters.get( BusinessReportConfig.TO_PERIOD)));
		rebuildedParams.put(BusinessReportConfig.FROM_PERIOD,                           DateHelper.toCalendar((Date) parameters.get( BusinessReportConfig.FROM_PERIOD)));
		return rebuildedParams;
	}


	private static Integer getIntValueFromBigInteger(BigInteger value)
	{
		if (value == null)
			return 0;
		return value.intValue();
	}
}
