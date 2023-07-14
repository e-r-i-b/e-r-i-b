package com.rssl.phizic.web.common.client.cards;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.CardType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.account.EditExternalLinkOperation;
import com.rssl.phizic.operations.card.GetCardInfoOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.ermb.EditProductAliasOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.MoneyBoxListOperation;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Детальная информация по карте
 * @author lukina
 * @ created 26.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowCardInfoAction extends ShowCardInfoBaseAction
{
	private static final String CARD_SMS_ALIAS_FIELD = "clientSmsAlias";
	private static final String CARD_AUTO_SMS_ALIAS_FIELD = "autoSmsAlias";
	private static final String CARD_NAME_FIELD = "cardName";

	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = new HashMap<String, String>();
	    keyMap.put("button.saveCardName", "saveCardName");
	    keyMap.put("button.saveClientSmsAlias", "saveClientSmsAlias");
        return keyMap;
    }

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
		ShowCardInfoForm frm = (ShowCardInfoForm) form;
		GetCardInfoOperation cardInfoOperation = (GetCardInfoOperation) operation;

		CardLink link = cardInfoOperation.getEntity();
		frm.setCardLink(link);

		if (checkAccess(MoneyBoxListOperation.class, "MoneyBoxManagement"))
		{
			MoneyBoxListOperation moneyBoxListOperation = createOperation(MoneyBoxListOperation.class, "MoneyBoxManagement");
			moneyBoxListOperation.initialize(link, true);
			frm.setMoneyBoxesTargets(moneyBoxListOperation.getMoneyBoxesTargets());
		}

		CardInfoFormHelper.setAdditionalCards(frm, createOperation(GetCardsOperation.class));

		frm.setField(CARD_NAME_FIELD, link.getName());

		// если клиент имеет ЕРМБ-профиль, то надо установить значения смс-алиасов
		if (ErmbHelper.isERMBConnectedPerson())
		{
			String autoSmsAlias = link.getAutoSmsAlias();
			frm.setField(CARD_AUTO_SMS_ALIAS_FIELD, autoSmsAlias);
			frm.setField(CARD_SMS_ALIAS_FIELD, StringUtils.defaultIfEmpty(link.getErmbSmsAlias(), autoSmsAlias));
		}
	}

    protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
    {
        GetCardInfoOperation cardInfoOperation = createOperation(GetCardInfoOperation.class);
        cardInfoOperation.initializeNew(frm.getId());

        if (cardInfoOperation.isUseStoredResource())
        {
            saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) cardInfoOperation.getEntity().getCard()));
        }

        return cardInfoOperation;
	}

	public ActionForward saveCardName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowCardInfoForm frm = (ShowCardInfoForm) form;
		Long linkId = frm.getId();
		EditExternalLinkOperation operation = createOperation(EditExternalLinkOperation.class);
		operation.initialize(linkId, CardLink.class);

		FieldValuesSource valuesSource = getSaveCardNameFieldValuesSource(frm);

		Form editForm = ShowCardInfoForm.EDIT_CARD_NAME_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editForm);

		if(processor.process())
		{
            Map<String, Object> result = processor.getResult();
            operation.saveLinkName((String) result.get(CARD_NAME_FIELD));
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
        return forwardSaveCardName(mapping, form, request, response);
    }

    protected MapValuesSource getSaveCardNameFieldValuesSource(ShowCardInfoForm form)
    {
        return new MapValuesSource(form.getFields());
    }

    protected ActionForward forwardSaveCardName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return start(mapping, form, request, response);
    }

    public ActionForward saveClientSmsAlias(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowCardInfoForm frm = (ShowCardInfoForm) form;
		Long linkId = frm.getId();
		EditProductAliasOperation operation = createOperation(EditProductAliasOperation.class);
		operation.initialize(linkId, CardLink.class);

		Map<String, Object> map = frm.getFields();
		FieldValuesSource valuesSource = new MapValuesSource(map);

		Form editForm = frm.editCardAliasForm();
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editForm);

		if(processor.process())
		{
			operation.saveProductSmsAlias((String)frm.getField(CARD_SMS_ALIAS_FIELD));
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return start(mapping, form, request, response);
	}
}
