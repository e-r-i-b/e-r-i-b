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
 * �������� ��������� ����������� �������� ������
 */
public class ExceptionEntryListOperation extends OperationBase implements ListEntitiesOperation
{
	private boolean isDecoratedException;
	private ExceptionEntryType exceptionEntryType;

	/**
	 * �������������� ��������
	 * @param isDecoratedException - � ����� ����� ������ ��������. � ��� �������������(����� message ��� ��� ���)
	 * @param exceptionEntryType - ��� ������, � ������� ��������
	 */
	public void initialize(boolean isDecoratedException, ExceptionEntryType exceptionEntryType)
	{
		this.isDecoratedException = isDecoratedException;
		this.exceptionEntryType = exceptionEntryType;
	}

	/**
	 * @return isDecoratedException - � ����� ����� ������ ��������. � ��� �������������(����� message ��� ��� ���)
	 */
	@QueryParameter
	public boolean isDecoratedException()
	{
		return isDecoratedException;
	}

	/**
	 * @return ��� ������, � ������� ��������
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
	 * @return ��� ����� ��.
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
