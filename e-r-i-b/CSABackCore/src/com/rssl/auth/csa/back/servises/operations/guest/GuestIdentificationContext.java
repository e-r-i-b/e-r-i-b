package com.rssl.auth.csa.back.servises.operations.guest;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.IdentificationFailedException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.SyncUtil;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;

/**
 * Контексо идентификации зарегистрированного гостя
 * @author niculichev
 * @ created 19.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestIdentificationContext
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private GuestProfile profile;

	private GuestIdentificationContext(GuestProfile profile)
	{
		this.profile = profile;
	}

	/**
	 * Идентифицировать клиента по номеру телефона входа
	 * @param phone номер телефона входа
	 * @return контекс идентфикации
	 * @throws Exception
	 */
	public static GuestIdentificationContext createByPhone(String phone)  throws Exception
	{
		if (StringHelper.isEmpty(phone))
			throw new IllegalArgumentException("Телефон должен быть задан");

		GuestProfile profile = GuestProfile.findByPhone(phone);
		if(profile == null)
			throw new IdentificationFailedException("Не найден профиль гостя по номеру телефона " + phone);

		return new GuestIdentificationContext(profile);
	}

	public GuestProfile getProfile()
	{
		return profile;
	}
}
