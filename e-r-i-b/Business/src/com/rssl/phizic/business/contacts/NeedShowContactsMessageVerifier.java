package com.rssl.phizic.business.contacts;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.Stage;
import com.rssl.phizic.auth.modes.StageVerifier;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.addressbook.AddressBookSynchronizationConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.userSettings.SettingsProcessor;
import com.rssl.phizic.userSettings.UserPropertiesConfig;

/**
 * @author lepihina
 * @ created 10.06.14
 * $Author$
 * $Revision$
 * Условие на отображение клиенту информирующего сообщения об услуге адресной книги
 */
public class NeedShowContactsMessageVerifier implements StageVerifier
{
	private static final ServiceService serviceService = new ServiceService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		try
		{
			if (!ConfigFactory.getConfig(AddressBookSynchronizationConfig.class).getShowMessage())
				return false;

			if (!serviceService.isPersonServices(context.getLogin().getId(), "ContactSyncService"))
				return false;

			return !isShowAddressBookMessage(context);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}

	private boolean isShowAddressBookMessage(AuthenticationContext context) throws BusinessException
	{
		return UserPropertiesConfig.processUserSettingsWithoutPersonContext(context.getLogin(), new SettingsProcessor<Boolean>()
		{
			public Boolean onExecute(UserPropertiesConfig userProperties)
			{
				return userProperties.isShowContactsMessage();
			}
		});
	}
}
