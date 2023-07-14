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
 * ������ ��� ���������� ������ ��� �������� ����������� ����� ����
 * @author Rtischeva
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 */
public class SubscribeFeeService
{
	private final SimpleJobService jobService = new SimpleJobService(ERMB_QUARTZ_PROPERTIES);

	/**
	 * ��������� �������� ����-������������ ���: ������� ������ � ������� � ���������� �����
	 * @param unloadTimeList - ������ ������ ������� �������� ���
	 * @throws BusinessException
	 */
	public void updateFPPTriggers(List<String> unloadTimeList) throws BusinessException
	{
		//������� ������ �������� ����-������������
		deleteTriggers();
		//������� � ���������� ����� �������� ����-������������
		createTriggers(unloadTimeList);
	}

	private void deleteTriggers() throws BusinessException
	{
        //�������� ������ ���������, ����������� � ����� � ������� ��
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
