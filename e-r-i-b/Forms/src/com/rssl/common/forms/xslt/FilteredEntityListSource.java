package com.rssl.common.forms.xslt;

import com.rssl.common.forms.FormException;

import javax.xml.transform.Source;
import java.util.Map;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 31.01.2006
 * @ $Author: omeliyanchuk $
 * @ $Revision: 8314 $
 */

public interface FilteredEntityListSource extends Serializable
{
	/**
	 * @param params ��������� ������ (�������).
	 * ���� - ��� ���� �������, �������� - �������� �������
	 * @param selectedKeys ��������� �����
	 * @return XML �������� ���������� ��������� ���������
	 */
	Source getList(Map<String, Object> params, String[] selectedKeys) throws FormException;

	/**
	 * @param params ��������� ������ (�������).
	 * ���� - ��� ���� �������, �������� - �������� �������
	 * @return XML �������� ���������� ������� ������
	 */
	Source getFilter(Map<String, Object> params);

}
