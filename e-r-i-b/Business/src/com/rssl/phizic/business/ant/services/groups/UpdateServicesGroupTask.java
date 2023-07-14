package com.rssl.phizic.business.ant.services.groups;

import com.rssl.phizic.utils.test.SafeTaskBase;

/**
 * @author akrenev
 * @ created 21.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���� ���������� ����� �������� ���� �������
 */

public class UpdateServicesGroupTask extends SafeTaskBase
{
	@Override
	public void safeExecute() throws Exception
	{
		log("�������� ���������� ������ ��������");
		new ServicesGroupLoader().load();
		log("��������� ���������� ������ ��������");
	}

	public UpdateServicesGroupTask clone() throws CloneNotSupportedException
	{
		return (UpdateServicesGroupTask) super.clone();
	}
}
