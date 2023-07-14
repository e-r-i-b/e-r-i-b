package com.rssl.phizic.operations.stash;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;

/**
 * "избранное" в мобильном приложении
 * @author Dorzhinov
 * @ created 06.09.13
 * @ $Author$
 * @ $Revision$
 */
public class EditStashOperation extends OperationBase
{
	private static final ProfileService PROFILE_SERVICE = new ProfileService();

	private Profile profile;

	/**
	 * Инициализация
	 */
	public void init() throws BusinessException
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getLogin();
		profile = PROFILE_SERVICE.findByLogin(login);
		if (profile == null)
			throw new BusinessException("Не найден профиль пользователя с логином id=" + login.getId());
	}

	/**
	 * @return строка с избранным
	 */
	public String getStash()
	{
		return profile.getStash();
	}

	/**
	 * обновляет избранное
	 * @param stash    строка с избранным
	 */
	public void setStash(String stash) throws BusinessException
	{
		profile.setStash(stash);
		PROFILE_SERVICE.update(profile);
	}
}
