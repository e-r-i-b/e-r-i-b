package com.rssl.phizic.security.password;

import com.rssl.phizic.auth.*;

import java.util.Set;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Egorova
 * @ created 27.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class UserBlocksValidator
{
	private static final SecurityService securityService = new SecurityService();
	/**
	 *
	 * @param login- ����� ������������, ��� �������� ��������� ����������
	 * @param reasonType - ������� ���������� (���������, ��� ����������. ���������)
	 * @return true - ���� ����� ���������� ��� ��� (��� ��� ���������)
	 *         false - ���� ��� ���������� �������� ���������� ������ ����
	 */
	public boolean checkIfBlockOfThisTypeDoesntExist(CommonLogin login, BlockingReasonType reasonType)
	{
		List<LoginBlock> blocks = securityService.getBlocksForLogin(login,new GregorianCalendar().getTime(), null);

		if(!blocks.isEmpty())
		{
			for (LoginBlock block: blocks)
			{
			//������, ��� ���������� �������� ���������� ������ �� ���� (�� ��� �� �������)
				return !block.getReasonType().equals(reasonType);
			}
		}
	    return true;
	}

	public boolean unpossibleUnlock(String state, CommonLogin login, BlockingReasonType reasonType)
	{
		return (!checkIfBlockOfThisTypeDoesntExist(login, reasonType) &&  state.equals("A"));
	}
}
