package com.rssl.phizic.web.common.socialApi.permissions;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.Map;

/**
 * ������ ��������� �������� � mAPI
 * @author Dorzhinov
 * @ created 08.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListPermissionsMobileForm extends ActionFormBase
{
    private Map<String, Boolean> permissions; //����� <���� ������ mAPI, ����������� ������� ������ ������>
	private Map<String, Integer> dictionaries; //����� <���� ����������� ���������� �����������, ����������� 1-��������/0 ����������.>

    public Map<String, Boolean> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Map<String, Boolean> permissions)
    {
        this.permissions = permissions;
    }

	public Map<String, Integer> getDictionaries()
	{
		return dictionaries;
	}

	public void setDictionaries(Map<String, Integer> dictionaries)
	{
		this.dictionaries = dictionaries;
	}
}
