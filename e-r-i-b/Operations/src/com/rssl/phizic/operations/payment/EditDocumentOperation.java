package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.ErrorsCollector;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.business.operations.Operation;

import java.util.Map;

/**
 * @author Evgrafov
 * @ created 13.12.2005
 * @ $Author: rtishcheva $
 * @ $Revision: 83828 $
 */

public interface EditDocumentOperation extends Operation
{
	/**
	 * @param source ������ ��� ������ ��������� � ����������
	 * @throws BusinessException
	 */
	void initialize(DocumentSource source) throws BusinessException, BusinessLogicException;

	/**
	 * @param source ������ ��� ������ ��������� � ����������
	 * @param additionalFieldValuesSource �������������� �������� ������ �����.
	 * @throws BusinessException
	 */
	void initialize(DocumentSource source, FieldValuesSource additionalFieldValuesSource) throws BusinessException, BusinessLogicException;

	/**
	 * ��������� ��������
	 * @return id ������������ ���������
	 * @throws BusinessException
	 */
	Long save() throws BusinessException, BusinessLogicException;

	/**
	 * ��������� ��� ������������ ���������
	 * @return id ������������ ���������
	 * @throws BusinessException
	 */
	Long edit() throws BusinessException, BusinessLogicException;

	/**
	 * ��������� ��������
	 * @return id ������������ ���������
	 * @throws BusinessException
	 */
	Long saveDraft() throws BusinessException, BusinessLogicException;

	/**
	 * ��������� �������� ��� ������
	 * @param templateName ��� �������
	 * @return id ������������ �������
	 * @throws BusinessException
	 */
	Long saveAsTemplate(String templateName) throws BusinessException, BusinessLogicException;

	/**
	 * ��������� �������� � ��������� ���������
	 */
	void saveAsInitial() throws BusinessException, BusinessLogicException;

	/**
	 * ��������� �������� � ��������� � ������ TM
	 */
	public void doWaitTM() throws BusinessException, BusinessLogicException;

	/**
	 * @return ����������
	 */
	Metadata getMetadata();

	/**
	 * @return ������ ���������������� ����������
	 */
	String getMetadataPath() throws BusinessException;

	/**
	 * �������� �������� ���������� �������
	 * @param formDataMap ��������� ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	void updateDocument(Map<String, Object> formDataMap) throws BusinessException, BusinessLogicException;

	/**
	 * @return ������������� ��������.
	 */
	BusinessDocument getDocument();

	/**
	 * @return �������� ������ �������������� ���������.
	 */
	FieldValuesSource getFieldValuesSource()  throws BusinessException;

	/**
	 * @return �������� �������� ����� ��� ��������� ����� ������� (can be null)
	 */
	FieldValuesSource getValidateFormFieldValuesSource();

	/**
	 * ������� ������������� �������� ���������� ����������
	 * @param formDataMap ������ ��� ���������� ���������(null ���� ��������� �� ����)
	 */
	void makeLongOffer(Map<String, Object> formDataMap) throws BusinessException, BusinessLogicException;

	/**
	 * @return �������� �� ������������� �������� ���������� ����������
	 */
	boolean isLongOffer();

	/**
	 * ��������� ��������� (�������� ����������� �������) � ������� ����� ����� ����� ��������� ���������� �� �������
	 * ������ ����� ��������� �������� ��� ������ �� ���������
	 * @param errorsCollector ������� ������
	 * @return ������� ������ � ����������� �� ��������� �������� ��� ������.
	 */
	<E> ErrorsCollector<E> getDocumentErrors(ErrorsCollector<E> errorsCollector) throws BusinessException;

	/**
	 * @return �������� ������ ��������� � �������� ������ ���������. ��� null � ������ ������������� ��������� �������.
	 */
	MachineState getDocumentSate();

	/**
	 * ������ �������� ����� �������
	 * @param fieldValuesSource - �������� �������� �����
	 * @param webRoot
	 * @param resourceRoot
	 * @param isDefaultShow - true - ����� ����� ��-���������, false - � ��������� �������(��������, ��� ������ ���������, ���������� ������ � �.�.)
	 * @param skinUrl Skin URL ��� �������� ������������
     * @return HTML-�������� �������� ����� �������
	 */
	String buildFormHtml(FieldValuesSource fieldValuesSource, String webRoot, String resourceRoot, String skinUrl, boolean isDefaultShow) throws BusinessException;

	/**
	 * ������ �������� ��������� ��� ���� API v2.x
	 * @param fieldValuesSource - �������� �������� �����
	 * @return XML-�������� �������
	 */
	String buildMobileXml(FieldValuesSource fieldValuesSource) throws BusinessException;

	/**
	 * ������ �������� ��������� socialAPI
	 * @param fieldValuesSource - �������� �������� �����
	 * @return XML-�������� �������
	 */
	String buildSocialXml(FieldValuesSource fieldValuesSource) throws BusinessException;

	/**
	 * ������ �������� ��������� ��� ATM ����������
	 * @param fieldValuesSource - �������� �������� �����
	 * @return XML-�������� �������
	 */
	String buildATMXml(FieldValuesSource fieldValuesSource) throws BusinessException;	

	/**
	 * @return �������� ������� (�������)
	 * @throws BusinessException
	 */
	Person getPerson() throws BusinessException;

	/**
	 * �������� � ������������ ���������
	 * @return true - ������������ ����������� (��������� ��������), false - ���
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	boolean upgrade() throws BusinessLogicException, BusinessException;

	/**
	 * ���������� ������ �� ������ ��������� ����� � ������ �� ������
	 * @param accountClaimId - ������������� ������ �� �������� �����
	 * @throws BusinessException
	 */
	void saveNewAccountInLoanClaim(Long accountClaimId) throws BusinessException;
}
