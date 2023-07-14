package com.rssl.phizic.operations.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.messaging.quartz.ArchiveMailJobService;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.quartz.QuartzException;
import com.rssl.phizic.utils.StringHelper;

import java.io.File;
import java.text.ParseException;
import java.util.Collections;
import java.util.Set;

/**
 * @author mihaylov
 * @ created 22.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditArchivingMailOperation extends EditPropertiesOperation
{
	private static final String CRON_EXPRESSION_DELIMITER = " ";
	private static final String PERRIOD_DAY = "DAY";
	private static final String PERRIOD_WEEK = "WEEK";
	private static final String PERRIOD_MONTH = "MONTH";
	private static final String ARCHIVE_OUTGOIMG_JOB = "DeleteOutgoingMailJob";
	private static final String ARCHIVE_INCOMING_JOB = "DeleteIncomingMailJob";
	private static final String ARCHIVE_DELETED_JOB = "ArchiveMailJob";

	private final ArchiveMailJobService archiveMailJobService = new ArchiveMailJobService();

	@Override
	public void initialize(PropertyCategory propertyCategory, Set propertyKeys) throws BusinessException
	{
		super.initialize(propertyCategory, propertyKeys);
		try
		{
			String jobExpression = archiveMailJobService.getCronExpression(ARCHIVE_OUTGOIMG_JOB);
			if(jobExpression == null)
				jobExpression = "* 0 4 * */3 ? *";
			setPropertiesFromExpression(jobExpression,"outgoing.");
			jobExpression = archiveMailJobService.getCronExpression(ARCHIVE_INCOMING_JOB);
			if(jobExpression == null)
				jobExpression = "* 0 4 * */3 ? *";
			setPropertiesFromExpression(jobExpression,"incoming.");
			jobExpression = archiveMailJobService.getCronExpression(ARCHIVE_DELETED_JOB);
			if(jobExpression == null)
				jobExpression = "* 0 4 * */6 ? *";
			setPropertiesFromExpression(jobExpression,"deleted.");
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

	@Transactional
	public void save() throws BusinessException, BusinessLogicException
	{
		createJob(ARCHIVE_OUTGOIMG_JOB, "com.rssl.iccs.mail.archive.outgoing.");
		createJob(ARCHIVE_INCOMING_JOB, "com.rssl.iccs.mail.archive.incoming.");
		createJob(ARCHIVE_DELETED_JOB, "com.rssl.iccs.mail.archive.deleted.");

		super.save();
	}

	private void createJob(String jobClassName, String paramKey) throws BusinessException, BusinessLogicException
	{
		String period = (String) getEntity().get(paramKey + "mail.period");
		String periodType = (String) getEntity().get(paramKey + "mail.period.type");
		String archTime = (String) getEntity().get(paramKey + "mail.archTime");
		String[] time = archTime.split(":");
		String cronExpression = getExpression(time[1],time[0],periodType,period);

		try
		{
			archiveMailJobService.addNewCronJob(jobClassName ,cronExpression, Collections.<String, String>emptyMap());
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
	 * @return
	 */
	private String getExpression(String minute, String hour, String periodType, String period)
	{
		StringBuilder expression = new StringBuilder();
		expression.append("* "); // добавляем секунды
		expression.append(minute).append(" ");// добавляем минуты
		expression.append(hour).append(" ");// добавляем часы

		if(PERRIOD_DAY.equals(periodType))
			expression.append("*/"+period+" ");
		else
			if(PERRIOD_WEEK.equals(periodType))
				expression.append("? ");
			else
				expression.append("* ");
		if(PERRIOD_MONTH.equals(periodType))
			expression.append("*/"+period+" ");
		else
			expression.append("* ");
		if(PERRIOD_WEEK.equals(periodType))
			expression.append("MON#"+period+" ");
		else
			expression.append("? ");
		expression.append("*"); // задание будет выполняться каждый год

		return expression.toString();
	}

	/**
	 *
	 * @param cronExpression - выражение для тригерра в формате "second minute hour dayOfMonth month dayOfWeek year"
	 */
	private void setPropertiesFromExpression(String cronExpression, String paramKey)
	{
		String[] parameters = cronExpression.split(CRON_EXPRESSION_DELIMITER);
		addProperty(paramKey + "mail.archTime", parameters[2] + ":" + StringHelper.appendLeadingZeros(parameters[1], 2));
		String period = null;
		String periodType = null;
		if(!"*".equals(parameters[3]) && !"?".equals(parameters[3]))
		{
			period = parameters[3].split("/")[1];
			periodType = PERRIOD_DAY;
		}
		else if(!"*".equals(parameters[4]))
		{
			period = parameters[4].split("/")[1];
			periodType = PERRIOD_MONTH;
		}
		else if(!"?".equals(parameters[5]))
		{
			period = parameters[5].split("#")[1];
			periodType = PERRIOD_WEEK;
		}
		addProperty(paramKey + "mail.period", period);
		addProperty(paramKey + "mail.period.type", periodType);
	}

	public void checkFilePath (String filePath) throws BusinessLogicException
	{
		File file = new File(filePath);
		if(!file.exists())
			throw new BusinessLogicException("Вы неправильно указали каталог для архивации писем. Пожалуйста, проверьте путь и название каталога.");
	}
}
