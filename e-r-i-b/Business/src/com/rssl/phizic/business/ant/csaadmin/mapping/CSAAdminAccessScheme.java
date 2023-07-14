package com.rssl.phizic.business.ant.csaadmin.mapping;

import com.rssl.phizic.business.schemes.AccessScheme;

/**
 * @author akrenev
 * @ created 10.12.2013
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��� �����
 */

@SuppressWarnings("ALL")
public class CSAAdminAccessScheme
{
	private Long externalId;
	private String name;
	private String category;
	private boolean CAAdminScheme;
	private boolean VSPEmployeeScheme;
	private boolean mailManagement;

	/**
	 * �����������
	 */
	public CSAAdminAccessScheme(){}

	/**
	 * �����������
	 * @param scheme - ����� �������� ������
	 */
	public CSAAdminAccessScheme(AccessScheme scheme)
	{
		this.name = scheme.getName();
		this.category = scheme.getCategory();
		this.CAAdminScheme = scheme.isCAAdminScheme();
		this.VSPEmployeeScheme = scheme.isVSPEmployeeScheme();
		this.mailManagement = scheme.isMailManagement();
	}
}
