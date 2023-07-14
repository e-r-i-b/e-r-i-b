package com.rssl.phizic.payment;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.phizic.engine.Engine;

/**
 * @author Erkin
 * @ created 31.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��������
 */
public interface PaymentEngine extends Engine
{

	/**
	 * @return �����, � ������� �������� ����� (never null)
	 */
	CreationType getDocumentCreationType();

	/**
	 * @return ��������� ��������� ����������, ������������ ��� ���� �������� ������ (never null)
	 */
	ValidationStrategy getDocumentValidationStrategy();
}
