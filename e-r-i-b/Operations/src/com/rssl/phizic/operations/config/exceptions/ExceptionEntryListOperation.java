package com.rssl.phizic.operations.config.exceptions;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.documents.templates.ConfigImpl;
import com.rssl.phizic.business.exception.ExceptionEntryType;
import com.rssl.phizic.business.exception.ExceptionSettingsService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.query.QueryParameter;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author mihaylov
 * @ created 17.04.2013
 * @ $Author$
 * @ $Revision$
 * Операция просмотра справочника маппинга ошибок
 */
public class ExceptionEntryListOperation extends OperationBase implements ListEntitiesOperation
{
	private boolean isDecoratedException;
	private ExceptionEntryType exceptionEntryType;

	/**
	 * Инициализируем операцию
	 * @param isDecoratedException - с каким типом ошибок работаем. С уже обработанными(задан message или ещё нет)
	 * @param exceptionEntryType - тип ошибки, с которым работаем
	 */
	public void initialize(boolean isDecoratedException, ExceptionEntryType exceptionEntryType)
	{
		this.isDecoratedException = isDecoratedException;
		this.exceptionEntryType = exceptionEntryType;
	}

	/**
	 * @return isDecoratedException - с каким типом ошибок работаем. С уже обработанными(задан message или ещё нет)
	 */
	@QueryParameter
	public boolean isDecoratedException()
	{
		return isDecoratedException;
	}

	/**
	 * @return тип ошибки, с которым работаем
	 */
	@QueryParameter
	public String getExceptionEntryType()
	{
		return exceptionEntryType.name();
	}

	@Override
	protected String getInstanceName()
	{
		return Constants.DB_INSTANCE_NAME;
	}

	/**
	 * @return имя линка дб.
	 */
	@QueryParameter
	public String getLinkName()
	{
		if (MultiBlockModeDictionaryHelper.isMultiBlockMode())
		{
			return ConfigFactory.getConfig(ExceptionSettingsService.class).getCsaAdminDbLinkName();
		}
		return ConfigFactory.getConfig(ConfigImpl.class).getDbLinkName();
	}
}
