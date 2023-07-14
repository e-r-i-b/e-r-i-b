package com.rssl.phizic.business.documents;

import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * @author eMakarov
 * @ created 09.01.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class ClosingClaim extends GateExecutableDocument
{
	public static final String CLOSING_DATE_ATTRIBUTE_NAME = "closing-date";

	public Calendar getClosingDate()
	{
		return DateHelper.toCalendar((Date) getNullSaveAttributeValue(CLOSING_DATE_ATTRIBUTE_NAME));
	}

	public void setClosingDate(Calendar closingDate)
	{
		setNullSaveAttributeCalendarValue(CLOSING_DATE_ATTRIBUTE_NAME, closingDate);
	}
}
