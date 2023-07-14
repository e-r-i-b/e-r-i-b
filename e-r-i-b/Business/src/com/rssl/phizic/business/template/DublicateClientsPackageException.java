package com.rssl.phizic.business.template;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 16.02.2007
 * Time: 14:43:41
 * To change this template use File | Settings | File Templates.
 */
public class DublicateClientsPackageException extends BusinessLogicException
{
	public DublicateClientsPackageException()
	{
		super("Этот пакет уже подключен");
	}
}
