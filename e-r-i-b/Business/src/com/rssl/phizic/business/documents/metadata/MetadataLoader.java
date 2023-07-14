package com.rssl.phizic.business.documents.metadata;

import com.rssl.common.forms.FormException;

/**
 * @author Evgrafov
 * @ created 10.11.2006
 * @ $Author: khudyakov $
 * @ $Revision: 49377 $
 */

public interface MetadataLoader
{
	/**
	 * ��������� ���������� �� ����� �����
	 * @param formName ��� �����
	 * @return ����������
	 */
	Metadata load(String formName) throws FormException;
}
