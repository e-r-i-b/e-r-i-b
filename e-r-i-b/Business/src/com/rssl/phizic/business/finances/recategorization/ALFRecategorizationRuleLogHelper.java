package com.rssl.phizic.business.finances.recategorization;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.finances.recategorization.ALFRecategorizationRuleEntry;
import com.rssl.phizic.logging.finances.recategorization.ALFRecategorizationRuleEntryType;
import com.rssl.phizic.logging.finances.recategorization.ALFRecategorizationRuleLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.RecategorizationRuleLogWriter;

import java.util.Calendar;

/**
 * @author lepihina
 * @ created 01.04.14
 * $Author$
 * $Revision$
 */
public class ALFRecategorizationRuleLogHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Добавляет запись в лог о добавлении нового правила перекатегоризации
	 * @param rule - правило перекатегоризации
	 * @param oldCategoryName - название исходной категории
	 * @param ruleEntryType - тип действия
	 */
	public static void writeEntryToLog(ALFRecategorizationRule rule, String oldCategoryName, ALFRecategorizationRuleEntryType ruleEntryType)
	{
		try
		{
			ALFRecategorizationRuleEntry entry = createEntry(rule, oldCategoryName, ruleEntryType, 1);
			writeToActionLog(entry);
		}
		catch (Throwable t)
		{
			//падение логирования не должно нарушать работу системы.
			log.error(t);
		}
	}

	/**
	 * Добавляет запись в лог о добавлении нового правила перекатегоризации
	 * @param rule - правило перекатегоризации
	 * @param oldCategoryName - название исходной категории
	 * @param count - количество записей
	 */
	public static void writeApplyEntryToLog(ALFRecategorizationRule rule, String oldCategoryName, int count)
	{
		try
		{
			ALFRecategorizationRuleEntry entry = createEntry(rule, oldCategoryName, ALFRecategorizationRuleEntryType.APPLY, count);
			writeToActionLog(entry);
		}
		catch (Throwable t)
		{
			//падение логирования не должно нарушать работу системы.
			log.error(t);
		}
	}

	private static ALFRecategorizationRuleEntry createEntry(ALFRecategorizationRule rule, String oldCategoryName, ALFRecategorizationRuleEntryType type, int count)
	{
		ALFRecategorizationRuleEntry entry = new ALFRecategorizationRuleEntry();
		entry.setDate(Calendar.getInstance());
		entry.setDescription(rule.getDescription());
		entry.setMccCode(rule.getMccCode());
		entry.setOriginalCategory(oldCategoryName);
		entry.setNewCategory(rule.getNewCategory().getName());
		entry.setType(type);
		entry.setCount(count);

		return entry;
	}

	private static void writeToActionLog(ALFRecategorizationRuleEntry entry)
	{
		if(entry == null)
			return;

		ALFRecategorizationRuleLogConfig config = ConfigFactory.getConfig(ALFRecategorizationRuleLogConfig.class);
		RecategorizationRuleLogWriter writer = config.getWriter();
		try
		{
			if (writer != null)
			{
				writer.write(entry);
			}
		}
		catch (Throwable t)
		{
			//падение логирования не должно нарушать работу системы.
			log.error(t);
		}
	}
}
