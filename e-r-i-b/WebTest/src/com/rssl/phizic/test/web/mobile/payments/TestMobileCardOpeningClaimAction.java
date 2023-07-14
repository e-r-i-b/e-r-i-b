package com.rssl.phizic.test.web.mobile.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.lang.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 29.02.12
 * Time: 10:50
 */
public class TestMobileCardOpeningClaimAction  extends TestMobileDocumentAction
{
    protected static final String FORWARD_INIT = "Init";
    protected static final String INCOME_STEP = "IncomeStep";
    protected static final String FORWARD_LOAN_CARD_OFFER_STEP = "LoanCardOfferStep";
    protected static final String FORWARD_LOAN_CARD_PRODUCT_STEP = "LoanCardProductStep";
    protected static final String FORWARD_LOAN_PRODUCT = "LoanCardProduct";
    protected static final String FORWARD_LOAN_OFFER = "LoanCardOffer";

    public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        TestMobileCardOpeningClaimForm form = (TestMobileCardOpeningClaimForm) frm;
        String operation = form.getOperation();

		if (StringHelper.isEmpty(operation))
			return mapping.findForward(FORWARD_INIT);

		return submitInit(mapping, form);
    }

    protected ActionForward submitInit(ActionMapping mapping, TestMobileDocumentForm form)
    {
        int stateCode = send(form);
        if (stateCode == 0)
        {
            try
			{
				Object response = form.getResponse();
				if (response != null)
				{
                    if (MethodUtils.invokeMethod(response, "getLoanCardOfferStage", null) != null)
						return mapping.findForward(FORWARD_LOAN_CARD_OFFER_STEP);

                    if (MethodUtils.invokeMethod(response, "getLoanCardProductStage", null) != null)
						return mapping.findForward(FORWARD_LOAN_CARD_PRODUCT_STEP);

                    if (MethodUtils.invokeMethod(response, "getIncomeStage", null) != null)
						return mapping.findForward(INCOME_STEP);

                    Object initialData = MethodUtils.invokeMethod(response, "getInitialData", null);
					if (initialData != null) {
                        if (MethodUtils.invokeMethod(initialData, "getLoanCardProduct", null) != null)
							return mapping.findForward(FORWARD_LOAN_PRODUCT);
                        if (MethodUtils.invokeMethod(initialData, "getLoanCardOffer", null) != null)
							return mapping.findForward(FORWARD_LOAN_OFFER);
					}

                    Object document = MethodUtils.invokeMethod(response, "getDocument", null);
					if (document != null) {
						if (MethodUtils.invokeMethod(document, "getLoanCardProductDocument", null) != null)
							return mapping.findForward(FORWARD_LOAN_PRODUCT);
                        if (MethodUtils.invokeMethod(document, "getLoanCardOfferDocument", null) != null)
							return mapping.findForward(FORWARD_LOAN_OFFER);
					}
				}
			} catch (NoSuchMethodException e) {
				throw new RuntimeException("Некорректный тип документа", e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Некорректный тип документа", e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException("Ошибка выполнения метода", e);
			}
        }
        return  mapping.findForward(FORWARD_INIT);
    }
}
