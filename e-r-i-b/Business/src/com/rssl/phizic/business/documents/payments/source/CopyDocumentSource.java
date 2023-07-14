package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;

import java.util.Collections;

/**
 * @author vagin
 * @ created 27.02.14
 * @ $Author$
 * @ $Revision$
 * �������� ������ ���������, ������������ �� ������ ������������� ���������
 */
public class CopyDocumentSource extends NewDocumentSource
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	/**
	 * ���������� ��������� ������ �������� �� ������ ������������� ���������
	 * @param paymentId id ������������� ���������, �� ������ �������� ��������� ����� ��������
	 * @param documentValidator ��������� ������� ������������ �������������� ������� ���������
	 * @param creationType �������� �������� - ���/��������/��������� ����������
	 */
	public CopyDocumentSource(Long paymentId, DocumentValidator documentValidator, CreationType creationType) throws BusinessException, BusinessLogicException
	{
		super(getSourceDocument(paymentId), documentValidator, creationType);
	}

	private static BusinessDocument getSourceDocument(Long paymentId) throws BusinessException
	{
		BusinessDocument document = businessDocumentService.findById(paymentId);
		//��� �������� ����� ������� ���������� �������� ����� ��������.
		if(document instanceof BusinessDocumentBase)
			((BusinessDocumentBase)document).setWriteDownOperations(Collections.EMPTY_LIST);
		return document;
	}
}
