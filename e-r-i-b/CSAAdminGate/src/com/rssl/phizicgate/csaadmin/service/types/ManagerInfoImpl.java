package com.rssl.phizicgate.csaadmin.service.types;

import com.rssl.phizic.gate.employee.ManagerInfo;
import com.rssl.phizicgate.csaadmin.service.generated.ManagerInfoType;

/**
 * @author akrenev
 * @ created 17.07.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ������������ ���������
 */

public class ManagerInfoImpl implements ManagerInfo
{
	private String name;
	private String email;
	private String phone;

	/**
	 * �����������
	 * @param managerInfo ���������� � ���������
	 */
	public ManagerInfoImpl(ManagerInfoType managerInfo)
	{
		name = managerInfo.getName();
		email = managerInfo.getEmail();
		phone = managerInfo.getPhone();
	}

	public String getName()
	{
		return name;
	}

	public String getEmail()
	{
		return email;
	}

	public String getPhone()
	{
		return phone;
	}
}
