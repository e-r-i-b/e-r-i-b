package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

/**
 * �������� ��� ������ ������� ������� � ������� ������� �������.
 * @author Dorzhinov
 * @ created 24.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class GetPaymentStateOperation extends OperationBase
{
    private static final BusinessDocumentService service = new BusinessDocumentService();
    private static final DocumentValidator validator = new IsOwnDocumentValidator();

	private BusinessDocument document;

	/**
	 * �������������
	 * @param id ������������� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(String id) throws BusinessException, BusinessLogicException
	{
		if(StringHelper.isEmpty(id))
            throw new BusinessException("�� ����� ������������� �������.");

        final Long idL;
        try {
            idL = Long.valueOf(id);
        } catch (NumberFormatException ignore) {
            throw new BusinessException("������������ id=" + id);
        }

        initialize(idL);
	}

	/**
	 * �������������
	 * @param id ������������� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("�� ����� ������������� �������.");
		}
		
		document = service.findById(id);
        if (document == null)
        {
			throw new ResourceNotFoundBusinessException("�� ������ �������� � ��������������� " + id, BusinessDocument.class);
        }

		validator.validate(document);  //��������� �����
	}

    /**
     * ��������� ���� ������� ������� �� �������������� �������
     * @return ��� ������� �������
     * @throws BusinessException
     */
    public String getPaymentState() throws BusinessException, BusinessLogicException
    {
        return document.getState().getCode();
    }

    /**
     * ��������� ������� ������� ������� �� �������������� �������
     * @return ������ ������� ������� ��� ������ ������ ���� �� �� ����������
     * @throws BusinessException
     */
    public String getTicketsInfo() throws BusinessException, BusinessLogicException
    {
        if(!(document instanceof JurPayment))
            throw new BusinessException("������������ ��� �������.");
        return StringHelper.getEmptyIfNull(((JurPayment) document).getTicketInfo());
    }

	/**
	 * ��������� ���� ������� ������� �� �������������� �������
	 * @param documentId ������������� �������
	 * @return ��� ������� �������
	 * @throws BusinessException
	 */
	public String getPaymentStateById(Long documentId) throws BusinessException
	{
		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();
		String state = service.getPaymentStateById(documentId, loginId);
		if (StringHelper.isEmpty(state))
			throw new BusinessException("�������� � �� " + documentId + " ��� ������������ � �� " + loginId + " �� ������");
		return state;
	}
}
