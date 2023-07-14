package com.rssl.phizic.business.bki;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.loanreport.CreditBureauConfig;
import com.rssl.phizic.config.loanreport.CreditBureauConfigStorage;
import com.rssl.phizic.web.util.MoneyFunctions;

import java.util.Calendar;

/**
 * @author Rtischeva
 * @ created 24.10.14
 * @ $Author$
 * @ $Revision$
 */
public class CreditReportHelper
{
	/**
	 * ������� �� ����� �������� ������ �� ���
	 * @param paymentDate - ���� ������� (�������)
	 * @return
	 */
	public static boolean isCreditReportTimeOut(Calendar paymentDate)
	{
		CreditBureauConfigStorage creditBureauConfigStorage = new CreditBureauConfigStorage();
		CreditBureauConfig config = creditBureauConfigStorage.loadConfig();

		Calendar timeoutTime = Calendar.getInstance();
		timeoutTime.add(Calendar.MINUTE, -config.bkiTimeoutInMinutes);
		return paymentDate.before(timeoutTime);
	}

	/**
	 * ��������� ������ ��� ����������� ���� �������, ��� ����� ����������
	 * @param applicationDate - ���� �������
	 * @return
	 * @throws BusinessException
	 */
	public static String getApplicationDate(String applicationDate) throws BusinessException
	{
		return CreditBuilderFormatter.formatDate(applicationDate).get(Calendar.DAY_OF_MONTH) + CreditBuilderFormatter.getMonthString(applicationDate) + CreditBuilderFormatter.getYear(applicationDate);
	}

	/**
	 *
	 * @param currency - ��� ������
	 * @return
	 */
	public static String getCurrencySign(String currency)
	{
		return MoneyFunctions.getCurrencySign(currency);
	}
}
