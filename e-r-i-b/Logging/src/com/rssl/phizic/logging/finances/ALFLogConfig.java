package com.rssl.phizic.logging.finances;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.writers.CardOperationCategoryChangingLogWriter;
import com.rssl.phizic.logging.writers.FilterOutcomePeriodLogWriter;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author lukina
 * @ created 05.08.2014
 * @ $Author$
 * @ $Revision$
 */
public class ALFLogConfig extends Config
{
	public static final String WRITER_FOP_CLASS_KEY = "com.rssl.phizic.logging.writers.FilterOutcomePeriodLogWriter";
	public static final String WRITER_COCC_CLASS_KEY = "com.rssl.phizic.logging.writers.CardOperationCategoryChangingLogWriter";

	private FilterOutcomePeriodLogWriter filterOutcomePeriodLogWriter;
	private CardOperationCategoryChangingLogWriter cardOperationCategoryChangingLogWriter;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	public ALFLogConfig(PropertyReader reader)
	{
		super(reader);
	}


	public void doRefresh() throws ConfigurationException
	{

		String classNameFOP = getProperty(WRITER_FOP_CLASS_KEY);
		if (filterOutcomePeriodLogWriter == null || !filterOutcomePeriodLogWriter.getClass().getName().equals(classNameFOP))
		{
			filterOutcomePeriodLogWriter = loadFilterOutcomePeriodLogWriter(classNameFOP);
		}
		String classNameCOCC = getProperty(WRITER_COCC_CLASS_KEY);
		if (cardOperationCategoryChangingLogWriter == null || !cardOperationCategoryChangingLogWriter.getClass().getName().equals(classNameCOCC))
		{
			cardOperationCategoryChangingLogWriter = loadCardOperationCategoryChangingLogWriter(classNameCOCC);
		}

	}

	private FilterOutcomePeriodLogWriter loadFilterOutcomePeriodLogWriter(String className)
	{
		if (StringHelper.isEmpty(className))
			return null;

		try
		{
			return (FilterOutcomePeriodLogWriter) ClassHelper.loadClass(className).newInstance();
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка загрузки " + className, e);
		}
	}	private CardOperationCategoryChangingLogWriter loadCardOperationCategoryChangingLogWriter(String className)
	{
		if (StringHelper.isEmpty(className))
			return null;

		try
		{
			return (CardOperationCategoryChangingLogWriter) ClassHelper.loadClass(className).newInstance();
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка загрузки " + className, e);
		}
	}

	public FilterOutcomePeriodLogWriter getFilterOutcomePeriodLogWriter()
	{
		return filterOutcomePeriodLogWriter;
	}

	public CardOperationCategoryChangingLogWriter getCardOperationCategoryChangingLogWriter()
	{
		return cardOperationCategoryChangingLogWriter;
	}
}
