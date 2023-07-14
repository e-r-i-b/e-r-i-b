package com.rssl.phizic.csaadmin.business.login;

import java.util.Comparator;

/**
 * @author mihaylov
 * @ created 15.11.13
 * @ $Author$
 * @ $Revision$
 */
public class LoginBlocksComparator implements Comparator<LoginBlock>
{
	private static final LoginBlocksComparator INSTANCE = new LoginBlocksComparator();

	public static LoginBlocksComparator getInstance()
	{
		return INSTANCE;
	}

	public int compare(LoginBlock loginBlock1, LoginBlock loginBlock2)
	{
		BlockType blockType1 = loginBlock1.getReasonType();
		BlockType blockType2 = loginBlock2.getReasonType();
		if (blockType1 == blockType2)
			return 0;
		if (blockType1 == BlockType.employee)
			return 1;
		if (blockType2 ==BlockType.employee)
			return -1;
		if (blockType1 == BlockType.wrongLogons)
			return 1;
		return -1;
	}
}
