package com.rssl.phizic.business.groups;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 16.11.2006 Time: 10:31:15 To change this template use
 * File | Settings | File Templates.
 */
public class DublicateGroupNameException extends BusinessLogicException
{
	DublicateGroupNameException()
	{
		super("Группа с таким именем и типом уже существует в текущем отделении");
	}
}
