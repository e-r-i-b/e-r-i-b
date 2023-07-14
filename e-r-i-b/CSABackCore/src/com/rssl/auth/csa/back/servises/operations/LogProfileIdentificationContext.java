package com.rssl.auth.csa.back.servises.operations;

import com.rssl.phizic.common.types.csa.IdentificationType;

/**
 * @author tisov
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 * ��������� ��������� ������������� ��� �����������
 */
public interface LogProfileIdentificationContext
{
	/**
	 * �������� �������������
	 * @return
	 */
	public String getIdentificationParam();

	/**
	 * ��� �������������
	 * @return
	 */
	public IdentificationType getIdentificationType();

}
