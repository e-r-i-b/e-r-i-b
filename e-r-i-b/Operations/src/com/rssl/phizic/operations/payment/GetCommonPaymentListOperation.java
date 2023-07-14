package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.businessProperties.BusinessPropertyService;
import com.rssl.phizic.business.documents.FilterPaymentForm;
import com.rssl.phizic.business.documents.payments.PaymentHistoryHelper;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.query.QueryParameter;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.Calendar;
import java.util.List;

/**
 * @author Kosyakova
 * @ created 31.01.2007
 * @ $Author$
 * @ $Revision$
 */
public class GetCommonPaymentListOperation extends OperationBase implements ListEntitiesOperation
{
	private final static Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private final static BusinessPropertyService businessPropertyService = new BusinessPropertyService();

	private List<AccountLink>  accounts;
	private List<CardLink>  cards;
	private List<IMAccountLink> imaccounts;

	public Long getLoginId()
	{
		return getPersonData().getPerson().getLogin().getId();
	}

	public List<AccountLink> getAccounts () throws BusinessException, BusinessLogicException
	{
	    if (accounts==null)
	    {
		    accounts = getPersonData().getAccounts();
	    }
	    return accounts;
	}
	
	public List<CardLink> getCards () throws BusinessException, BusinessLogicException
	{
	    if (cards==null)
	    {
	        cards = getPersonData().getCards();
	    }
	    return cards;
	}

	/**
	 * ОМС клиента
	 * @return List<IMAccountLink> - список ОМС клиента
	 * @throws BusinessException
	 */

	public List<IMAccountLink> getIMAccounts () throws BusinessException, BusinessLogicException
	{
		if (imaccounts==null)
		{
			imaccounts = getPersonData().getIMAccountLinks();
		}
		return imaccounts;
	}

	public PersonData getPersonData()
	{
		return PersonContext.getPersonDataProvider().getPersonData();
	}

	/**
	 * Формируем список FilterPaymentForm (идентификаторы форм:псевдоним формы)
	 * @return список FilterPaymentForm
	 */
	public List<FilterPaymentForm> getFilterPaymentForms() throws BusinessException
	{
		return PaymentHistoryHelper.getFilterPaymentForms();
	}

	/**
	 * @return Дата, раньше которой не требуется показывать неподтвержденные платежи.
	 */
	@QueryParameter
	public Calendar getNotConfirmDocumentsDate()
	{
		try
		{
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -ConfigFactory.getConfig(PaymentsConfig.class).getClearNotConfirmDocumentsPeriod());
			return calendar;
		}
		catch (Exception e)
		{
			log.error("Ошибка получения настройки clearNotConfirmDocumentsPeriod", e);
			return null;
		}
	}

	/**
	 * @return Дата, раньше которой не требуется показывать платежи, ожидающие дополнительного подтверждения.
	 */
	@QueryParameter
	public Calendar getWaitConfirmDocumentsDate()
	{
		try
		{
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -ConfigFactory.getConfig(PaymentsConfig.class).getClearWaitConfirmDocumentsPeriod());
			return calendar;
		}
		catch (Exception e)
		{
			log.error("Ошибка получения настройки clearWaitConfirmDocumentsPeriod", e);
			return null;
		}
	}

	@Override public Query createQuery(String name)
	{
		Query query = super.createQuery(name);
		query.setListTransformer(new ClientHistoryListTransformer(getPersonData().createDocumentOwner()));
		return query;
	}
}

