package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.groups.Group;

/**
 * @author rydvanskiy
 * @ created 26.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class SystemGroupRemoveException extends BusinessLogicException
{

	SystemGroupRemoveException(Group group)
	{
		super("Невозможно удалить системную группу "+ group.getName() +".");
	}

}
