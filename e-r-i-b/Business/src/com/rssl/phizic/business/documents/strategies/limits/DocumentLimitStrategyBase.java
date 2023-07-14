package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;

import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.limits.process.LimitProcessorsFactory;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.limits.users.*;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author khudyakov
 * @ created 10.04.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class DocumentLimitStrategyBase implements DocumentLimitStrategy
{
	protected static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	protected static final LimitService limitService = new LimitService();

	private Limit currentLimit;
	private Money availableAmount;
	private Money accumulatedAmount;

	private BusinessDocument document;
	protected List<Limit> limits = new ArrayList<Limit>();

	public DocumentLimitStrategyBase(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		this.document = document;

		//если учитывать данную стратегию не нужно
		if (needUse(document))
		{
			limits = getStrategyLimits();
		}
	}

	public Limit getCurrentLimit()
	{
		return currentLimit;
	}

	public Money getAvailableAmount()
	{
		return availableAmount;
	}

	public Money getAccumulatedAmount()
	{
		return accumulatedAmount;
	}

	public boolean check(ClientAccumulateLimitsInfo amountInfo) throws BusinessException
	{
		try
		{
			doCheck(limits, document, amountInfo);
			return true;
		}
		catch (BusinessLogicException ignore)
		{
			return false;
		}
	}

	public boolean checkAndThrow(ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		doCheck(limits, document, amountInfo);
		return true;
	}

	public void process(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		//проверка документа по вхождению в разрешенную сумму
		doProcess(limitDocumentInfo, amountInfo);
	}

	/**
	 * Определяем необходимость использования стратегии учета лимита для данной операции
	 *
	 * @param document документ
	 * @return true - стратегия должна учитываться
	 * @throws BusinessException
	 */
	boolean needUse(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		try
		{
			//если автоплатеж, то проверяем нужно ли его учитывать
			if (DocumentHelper.isLongOffer(document))
			{
				return DocumentHelper.needUseLongOffer(document);
			}

			//учитываем только операции на внешние счета
			if (TypeOfPayment.EXTERNAL_PAYMENT_OPERATION != document.getTypeOfPayment())
			{
				return false;
			}

			return !document.isPaymentByConfirmTemplate();
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	protected List<Limit> getStrategyLimits() throws BusinessException
	{
		return Collections.emptyList();
	}

	BusinessDocument getDocument()
	{
		return document;
	}

	/**
	 * Создаем журнале записи учета лимитов
	 */
	protected void writeToJournal(LimitDocumentInfo limitDocumentInfo) throws BusinessException, BusinessLogicException
	{
		// заполняем информацию по лимитам
		for (Limit limit : limits)
			limitDocumentInfo.addLimitInfo(new LimitInfo(limit.getType(), limit.getRestrictionType(), limit.getGroupRisk() != null ? limit.getGroupRisk().getExternalId() : null));
	}

	boolean checkAccess(String operationKey, String serviceKey) throws BusinessException
	{
		return new OperationFactoryImpl(new RestrictionProviderImpl()).checkAccess(operationKey, serviceKey);
	}

	void doProcess(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		//проверяем ограничение по лимитам вцелом
		doCheck(limits, document, amountInfo);

		//добавляем записи в журнал
		writeToJournal(limitDocumentInfo);
	}

	void doCheck(List<Limit> limits, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		for (Limit limit : limits)
		{
			try
			{
				LimitProcessorsFactory.getProcessor(limit).process(limit, document, amountInfo);
				if (RestrictionType.isByAmount(limit))
				{
					accumulatedAmount = amountInfo.getAccumulateAmount(limit);
					availableAmount = limit.getAmount().sub(accumulatedAmount);
				}
			}
			catch (BusinessDocumentLimitException e)
			{
				currentLimit = limit;
				accumulatedAmount = e.getAmount();

				throw e;
			}
		}
	}
}
