package com.rssl.phizic.web.configure;

import com.rssl.phizic.config.PropertyCategory;

/**
 * ���� ������� ������
 * ���� ������������ ����������� ������ ������ �� ����� �������������� ��������
 * @author gladishev
 * @ created 05.02.14
 * @ $Author$
 * @ $Revision$
 */
enum ExternalSystemType
{
	ESB("���", PropertyCategory.Phizic),
	IPAS("iPas", PropertyCategory.Phizic),
	CSA("CSA", PropertyCategory.Phizic),
	CSA_FRONT("CSA Front", PropertyCategory.CSAFront),
	CSA_BACK("CSA Back", PropertyCategory.CSABack),
	RSA("����-����������", PropertyCategory.RSA);

	private String description;
	private PropertyCategory category;

	ExternalSystemType(String description, PropertyCategory category)
	{
		this.description = description;
		this.category = category;
	}

	public String getDescription()
	{
		return description;
	}

	PropertyCategory getCategory()
	{
		return category;
	}
}
