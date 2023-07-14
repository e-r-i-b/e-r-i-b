package com.rssl.phizgate.messaging;

import java.util.Random;

/**
 * @author Krenev
 * @ created 08.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class RetailMessageGeneratorB extends RetailMessageGeneratorBase
{
	private static final Random random = new Random();

	protected String generateId()
	{
		return MessageIdGenerator.getId(random.nextInt(19));
	}
}
