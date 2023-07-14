package com.rssl.phizic.operations.ext.sbrf.technobreaks;

import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.technobreaks.TechnoBreakHelper;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreakStatus;
import com.rssl.phizic.business.ext.sbrf.technobreaks.TechnoBreaksService;
import com.rssl.phizic.business.marker.JobExecutionMarkerService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.payments.job.SendOfflineDelayedPaymentsJob;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

import java.util.Calendar;

/**
 * @author komarov
 * @ created 06.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * ќпераци€ удалени€ тех.перерывов
 */

public class RemoveTechnoBreakOperation extends OperationBase<Restriction> implements RemoveEntityOperation<TechnoBreak, Restriction>
{
	private static final TechnoBreaksService technoBreaksService = new TechnoBreaksService();
	private static final String NOT_FOUND_TECHNOBREAK = "“ехнологический перевыв с идентификатором %d не найден в базе данных";
	private static final JobExecutionMarkerService jobExecutionMarkerService = new JobExecutionMarkerService();

	private TechnoBreak technoBreak;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		technoBreak = technoBreaksService.findById(id);

		if(technoBreak == null)
			throw new BusinessLogicException(String.format(NOT_FOUND_TECHNOBREAK, id));

		if(!TechnoBreakHelper.isActive(technoBreak))
		{
			throw new BusinessLogicException("¬ы не можете отредактировать или удалить неактуальный технологический перерыв.");
		}
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		// помечаем технологический перерыв как удаленный
		technoBreak.setStatus(TechnoBreakStatus.DELETED);
		// устанавливаем в качестве окончани€ текущую дату
		technoBreak.setToDate(Calendar.getInstance());
		// обновл€ем в базе
		technoBreaksService.saveOrUpdate(technoBreak);
		jobExecutionMarkerService.createForJob(SendOfflineDelayedPaymentsJob.class.getName());
	}

	public TechnoBreak getEntity()
	{
		return technoBreak;
	}
}
