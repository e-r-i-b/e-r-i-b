package com.rssl.phizic.web.util;

import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.web.news.ProcessBBCode;

/**
 * @author lukina
 * @ created 13.03.2014
 * @ $Author$
 * @ $Revision$
 */
public class PersonalOfferNotificationUtils
{
	private  static final  String   PERCENT = "\\[percentRate/\\]";  //���������� ������
	private  static final  String   LIMIT = "\\[creditLimit/\\]";    //��������� �����
	private  static final  String   DURATION = "\\[creditDuration/\\]"; //���� �������

	/**
	 * �������� ���� �� �������� �� ���������� �����������
	 * @param text �������� �����
	 * @param loanOffer -  �����������  �� �������������� ������
	 * @return ����� �����������
	 */
	public static String getLoanOfferText(String text, LoanOffer loanOffer)
	{
		text = ProcessBBCode.replaceRegExp(text, LIMIT,    loanOffer == null ? "<��������� �����>" : MoneyFunctions.getFormatAmount(loanOffer.getMaxLimit()));
		text = ProcessBBCode.replaceRegExp(text, DURATION, loanOffer == null ? "<���� �������>" :  loanOffer.getDuration()+ " ���.");
		text = ProcessBBCode.replaceRegExp(text, PERCENT,  loanOffer == null ? "<���������� ������>" :  loanOffer.getPercentRate().toString()+"%");
		return text;
	}

	/**
	 * �������� ���� �� �������� �� ����������� �� �������� �����
	 * @param text  - �������� ����� �����������
	 * @param loanCardOffer  - �����������  �� �������������� ��������� �����
	 * @return  ����� �����������
	 */
	public static String getLoanCardOfferText(String text, LoanCardOffer loanCardOffer)
	{
		return ProcessBBCode.replaceRegExp(text,LIMIT, loanCardOffer == null ? "<��������� �����>" : MoneyFunctions.getFormatAmount(loanCardOffer.getMaxLimit()));
	}
}
