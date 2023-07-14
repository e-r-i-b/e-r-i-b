package com.rssl.phizic;

import com.rssl.phizic.person.ClientErmbProfile;
import com.rssl.phizic.person.ErmbProfileService;
import com.rssl.phizic.person.QueryErmbProfileOptions;
import com.rssl.phizic.person.UpdateErmbProfileOptions;
import com.rssl.phizic.utils.PhoneNumber;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author Erkin
 * @ created 02.08.2014
 * @ $Author$
 * @ $Revision$
 */
class RemoveErmbPhoneRequestProcessor extends CSABackRefRequestProcessorBase<RemoveErmbPhoneRequest, VoidResponse>
{
	private final ErmbProfileService ermbProfileService = new ErmbProfileService();

	protected VoidResponse doProcessRequest(RemoveErmbPhoneRequest request)
	{
		PhoneNumber deletingPhone = request.phone;
		if (deletingPhone == null)
			throw new IllegalArgumentException("Не указан телефон");

		// 1. Ищем ЕРМБ-профиль
		QueryErmbProfileOptions queryOptions = new QueryErmbProfileOptions();
		queryOptions.findByActualIdentity = true;
		queryOptions.findByOldIdentity = false;
		ClientErmbProfile profile = ermbProfileService.queryProfile(request.clientIdentity, queryOptions);
		if (profile == null)
			return null;

		// 2. Убираем телефон из профиля
		// Если указанный телефон не входит в список подключённых, ничего не делаем.
		// Если указанный телефон совпадает с главным телефоном, главным становится первый попавшийся из оставшихся.
		// Если телефонов после удаления не остаётся, услуга отключается.
		if (profile.phones == null || !profile.phones.remove(deletingPhone))
			return null;

		if (deletingPhone.equals(profile.mainPhone))
		{
			if (!CollectionUtils.isEmpty(profile.phones))
				profile.mainPhone = profile.phones.iterator().next();
			else profile.mainPhone = null;
		}

		if (profile.mainPhone == null)
			profile.serviceStatus = false;

		// 3. Сохраняем изменения в профиле
		UpdateErmbProfileOptions updateOptions = new UpdateErmbProfileOptions();
		updateOptions.notifyMSS = true;
		updateOptions.notifyMBK = true;
		updateOptions.notifyCSA = true;
		updateOptions.notifyCOD = true;
		updateOptions.notifyWAY = true;
		updateOptions.notifyClient = true;
		ermbProfileService.updateProfile(profile, updateOptions);
		return null;
	}

	public String getRequestName()
	{
		return RemoveErmbPhoneRequest.REQUEST_NAME;
	}
}
