package com.rssl.phizic.web.actions.payments.forms.extendedLoanClaims;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntitySubType;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;
import com.rssl.phizic.business.receptiontimes.TimeMatching;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.SettingsHelper;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.web.actions.payments.forms.EditPaymentAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rtischeva
 * @ created 07.07.15
 * @ $Author$
 * @ $Revision$
 */
public class AsyncAccountOpeningClaimEditAction extends EditPaymentAction
{
	private static final DepositProductService depositProductService = new DepositProductService();
	private static final ReceptionTimeService receptionTimeService = new ReceptionTimeService();

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.remove("button.next");
		map.remove("button.prev");
		map.remove("button.saveAsDraft");
		map.remove("makeLongOffer");
		map.remove("button.makeLongOffer");
		map.remove("button.remove");
		map.remove("button.edit");
		map.remove("button.search");
		map.remove("back");
		map.remove("button.edit_template");
		map.remove("button.makeAutoTransfer");
		map.remove("afterAccountOpening");
		map.remove("button.changeConditions");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Map<String, Object> formData = new HashMap<String, Object>();

		LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);

		Long depositType = Long.valueOf(loanClaimConfig.getNewOpeningAccountDepositType());
		Long depositGroup;
		Long depositSubType = 1L;
		Long buyAmount = 0L;
		String toResourceCurrency = "RUB";

		formData.put("depositType", depositType);
		formData.put("depositSubType", depositSubType);
		formData.put("buyAmount", buyAmount);
		formData.put("toResourceCurrency", toResourceCurrency);
		formData.put("fromExtendedLoanClaim", true);
		formData.put(PaymentFieldKeys.EXACT_AMOUNT_FIELD_NAME, AbstractPaymentDocument.DESTINATION_FIELD_EXACT_AMOUNT_ATTRIBUTE_VALUE);

		boolean useCasNsi = SettingsHelper.isUseCasNsiDictionaries();
		if (useCasNsi)
		{
			depositGroup = Long.valueOf(loanClaimConfig.getNewOpeningAccountDepositGroupCasNSI());
			List<DepositProductEntitySubType> entitySubTypes = depositProductService.getEntitySubTypesInfo(depositType, depositGroup);
			formData.put("depositName", entitySubTypes.get(0).getName());
		}
		else
		{
			depositGroup = Long.valueOf(loanClaimConfig.getNewOpeningAccountDepositGroupOld());
			DepositProduct product = depositProductService.findByProductId(depositType);
			formData.put("depositName", product.getName());
			formData.put("depositId", product.getId());
		}

		formData.put("depositGroup", depositGroup);

		FieldValuesSource valuesSource = new MapValuesSource(formData);

		DocumentSource source = new NewDocumentSource("AccountOpeningClaim", valuesSource, CreationType.internet, CreationSourceType.ordinary);
		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) source.getDocument();

		DepartmentService departmentService = new DepartmentService();
		Department department = departmentService.getDepartment(request.getParameter("tb"), request.getParameter("osb"), request.getParameter("vsp"));
		if (department != null)
			accountOpeningClaim.setDepartment(department);

		boolean isReceptionTime = checkReceptionTime(accountOpeningClaim);
		if (!isReceptionTime)
		{
			saveError(request, "Выберите другой способ получения кредита.");
			ActionForward temp = getCurrentMapping().findForward("ShowForm");
			return new ActionForward(temp.getPath() + "?id="+ request.getParameter("id"), true);
		}

		Calendar openingDate = accountOpeningClaim.getOpeningDate();
		EditDocumentOperation editOperation = createOperation(CreateFormPaymentOperation.class, getServiceName(source));
		editOperation.initialize(source, valuesSource);


		editOperation.updateDocument(formData);
		accountOpeningClaim.setOpeningDate(openingDate);
		accountOpeningClaim.setFromExtendedLoanClaim(true);

		saveOperation(request, editOperation);
		return save(mapping, form, request, response);
	}

	@Override
	protected FieldValuesSource getShowFormFieldValuesSource(EditDocumentOperation operation) throws BusinessException
	{
		BusinessDocument document = operation.getDocument();
		return new DocumentFieldValuesSource(operation.getMetadata(), document);
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.save(mapping, form, request, response);
		EditDocumentOperation operation  = getOperation(request);
		ActionForward temp = getCurrentMapping().findForward("DefaultForward");
		return new ActionForward(temp.getPath() + "?id="+ operation.getDocument().getId(), true);
	}

	private boolean checkReceptionTime(AccountOpeningClaim claim) throws Exception
	{
		// Если сейчас рабочее время (в т.ч. не выходной) то можно создать заявку
		return receptionTimeService.getWorkTimeInfo(claim).isWorkTimeNow() == TimeMatching.RIGHT_NOW;
	}
}
