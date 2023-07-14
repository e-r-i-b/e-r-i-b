package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.profile.images.AvatarInfo;
import com.rssl.phizic.business.profile.images.AvatarJurnalService;
import com.rssl.phizic.business.profile.images.AvatarService;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.userSettings.SettingsProcessor;
import com.rssl.phizic.userSettings.UserPropertiesConfig;

import java.util.Arrays;
import java.util.List;

/**
 * Операция для работы с аватаром клиента
 * @author miklyaev
 * @ created 28.05.14
 * @ $Author$
 * @ $Revision$
 */

public class EditPersonAvatarOperation extends OperationBase
{
	private static final ProfileService profileService = new ProfileService();

	/**
	 * Получить информацию об аватаре клиента
	 * @param type - тип аватара
	 * @param loginId - идентификатор логина
	 * @return информацию об аватаре
	 * @throws BusinessException
	 */
	public AvatarInfo getPersonAvatarInfo(AvatarType type, long loginId) throws BusinessException
	{
		return AvatarService.get().getAvatarInfoByLoginId(type, loginId);
	}

	/**
	 * Удаление текущего аватара
	 * @param loginId - идентификатор логина
	 * @throws BusinessException
	 */
	public void deletePersonAvatar(long loginId) throws BusinessException
	{
        try
		{
			AvatarInfo info = getPersonAvatarInfo(AvatarType.SOURCE, loginId);
			profileService.deleteAvatarInfoByLoginId(loginId);
			AvatarService.get().deleteImageInfo(info, loginId);
			UserPropertiesConfig.<Void>processUserSettingsWithoutPersonContext(loginId, new SettingsProcessor<Void>()
			{
				public Void onExecute(UserPropertiesConfig userProperties)
				{
					userProperties.setFirstAvatarShow(true);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
