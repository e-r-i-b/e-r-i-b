package com.rssl.phizic.operations.ext.sbrf.payment.templates;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.limits.OverallAmountPerDayTemplateLimitStrategy;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.documents.templates.strategies.limits.BlockTemplateOperationLimitStrategy;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.operations.access.PersonLoginSource;
import com.rssl.phizic.operations.documents.templates.ViewTemplateOperationBase;

/**
 * @ author: Vagin
 * @ created: 16.05.2013
 * @ $Author
 * @ $Revision
 * �������� ��������� ������� ������� � ��� ����������
 */
public class EmployeeViewTemplateOperation extends ViewTemplateOperationBase
{
	private ActivePerson person;

	/**
	 * ������������� ��������.
	 * @param personId - ������������� �������.
	 * @param source - ����.
	 * @throws BusinessException
	 */
	public void initialize(Long personId, TemplateDocumentSource source) throws BusinessException, BusinessLogicException
	{
		super.initialize(source);
		person = new PersonLoginSource(personId).getPerson();
	}

	public ActivePerson getPerson()
	{
		return person;
	}

	/**
	 * �������������� ���������� �������� �������.
	 */
	@Transactional
	public void recover() throws BusinessException, BusinessLogicException
	{
		executor.fireEvent(new ObjectEvent(DocumentEvent.RECOVERDELETED, "employee"));
		TemplateDocumentService.getInstance().addOrUpdate(getEntity());
	}

	/**
	 * ������������� �������-������� � �������������.
	 */
	@Transactional
	public void confirm() throws BusinessException, BusinessLogicException
	{
		new BlockTemplateOperationLimitStrategy(template).checkAndThrow(null);
		new OverallAmountPerDayTemplateLimitStrategy(template).checkAndThrow(null);

		executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, "employee"));
		TemplateDocumentService.getInstance().addOrUpdate(getEntity());
	}
}
