package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.utils.jaxb.AbstractCalendarAdapter;

import java.text.SimpleDateFormat;

/**
 * Дата в формате МБК
 * Varchar(4) Месяц и год
 * @author Puzikov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */

public class MbkDateAdapter extends AbstractCalendarAdapter
{
	@Override
	protected SimpleDateFormat getDateFormat()
	{
		return new SimpleDateFormat("MMyy");
	}
}
