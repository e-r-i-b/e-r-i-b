package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.SecurityLogicException;

import java.util.List;
import java.util.ArrayList;

/**
 * @author egorova
 * @ created 12.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * В КЛАССЕ НЕ ДОЛЖНО БЫТЬ СОСТОЯНИЯ!!!!!!
 */
public class CompositeAthenticationCompleteAction implements AthenticationCompleteAction
{
	//тут все нормально, потому у клиентов с одной схемой входы одинаковые, а внутренности листа без состояния 
	public List<AthenticationCompleteAction> actions = new ArrayList<AthenticationCompleteAction>();

	public void addAction(AthenticationCompleteAction action)
	{
		actions.add(action);
	}

	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		if (actions.isEmpty())
			return;
		
		for (AthenticationCompleteAction action : actions)
		{
			action.execute(context);
		}
	}
}
