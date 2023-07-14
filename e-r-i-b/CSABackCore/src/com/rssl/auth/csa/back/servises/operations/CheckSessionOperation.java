package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.Session;

/**
 * @author krenev
 * @ created 17.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class CheckSessionOperation extends SessionContextOperation
{
	public CheckSessionOperation() {}

	public CheckSessionOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	/**
	 * ������������������� ��������
	 * @param sid ������������ ������
	 */
	public void initialize(String sid) throws Exception
	{
		setSid(sid);
		//��������� � ���� �� ����� - �����.
	}

	/**
	 * ��������� ������ �� ����������(����)
	 * @return �����������
	 */
	public Session execute() throws Exception
	{
		return getSession();
	}
}
