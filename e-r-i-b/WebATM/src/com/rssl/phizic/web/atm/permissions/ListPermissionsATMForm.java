package com.rssl.phizic.web.atm.permissions;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.Map;

/**
 * ����� ������� �������
 * @author Pankin
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ListPermissionsATMForm extends ActionFormBase
{
    private Map<String, Boolean> permissions; //����� <���� ������ atmAPI, ����������� ������� ������ ������>
	private boolean needCheckUDBO;
	private boolean checkedUDBO;

    public Map<String, Boolean> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Map<String, Boolean> permissions)
    {
        this.permissions = permissions;
    }

	public boolean isNeedCheckUDBO()
	{
		return needCheckUDBO;
	}

	public void setNeedCheckUDBO(boolean needCheckUDBO)
	{
		this.needCheckUDBO = needCheckUDBO;
	}

	public boolean isCheckedUDBO()
	{
		return checkedUDBO;
	}

	public void setCheckedUDBO(boolean checkedUDBO)
	{
		this.checkedUDBO = checkedUDBO;
	}
}
