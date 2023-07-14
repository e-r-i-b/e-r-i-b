package com.rssl.phizic.operations.contacts;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessagesService;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.userSettings.SettingsProcessor;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.PropertyConfig;

/**
 * @author lepihina
 * @ created 10.06.14
 * $Author$
 * $Revision$
 * Операция для отображения информирующего сообщения об услуге адресной книги
 */
public class ShowContactsMessageOperation extends OperationBase
{
	private static final String ADDRESS_BOOK_MESSAGE_KEY = "addressBookMessageAsNewService";
	private static final StaticMessagesService staticMessagesService = new StaticMessagesService();
	private static final String SEPARATOR = ".";

	/**
	 * @return информирующее сообщение об услуге адресной книги
	 * @throws BusinessException
	 */
	public String getAddressBookMessage() throws BusinessException
	{
		StaticMessage msg = staticMessagesService.findByKey(ADDRESS_BOOK_MESSAGE_KEY + getLocalizationSuffix());
		if (msg == null)
			return ConfigFactory.getConfig(PropertyConfig.class).getProperty(ADDRESS_BOOK_MESSAGE_KEY);
		return msg.getText();
	}

	/**
	 * Сохраняет флаг, что клиенту отобразили информирующее сообщение об услуге адресной книги
	 */
	public void readShowAddressBookMessage() throws BusinessException
	{
		UserPropertiesConfig.processUserSettingsWithoutPersonContext(AuthenticationContext.getContext().getLogin(), new SettingsProcessor<Void>()
		{
			public Void onExecute(UserPropertiesConfig userProperties)
			{
				userProperties.setShowContactsMessage(true);
				PersonSettingsManager.savePersonData(PersonSettingsManager.SHOW_CONTACTS_MESSAGE_KEY, true);
				return null;
			}
		});
	}

	private String getLocalizationSuffix()
	{
		if (MultiLocaleContext.isDefaultLocale())
			return "";
		return SEPARATOR+MultiLocaleContext.getLocaleId();
	}
}
