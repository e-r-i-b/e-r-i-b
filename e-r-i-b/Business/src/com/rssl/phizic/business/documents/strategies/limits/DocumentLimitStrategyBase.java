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

		//���� ��������� ������ ��������� �� �����
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
		//�������� ��������� �� ��������� � ����������� �����
		doProcess(limitDocumentInfo, amountInfo);
	}

	/**
	 * ���������� ������������� ������������� ��������� ����� ������ ��� ������ ��������
	 *
	 * @param document ��������
	 * @return true - ��������� ������ �����������
	 * @throws BusinessException
	 */
	boolean needUse(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		try
		{
			//���� ����������, �� ��������� ����� �� ��� ���������
			if (DocumentHelper.isLongOffer(document))
			{
				return DocumentHelper.needUseLongOffer(document);
			}

			//��������� ������ �������� �� ������� �����
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
	 * ������� ������� ������ ����� �������
	 */
	protected void writeToJournal(LimitDocumentInfo limitDocumentInfo) throws BusinessException, BusinessLogicException
	{
		// ��������� ���������� �� �������
		for (Limit limit : limits)
			limitDocumentInfo.addLimitInfo(new LimitInfo(limit.getType(), limit.getRestrictionType(), limit.getGroupRisk() != null ? limit.getGroupRisk().getExternalId() : null));
	}

	boolean checkAccess(String operationKey, String serviceKey) throws BusinessException
	{
		return new OperationFactoryImpl(new RestrictionProviderImpl()).checkAccess(operationKey, serviceKey);
	}

	void doProcess(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		//��������� ����������� �� ������� ������
		doCheck(limits, document, amountInfo);

		//��������� ������ � ������
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
