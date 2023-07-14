package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * User: IIvanova
 * Date: 14.02.2006
 * Time: 13:28:22
 */
public class PersonsConfigureAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("SetPersonSecuritySettingsOperation");
	}

	@Override
	protected EditPropertiesOperation getViewOperation() throws BusinessException, BusinessLogicException
	{
		return createOperation("ViewPersonSecuritySettingsOperation");
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(form, operation);

		PersonsConfigureForm frm = (PersonsConfigureForm) form;
		SecurityConfig config = ConfigFactory.getConfig(SecurityConfig.class);
		frm.setPasswordCharsets(config.getPasswordCharsets());
		if (StringHelper.isEmpty((String) frm.getField(Constants.LOGIN_ALLOWED_CHARS)))
			frm.setField(Constants.LOGIN_ALLOWED_CHARS, config.getLoginAllowedChars());
	}
}
