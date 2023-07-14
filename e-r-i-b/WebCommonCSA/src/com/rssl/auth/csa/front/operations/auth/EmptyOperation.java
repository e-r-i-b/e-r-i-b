package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.Operation;

/**
 * @author niculichev
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EmptyOperation implements Operation
{
	public void execute() throws FrontLogicException, FrontException
	{
	}
}
