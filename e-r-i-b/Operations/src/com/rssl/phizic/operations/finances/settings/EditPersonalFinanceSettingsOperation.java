package com.rssl.phizic.operations.finances.settings;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.messaging.quartz.DeleteOldCardOperationJobService;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.quartz.QuartzException;
import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;
import java.util.Collections;
import java.util.Set;

/**
 * @author komarov
 * @ created 23.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class EditPersonalFinanceSettingsOperation  extends EditPropertiesOperation
{
	private static final String CRON_EXPRESSION_DELIMITER = " ";
	private static final String PERIOD_DAY = "DAY";
	private static final String PERIOD_WEEK = "WEEK";
	private static final String PERIOD_MONTH = "MONTH";

	private static final String CARD_OPERATION_JOB_PREFIX = "DeleteOldCardOperation.";

	private final DeleteOldCardOperationJobService deleteOldCardOperationJobService = new DeleteOldCardOperationJobService();

	@Override
	public void initialize(PropertyCategory propertyCategory, Set propertyKeys) throws BusinessException
	{
		super.initialize(propertyCategory, propertyKeys);
		try
		{
			String jobExpression = deleteOldCardOperationJobService.getCronExpression();
			if(jobExpression == null)
				jobExpression = "* 0 4 * */3 ? *";
			setPropertiesFromExpression(jobExpression);
		}
		catch (QuartzException e)
		{
			throw new BusinessException(e);
		}
	}

	public void initializeReplicate(PropertyCategory category, Set keys) throws BusinessException, BusinessLogicException
	{
		super.initialize(category, keys);
	}

	/**
	 * @param cronExpression - выражение для тригерра в формате "second minute hour dayOfMonth month dayOfWeek year"
	 */
	private void setPropertiesFromExpression(String cronExpression)
	{
		String[] parameters = cronExpression.split(CRON_EXPRESSION_DELIMITER);
		addProperty(CARD_OPERATION_JOB_PREFIX + "time", parameters[2] + ":" + StringHelper.appendLeadingZeros(parameters[1], 2));
		String period = null;
		String periodType = null;
		if(!"*".equals(parameters[3]) && !"?".equals(parameters[3]))
		{
			period = parameters[3].split("/")[1];
			periodType = PERIOD_DAY;
		}
		else if(!"*".equals(parameters[4]))
		{
			period = parameters[4].split("/")[1];
			periodType = PERIOD_MONTH;
		}
		else if(!"?".equals(parameters[5]))
		{
			period = parameters[5].split("#")[1];
			periodType = PERIOD_WEEK;
		}
		addProperty(CARD_OPERATION_JOB_PREFIX + "period", period);
		addProperty(CARD_OPERATION_JOB_PREFIX + "period.type", periodType);
	}

	private void createJob() throws BusinessException, BusinessLogicException
	{
		String period = (String) getEntity().remove(CARD_OPERATION_JOB_PREFIX + "period");
		String periodType = (String) getEntity().remove(CARD_OPERATION_JOB_PREFIX + "period.type");
		String archTime = (String) getEntity().remove(CARD_OPERATION_JOB_PREFIX + "time");
		String[] time = archTime.split(":");
		String cronExpression = getExpression(time[1], time[0], periodType, period);

		try
		{
			deleteOldCardOperationJobService.addNewCronJob(cronExpression, Collections.<String, String>emptyMap());
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}
		catch (QuartzException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Создать Cron выражение для запуска шедуллера в заданное время
	 * Cron выражение должно быть в следующем формате: "second minute hour dayOfMonth month dayOfWeek year"
	 * @param minute - время запуска джоба в минутах
	 * @param hour - время запуска джоба в часах
	 * @param periodType - тип периодичности запуска джоба
	 * @param period - период
	 * @return cron выражение
	 */
	private String getExpression(String minute, String hour, String periodType, String period)
	{
		StringBuilder expression = new StringBuilder();
		expression.append("* "); // добавляем секунды
		expression.append(minute).append(" ");// добавляем минуты
		expression.append(hour).append(" ");// добавляем часы

		if(PERIOD_DAY.equals(periodType))
			expression.append("*/").append(period).append(" ");
		else
			if(PERIOD_WEEK.equals(periodType))
				expression.append("? ");
			else
				expression.append("* ");
		if(PERIOD_MONTH.equals(periodType))
			expression.append("*/").append(period).append(" ");
		else
			expression.append("* ");
		if(PERIOD_WEEK.equals(periodType))
			expression.append("MON#").append(period).append(" ");
		else
			expression.append("? ");
		expression.append("*"); // задание будет выполняться каждый год

		return expression.toString();
	}

	@Transactional
	public void save() throws BusinessException, BusinessLogicException
	{
		createJob();

		super.save();
	}
}
