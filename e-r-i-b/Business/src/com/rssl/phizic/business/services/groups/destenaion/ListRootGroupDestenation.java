package com.rssl.phizic.business.services.groups.destenaion;

import com.rssl.phizic.business.services.groups.ServicesGroupInformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс для получения групп сервисов в виде листа
 * @author komarov
 * @ created 08.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class ListRootGroupDestenation implements GroupDestenation< List<ServicesGroupInformation>>
{
	private List<ServicesGroupInformation> result = new ArrayList<ServicesGroupInformation>();
	public void add(ServicesGroupInformation information)
	{
		if(information.getParentId() == null)
			result.add(information);
	}

	public  List<ServicesGroupInformation> getResult()
	{
		return Collections.unmodifiableList(result);
	}
}
