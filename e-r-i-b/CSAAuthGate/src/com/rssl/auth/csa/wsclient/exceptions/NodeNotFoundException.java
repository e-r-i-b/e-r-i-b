package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author osminin
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Исключение о поиске блока
 */
public class NodeNotFoundException extends BackLogicException
{
	/**
	 * ctor
	 * @param message сообщение
	 */
	public NodeNotFoundException(String message)
	{
		super(message);
	}
}
