package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Экшен для настроек Профиля клиента
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ClientProfileConfigureAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		if (PermissionUtil.impliesOperation("EditClientProfileConfigureOperation", "ClientProfileConfigureService"))
			return createOperation("EditClientProfileConfigureOperation", "ClientProfileConfigureService");
		else if (PermissionUtil.impliesOperation("EditClientProfileConfigureOperation", "ClientProfileConfigureServiceEmployee"))
			return createOperation("EditClientProfileConfigureOperation", "ClientProfileConfigureServiceEmployee");
		else
			return createOperation("EditClientProfileConfigureOperation");
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(form, operation);

		ClientProfileConfigureForm frm = (ClientProfileConfigureForm) form;

		if (frm.getField(ProfileConfig.AVATAR_AVAILABLE_FILES) != null)
		{
			String avatarAvailableFiles = (String) frm.getField(ProfileConfig.AVATAR_AVAILABLE_FILES);
			if (avatarAvailableFiles.contains("jpg") && avatarAvailableFiles.contains("jpeg"))
				frm.setCheckedJPG(true);
			if (avatarAvailableFiles.contains("gif"))
				frm.setCheckedGIF(true);
			if (avatarAvailableFiles.contains("png"))
				frm.setCheckedPNG(true);
		}
	}
}
