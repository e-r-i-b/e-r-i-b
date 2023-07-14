package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.ProfileLockedException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.exceptions.TooManyCSAConnectorsException;
import com.rssl.auth.csa.back.servises.ProfileLock;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.operations.UserLogonOperation;
import sun.font.FontManager;

import java.util.List;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 * ����������� �� ���� �� ���������������� �������
 */
public class ProfileLockedRestriction implements OperationRestriction<UserLogonOperation>
{
	private static final ProfileLockedRestriction INSTANCE = new ProfileLockedRestriction();
	private static final String UNBOUNDED_ERROR_MESSAGE = "������ � ������� �������� ��������������� � %1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS. �������: %3$s.";
	private static final String BOUNDED_ERROR_MESSAGE = "������ � ������� �������� ��������������� � %1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS �� %2$td.%2$tm.%2$tY %2$tH:%2$tM:%2$tS. �������: %3$s.";

	/**
	 * @return ������� �����������
	 */
	public static ProfileLockedRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(UserLogonOperation operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("�������� �� ����� ���� null");
		}
		List<ProfileLock> locks = operation.getProfile().getActiveLocks();
		if (locks.isEmpty())
		{
			return;
		}
		//����� ������ �� ����������
		ProfileLock profileLock = locks.get(0);
		throw new ProfileLockedException(String.format(profileLock.getTo() == null ? UNBOUNDED_ERROR_MESSAGE : BOUNDED_ERROR_MESSAGE, profileLock.getFrom(), profileLock.getTo(), profileLock.getReason()));
	}
}