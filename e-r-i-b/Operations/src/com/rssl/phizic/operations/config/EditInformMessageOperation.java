package com.rssl.phizic.operations.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessagesService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.config.Property;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.PropertyConfig;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author koptyaev
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 */
public class EditInformMessageOperation extends OperationBase
{
	public static final String ADDRESS_BOOK_MESSAGE_KEY = "addressBookMessageAsNewService";
	public static final String ADDRESS_BOOK_SHOW_MESSAGE_KEY = "com.rssl.phizic.web.configure.addressBookSettings.address.book.show";
	private static final StaticMessagesService staticMessagesService = new StaticMessagesService();
	private static final String SEPARATOR = ".";
	private String localeId = null;

	/**
	 * @return показывать ли сообщение
	 */
	public Boolean getShowMessage()
	{
		Property property = DbPropertyService.findProperty(ADDRESS_BOOK_SHOW_MESSAGE_KEY,PropertyCategory.Phizic.getValue(),DbPropertyService.getDbInstance(PropertyCategory.Phizic.getValue()));
		return property==null?false:Boolean.valueOf(property.getValue());
	}

	/**
	 * @param showMessage флаг необходимости показывать сообщение
	 */
	public void setShowMessage(Boolean showMessage)
	{
		DbPropertyService.updateProperty(ADDRESS_BOOK_SHOW_MESSAGE_KEY, showMessage.toString(), PropertyCategory.Phizic);
	}

	/**
	 * @return получить информационное сообщение
	 * @throws BusinessException
	 */
	public String getMessage() throws BusinessException
	{
		StaticMessage msg = staticMessagesService.findByKey(ADDRESS_BOOK_MESSAGE_KEY + getLocalizationSuffix());
		if (msg == null )
			return StringHelper.isEmpty(localeId)?(ConfigFactory.getConfig(PropertyConfig.class).getProperty(ADDRESS_BOOK_MESSAGE_KEY)):null;
		return msg.getText();
	}
	/**
	 * Обновить информационное сообщение
	 * @param message - сообщение
	 * @throws BusinessException
	 */
	public void updateMessage(String message) throws BusinessException
	{
		staticMessagesService.addOrUpdate(new StaticMessage(ADDRESS_BOOK_MESSAGE_KEY + getLocalizationSuffix(), message));
	}

	/**
	 * Установить идентификатор локали
	 * @param localeId идентификатор
	 */
	public void initialize(String localeId)
	{
		this.localeId = localeId;
	}

	private String getLocalizationSuffix()
	{
		if (StringHelper.isNotEmpty(localeId))
			return SEPARATOR+localeId;
		return "";
	}
}
