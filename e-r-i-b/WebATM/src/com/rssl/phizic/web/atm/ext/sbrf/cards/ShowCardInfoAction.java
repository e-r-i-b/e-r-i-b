package com.rssl.phizic.web.atm.ext.sbrf.cards;

/**
 * @ author: filimonova
 * @ created: 24.05.2010
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.card.GetCardInfoOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.MoneyBoxListOperation;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.atm.cards.ShowCardInfoATMForm;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.common.forms.doc.CreationType;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.security.AccessControlException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowCardInfoAction extends ViewActionBase
{
	private static final Long MAX_COUNT_OF_TRANSACTIONS = 10L;

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		GetCardInfoOperation cardInfoOperation = createOperation(GetCardInfoOperation.class);
		cardInfoOperation.initialize(frm.getId());

		return cardInfoOperation;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			return 	super.start(mapping,form,request,response);
		}
		catch (ResourceNotFoundBusinessException ex)
		{
			log.error(ex);
			throw new AccessControlException(ex.getMessage());
		}
	}

	protected void updateCommonData(GetCardInfoOperation operation, ShowCardInfoATMForm form)
			throws BusinessException, BusinessLogicException
	{
		CardLink cardLink = operation.getEntity();
		form.setCardLink(cardLink);
		//Получение списка копилок, приязанных к карте
		if (checkAccess(MoneyBoxListOperation.class, "MoneyBoxManagement"))
		{
			MoneyBoxListOperation moneyBoxListOperation = createOperation(MoneyBoxListOperation.class, "MoneyBoxManagement");
			//Получаем все копилки, привязанные к карте, во всех статусах
			moneyBoxListOperation.initialize(cardLink, true);
			form.setMoneyBoxes(moneyBoxListOperation.getData());
		}
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowCardInfoATMForm frm = (ShowCardInfoATMForm) form;
		GetCardInfoOperation cardInfoOperation = (GetCardInfoOperation) operation;

		updateCommonData(cardInfoOperation, frm);
		setCards(frm);

		try
		{
			frm.setCardAbstract(getCardAbstract(frm.getId(), frm));
			frm.setTemplates(getDocumentTemplates(frm.getCardLink().getNumber()));
			frm.setField("cardName",frm.getCardLink().getName());
		}
		catch (DataAccessException ex)
		{
			throw new BusinessException(ex);
		}

		setDefaultFilter(frm);
	}

	private void setDefaultFilter(ShowCardInfoATMForm frm)
	{
		if (frm.getFilter("fromAbstract") == null)
		{
			Calendar startDate = new GregorianCalendar();
			startDate.add(Calendar.MONTH, -1);
			frm.setFilter("fromAbstract", startDate.getTime());
		}
		if (frm.getFilter("toAbstract") == null)
			frm.setFilter("toAbstract", new GregorianCalendar().getTime());
		if (StringHelper.isEmpty((String) frm.getFilter("typeAbstract")))
			frm.setFilter("typeAbstract", "month");
	}

	protected CardAbstract getCardAbstract(Long cardId, ShowCardInfoATMForm form) throws BusinessException, BusinessLogicException, DataAccessException
	{
		return getCardAbstract(MAX_COUNT_OF_TRANSACTIONS, form, createGetCardAbstractOperation(cardId));
	}

	protected CardAbstract getCardAbstract(Long count, ShowCardInfoATMForm form, GetCardAbstractOperation operation) throws BusinessException, BusinessLogicException, DataAccessException
	{
		CardAbstract cardAbstract = operation.getCardAbstract(count).get(operation.getCard());
		form.setAbstractMsgError(operation.getCardAbstractMsgErrorMap().get(operation.getCard()));
		form.setBackError(operation.isBackError());
		return cardAbstract;
	}

	protected GetCardAbstractOperation createGetCardAbstractOperation(Long id) throws BusinessException
	{
		GetCardAbstractOperation operation = createOperation(GetCardAbstractOperation.class);
		operation.initialize(id);
		return operation;
	}

	private List<TemplateDocument> getDocumentTemplates(String payerAccount) throws BusinessException, BusinessLogicException
	{
		if (checkAccess(ListTemplatesOperation.class))
		{
			ListTemplatesOperation templateOperation = createOperation(ListTemplatesOperation.class);
			templateOperation.initialize(CreationType.atm, payerAccount);
			return templateOperation.getEntity();
		}
		return Collections.emptyList();
	}

	private void setCards(ShowCardInfoATMForm form) throws BusinessException, BusinessLogicException
	{
		// карта, по которой просматривается детальная информация
		CardLink cardLink = form.getCardLink();

		GetCardsOperation operationCards = createOperation(GetCardsOperation.class);
		//Получаем ВСЕ карты пользователя
   		List<CardLink> personCardLinks = operationCards.getPersonCardLinks();

		List<CardLink> result = new ArrayList<CardLink>();
		result.addAll(personCardLinks);
		//Если карта, по которой запрашиваем инфу основная, получаем все дополнительные к ней карты
		Card card = cardLink.getCard();
		if (!card.isVirtual() && card.isMain())
		{
			List<CardLink> additionalCards = operationCards.getAdditionalCards(Collections.singletonList(cardLink));
			form.setAdditionalCards(additionalCards);
			if (additionalCards != null && result != null)
				result.removeAll(additionalCards);
		}

		if (result != null)
		{
			result.remove(cardLink);
			form.setOtherCards(result);
		}
	}
}