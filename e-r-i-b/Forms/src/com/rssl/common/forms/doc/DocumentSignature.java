package com.rssl.common.forms.doc;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 22.01.2007
 * @ $Author: emakarov $
 * @ $Revision: 9379 $
 */

public interface DocumentSignature extends Serializable
{
	/**
	 * @return ����� �������
	 */
	String getText();

	/**
	 * @param text ����� �������
	 */
	void setText(String text);

	/**
	 * @return ID �������� � ������� ���� ��������� �������
	 */
	String getOperationId();

	/**
	 * @param operationId ID �������� � ������� ���� ��������� �������
	 */
	void setOperationId(String operationId);

	/**
	 * @return ID ������ � ������� ���� ��������� �������
	 */
	String getSessionId();

	/**
	 * @param sessionId ID ������ � ������� ���� ��������� �������
	 */
	void setSessionId(String sessionId);

	Calendar getCheckDate();

	void setCheckDate(Calendar checkDate);
}