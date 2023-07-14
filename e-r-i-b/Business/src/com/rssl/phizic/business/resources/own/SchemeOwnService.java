package com.rssl.phizic.business.resources.own;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.common.types.client.DefaultSchemeType;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 19.10.2005
 * @ $Author: egorovaav $
 * @ $Revision: 1.1 $
 */

public class SchemeOwnService extends MultiInstanceSchemeOwnService
{

	public AccessScheme findScheme(CommonLogin login, AccessType accessType) throws BusinessException
	{
		return super.findScheme(login, accessType, null);
	}

	public void removeAllSchemes(CommonLogin login) throws BusinessException
	{
		super.removeAllSchemes(login, null);
	}

	public void removeScheme(final CommonLogin login, final AccessType accessType) throws BusinessException
	{
		super.removeScheme(login, accessType, null);
	}

	/**
	 * Установить схемы по умолчанию для клиента
	 * @param login логин клиента
	 * @param creationType тип клиента
	 * @param departmentTb код Тербанка клиента, null если клиент карточный
	 */
	public void setClientDefaultSchemes(Login login, DefaultSchemeType creationType, String departmentTb) throws BusinessException
	{
		super.setClientDefaultSchemes(login, creationType, departmentTb, null, null);
	}

	public void setClientDefaultSchemesParams(Login login, DefaultSchemeType creationType, String departmentTb, AccessType accessType) throws BusinessException
	{
		super.setClientDefaultSchemes(login, creationType, departmentTb, null, accessType);
	}

	public void setEmployeeDefaultScheme(BankLogin login) throws BusinessException
	{
		super.setEmployeeDefaultScheme(login, null);
	}

	public void setScheme(final CommonLogin login, final AccessType accessType, final AccessScheme scheme) throws BusinessException
	{
		super.setScheme(login, accessType, scheme, null);    
	}

	public List<String> findClientSchemeOwnTypes(CommonLogin login, List<String> accessTypes) throws BusinessException
	{
		return super.findClientSchemeOwnTypes(login, accessTypes);
	}
}
