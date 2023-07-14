package com.rssl.phizic.operations.dictionaries.jbt;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.jbt.BillingJBTFetcher;
import com.rssl.phizic.business.dictionaries.jbt.JBTFetcher;
import com.rssl.phizic.business.dictionaries.jbt.TaxJBTFetcher;
import com.rssl.phizic.business.dictionaries.jbt.UnloadingState;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * @author akrenev
 * @ created 22.01.2010
 * @ $Author$
 * @ $Revision$
 */
public class UnloadJBTOperation extends OperationBase implements ListEntitiesOperation
{
	private Calendar unloadPeriodDateFrom;
	private Calendar unloadPeriodDateTo;
	private JBTFetcher fetcher;


	public void initialize(String[] providerIds, UnloadingState state, String billingId, Calendar unloadPeriodDateFrom, Calendar unloadPeriodDateTo) throws BusinessException
	{
		this.unloadPeriodDateFrom = unloadPeriodDateFrom;
		this.unloadPeriodDateTo = unloadPeriodDateTo;

		fetcher = !StringHelper.isEmpty(billingId) ? new BillingJBTFetcher() : new TaxJBTFetcher();
		fetcher.initialize(providerIds, state, billingId);
	}

	public String getArchiveName() throws BusinessException
	{
		return fetcher.getSourceName() + ".zip";
	}

	public HashMap<String, StringBuilder> getUnloadedData() throws BusinessException, BusinessLogicException
	{
		try
		{
			//переменные для того, что бы сравнивать только по дате, без учета времени
			Calendar beginNullTimeDate = DateHelper.toCalendar(DateHelper.setTime(unloadPeriodDateFrom.getTime(),0,0,0));
			Calendar endNullTimeDate = DateHelper.toCalendar(DateHelper.setTime(unloadPeriodDateTo.getTime(),0,0,0));
			endNullTimeDate.set(Calendar.MILLISECOND,000);

			HashMap<String, StringBuilder> entries = new HashMap<String, StringBuilder>();
			if (beginNullTimeDate.compareTo(endNullTimeDate) == 0)
			{
				entries.putAll(fetcher.downloadArhive(fetcher.getSource(unloadPeriodDateFrom.getTime(), unloadPeriodDateTo.getTime()), DateHelper.toString(unloadPeriodDateFrom.getTime()).split("\\.")));
			}
			else
			{
				while (beginNullTimeDate.compareTo(endNullTimeDate) <= 0)
				{
					if (beginNullTimeDate.compareTo(endNullTimeDate) == 0)
					{
						entries.putAll(fetcher.downloadArhive(fetcher.getSource(unloadPeriodDateFrom.getTime(), unloadPeriodDateTo.getTime()), DateHelper.toString(unloadPeriodDateFrom.getTime()).split("\\.")));
					}
					else
					{
						Date endOfDayDate = DateHelper.setTime(beginNullTimeDate.getTime(),23,59,59);
						entries.putAll(fetcher.downloadArhive(fetcher.getSource(unloadPeriodDateFrom.getTime(), endOfDayDate), DateHelper.toString(unloadPeriodDateFrom.getTime()).split("\\.")));
					}
					beginNullTimeDate = DateHelper.toCalendar(DateHelper.add(beginNullTimeDate.getTime(), 0, 0, 1));
					unloadPeriodDateFrom = beginNullTimeDate;
				}
			}
			if (entries.isEmpty())
			{
				//на случай, если в выгружаемом архиве ничего нет, добавляем пустой ZipEntry
				entries.put("", new StringBuilder(""));
			}
			return entries;
		}
		catch (IOException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}