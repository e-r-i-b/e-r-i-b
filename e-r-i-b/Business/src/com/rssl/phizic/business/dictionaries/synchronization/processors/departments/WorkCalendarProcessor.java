package com.rssl.phizic.business.dictionaries.synchronization.processors.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.calendar.WorkCalendar;
import com.rssl.phizic.business.calendar.WorkDay;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Iterator;
import java.util.Set;

/**
 * @author mihaylov
 * @ created 02.02.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор для синхронизации календарей системы
 */
public class WorkCalendarProcessor extends ProcessorBase<WorkCalendar>
{
	public static final String MULTI_BLOCK_RECORD_ID_FIELD_NAME = "uuid";

	@Override
	protected Class<WorkCalendar> getEntityClass()
	{
		return WorkCalendar.class;
	}

	@Override
	protected WorkCalendar getNewEntity()
	{
		return new WorkCalendar();
	}

	@Override
	protected WorkCalendar getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq(MULTI_BLOCK_RECORD_ID_FIELD_NAME, uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(WorkCalendar source, WorkCalendar destination) throws BusinessException
	{
		destination.setUuid(source.getUuid());
		destination.setName(source.getName());
		destination.setTb(source.getTb());
		updateWorkDays(destination.getWorkDays(),source.getWorkDays());
	}

	/**
	 * Обновляем дни в календаре.
	 * @param destinationWorkDays - приемник данных
	 * @param sourceWorkDays - источник данных
	 */
	private void updateWorkDays(Set<WorkDay> destinationWorkDays, Set<WorkDay> sourceWorkDays)
	{
		Iterator<WorkDay> destIterator = destinationWorkDays.iterator();
		while(destIterator.hasNext())
		{
			WorkDay destinationWorkDay = destIterator.next();
			if(sourceWorkDays.contains(destinationWorkDay))
				sourceWorkDays.remove(destinationWorkDay);
			else
				destIterator.remove();
		}
		for (WorkDay sourceWorkDay : sourceWorkDays)
		{
			WorkDay addWorkDay = new WorkDay();
			addWorkDay.setDate(sourceWorkDay.getDate());
			addWorkDay.setIsWorkDay(sourceWorkDay.getIsWorkDay());
			destinationWorkDays.add(addWorkDay);
		}
	}


}
