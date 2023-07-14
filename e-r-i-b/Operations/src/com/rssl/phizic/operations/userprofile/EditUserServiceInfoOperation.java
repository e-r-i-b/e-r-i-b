package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.persons.UserServiceInfo;
import com.rssl.phizic.business.persons.UserServiceInfoService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * ќпераци€ сохранени€ служебной информации в профиле клиента.
 * »спользуетс€ дл€ устройств самообслуживани€.
 *
 * @author  Balovtsev
 * @created 10.09.14.
 */
public class EditUserServiceInfoOperation extends OperationBase implements EditEntityOperation
{
	private static final UserServiceInfoService service       = new UserServiceInfoService();
	private static final SimpleService          simpleService = new SimpleService();

	private UserServiceInfo userServiceInfo;

	/**
	 * ¬ методе прозводитс€ инициализаци€ операции первичными данными.
	 *
	 * @param  login логин пользовател€
	 * @throws BusinessException
	 */
	public void initialize(final Login login) throws BusinessException
	{
		userServiceInfo = service.findByLoginId(login.getId());

		if (userServiceInfo == null)
		{
			userServiceInfo = new UserServiceInfo(null, login.getId(), null);
		}
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		simpleService.addOrUpdate(userServiceInfo);
	}

	public UserServiceInfo getEntity() throws BusinessException, BusinessLogicException
	{
		return userServiceInfo;
	}
}
