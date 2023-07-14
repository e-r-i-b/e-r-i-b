package com.rssl.phizic.web.client.userprofile.promo;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.WrongPromoCodeLogicException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesMessage;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.promo.InsertPromoCodesSystemOperation;
import com.rssl.phizic.operations.promo.RemovePromoCodesSystemOperation;
import com.rssl.phizic.operations.promo.ShowPromoCodesSystemOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Просмотр промокодов клиента.
 *
 * @author sergunin
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ViewClientPromoCodesAction extends OperationalActionBase
{
    private static final String ADD_OR_REMOVE = "AddOrRemove";
    private static final String OLD_START = "oldStart";

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> map = super.getKeyMethodMap();
        map.put("start", "start");
        map.put("button.insert", "insert");
        map.put("button.remove", "remove");
        return map;
    }

    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        InsertPromoCodesSystemOperation operation =  createOperation(InsertPromoCodesSystemOperation.class,  "ClientPromoCode");
        ViewClientPromoCodesForm frm = (ViewClientPromoCodesForm) form;

        Map map = new HashMap();
        map.put("clientPromo", frm.getClientPromo());
        FormProcessor processor = createFormProcessor(new MapValuesSource(map), ViewClientPromoCodesForm.EDIT_FORM);
        PromoCodesDepositConfig promoCodesDepositConfig = ConfigFactory.getConfig(PromoCodesDepositConfig.class);
        try {
            if(processor.process())
            {
                Map<String,Object> result = processor.getResult();
                operation.initializeInsert((String)result.get("clientPromo"));
                operation.save();
            }
            else {
                frm.setErrorMessage(promoCodesDepositConfig.getPromoCodesMessagesMap().get("MSG03"));
            }
        }
        catch (WrongPromoCodeLogicException e) {
            log.error("Ошибка при добавлении промо-кода: " + frm.getClientPromo(), e);

            Calendar promoBlockUntil = PersonContext.getPersonDataProvider().getPersonData().getPromoBlockUntil();
            if (promoBlockUntil == null)
                frm.setErrorMessage(e.getPromoCodesMessage());
            else
            {
                String dateText = DateHelper.formatDateToStringOnPattern(promoBlockUntil, "HH:mm dd.MM.yyyy");
                PromoCodesMessage msg011 = promoCodesDepositConfig.getPromoCodesMessagesMap().get("MSG011").clone();
                msg011.setText(String.format(msg011.getText(), dateText));
                frm.setErrorMessage(msg011);
                PromoCodesMessage msg12 = promoCodesDepositConfig.getPromoCodesMessagesMap().get("MSG012").clone();
                msg12.setText(String.format(msg12.getText(), dateText));
                frm.setError12Message(msg12);
            }
        }

        return mapping.findForward(ADD_OR_REMOVE);
    }


    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        RemovePromoCodesSystemOperation operation = createOperation(RemovePromoCodesSystemOperation.class, "ClientPromoCode");
        ViewClientPromoCodesForm frm = (ViewClientPromoCodesForm) form;
        try
        {
            operation.initializeRemove(frm.getId());
            operation.save();
        }
        catch (BusinessLogicException e) {
            log.error("Ошибка при удалении промо-кода: " + frm.getId(), e);
        }
        setFormInfo(form);

        if (PersonHelper.availableNewProfile())
            return mapping.findForward(FORWARD_START);
        return mapping.findForward(OLD_START);
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        setFormInfo(form);
        if (PersonHelper.availableNewProfile())
            return mapping.findForward(FORWARD_START);
        return mapping.findForward(OLD_START);
	}

    private void setFormInfo(ActionForm form) throws Exception
    {
        ShowPromoCodesSystemOperation operation = createOperation(ShowPromoCodesSystemOperation.class, "ShowClientPromoCodeList");
        ViewClientPromoCodesForm frm = (ViewClientPromoCodesForm) form;

        Long loginId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();

        operation.initializeShow(loginId);
        frm.setActivePromoCodes(operation.getActiveClientPromoCode());
        frm.setArchivePromoCodes(operation.getArchiveClientPromoCode());
        PromoCodesDepositConfig promoCodesDepositConfig = ConfigFactory.getConfig(PromoCodesDepositConfig.class);

        frm.setMessageNine(promoCodesDepositConfig.getPromoCodesMessagesMap().get(String.format(PromoCodesDepositConfig.PROMO_CODES_MSG, 9)));
        frm.setMessageTen(promoCodesDepositConfig.getPromoCodesMessagesMap().get(String.format(PromoCodesDepositConfig.PROMO_CODES_MSG, 10)));

        frm.setMessageSeven(promoCodesDepositConfig.getPromoCodesMessagesMap().get(String.format(PromoCodesDepositConfig.PROMO_CODES_MSG, 7)));

    }
}
