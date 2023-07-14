package com.rssl.phizic.test;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.loanCardOffer.LoanCardOffer;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.loanOffer.LoanOffer;
import com.rssl.phizic.business.loanOffer.OffersService;
import com.rssl.phizic.business.loans.products.ModifiableLoanProduct;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.security.test.SecurityTestBase;

import java.math.BigDecimal;

/**
 * User: Moshenko
 * Date: 25.05.2011
 * Time: 15:08:48
 */
public class LoanOfferServiceTest extends SecurityTestBase

{
	OffersService loanOffersService = new OffersService();
	PersonService personService = new PersonService();
	SecurityService securityService = new SecurityService();
	DepartmentService departmentService = new DepartmentService();

	public void  testAddOffer() throws Exception
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Login login = securityService.getClientLogin("73499347");
//		Department department = departmentService.findById(87L);
		LoanOffer loanOffer = new LoanOffer();
//		loanOffer.setLogin(login);
//		loanOffer.setDepartment(87L);
		loanOffer.setDuration(new Long(7));
		loanOffer.setMaxLimit(new Money(new BigDecimal("99.9"),currencyService.findByAlphabeticCode("RUB")));
//		loanOffer.setLoanProductId(buildLoanProduct());
		loanOffer = loanOffersService.addOrUpdate(loanOffer);
//		assertEquals(loanOffer,loanOffersService.getLoanCardOfferByPersonData(login,null));

		LoanCardOffer loanCardOffer = new LoanCardOffer();
//		loanCardOffer.setLogin(login);
//		loanCardOffer.setDepartment(87L);
		loanCardOffer.setMaxLimit(new Money(new BigDecimal("99.9"),currencyService.findByAlphabeticCode("RUB")));
		loanCardOffer.setOfferType(LoanCardOfferType.newCard);
		loanCardOffer = loanOffersService.addOrUpdate(loanCardOffer);
//		assertEquals(loanCardOffer,loanOffersService.getLoanCardOfferByPersonData(login,null));
	}

	private ModifiableLoanProduct buildLoanProduct()
	{
		ModifiableLoanProduct product = new ModifiableLoanProduct();
		product.setId(new Long(1));
		product.setAdditionalTerms("Доп. усл.");
		return product;
	}
}
