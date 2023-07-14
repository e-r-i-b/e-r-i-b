package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 20.09.2012
 * @ $Author$
 * @ $Revision$
 * »сключение, сигнализирующее о превышении допустимого количества мобильных коннекторов 
 */
public class TooManyMobileConnectorsException extends RestrictionException
{
	public TooManyMobileConnectorsException(Long profileId)
	{
		super("ƒл€ профил€ " + profileId + " превышено количество количество зарегистрированных мобильных устройств.");
	}
}
