package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.operations.ConfirmableOperation;
import com.rssl.phizic.security.SecurityLogicException;
import org.w3c.dom.Document;

import java.io.Serializable;
import javax.xml.transform.Source;

/**
 * @author Roshka
 * @ created 18.05.2006
 * @ $Author$
 * @ $Revision$
 */
public interface ConfirmFormOperation extends ConfirmableOperation, Serializable
{
	/**
	 * ���������������� ��������
	 * @param source �������� ���������
	 */
	void initialize(DocumentSource source) throws BusinessException, BusinessLogicException;

	/**
	 * @return ���������� ���������
	 */
	Metadata getMetadata();

	/**
	 * @return ������ ���������������� ����������
	 */
	String getMetadataPath() throws BusinessException;

	/**
	 * ������� Source � ������� �����
	 * @return ������ �����;
	 */
	Source createFormData() throws BusinessException;

	/**
	 * �������� ��������� �� ��������� (�������� � ������).
	 * @throws SecurityLogicException - ������ ������������� (������� �������� ������, �������� ������� etc)
	 * @throws BusinessLogicException - ���� �������� �������� ��-�� ������� ����������� ������������� ������
	 */
	void confirm() throws BusinessException, SecurityLogicException, BusinessLogicException;

	/**
	 * @return XML ������������� �����
	 * @throws BusinessException
	 */
    Document createDocumentXml() throws BusinessException;

}
