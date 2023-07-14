package com.rssl.phizic.test.web.mobile.payments;

import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.lang.reflect.InvocationTargetException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileJurPaymentAction extends TestMobileDocumentAction
{
	private static final String FORWARD_INIT = "Init";
	private static final String FORWARD_FIRST_STEP = "FirstStep";
	private static final String FORWARD_SECOND_STEP = "SecondStep";
	private static final String FORWARD_SELECT_PROVIDER = "SelectProvider";
	private static final String FORWARD_BILLING = "Billing";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response)
	{
		TestMobileJurPaymentForm form = (TestMobileJurPaymentForm) frm;
		String operation = form.getOperation();

		if (StringHelper.isEmpty(operation))
			return mapping.findForward(FORWARD_INIT);

		if ("init".equals(operation))
			return submitInit(mapping, form);

		if ("next".equals(operation))
			return submitFirstStep(mapping, form);

		return null;
	}

	private ActionForward submitInit(ActionMapping mapping, TestMobileDocumentForm form)
	{
		switch (send(form))
		{
			case 0:
			case 1:
				if (form.getDocument() != null)
					return mapping.findForward(FORWARD_SECOND_STEP);
				break;
		}
		return mapping.findForward(FORWARD_FIRST_STEP);
	}

	private ActionForward submitFirstStep(ActionMapping mapping, TestMobileJurPaymentForm form)
	{
		switch (send(form))
		{
			case 0:
				if (form.getResponse() != null)
				{
					VersionNumber versionNumber;
					try {
						versionNumber = VersionNumber.fromString(form.getVersion());
					} catch (MalformedVersionFormatException e) {
						throw new IllegalStateException(e);
					}

					if(versionNumber.ge(MobileAPIVersions.V5_00))
					{
						try
						{
							if (MethodUtils.invokeMethod(form.getResponse(), "getBillingPayments", null) != null)
								return mapping.findForward(FORWARD_SELECT_PROVIDER);
						}
						catch (NoSuchMethodException e)
						{
							throw new RuntimeException(e);
						}
						catch (IllegalAccessException e)
						{
							throw new RuntimeException(e);
						}
						catch (InvocationTargetException e)
						{
							throw new RuntimeException(e);
						}
					}
				}
				if (form.getDocument() != null)
					return mapping.findForward(FORWARD_SECOND_STEP);
				break;
			case 1:
				if (form.getDocument() != null)
					return mapping.findForward(FORWARD_SECOND_STEP);
				break;
		}

		return mapping.findForward(FORWARD_FIRST_STEP);
	}
}
