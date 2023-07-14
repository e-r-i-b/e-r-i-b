package com.rssl.phizic.operations.loans.loan;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.LoanClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.business.loans.products.LoanProductService;
import com.rssl.phizic.business.operations.restrictions.LoanRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.template.PackageService;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoansService;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.GroupResultHelper;

import java.util.List;

/**
 * @author gladishev
 * @ created 14.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class PrintLoanDocumentsOperation extends OperationBase<LoanRestriction>
{
	private final LoansService loanService = GateSingleton.getFactory().service(LoansService.class);
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private final LoanProductService loanProductService = new LoanProductService();
	private final PackageService packageService = new PackageService();

	private LoanClaim claim;
	private Loan loan;
	private List<ScheduleItem> schedule;
	private List documents;

	/**
	 * Инициализация операции по id ЗАЯВКИ на кредит
	 * @param claimId - id кредитной заявки
	 */
	public void initialize(Long claimId) throws BusinessException
	{
		BusinessDocument tmp = businessDocumentService.findById(claimId);
		if (tmp instanceof LoanClaim)
			claim = (LoanClaim) tmp;
		else
			throw new BusinessException("Неверный тип документа " + claim.getClass().getName() + ". Ожидается LoanClaim");

		if (!checkBeforePrintDocuments(claim.getState()))
			throw new BusinessException("Неверный статус завки.");

		try
		{
			loan = loanService.getLoanByClaim(claim);

			if (!getRestriction().accept(loan))
				throw new RestrictionViolationException("Кредит с id = " + loan.getId());

			ScheduleAbstract scheduleAbstract = GroupResultHelper.getOneResult(loanService.getSchedule(null,null,loan));
			schedule = scheduleAbstract.getSchedules();
		}
		catch(GateException e)
		{
			throw new BusinessException("Ошибка при получении кредита по заявке. claimId=" +  claimId, e);
		}
		catch(IKFLException e)
		{
			throw new BusinessException("Ошибка при получении дополнительной информации по кредиту. claimId=" +  claimId, e);
		}
		documents = getDocumentsByClaim(claim);
	}

	private List getDocumentsByClaim(LoanClaim claim) throws BusinessException
	{
		ExtendedAttribute productAttribute = claim.getAttribute("product");
		if (productAttribute == null) throw new BusinessException("У заявки с id=" + claim.getId() + " не задан атрибут [product]");

		LoanProduct product = loanProductService.findById(Long.parseLong(productAttribute.getStringValue()));
		PackageTemplate packageTemplate = packageService.getTemplateById(product.getPackageTemplateId());
		return packageTemplate.getTemplates();
	}

	/**
	 * Проверка статуса заявки перед печатью документов.
	 * @return true - можно печатать (статус заявки “Утверждена”)
	 */
	public boolean checkBeforePrintDocuments(State state)
	{
		if (state.getCode().equals("APPROVED"))
			return true;

		return false;
	}

	public LoanClaim getClaim()
	{
		return claim;
	}

	public Loan getLoan()
	{
		return loan;
	}

	public List<ScheduleItem> getSchedule()
	{
		return schedule;
	}

	public List getDocuments()
	{
		return documents;
	}
}
