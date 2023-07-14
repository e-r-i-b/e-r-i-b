package com.rssl.phizic.web.client.loans;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.LoanClaim;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.phizic.business.dictionaries.offices.loans.LoanOfficeService;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.kinds.LoanKindService;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.loans.claims.LoanClaimOperation;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Krenev
 * @ created 11.01.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class LoanClaimActionBase extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_NEXT = "Next";
	private static final String FORWARD_EDIT = "Edit";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.next", "next");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanClaimOperation operation = createOperation(LoanClaimOperation.class);
		LoanClaimForm frm = (LoanClaimForm) form;
		operation.initialize();
		List<LoanClaim> list = operation.createQuery("getDraftList").executeList();
		if (list.isEmpty() || (frm.getForce())){
			updateForm(frm);
			return mapping.findForward(FORWARD_START);
		}
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(mapping.findForward(FORWARD_EDIT).getPath()).addParameter("id", list.get(0).getId().toString());
		return new ActionForward(urlBuilder.getUrl(), true);
	}

	private void updateForm(LoanClaimForm form) throws BusinessException
	{
		List<LoanOffice> loanOffises = buildLoanOffisesList();
		form.setLoanOffices(loanOffises);
		Map<String, LoanKind> loanKinds = buildLoanKindsMap();
		form.setLoanKinds(loanKinds);
		Map<String, LoanProduct> loanProducts = buildLoanProductsMap();
		form.setLoanProducts(loanProducts);
	}

	protected abstract Map<String, LoanProduct> buildLoanProductsMap() throws BusinessException;


	private Map<String, LoanKind> buildLoanKindsMap() throws BusinessException
	{
		Map<String, LoanKind> kinds = new HashMap<String, LoanKind>();
		LoanKindService loanKindService = new LoanKindService();
		for (LoanKind kind : loanKindService.getAll())
		{
			kinds.put(kind.getId().toString(), kind);
		}
		return kinds;
	}

	private List<LoanOffice> buildLoanOffisesList() throws BusinessException
	{
		List<LoanOffice> offices = new ArrayList<LoanOffice>();
		LoanOfficeService loanOfficeService = new LoanOfficeService();
		for (LoanOffice office : loanOfficeService.getAll())
		{
			offices.add(office);
		}
		return offices;
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanClaimForm      frm          = (LoanClaimForm) form;
		MapValuesSource fieldsSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> processor    = createFormProcessor(fieldsSource, LoanClaimForm.FORM);
		ActionForward                    actionForward;

		if ( processor.process() )
		{
		    Map<String, Object> result      = processor.getResult();
		    UrlBuilder urlBuilder = new UrlBuilder();
		    urlBuilder.setUrl( mapping.findForward(FORWARD_NEXT).getPath() );

			Map<String, String> parameters = new HashMap<String, String>();

			parameters.put("kind", (String) result.get("kind"));
			parameters.put("product", (String) result.get("product"));
			parameters.put("office", (String) result.get("office"));
			urlBuilder.addParameters(parameters);

			addLogParameters( new CompositeLogParametersReader(
									new SimpleLogParametersReader("Первый параметр", "kind",    result.get("kind")),
									new SimpleLogParametersReader("Второй параметр", "product", result.get("product")),
									new SimpleLogParametersReader("Третий параметр", "office",  result.get("office"))
							));

		    actionForward = new ActionForward(urlBuilder.getUrl(), true);
		}
		else
		{
		    saveErrors(request, processor.getErrors());
		    actionForward = start(mapping, form, request, response);
		}

		return actionForward;
	}
}
