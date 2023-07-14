package com.rssl.phizic.business.payments.forms.meta;

import com.google.gson.Gson;
import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ShortLoanClaim;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProductService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductTypeService;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditSubProductType;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.EntityUtils;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.json.NoPrettyPrintingGsonSingleton;

import java.util.List;

/**
 * @author Balovtsev
 * @since 15.01.14
 */
public class ExtendedLoanClaimAccessHandler extends BusinessDocumentHandlerBase
{
    private static final BusinessDocumentService        documentService   = new BusinessDocumentService();
	private static final CreditProductConditionService  conditionService  = new CreditProductConditionService();
	private static final CreditProductTypeService       typeService       = new CreditProductTypeService();
	private static final CreditProductService           productService    = new CreditProductService();
	private static final DepartmentService              departmentService = new DepartmentService();

    public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
    {
        if (!(document instanceof ShortLoanClaim))
        {
            throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() + " (Ожидается ShortLoanClaim)");
        }

        ShortLoanClaim shortLoanDocument = (ShortLoanClaim) document;

        boolean onlyShortClaim = true;
        try
        {
	        Login owner = shortLoanDocument.getOwner().getLogin();
            // Просмотр истории операций и поиск заявки со статусом "Обрабатывается банком" или "Отказан"
            if (!documentService.isExtendedLoanClaimAvailable(owner, shortLoanDocument.getOwner().getPerson()))
            {
	            onlyShortClaim = keepAndValidate(shortLoanDocument);
            }
            else
            {
	            keepAndValidate(shortLoanDocument);
                onlyShortClaim = true;
            }
        }
        catch (BusinessException e)
        {
            throw new DocumentException(e);
        }

        shortLoanDocument.setOnlyShortClaim(onlyShortClaim);
    }

	private boolean keepAndValidate(final ShortLoanClaim document) throws DocumentException
	{
		try
		{
			if (StringHelper.isEmpty(document.getLoanOfferId()))
			{
				CreditProductCondition commonCondition = conditionService.findeById(document.getConditionId());
				if (commonCondition == null)
					throw new DocumentException("Не найдено кредитное условие c id=" + document.getConditionId());

				CurrencyCreditProductCondition currencyCondition = EntityUtils.findById(commonCondition.getCurrConditions(), document.getConditionCurrencyId());
				if (currencyCondition == null)
					throw new DocumentException("Не найдено повалютное условие с id=" + document.getConditionCurrencyId());

				keepRequiredCommonConditionFields(document, commonCondition, currencyCondition);

				return !extendedClaimParametersPresent(document, currencyCondition);
			}
			else
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				OfferId offerId = OfferId.fromString(document.getLoanOfferId());
				LoanOffer loanOffer = personData.findLoanOfferById(offerId);
				if (loanOffer == null)
				{
					throw new DocumentException("Не найдено выбранное предодобренное предложение");
				}

				boolean productTypeCodeFound = true;
				CreditProductCondition creditProductCondition  = null;

				// Если нет кода продукта - расширенная заявка недоступна.
				if (StringHelper.isEmpty(loanOffer.getProductCode()) || StringHelper.isEmpty(loanOffer.getSubProductCode()))
				{
					productTypeCodeFound = false;
				}
				else
				{
					List<CreditProductCondition> conditions = conditionService.getConditionsByProductCode( loanOffer.getProductCode() );
					// Нет ни одного условия - расширенная заявка недоступна.
					if (conditions.isEmpty())
					{
						productTypeCodeFound = false;
					}
					else
					{
						creditProductCondition = CreditProductConditionHelper.getCreditProductConditionByLoanOffer(loanOffer);
						if (creditProductCondition == null)
							productTypeCodeFound = false;
					}
				}

				keepLoanOfferFields(document, loanOffer, creditProductCondition);
				return !productTypeCodeFound;
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public void keepLoanOfferFields(final ShortLoanClaim shortLoanDocument,final LoanOffer loanOffer, final CreditProductCondition creditProductCondition) throws BusinessException
	{
		shortLoanDocument.setLoanRate(loanOffer.getPercentRate());
		shortLoanDocument.setLoanCurrency(loanOffer.getMaxLimit().getCurrency().getCode());
		shortLoanDocument.setProductName(loanOffer.getProductName());
		String typeCode = null;
		if (creditProductCondition != null)
			typeCode = creditProductCondition.getCreditProductType().getCode();

		if (!StringHelper.isEmpty(typeCode))
		{
			CreditProductType type = typeService.findeByCode(typeCode);
			if (type != null)
				shortLoanDocument.setProductTypeName(type.getName());
		}

		shortLoanDocument.setTb(loanOffer.getTerbank().toString());
		setCommonFields(shortLoanDocument);
		shortLoanDocument.setEndDate(loanOffer.getEndDate());
	}

	private void setCommonFields(final ShortLoanClaim shortLoanDocument)
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		shortLoanDocument.setFirstName(person.getFirstName());
		shortLoanDocument.setSurName(person.getSurName());
		shortLoanDocument.setPatrName(person.getPatrName());
	}

	private void keepRequiredCommonConditionFields(final ShortLoanClaim shortLoanDocument, final CreditProductCondition condition, final CurrencyCreditProductCondition currCondition) throws BusinessException
	{
		shortLoanDocument.setCurrMinPercentRate(currCondition.getMinPercentRate());
		shortLoanDocument.setCurrMaxPercentRate(currCondition.getMaxPercentRate());
		shortLoanDocument.setProductName(condition.getCreditProduct().getName());
		shortLoanDocument.setProductTypeName(condition.getCreditProductType().getName());
		shortLoanDocument.setLoanCurrency(currCondition.getCurrency().getCode());
		shortLoanDocument.setJsonCondition(getJsonCondition(condition));
		shortLoanDocument.setJsonConditionCurrency(getJsonCondition(currCondition));
		shortLoanDocument.setTb(shortLoanDocument.getDepartment().getRegion());
		setCommonFields(shortLoanDocument);
	}
	/**
	 * @param object Общие условие
	 * @return Все поля условия в виде json, для последующего сравнения.
	 */
	private String getJsonCondition(Object object)
	{
		Gson gson = NoPrettyPrintingGsonSingleton.getGson();
		return gson.toJson(object);
	}

    private boolean extendedClaimParametersPresent(ShortLoanClaim document, CurrencyCreditProductCondition currencyCondition) throws DocumentException
    {
	    CreditProductCondition commonCondition = currencyCondition.getCreditProductCondition();
	    CreditProduct creditProduct = commonCondition.getCreditProduct();

	    try
	    {
		    String documentOwnerTb = departmentService.getNumberTB(document.getDepartment().getId());
		    String productSubTypeCode = null;

		    for (CreditSubProductType subProduct : creditProduct.getCreditSubProductTypes())
		    {
			    String terbank = subProduct.getTerbank();
			    Currency currency = subProduct.getCurrency();
			    if (terbank.equals(documentOwnerTb) && currency.compare(currencyCondition.getCurrency()))
			    {
				    productSubTypeCode = subProduct.getCode();
				    break;
			    }
		    }

		    return (commonCondition.isTransactSMPossibility() &&
				    StringHelper.isNotEmpty(creditProduct.getCode()) &&
				    StringHelper.isNotEmpty(commonCondition.getCreditProductType().getCode()) &&
				    StringHelper.isNotEmpty(productSubTypeCode));
	    }
	    catch (BusinessException e)
	    {
		    throw new DocumentException(e);
	    }
    }
}
