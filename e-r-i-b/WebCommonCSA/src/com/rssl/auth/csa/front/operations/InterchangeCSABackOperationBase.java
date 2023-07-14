package com.rssl.auth.csa.front.operations;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * @author osminin
 * @ created 01.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовая операция общения с ЦСА Бэк
 */
public abstract class InterchangeCSABackOperationBase implements Operation
{
	public void execute() throws FrontLogicException, FrontException
	{
		Document response = doRequest();
		if (response != null)
			processResponse(response);
	}

	/**
	 * Инициализация операции
	 * @param data данные
	 * @throws FrontLogicException
	 * @throws FrontException
	 */
	public void initialize(Map<String, Object> data) throws FrontLogicException, FrontException
	{
		checkRestrict(data);
	}

	protected abstract void checkRestrict(Map<String, Object> data) throws FrontException, FrontLogicException;

	protected abstract Document doRequest() throws FrontException, FrontLogicException;

	protected abstract void processResponse(Document response) throws FrontException, FrontLogicException;
}
