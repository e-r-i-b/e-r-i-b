package com.rssl.phizic.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.common.types.Money;

/**
 * ��������� ��������
 *
 * @author khudyakov
 * @ created 17.11.14
 * @ $Author$
 * @ $Revision$
 */
public interface AbstractPaymentTransfer extends StateObject
{
	/**
	 * ���������� ��� ������� �� PersonContext-� � ������� "��� �������� �."
	 * ���� �������� ����, �� ������������ ������ ������
	 * @return ��� ������� � ������� "��� �������� �."
	 */
	String getFormattedPersonName() throws DocumentException;

	/**
	 * @return ����� �������
	 */
	Money getExactAmount();
}
