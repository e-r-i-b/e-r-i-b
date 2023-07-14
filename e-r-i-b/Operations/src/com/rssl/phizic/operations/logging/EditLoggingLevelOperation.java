package com.rssl.phizic.operations.logging;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.messaging.quartz.ArchiveJournalJobService;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.operations.logging.archive.ArchivationValuesMaps;
import com.rssl.phizic.quartz.QuartzException;
import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author eMakarov
 * @ created 23.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditLoggingLevelOperation extends EditPropertiesOperation
{
	private Map<String, String> prevProperties; // —писок редактируемых настроек.

	private String prefix;
	private final String ARCHIVE_KEY = Constants.AUTO_ARCHIVE_SUFFIX;

	public void initialize(PropertyCategory category, Set<String> keys, String prefix) throws BusinessException, BusinessLogicException
	{
		super.initialize(category, keys);
		this.prefix = prefix;
		prevProperties = new HashMap<String, String>(getEntity());
	}

	@Transactional
	public void save() throws BusinessException, BusinessLogicException
	{
		super.save();
		resolveArchivationOperation(prevProperties);
	}

	private void resolveArchivationOperation(Map<String, String> prevValues) throws BusinessException, BusinessLogicException
	{
		Map<String, String> currValues = (Map<String, String>) getEntity();

		boolean prevState = Boolean.parseBoolean(prevValues.get(ARCHIVE_KEY));
		boolean currState = Boolean.parseBoolean(currValues.get(ARCHIVE_KEY));

		String triggerPrefix = ArchivationValuesMaps.getArchiveName(prefix) + "Archivation";

		try
		{
			if (!prevState && currState)
			{
				// создать новый job
				// все исключени€ пролетают наверх
				String jobClass = ArchivationValuesMaps.getJobClassName(prefix);
				ArchiveJournalJobService.addNewJob(triggerPrefix, createExpression(currValues), jobClass);
			}
			else if (prevState && !currState)
			{
				// удалить Job
				ArchiveJournalJobService.removeJob(triggerPrefix, createExpression(prevValues));
			}
			else if (prevState && currState)
			{
				// выполнить обновление job'a
				String prevExpression = createExpression(prevValues);
				String currExpression = createExpression(currValues);
				if (!prevExpression.equals(currExpression))
				{
					ArchiveJournalJobService.updateJob(triggerPrefix, prevExpression, currExpression);
				}
			}
			else if (!prevState && !currState)
			{
				// ничего делать не надо
			}
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

	private String createExpression(Map<String, String> fields) throws BusinessException
	{
		int period = Integer.parseInt(fields.get(prefix + "archive.period"));
		String archTime = fields.get(prefix + "archive.archTime");
		if (StringHelper.isEmpty(archTime))
			return "";
		int archHours = Integer.parseInt(archTime.split(":")[0]);
		int archMins = Integer.parseInt(archTime.split(":")[1]);

		String periodType = fields.get(prefix + "archive.period.type");
		if (StringHelper.isEmpty(periodType))
			return "";
		if (Constants.AUTO_ARCHIVE_ARCH_PERIOD_DAY.equals(periodType)){
			return "0 " + archMins + " " + archHours + " */" + period + " * ?";
	    }else if (Constants.AUTO_ARCHIVE_ARCH_PERIOD_MONTH.equals(periodType)){
			return "0 " + archMins + " " + archHours + " * */" + period + " ?";
		}
		throw new BusinessException("Ќеподдерживаемый тип периода "+periodType);
	}
}
