package com.rssl.phizic.operations.templates.offer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.business.template.offer.CreditOfferTemplateType;
import com.rssl.phizic.business.template.offer.Status;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * Операция редактирования шаблонов кредитных оферт
 *
 * @author Balovtsev
 * @since 05.06.2015.
 */
public class CreditOfferTemplateEditOperation<R extends Restriction> extends AbstractCreditOfferTemplateOperation<R> implements EditEntityOperation<CreditOfferTemplate, R>
{
	private CreditOfferTemplate template;

	/**
	 * Инициализация операции
	 *
	 * @param  id идентификатор редактируемой сущности. Если null создается новый шаблон
	 * @throws BusinessException
	 */
	public void init(Long id) throws BusinessException, BusinessLogicException
	{
		if (id == null || id == 0)
		{
			template = new CreditOfferTemplate(null, DateHelper.clearTime(Calendar.getInstance()), null, null, Status.INTRODUCED, CreditOfferTemplateType.ERIB);
		}
		else
		{
			template = TEMPLATE_SERVICE.find(id);
			checkAndThrow();
		}
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		TEMPLATE_SERVICE.save(template, getInstanceName());
		MultiBlockModeDictionaryHelper.updateDictionary(getEntity().getClass());
	}

	public CreditOfferTemplate getEntity() throws BusinessException, BusinessLogicException
	{
		return template;
	}

	private void checkAndThrow() throws BusinessException, BusinessLogicException
	{
		if (getEntity().getStatus() != Status.INTRODUCED)
		{
			throw new BusinessLogicException("credit.loan.offer.edit.unsupported");
		}
	}
}
