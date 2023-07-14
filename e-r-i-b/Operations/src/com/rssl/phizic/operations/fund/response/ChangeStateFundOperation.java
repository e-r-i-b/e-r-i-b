package com.rssl.phizic.operations.fund.response;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fund.sender.FundSenderResponse;
import com.rssl.phizic.business.fund.sender.FundSenderResponseService;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 16.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Базовая операция изменения статуса обработки входящего запроса на сбор средств
 */
public abstract class ChangeStateFundOperation extends EditDocumentOperationBase
{
	protected static final FundSenderResponseService fundSenderResponseService = new FundSenderResponseService();

	private FundSenderResponse response;
	private BigDecimal sum;
	private String message;
	private Boolean closeConfirm;
	private Long fromResource;

	/**
	 * Инциализация операции
	 * @param externalId Идентификатор входящего запроса на сбор средств
	 * @param sum сумма
	 * @param message сообщение
	 * @param closeConfirm подтвержедно ли удовлетворение запроса, если запрос закрыт
	 * @param fromResource ресурс списания
	 */
	public void initialize(String externalId, BigDecimal sum, String message, Boolean closeConfirm, Long fromResource) throws BusinessException
	{
		if (StringHelper.isEmpty(externalId))
		{
			throw new IllegalArgumentException("Идентификатор входящего запроса на сбор средств не может быть null.");
		}
		response = fundSenderResponseService.getByExternalId(externalId);

		if (response == null)
		{
			throw new BusinessException("Входящий запрос на сбор средств по идентификатору " + externalId + " не найден.");
		}

		this.sum = sum;
		this.message = message;
		this.closeConfirm = closeConfirm;
		this.fromResource = fromResource;
	}

	/**
	 * @return название метода экшена для обработки
	 */
	public abstract String getStateMethodName();

	/**
	 * выполнить действие
	 */
	public abstract void execute() throws BusinessException, BusinessLogicException;

	protected FundSenderResponse getResponse()
	{
		return response;
	}

	protected BigDecimal getSum()
	{
		return sum;
	}

	protected String getMessage()
	{
		return message;
	}

	protected Boolean getCloseConfirm()
	{
		return closeConfirm;
	}

	protected Long getFromResource()
	{
		return fromResource;
	}
}
