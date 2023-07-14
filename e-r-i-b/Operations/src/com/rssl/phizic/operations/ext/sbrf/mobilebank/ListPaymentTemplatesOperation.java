package com.rssl.phizic.operations.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.service.filters.MobileBankTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.ReadyToPaymentTemplateFilter;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.PaymentTemplateShortcut;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Erkin
 * @ created 25.05.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция получения списка шаблонов платежей пользователя
 * (для отображения на странице "Создание SMS-шаблона на основе шаблона платежа")
 */
public class ListPaymentTemplatesOperation extends OperationBase
{
	private static final MobileBankBusinessService mobileBankBusinessService = new MobileBankBusinessService();

	private List<PaymentTemplateShortcut> templateShortcuts = new ArrayList<PaymentTemplateShortcut>();


	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализация операции
	 */
	public void initialize(String cardCode) throws BusinessException, BusinessLogicException
	{
		ActivePerson person = PersonHelper.getContextPerson();

		Card card = mobileBankBusinessService.getPersonCardByNumberCode(person.getLogin(), cardCode);
		if(card.getCardState() == CardState.blocked || card.getCardState() == CardState.delivery)
		{
			throw new BusinessLogicException("Вы не можете создать SMS-шаблон, потому что по Вашей карте запрещены платежные операции.");
		}

		List<TemplateDocument> templates = TemplateDocumentService.getInstance().getFiltered(person.asClient(), new ReadyToPaymentTemplateFilter(), new MobileBankTemplateFilter());
		if (CollectionUtils.isNotEmpty(templates))
		{
			for (TemplateDocument template : templates)
			{
				templateShortcuts.add(new PaymentTemplateShortcut(template));
			}
		}
	}

	/**
	 * Возвращает список шаблонов платежей текущего пользователя
	 * @return список шаблонов платежей
	 */
	public List<PaymentTemplateShortcut> findAll() throws BusinessException
	{
		return Collections.unmodifiableList(templateShortcuts);
	}
}
