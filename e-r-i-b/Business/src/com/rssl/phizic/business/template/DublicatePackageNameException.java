package com.rssl.phizic.business.template;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 10.02.2007
 * Time: 14:51:04
 * To change this template use File | Settings | File Templates.
 */
public class DublicatePackageNameException extends BusinessLogicException
{
	public DublicatePackageNameException()
	{
		super("Пакет с таким именем уже существует в текущем отделении");
	}
}
