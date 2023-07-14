package com.rssl.phizic.business.offers;

import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;

import java.util.Calendar;

/**
 * @author Rtischeva
 * @ created 20.01.15
 * @ $Author$
 * @ $Revision$
 */
public interface LoanCardOffer extends Offer
{
	String getCardNumber();

	LoanCardOfferType getOfferType();

	String getNewCardType();

	Long getOsb();

	Long getVsp();

	boolean isOfferUsed();

	String getSeriesAndNumber();

	Long getIdWay();

	Calendar getLoadDate();

	/**
	 * @return Дата окончания действия предложения
	 */
	Calendar getExpDate();
}
