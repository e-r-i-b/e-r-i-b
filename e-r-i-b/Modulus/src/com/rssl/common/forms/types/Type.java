package com.rssl.common.forms.types;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.parsers.FieldValueParser;

import java.util.List;
import java.io.Serializable;

/**
 * �������� ����
 * @author Evgrafov
 * @ created 13.02.2006
 * @ $Author: khudyakov $
 * @ $Revision: 50095 $
 */
public interface Type extends Serializable
{
	/**
	 * @return ��� ���� "string", "date" etc
	 */
	String getName();

	/**
	 * @return ������ �� �������� ��� ����
	 */
	FieldValueParser getDefaultParser();

	/**
	 * @return ��������� �� ��������� ��� ����
	 */
	List<FieldValidator> getDefaultValidators();

	/**
	 * @return ���������
	 */
	FieldValueFormatter getFormatter();
}
