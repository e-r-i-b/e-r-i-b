package com.rssl.phizic.web.common.socialApi.cards;

import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.card.GetCardInfoOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.cards.ShowCardInfoAction;
import com.rssl.phizic.web.common.client.cards.ShowCardInfoForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Детальная информация по карте
 * @ author: filimonova
 * @ created: 24.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ShowCardInfoMobileAction extends ShowCardInfoAction
{
    private static final String FORWARD_SAVE_NAME = "SaveName";

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("saveName", "saveCardName");
        return map;
    }

    protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowCardInfoMobileForm frm = (ShowCardInfoMobileForm) form;
		GetCardInfoOperation cardInfoOperation = (GetCardInfoOperation) operation;

        CardLink cardLink = cardInfoOperation.getEntity();
        frm.setCardLink(cardLink);
		frm.setMainCardId(cardInfoOperation.getMainCardId(cardLink));
    }

    protected MapValuesSource getSaveCardNameFieldValuesSource(ShowCardInfoForm form)
    {
        ShowCardInfoMobileForm frm = (ShowCardInfoMobileForm) form;
        Map<String,Object> filter = new HashMap<String,Object>();
        filter.put("cardName", frm.getCardName());
        return new MapValuesSource(filter);
    }

    protected ActionForward forwardSaveCardName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return mapping.findForward(FORWARD_SAVE_NAME);
    }
}