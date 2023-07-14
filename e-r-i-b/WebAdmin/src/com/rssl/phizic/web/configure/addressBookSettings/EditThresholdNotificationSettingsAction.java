package com.rssl.phizic.web.configure.addressBookSettings;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditThresholdNotificationOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * @author lepihina
 * @ created 09.06.14
 * $Author$
 * $Revision$
 * Редактирование настроек оповещения о превышении порога
 */
public class EditThresholdNotificationSettingsAction extends EditPropertiesActionBase<EditThresholdNotificationOperation>
{
	protected EditThresholdNotificationOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditThresholdNotificationOperation.class, "EditAddressBookSynchronizationSettings");
	}
}
