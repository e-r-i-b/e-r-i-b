package com.rssl.phizic.web.common.socialApi.permissions;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.Map;

/**
 * Список доступных операций в mAPI
 * @author Dorzhinov
 * @ created 08.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListPermissionsMobileForm extends ActionFormBase
{
    private Map<String, Boolean> permissions; //Карта <ключ услуги mAPI, доступность клиенту данной услуги>
	private Map<String, Integer> dictionaries; //Карта <ключ справочника доверенных получателей, доступность 1-доступен/0 недоступен.>

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
