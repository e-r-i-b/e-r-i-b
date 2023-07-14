package com.rssl.phizic.operations.config.exceptions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessagesService;
import com.rssl.phizic.business.exception.ExceptionSettingsService;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.PropertyConfig;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author mihaylov
 * @ created 25.04.2013
 * @ $Author$
 * @ $Revision$
 * �������� ��������� ��������� ��������� �� ������� ��� ������� � ����������
 */
public class ExceptionDefaultMessageEditOperation extends OperationBase 
{
	private static final String SEPARATOR = ".";
	private static final StaticMessagesService staticMessagesService = new StaticMessagesService();
	private String localeId;
	private ERIBLocale locale;
	private static final MultiInstanceEribLocaleService LOCALE_SERVICE = new MultiInstanceEribLocaleService();

	/**
	 * ���������������� � ������ ������
	 * @param localeId ������������� ������
	 */
	public void initialize(String localeId) throws BusinessException
	{
		this.localeId = localeId;
		if (StringHelper.isNotEmpty(localeId))
			try
			{
				locale = LOCALE_SERVICE.getById(localeId,getInstanceName());
			}
			catch (SystemException e)
			{
				throw new BusinessException(e);
			}
	}

	private String getLocalizationSuffix()
	{
		if (StringHelper.isNotEmpty(localeId))
			return SEPARATOR+localeId;
		return "";
	}
	/**
	 * @return ��������� ��������� �� ������ ��� �������
	 * @throws BusinessException
	 */
	public String getClientMessage() throws BusinessException
	{
		StaticMessage msg = staticMessagesService.findByKey(ExceptionSettingsService.CLIENT_DEFAULT_MESSAGE_KEY + getLocalizationSuffix());
		if (msg == null)
			return StringHelper.isEmpty(localeId)?(ConfigFactory.getConfig(PropertyConfig.class).getProperty(ExceptionSettingsService.CLIENT_DEFAULT_MESSAGE_KEY)):null;
		return msg.getText();
	}

	/**
	 * @return ��������� ��������� �� ������ ��� ����������
	 * @throws BusinessException
	 */
	public String getAdminMessage() throws BusinessException
	{
		StaticMessage msg = staticMessagesService.findByKey(ExceptionSettingsService.ADMIN_DEFAULT_MESSAGE_KEY + getLocalizationSuffix());
		if (msg == null)
			return StringHelper.isEmpty(localeId)?(ConfigFactory.getConfig(PropertyConfig.class).getProperty(ExceptionSettingsService.ADMIN_DEFAULT_MESSAGE_KEY)):null;
		return msg.getText();
	}

	/**
	 * �������� ��������� ��������� �� �������
	 * @param clientMessage - ��������� ��� �������
	 * @param adminMessage - ��������� ��� ����������
	 * @throws BusinessException
	 */
	public void updateMessages(String clientMessage, String adminMessage) throws BusinessException
	{
		staticMessagesService.addOrUpdate(new StaticMessage(ExceptionSettingsService.CLIENT_DEFAULT_MESSAGE_KEY + getLocalizationSuffix(), clientMessage));
		staticMessagesService.addOrUpdate(new StaticMessage(ExceptionSettingsService.ADMIN_DEFAULT_MESSAGE_KEY + getLocalizationSuffix(), adminMessage));
	}

	/**
	 * @return ������� ������
	 */
	public ERIBLocale getLocale()
	{
		return locale;
	}
}
