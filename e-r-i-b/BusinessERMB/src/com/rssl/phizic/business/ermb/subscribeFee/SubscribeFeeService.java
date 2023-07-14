package com.rssl.phizic.business.ermb.subscribeFee;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleJobService;
import com.rssl.phizic.config.ermb.SubscribeFeeConstants;
import com.rssl.phizic.quartz.QuartzException;
import com.rssl.phizic.utils.CronExpressionUtils;
import org.quartz.Trigger;

import java.text.ParseException;
import java.util.List;

import static com.rssl.phizic.business.SimpleJobService.ERMB_QUARTZ_PROPERTIES;

/**
 * Сервис для обновления джобов для списания абонентской платы ЕРМБ
 * @author Rtischeva
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 */
public class SubscribeFeeService
{
	private final SimpleJobService jobService = new SimpleJobService(ERMB_QUARTZ_PROPERTIES);

	/**
	 * обновляет триггеры джоб-координатора ФПП: удаляет старые и создает и подключает новые
	 * @param unloadTimeList - список времен запуска выгрузки ФПП
	 * @throws BusinessException
	 */
	public void updateFPPTriggers(List<String> unloadTimeList) throws BusinessException
	{
		//удаляем старые триггеры джоб-координатора
		deleteTriggers();
		//создаем и подключаем новые триггеры джоб-координатора
		createTriggers(unloadTimeList);
	}

	private void deleteTriggers() throws BusinessException
	{
        //получаем список триггеров, привязанных к джобу и удаляем их
		try
		{
			Trigger[] triggers = jobService.getTriggers(SubscribeFeeConstants.FPP_STARTER, SubscribeFeeConstants.FPP_STARTER);
			for (Trigger trigger : triggers)
				jobService.deletTrigger(trigger.getName(), SubscribeFeeConstants.FPP_STARTER);
		}
		catch (QuartzException e)
		{
			throw new BusinessException(e);
		}
	}

	private void createTriggers(List<String> unloadTimeList) throws BusinessException
	{
		try
		{
			int i = 1;
			for (String unloadTime : unloadTimeList)
			{
				String cronExpression = CronExpressionUtils.getDayTimeExp(unloadTime, "1");
				jobService.addNewCronTriggerToJob(SubscribeFeeConstants.FPP_STARTER + "_"+ i, cronExpression,  SubscribeFeeConstants.FPP_STARTER, SubscribeFeeConstants.FPP_STARTER_JOB_CLASS_NAME, SubscribeFeeConstants.FPP_STARTER);
				i++;
			}
		}
		catch (QuartzException e)
		{
			throw new BusinessException(e);
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}
	}

}
