package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.business.fields.StringFieldValidationParameter;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.dictionaries.provider.CheckValidatorOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class CheckValidatorAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String QUERY_NAME = "getEntity";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.check", "check");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CheckValidatorForm frm = (CheckValidatorForm) form;
		CheckValidatorOperation operation = createOperation(CheckValidatorOperation.class);
		Query query = operation.createQuery(QUERY_NAME);
		query.setParameter("id", frm.getId());
		Object[] entity = query.executeUnique();
		frm.setFieldName((String) entity[0]);
		frm.setFieldCode((String) entity[1]);
		frm.setValidatorExpression(((StringFieldValidationParameter) entity[2]).getValue());
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward check(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CheckValidatorForm frm = (CheckValidatorForm) form;
		CheckValidatorOperation operation = createOperation(CheckValidatorOperation.class);

		frm.setCheckingResult(
				operation.check(frm.getValidatorExpression(), frm.getCheckingValue())
		);

		addLogParameters(new BeanLogParemetersReader("Проверка валидатора", frm));

		return mapping.findForward(FORWARD_START);
	}
}
