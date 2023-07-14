package com.rssl.phizic.security.password;

import com.rssl.phizic.auth.LoginBlock;
import com.rssl.phizic.auth.BlockingReasonType;

import java.util.Comparator;

/**
 * @author Egorova
 * @ created 27.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoginBlocksComparator implements Comparator<LoginBlock>
{
	public int compare(LoginBlock o1, LoginBlock o2)
	{
		BlockingReasonType o1type = o1.getReasonType();
		BlockingReasonType o2type = o2.getReasonType();
		if (o1type.equals(o2type))
			return 0;
		if (o1type.equals(BlockingReasonType.employee))
			return 1;
		if (o2type.equals(BlockingReasonType.employee))
			return -1;
		if (o1type.equals(BlockingReasonType.wrongLogons))
			return 1;
		return -1;
	}
}
