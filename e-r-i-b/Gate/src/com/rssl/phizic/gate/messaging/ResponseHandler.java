package com.rssl.phizic.gate.messaging;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author Roshka
 * @ created 13.11.2006
 * @ $Author$
 * @ $Revision$
 */
public interface ResponseHandler
{
	String getMessageId();

	String getResponceTag();
	
	boolean isSuccess();

	boolean isVoid();

	public void throwException() throws GateException, GateLogicException;

	public Object getBody();

	void reset();

	/**
	 * @return код ошибки
	 */
	String getErrorCode();

	/**
	 * @return тектс ошибки
	 */
	public String getErrorText();
}