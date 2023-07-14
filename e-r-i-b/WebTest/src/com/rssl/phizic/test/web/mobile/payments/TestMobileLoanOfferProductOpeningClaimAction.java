package com.rssl.phizic.test.web.mobile.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 16.03.12
 * Time: 12:06
 * Тэстилка оформления заявок на открытие вклада
 */
public class TestMobileLoanOfferProductOpeningClaimAction extends TestMobileDocumentAction
{
	protected static final String FORWARD_INIT = "Init";
	protected static final String FORWARD_STAGE = "Stage";
	protected static final String FORWARD_CONDITIONS_AND_REGISTRATION = "ConditionsAndRegistration";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestMobileDocumentForm form = (TestMobileDocumentForm) frm;
		String operation = form.getOperation();

		if (StringHelper.isEmpty(operation))
			return mapping.findForward(FORWARD_INIT);

		return submitInit(mapping, form);
	}

	protected ActionForward submitInit(ActionMapping mapping, TestMobileDocumentForm form)
	{
		int stateCode = send(form);
		if (stateCode == 0 || stateCode == 1)
		{
			try
			{
				Object response = form.getResponse();
				if (response != null)
				{
					Class responseClass = response.getClass();
					Method getLoanOfferStage = responseClass.getMethod("getLoanOfferStage");
					if (getLoanOfferStage.invoke(response) != null)
						return mapping.findForward(FORWARD_STAGE);

					Method getInitialData = responseClass.getMethod("getInitialData");
					Object initialData = getInitialData.invoke(response);
					if (initialData != null) {
						Class initialDataClass = initialData.getClass();
						Method getLoanOffer = initialDataClass.getMethod("getLoanOffer");
						if (getLoanOffer.invoke(initialData) != null)
							return mapping.findForward(FORWARD_CONDITIONS_AND_REGISTRATION);
					}

					Method getDocument = responseClass.getMethod("getDocument");
					Object document = getDocument.invoke(response);
					if (document != null) {
						Class documentClass = document.getClass();
						Method getLoanOfferDocument = documentClass.getMethod("getLoanOfferDocument");
						if (getLoanOfferDocument.invoke(document) != null)
							return mapping.findForward(FORWARD_CONDITIONS_AND_REGISTRATION);
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
