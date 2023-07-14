package com.rssl.phizicgate.iqwave.messaging;

import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * @author Krenev
 * @ created 26.05.2010
 * @ $Author$
 * @ $Revision$
 * ��������� ���������� � ����������.
 */
public interface MessageInfoContainer
{
	/**
	 * @return ��� ���������
	 */
	String getMessageTag();

	/**
	 * @return ������������ ���������
	 */
	String getMessageId();

	/**
	 * @return �� �� ���������
	 */
	Calendar getMessageDate();

	/**
	 * @return ����������� ���������
	 */
	String getFromAbonent();

	/**
	 * @return ������������ ���������
	 */
	String getParentMessageId();

	/**
	 * @return �� �� ���������
	 */
	Calendar getParentMessageDate();

	/**
	 * @return ����������� ���������
	 */
	String getParentFromAbonent();

	/**
	 * @return ��������� body
	 */
	Document getBodyContent();

	/**
	 * @return ��� ������. ��� ���� ������ ���
	 */
	String getErrorCode();

	/**
	 * @return �������� ������. ��� ���� ������ ���
	 */
	String getErrorDescription();
}
