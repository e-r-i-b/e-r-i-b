package com.rssl.phizic.web.configure.addressBookSettings;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Настройка синхронизации УАК
 * @author shapin
 * @ created 10.10.14
 * @ $Author$
 * @ $Revision$
 */
public class EditSynchronizationSettingsAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditSmartAddressBookSynchronizationOperation","EditSmartAddressBookSynchronizationSettings");
	}

}
