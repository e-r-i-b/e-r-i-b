package com.rssl.phizic.business.pfp;

import com.rssl.phizic.auth.CheckLoginTest;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfileService;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PortfolioProduct;
import com.rssl.phizic.business.pfp.portfolio.product.BaseProduct;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author mihaylov
 * @ created 15.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class PersonalFinanceProfileServiceTest extends BusinessTestCaseBase
{

	private static final PersonalFinanceProfileService service = new PersonalFinanceProfileService();
	private static final RiskProfileService riskProfileService = new RiskProfileService();
	private static final SimpleService simpleService = new SimpleService();

	public void test() throws Exception
	{
		Login testLogin = CheckLoginTest.getTestLogin();
		PersonalFinanceProfile profile = service.getNewProfile(testLogin);

		List<PersonTarget> personTargetList = new ArrayList<PersonTarget>();

		PersonTarget target1 = new PersonTarget();
		target1.setName("Покупка автомобиля");
		Calendar planedDate1 = Calendar.getInstance();
		planedDate1.add(Calendar.YEAR,3);
		target1.setPlannedDate(planedDate1);
		target1.setAmount(new Money(1028000L,0L, MoneyUtil.getNationalCurrency()));

		personTargetList.add(target1);

		PersonTarget target2 = new PersonTarget();
		target2.setName("Строительство дома");
		Calendar planedDate2 = Calendar.getInstance();
		planedDate2.add(Calendar.YEAR,4);
		target2.setPlannedDate(planedDate2);
		target2.setAmount(new Money(5000000L,0L, MoneyUtil.getNationalCurrency()));

		personTargetList.add(target2);
		profile.setPersonTargets(personTargetList);

		RiskProfile riskProfile = riskProfileService.findRiskProfileForWeight(3, SegmentCodeType.NOTEXISTS);
		profile.setDefaultRiskProfile(riskProfile);

		service.addOrUpdateProfile(profile);

		PersonPortfolio portfolio = new PersonPortfolio();
		portfolio.setType(PortfolioType.START_CAPITAL);
		portfolio.setStartAmount(new Money(5000000L,0L, MoneyUtil.getNationalCurrency()));
		profile.addPortfolio(portfolio);

		PortfolioProduct product = new PortfolioProduct();
		BaseProduct baseProduct = new BaseProduct();
		baseProduct.setProductType(DictionaryProductType.ACCOUNT);
		baseProduct.setProductName("Страховой индекс");
		baseProduct.setAmount(new Money(12000L,0L, MoneyUtil.getNationalCurrency()));
		product.addBaseProduct(baseProduct);

		baseProduct = new BaseProduct();
		baseProduct.setProductType(DictionaryProductType.INSURANCE);
		baseProduct.setProductName("Allianz Indexx");
		baseProduct.setAmount(new Money(100000L,0L, MoneyUtil.getNationalCurrency()));
		product.addBaseProduct(baseProduct);

		product.setName("Страховой индекс плюс");
		product.setProductType(DictionaryProductType.COMPLEX_INSURANCE);
		portfolio.addPortfolioProduct(product);

		service.addOrUpdateProfile(profile);

		profile = service.getProfileById(profile.getId());

		simpleService.remove(profile);
	}

}
