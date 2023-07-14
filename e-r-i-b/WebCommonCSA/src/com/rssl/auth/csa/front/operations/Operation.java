package com.rssl.auth.csa.front.operations;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;

/**
 * Интерфейс операции
 * @author niculichev
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */
public interface Operation
{
	void execute() throws FrontLogicException, FrontException;
}
