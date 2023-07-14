package com.rssl.phizic.business.documents.metadata;

import com.rssl.common.forms.FormException;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 20.11.2006
 * @ $Author: khudyakov $
 * @ $Revision: 58441 $
 */

public interface ExtendedMetadataLoader extends Serializable
{
	/**
	 * ��������� ����������� ������ � �����
	 * ������ ����� ������������ ��� ��������� �������� ���� ��� ���� �� ��������� �� ���������� � ������� ������ ����������.
	 * @param metadata ������������ ����������
	 * @param fieldSource �������� �������� "���������������/����������������" ����������� ����:
	 * ��������, ��� ����������� �������� �������� ������ ��������� ����������� � ���������� �����.
	 * @return ����������� ������
	 */
	Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException, BusinessLogicException;

	/**
	 * �������� ���� ��� ������������� ���������.
	 * ����������� �������� ����� ��������� ����� ���� ����������.
	 * �� �������� com.rssl.phizic.gate.payments.systems.PaymentSystemPayment#getExtendedFields()
	 * @param metadata ������������ ����������
	 * @param document ��������, ���������� ����������� ���������� ��� �������� ����.
	 * @return ����������� ����������
	 */
	Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException, BusinessLogicException;

	/**
	 * �������� ���������� �� ������� ���������
	 * @param metadata ������������ ����������
	 * @param template ������ ���������
	 * @return ����������� ����������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	Metadata load(Metadata metadata, TemplateDocument template) throws BusinessException, BusinessLogicException;

	/**
	 * �������� ���������� �� ��������� � ������� ���������
	 * @param metadata ������������ ����������
	 * @param document ��������
	 * @param template ������ ���������
	 * @return ����������� ����������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException;

	/**
	 * ����, ���������������� ����������� ���������� ��� ��������� ��������� ������.
	 * ���� ���� ����������, �� � load'� ������ ���������� ���������� ������.
	 * ������ ����������� ���������� ����� �������� �� ��������� ����������(�������� ���� ����� ������������ �������),
	 * � ������ ������ ��������������� ����������� ����������� ��� ��������� ���� �� �����(������������ ������� ����� ����� �������� ������ �������� ��� ��������� ����)
	 * � ������ ������ ���������� ���������� null. �������� null �������������� � ���, ��� ���� ���� ������� ������ ������.
	 * @param fieldSource �������� �������� ��� ������������ �����.
	 * @return ���� ��� null
	 */
	String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException;
}