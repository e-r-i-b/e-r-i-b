package com.rssl.phizic.operations.documents.templates;

import com.rssl.common.forms.ErrorsCollector;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.reminders.ReminderLink;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.operations.EditEntityOperation;

import java.util.Calendar;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 08.03.14
 * @ $Author$
 * @ $Revision$
 */
public interface EditTemplateDocumentOperation extends EditEntityOperation
{
	/**
	 * �������������
	 * @param source ������
	 */
	void initialize(TemplateDocumentSource source) throws BusinessException, BusinessLogicException;

	/**
	 * �������������
	 * @param recipientId ������������� ���������� �����
	 */
	void initialize(Long recipientId) throws BusinessException, BusinessLogicException;

	/**
	 * @return ������
	 */
	TemplateDocument getTemplate();
	/**
	 * @return ����������
	 */
	Metadata getMetadata();

	/**
	 * @return StateMachineExecutor
	 */
	StateMachineExecutor getExecutor();

	/**
	 * �������� ������� � �����
	 * @param formData ������
	 */
	void update(Map<String, Object> formData) throws BusinessLogicException, BusinessException;

	/**
	 * ������������� ������
	 */
	void edit() throws BusinessException, BusinessLogicException;

	/**
	 * �������� �������� �������
	 * @param name ��������
	 */
	void changeName(String name) throws BusinessException, BusinessLogicException;

	/**
	 * �������� ������ ������� � �����������
	 * @param reminderInfo ���������� � �����������
	 * @param residualDate ���� ����������� ������������� �����
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	void changeReminderInfo(ReminderInfo reminderInfo, Calendar residualDate) throws BusinessLogicException, BusinessException;

	/**
	 * ������� ������ � ���� ���
	 */
	void saveQuicklyCreatedTemplate() throws BusinessException, BusinessLogicException;

	/**
	 * @param transformInfo ���������� �� ��������������
	 * @param formInfo ���������� �� �����
	 * @param source �������� ������
	 * @return html
	 */
	String buildFormHtml(TransformInfo transformInfo, FormInfo formInfo, FieldValuesSource source) throws BusinessException;

	/**
	 * @return ������ ���������������� ����������
	 * @throws BusinessException
	 */
	String getMetadataPath() throws BusinessException;

	/**
	 * �������� ������ ��������������
	 * @param errorsCollector errorsCollector
	 * @param <E>
	 * @return ������
	 * @throws BusinessException
	 */
	public <E> ErrorsCollector<E> getTemplateErrors(ErrorsCollector<E> errorsCollector) throws BusinessException;
}
