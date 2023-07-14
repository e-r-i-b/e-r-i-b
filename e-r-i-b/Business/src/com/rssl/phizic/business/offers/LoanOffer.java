package com.rssl.phizic.business.offers;

import com.rssl.ikfl.crediting.OfferCondition;
import com.rssl.ikfl.crediting.OfferTopUp;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * @author Rtischeva
 * @ created 20.01.15
 * @ $Author$
 * @ $Revision$
 */
public interface LoanOffer extends Offer
{
	String getProductName();

	Double getPercentRate();

	Calendar getEndDate();

	Long getDuration();

	String getPasportNumber();

	String getPasportSeries();

	String getProductCode();

	String getSubProductCode();

	String getProductTypeCode();

	List<OfferCondition> getConditions();

	Set<OfferTopUp> getTopUps();

	String getCampaignMemberId();
}
