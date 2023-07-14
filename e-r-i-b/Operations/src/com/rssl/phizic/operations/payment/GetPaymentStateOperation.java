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
 * Операция для опроса статуса платежа и статуса выпуска билетов.
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
	 * Инициализация
	 * @param id идентификатор платежа
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(String id) throws BusinessException, BusinessLogicException
	{
		if(StringHelper.isEmpty(id))
            throw new BusinessException("Не задан идентификатор платежа.");

        final Long idL;
        try {
            idL = Long.valueOf(id);
        } catch (NumberFormatException ignore) {
            throw new BusinessException("Некорректный id=" + id);
        }

        initialize(idL);
	}

	/**
	 * Инициализация
	 * @param id идентификатор платежа
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Не задан идентификатор платежа.");
		}
		
		document = service.findById(id);
        if (document == null)
        {
			throw new ResourceNotFoundBusinessException("Не найден документ с идентификатором " + id, BusinessDocument.class);
        }

		validator.validate(document);  //проверяем права
	}

    /**
     * Получение кода статуса платежа по идентификатору платежа
     * @return Код статуса платежа
     * @throws BusinessException
     */
    public String getPaymentState() throws BusinessException, BusinessLogicException
    {
        return document.getState().getCode();
    }

    /**
     * Получение статуса выпуска билетов по идентификатору платежа
     * @return Статус выпуска билетов или пустая строка если он не установлен
     * @throws BusinessException
     */
    public String getTicketsInfo() throws BusinessException, BusinessLogicException
    {
        if(!(document instanceof JurPayment))
            throw new BusinessException("Некорректный тип платежа.");
        return StringHelper.getEmptyIfNull(((JurPayment) document).getTicketInfo());
    }

	/**
	 * Получение кода статуса платежа по идентификатору платежа
	 * @param documentId Идентификатор платежа
	 * @return Код статуса платежа
	 * @throws BusinessException
	 */
	public String getPaymentStateById(Long documentId) throws BusinessException
	{
		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();
		String state = service.getPaymentStateById(documentId, loginId);
		if (StringHelper.isEmpty(state))
			throw new BusinessException("Документ с ИД " + documentId + " для пользователя с ИД " + loginId + " не найден");
		return state;
	}
}
