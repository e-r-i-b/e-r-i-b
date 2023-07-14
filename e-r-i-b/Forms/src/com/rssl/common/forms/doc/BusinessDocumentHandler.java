package com.rssl.common.forms.doc;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;

import java.io.Serializable;

/**
 * @author Roshka
 * @ created 13.10.2006
 * @ $Author$
 * @ $Revision$
 */
@PublicDefaultCreatable
public interface BusinessDocumentHandler<SO extends StateObject> extends Serializable
{
	/**
	 * ���������� ��������
	 * @param document ��������
	 * @param stateMachineEvent
	 * @throws DocumentLogicException ����������� �������� ��������, ����� ��������� ������
	 */
	void process(SO document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException;

	/**
	 * ���������� �������� ���������
	 * @param name ���
	 * @param value ��������
	 */
	void setParameter(String name, String value);

	/**
	 * �������� ��������
	 * @param name ��� ���������
	 * @return ��������
	 */
	String getParameter(String name);
}
