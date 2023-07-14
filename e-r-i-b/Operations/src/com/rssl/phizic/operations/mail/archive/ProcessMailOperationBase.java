package com.rssl.phizic.operations.mail.archive;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.config.*;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.OperationBase;

import java.util.Calendar;
import java.util.Iterator;

/**
 * @author mihaylov
 * @ created 08.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ѕазова€ операци€ дл€ удалени€ писем из списка вход€щих/исход€щих в корзину и последующей архивации
 */
public abstract class ProcessMailOperationBase extends OperationBase
{
	protected Iterator<Mail> mailIterator;


	public void initialize() throws BusinessException
	{
		initialize(getEndDate());
	}

	public void initialize(Calendar endArchivationDate) throws BusinessException
	{
		Query query = createQuery("list");
		query.setParameter("toDate", endArchivationDate);
		try
		{
			mailIterator = query.executeIterator();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	//получение даты, письма старше, которорой необходимо обрабатывать
	protected Calendar getEndDate() throws BusinessException
	{
		Integer lastMonth = getLastMonthValue();
		Calendar endDate = Calendar.getInstance();
		endDate.add(Calendar.MONTH,-lastMonth);
		endDate.get(Calendar.MONTH);
		return endDate;
	}

	/**
	 * ¬ыполнить действие над письмом
	 * @throws BusinessException
	 */
	public abstract void process() throws BusinessException;

	protected abstract Integer getLastMonthValue() throws BusinessException;

}
