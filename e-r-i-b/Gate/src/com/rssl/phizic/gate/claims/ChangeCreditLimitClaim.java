package com.rssl.phizic.gate.claims;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.SynchronizableDocument;

import java.util.Calendar;

/**
 * @author lukina
 * @ created 17.06.2015
 * @ $Author$
 * @ $Revision$
 */
public interface ChangeCreditLimitClaim extends SynchronizableDocument
{
	public String getCardNumber();

	public  String getCardName();

	public String getCardDesc();

	public Long getCardId();

	public String  getCardCurrency();

	public Money getCurrentLimit();

	public Money  getNewLimit();

	public String  getFeedbackType();

	public String  getOfferId();

	public Calendar getOfferExpDate();
}
