package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreakStatus;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreaksChecker;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.technobreaks.TechnoBreaksService;
import com.rssl.phizic.business.ext.sbrf.technobreaks.event.TechnoBreakFinishEvent;
import com.rssl.phizic.business.externalsystem.ExternalSystemRouteInfoService;
import com.rssl.phizic.business.marker.JobExecutionMarkerService;
import com.rssl.phizic.business.payments.job.SendOfflineDelayedPaymentsJob;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author akrenev
 * @ created 08.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * работа с тех. перерывами: отправка уведомления о завершении какого-либо тех.перерыва
 */

public class TechnoBreakJob extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final TechnoBreaksService technoBreaksService = new TechnoBreaksService();
	private static final ExternalSystemRouteInfoService externalSystemRouteInfoService = new ExternalSystemRouteInfoService();
	private static final JobExecutionMarkerService jobExecutionMarkerService = new JobExecutionMarkerService();

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			Calendar lastDate = Calendar.getInstance();
			Date date = context.getPreviousFireTime();
			if (date != null)
				lastDate.setTime(date);
			sendClientDataUpdate(lastDate);
		}
		catch (BusinessException e)
		{
		    log.error("Ошибка удаления технологических перерывов.", e);
		}
	}

	/**
	 * Отправляет jms сообщение в PersonDataProvider о том, что необходимо обновить данные пользователя.
	 *
	 * @param lastTestDate время предыдущей проверки.
	 * @throws BusinessException
	 */
	private void sendClientDataUpdate(Calendar lastTestDate) throws BusinessException
	{
		//Список всех тех. перерывов, активных в текущий момент или удаленных пользователем.
		List<TechnoBreak> breaks = technoBreaksService.getActiveOrJustStoppedTechnoBreaks(lastTestDate);
		//Получаем список неактивных тех. перерывов.
		boolean needUpdateOfflineDelayedPayments = true;
		for (TechnoBreak technoBreak : breaks)
		{
			if (technoBreak.getStatus() == TechnoBreakStatus.DELETED || TechnoBreaksChecker.isBecomeNotWithin(technoBreak, lastTestDate))
			{
				if (needUpdateOfflineDelayedPayments && updateOfflineDelayedPayments())
					needUpdateOfflineDelayedPayments = false;

				if (sendFinishTechnoBreakEvent(technoBreak.getAdapterUUID()))
					return;
			}
		}
	}

	private boolean updateOfflineDelayedPayments() throws BusinessException
	{
		jobExecutionMarkerService.createForJob(SendOfflineDelayedPaymentsJob.class.getName());
		return true;
	}

	private boolean sendFinishTechnoBreakEvent(String adapterUUID) throws BusinessException
	{
		if (StringHelper.isEmpty(adapterUUID) || CollectionUtils.isEmpty(externalSystemRouteInfoService.getProductTypeForAdapterId(adapterUUID)))
			return false;

		try
		{
			EventSender.getInstance().sendEvent(new TechnoBreakFinishEvent());
		}
		catch (Exception e)
		{
			log.error("Ошибка при отправке сообщения об окончании действия тех. перерыва", e);
		}

		return true;
	}
}
