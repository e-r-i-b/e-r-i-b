package com.rssl.phizic.csaadmin.security;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.login.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author mihaylov
 * @ created 15.11.13
 * @ $Author$
 * @ $Revision$
 * ¬алидатор блокировок пользовател€
 */
public class LoginBlockValidator
{
	private static final LoginBlockService loginBlockService = new LoginBlockService();

	private String message;

	/**
	 * ѕроверить блокировки логина
	 * @param login - логин
	 * @return true - логин не заблокирован, false - логин заблокирован
	 * @throws AdminException
	 * @throws AdminLogicException
	 */
	public boolean validate(Login login) throws AdminException, AdminLogicException
	{
		List<LoginBlock> loginBlockList = loginBlockService.getAllForLogin(login, Calendar.getInstance());
		if(CollectionUtils.isEmpty(loginBlockList))
			return true;
		Collections.sort(loginBlockList, LoginBlocksComparator.getInstance());
		LoginBlock loginBlock = loginBlockList.get(0);
		message = loginBlock.getMessage();
		return false;
	}

	/**
	 * @return сообщение об ошибке
	 */
	public String getMessage()
	{
		return message;
	}
}
