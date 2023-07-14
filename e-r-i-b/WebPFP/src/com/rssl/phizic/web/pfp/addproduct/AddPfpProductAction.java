package com.rssl.phizic.web.pfp.addproduct;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PortfolioProduct;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.operations.pfp.AddPfpProductOperation;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import com.rssl.phizic.web.pfp.PassingPFPActionBase;
import com.rssl.phizic.web.pfp.PassingPFPFormInterface;
import com.rssl.phizic.web.pfp.PersonalFinanceProfileUtils;
import com.rssl.phizic.web.util.MoneyFunctions;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 22.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class AddPfpProductAction extends PassingPFPActionBase
{
	private static final String BACK_TO_PORTFOLIO = "BackToPortfolio";


	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("button.next","save");
		return map;
	}

	protected AddPfpProductOperation getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		AddPfpProductForm frm = (AddPfpProductForm) form;
		AddPfpProductOperation operation = getOperation(AddPfpProductOperation.class, form);
		if(frm.getEditProductId() != null)
			operation.initializeForEdit(frm.getPortfolioId(), frm.getEditProductId());
		else
			operation.initialize(frm.getPortfolioId(), frm.getProductId(), frm.getDictionaryProductTypeEnum());
		return operation;
	}

	protected void updateForm(PassingPFPFormInterface passingPFPForm, EditPfpOperationBase editPfpOperation) throws BusinessException, BusinessLogicException
	{
		AddPfpProductForm form = (AddPfpProductForm) passingPFPForm;
		AddPfpProductOperation operation = (AddPfpProductOperation) editPfpOperation;
		PortfolioProduct editProduct = operation.getEditProduct();
		if (editProduct != null)
			form.updateEditProductFields(editProduct);
		updateFormData(form, operation);
	}

	protected ActionForward getStartForward(PassingPFPFormInterface form, EditPfpOperationBase operation) throws BusinessException
	{
		if (operation == null)
			return getPortfolioEditForward((AddPfpProductForm) form, getCurrentMapping());

		ActionMessages validateMessages = validateAdditionalFormData((AddPfpProductOperation) operation,false);
		if(!validateMessages.isEmpty())
			saveMessages(currentRequest(), validateMessages);
		return super.getStartForward(form, operation);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AddPfpProductForm frm = (AddPfpProductForm) form;
		AddPfpProductOperation operation = null;
		try
		{
			operation = getOperation(frm);
			MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
			Form editForm = frm.getEditForm(operation.getProfile(),operation.getPortfolio(),operation.getProduct());
			FormProcessor<ActionMessages, ?> processor = createFormProcessor(mapValuesSource,editForm);
			ActionMessages validateMessages = validateAdditionalFormData(operation,true);
			if(!validateMessages.isEmpty() || !processor.process())
			{
				saveErrors(request, validateMessages);
				saveErrors(request, processor.getErrors());
				updateFormData(frm,operation);
				return mapping.findForward(FORWARD_START);
			}
			operation.addProduct(processor.getResult());
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			if (operation != null)
				updateFormData(frm,operation);
			return mapping.findForward(FORWARD_START);
		}

		return getPortfolioEditForward(frm,mapping);
	}

	private ActionForward getPortfolioEditForward(AddPfpProductForm frm, ActionMapping mapping)
	{
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(BACK_TO_PORTFOLIO).getPath());
		redirect.addParameter("id", frm.getId());
		redirect.addParameter("portfolioId", frm.getPortfolioId());
		return redirect;
	}

	/**
	 *
	 * @param operation
	 * @param forSave
	 * @return
	 * @throws BusinessException
	 */
	private ActionMessages validateAdditionalFormData(AddPfpProductOperation operation, boolean forSave) throws BusinessException
	{
		ActionMessages msgs = new ActionMessages();
		PersonPortfolio personPortfolio = operation.getPortfolio();
		Money minAmountForProduct = PersonalFinanceProfileUtils.getMinAmount(operation.getProduct(),operation.getProductType(), personPortfolio.getType());
		if(minAmountForProduct == null)
			return msgs;
		Money freeAmountInPortfolio = personPortfolio.getFreeAmount();
		if(minAmountForProduct.compareTo(freeAmountInPortfolio) > 0)
		{
			StringBuilder sb = new StringBuilder();
			if(forSave)
			{
				sb.append("Вы не можете выполнить данную операцию, так как сумма средств в данном портфеле меньше суммы минимального взноса по продукту ");
				sb.append(MoneyFunctions.getFormatAmount(minAmountForProduct));
			}
			else
			{
				sb.append("Обратите внимание! В данном портфеле у Вас осталось свободных средств на сумму ");
				sb.append(MoneyFunctions.getFormatAmount(freeAmountInPortfolio));
				sb.append(" Эта сумма меньше минимального взноса по продукту ");
				sb.append(MoneyFunctions.getFormatAmount(minAmountForProduct));
			}
			msgs.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage(sb.toString(),false));
		}

		return msgs;
	}

	private void updateFormData(AddPfpProductForm form, AddPfpProductOperation operation)
	{
		form.setProduct(operation.getProduct());
		form.setPortfolio(operation.getPortfolio());
		form.setProfile(operation.getProfile());
	}
}
