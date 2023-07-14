package com.rssl.phizic.auth.modes;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.auth.modes.ConfirmResponse;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 03.01.2007
 * @ $Author: emakarov $
 * @ $Revision: 9379 $
 */

public interface ConfirmResponseReader
{
	/**
	 * @param valuesSource �������� ����� ��� ������
	 */
	void setValuesSource(FieldValuesSource valuesSource);

	/**
	 * @return true == ������� ���������
	 */
	boolean read();

	/**
	 * @return ����� ���� read() == true
	 */
	ConfirmResponse getResponse();

	/**
	 * @return ������ ���� read() == false
	 */
	List<String> getErrors();

}
