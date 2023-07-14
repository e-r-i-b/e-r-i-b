package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizgate.ext.sbrf.technobreaks.PeriodicType;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreakStatus;
import com.rssl.phizic.business.ext.sbrf.technobreaks.TechnoBreaksService;
import com.rssl.phizic.business.externalsystem.AutoStopSystemService;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.externalsystem.AutoTechnoBreakConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;
import org.quartz.StatefulJob;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Джоб для проверки внешних систем на недоступность
 * @author Pankin
 * @ created 06.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class CheckOfflineExternalSystemsJob extends BaseJob implements StatefulJob
{
	private static final AdapterService adapterService = new AdapterService();
	private static final AutoStopSystemService autoStopSystemService = new AutoStopSystemService();
	private static final TechnoBreaksService technoBreakService = new TechnoBreaksService();
	private static final String DEFAULT_MESSAGE = "По техническим причинам вы не можете выполнить данную операцию до %s. Пожалуйста, повторите попытку позже";

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			Calendar timeFrom = Calendar.getInstance();
			timeFrom.add(Calendar.MILLISECOND, (int) -((SimpleTrigger) context.getTrigger()).getRepeatInterval());
			List<OfflineSystemError> offlineSystemErrors = parseList(autoStopSystemService.getExternalSystemOfflineErrors(timeFrom));
			AutoTechnoBreakConfig autoTechnoBreakConfig = ConfigFactory.getConfig(AutoTechnoBreakConfig.class);
			for (OfflineSystemError offlineSystemError : offlineSystemErrors)
			{
				if (offlineSystemError.getErrors().compareTo(autoTechnoBreakConfig.getErrorLimitBySystemType(offlineSystemError.getAutoStopSystemType())) >= 0)
				{
					TechnoBreak technoBreak = new TechnoBreak();
					technoBreak.setAdapterUUID(offlineSystemError.getAdapter() == null? null : offlineSystemError.getAdapter().getUUID());
					technoBreak.setFromDate(Calendar.getInstance());
					Calendar toDate = Calendar.getInstance();
					toDate.add(Calendar.MINUTE, autoTechnoBreakConfig.getTechnoBreakDurationBySystemType(offlineSystemError.getAutoStopSystemType()).intValue());
					toDate.get(Calendar.MINUTE);
					technoBreak.setToDate(toDate);
					technoBreak.setMessage(String.format(DEFAULT_MESSAGE, DateHelper.toStringTime(toDate.getTime())));
					technoBreak.setStatus(TechnoBreakStatus.ENTERED);
					technoBreak.setPeriodic(PeriodicType.SINGLE);
					technoBreak.setAutoEnabled(true);
					technoBreak.setAllowOfflinePayments(autoTechnoBreakConfig.isAllowOfflinePayments());
					technoBreak.setUuid(new RandomGUID().toUUID());
					technoBreakService.addAutoTechnoBreak(technoBreak);
				}
			}
		}
		catch (GateException e)
		{
			throw new JobExecutionException(e);
		}
		catch (BusinessException e)
		{
			throw new JobExecutionException(e);
		}
	}

	private List<OfflineSystemError> parseList(List<Object[]> errorsList) throws GateException
	{
		if (CollectionUtils.isEmpty(errorsList))
			return Collections.emptyList();

		List<OfflineSystemError> result = new ArrayList<OfflineSystemError>();
		for (Object[] systemError : errorsList)
		{
			result.add(new OfflineSystemError((Long) systemError[0], (Long) systemError[1],
					(String) systemError[2]));
		}
		return result;
	}

	private class OfflineSystemError
	{
		private Adapter adapter;
		private Long errors;
		private AutoStopSystemType autoStopSystemType;

		private OfflineSystemError(Long adapterId, Long errors, String systemType) throws GateException
		{
			adapter = adapterService.getAdapterById(adapterId);
			this.errors = errors;
			autoStopSystemType = AutoStopSystemType.valueOf(systemType);
		}

		public Adapter getAdapter()
		{
			return adapter;
		}

		public void setAdapter(Adapter adapter)
		{
			this.adapter = adapter;
		}

		public Long getErrors()
		{
			return errors;
		}

		public void setErrors(Long errors)
		{
			this.errors = errors;
		}

		public AutoStopSystemType getAutoStopSystemType()
		{
			return autoStopSystemType;
		}

		public void setAutoStopSystemType(AutoStopSystemType autoStopSystemType)
		{
			this.autoStopSystemType = autoStopSystemType;
		}
	}
}
