package com.rssl.phizic.creditreport;

import com.rssl.phizic.business.bki.CreditReportHelper;
import com.rssl.phizic.config.loanreport.CreditBureauConfig;
import com.rssl.phizic.config.loanreport.CreditBureauConfigStorage;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 21.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class CreditReportFunctions
{
	private final static Log log = PhizICLogFactory.getLog(LogModule.Web);

	/**
	 * Истекло ли время ожидания ответа из БКИ
	 * @param paymentDate - дата платежа (запроса)
	 * @return
	 */
	public static boolean isCreditReportTimeOut(Calendar paymentDate)
	{
		try
		{
			return CreditReportHelper.isCreditReportTimeOut(paymentDate);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * @return URL Объединенное Кредитное Бюро
	 */
	public static String getBkiOkbUrl()
	{
		try
		{
			CreditBureauConfigStorage creditBureauConfigStorage = new CreditBureauConfigStorage();
			CreditBureauConfig config = creditBureauConfigStorage.loadConfig();
			return config.okbURL.toString();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return "";
		}
	}

	/**
	 * @return Отображать кнопку "Скачать отчет в формате pdf"
	 */
	public static boolean getPdfButtonShow()
	{
		try
		{
			CreditBureauConfigStorage creditBureauConfigStorage = new CreditBureauConfigStorage();
			CreditBureauConfig config = creditBureauConfigStorage.loadConfig();
			return config.pdfButtonShow;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}
}
