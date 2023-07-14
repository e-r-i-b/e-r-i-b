package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.DenyCbCodeOperationException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.Operation;

import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 * ������� ����� �����������, � ������� ��
 */

public abstract class CbCodeRestrictionBase<T extends Operation> implements OperationRestriction<T>
{
	public final void check(T operation) throws RestrictionException
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("�������� �� ����� ���� null");
		}
		Pattern denyPattern = getTBDenyPattern();
		if (denyPattern == null)
		{
			return;
		}

		if (denyPattern.matcher(operation.getCbCode()).matches())
		{
			throw new DenyCbCodeOperationException(getRestrictionViolatedMessage());
		}
	}

	/**
	 * @return ��������� � ����������� ����������.
	 */
	public abstract String getRestrictionViolatedMessage();

	/**
	 * @return ���������� ��������� ����������� ��. null, ���� ������� ���
	 */
	public abstract Pattern getTBDenyPattern();
}
