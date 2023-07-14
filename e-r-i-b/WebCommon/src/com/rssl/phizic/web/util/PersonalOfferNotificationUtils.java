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
	private  static final  String   PERCENT = "\\[percentRate/\\]";  //процентна€ ставка
	private  static final  String   LIMIT = "\\[creditLimit/\\]";    //кредитный лимит
	private  static final  String   DURATION = "\\[creditDuration/\\]"; //срок кредита

	/**
	 * «амен€ем теги на значени€ из кредитного предложени€
	 * @param text исходный текст
	 * @param loanOffer -  предложение  на предодобренный кредит
	 * @return текст уведомлени€
	 */
	public static String getLoanOfferText(String text, LoanOffer loanOffer)
	{
		text = ProcessBBCode.replaceRegExp(text, LIMIT,    loanOffer == null ? "< редитный лимит>" : MoneyFunctions.getFormatAmount(loanOffer.getMaxLimit()));
		text = ProcessBBCode.replaceRegExp(text, DURATION, loanOffer == null ? "<—рок кредита>" :  loanOffer.getDuration()+ " мес.");
		text = ProcessBBCode.replaceRegExp(text, PERCENT,  loanOffer == null ? "<ѕроцентна€ ставка>" :  loanOffer.getPercentRate().toString()+"%");
		return text;
	}

	/**
	 * «амен€ем теги на значени€ из предложени€ на редитную карту
	 * @param text  - исходный текст уведомлени€
	 * @param loanCardOffer  - предложение  на предодобренную кредитную карту
	 * @return  текст уведомлени€
	 */
	public static String getLoanCardOfferText(String text, LoanCardOffer loanCardOffer)
	{
		return ProcessBBCode.replaceRegExp(text,LIMIT, loanCardOffer == null ? "< редитный лимит>" : MoneyFunctions.getFormatAmount(loanCardOffer.getMaxLimit()));
	}
}
