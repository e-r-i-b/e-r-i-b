package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.ant.pfp.dictionary.*;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceCompany;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceType;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.PeriodType;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexFundInvestmentProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexIMAInvestmentProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexInsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.IMAProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.TrustManagingProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.PensionProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFund;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.AgeCategory;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Question;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.business.dictionaries.pfp.targets.Target;
import com.rssl.phizic.business.BusinessException;

import java.util.Comparator;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * Енум с конфигурациями для загрузки справочников
 */
public enum PFPDictionaryConfig
{
	productTypes("productType", "Справочник параметров типов продуктов", ProductTypeParameters.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new ProductTypeParametersAction();
				}
				public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
				{
					//noinspection unchecked
					return new PPFPDictionaryReplicaDestinationProductTypeParameters(dictionary.getDictionary(this), dbInstanceName);
				}
			},
	loanKinds("loanKind", "Справочник кредитных продуктов", LoanKindProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new LoanKindsAction();
				}
				public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
				{
					//noinspection unchecked
					return new PFPDictionaryReplicaDestinationOneImage(dictionary.getDictionary(this), dbInstanceName);
				}
			},
	targets("target", "Справочник целей", Target.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new TargetsAction();
				}
				public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
				{
					//noinspection unchecked
					return new PFPDictionaryReplicaDestinationOneImage(dictionary.getDictionary(this), dbInstanceName);
				}
			},
	questions("question", "Справочник вопросов", Question.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new QuestionsAction();
				}
			},
	ageCategories("ageCategory", "Возрастные группы", AgeCategory.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new AgeCategoriesAction();
				}
			},
	riskProfiles("riskProfile", "Справочник риск-профилей", RiskProfile.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new RiskProfilesAction();
				}
			},
	risks("risk", "Справочник рисков", Risk.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new RisksAction();
				}
			},
	investmentPeriods("investmentPeriod", "Справочник горизонтов инвестирования", InvestmentPeriod.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new InvestmentPeriodsAction();
				}
			},
	insuranceTypes("insuranceType", "Справочник типов страховых продуктов", InsuranceType.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new InsuranceTypesAction();
				}
				public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
				{
					//noinspection unchecked
					return new PFPDictionaryReplicaDestinationOneImage(dictionary.getDictionary(this), dbInstanceName);
				}
			},
	insuranceCompanies("insuranceCompany", "Справочник страховых компаний", InsuranceCompany.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new InsuranceCompaniesAction();
				}
				public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
				{
					//noinspection unchecked
					return new PFPDictionaryReplicaDestinationOneImage(dictionary.getDictionary(this), dbInstanceName);
				}
			},
	periodTypes("periodType", "Справочник типов периодов", PeriodType.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new PeriodTypesAction();
				}
			},
	accountProducts("accountProduct", "Справочник вкладов", AccountProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new AccountProductsAction();
				}
				public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
				{
					//noinspection unchecked
					return new PFPDictionaryReplicaDestinationOneImage(dictionary.getDictionary(this), dbInstanceName);
				}
			},
	fundProducts("fundProduct", "Справочник ПИФов", FundProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new FundProductsAction();
				}
				public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
				{
					//noinspection unchecked
					return new PFPDictionaryReplicaDestinationOneImage(dictionary.getDictionary(this), dbInstanceName);
				}
			},
	imaProducts("imaProduct", "Справочник ОМС", IMAProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new IMAProductsAction();
				}
				public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
				{
					//noinspection unchecked
					return new PFPDictionaryReplicaDestinationOneImage(dictionary.getDictionary(this), dbInstanceName);
				}
			},
	insuranceProducts("insuranceProduct", "Справочник страховых продуктов", InsuranceProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new InsuranceProductsAction();
				}
			},
	complexInsuranceProducts("complexInsuranceProduct", "Справочник комплексных страховых продуктов", ComplexInsuranceProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new ComplexInsuranceProductsAction();
				}
			},
	complexFundInvestmentProducts("complexFundInvestmentProduct", "Справочник комплексных продуктов (Депозит+ПИФ)", ComplexFundInvestmentProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new ComplexFundInvestmentProductsAction();
				}
			},
	complexIMAInvestmentProducts("complexIMAInvestmentProduct", "Справочник комплексных продуктов (Депозит+ПИФ+ОМС)", ComplexIMAInvestmentProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new ComplexIMAInvestmentProductsAction();
				}
			},
	cards("card", "Справочник карт", Card.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new CardsAction();
				}
				public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
				{
					//noinspection unchecked
					return new PFPDictionaryReplicaDestinationCard(dictionary.getDictionary(this), dbInstanceName);
				}
			},
	trustManagingProducts("trustManagingProduct", "Справочник доверительного управления", TrustManagingProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new TrustManagingProductsAction();
				}
				public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
				{
					//noinspection unchecked
					return new PFPDictionaryReplicaDestinationOneImage(dictionary.getDictionary(this), dbInstanceName);
				}
			},
	pensionFunds("pensionFund", "Справочник пенсионных фондов", PensionFund.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new PensionFundsAction();
				}
				public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
				{
					//noinspection unchecked
					return new PFPDictionaryReplicaDestinationOneImage(dictionary.getDictionary(this), dbInstanceName);
				}
			},
	pensionProducts("pensionProduct", "Справочник пенсионных продуктов", PensionProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new PensionProductsAction();
				}
				public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
				{
					//noinspection unchecked
					return new PFPDictionaryReplicaDestinationOneImage(dictionary.getDictionary(this), dbInstanceName);
				}
			};

	private final String entriesName;                                // ключ записи
	private final String entriesDescription;                         // имя справочника
	private final Class<? extends PFPDictionaryRecord> entriesClass; // класс записи

	//пустой компаратор (обновляем все записи)
	private static final Comparator ENTRIES_COMPARATOR = new Comparator()
		{
			public int compare(Object o1, Object o2)
			{
				return -1;
			}
		};

	PFPDictionaryConfig(String entriesName, String entriesDescription, Class<? extends PFPDictionaryRecord> entriesClass)
	{
		this.entriesName = entriesName;
		this.entriesDescription = entriesDescription;
		this.entriesClass = entriesClass;
	}

	/**
	 * @return ключ записи
	 */
	public String getEntriesName()
	{
		return entriesName;
	}

	/**
	 * @return экшен для обработки записи
	 */
	public DictionaryRecordsActionBase getAction()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * @return класс записи
	 */
	public Class<? extends PFPDictionaryRecord> getDictionaryClass()
	{
		return entriesClass;
	}

	/**
	 * @param dictionary спарвочник всех загружаемых записей
	 * @return xml соурс для репликации
	 */
	public PFPDictionaryReplicaSource getReplicaSource(PFPDictionary dictionary) throws BusinessException
	{
		//noinspection unchecked
		return new PFPDictionaryReplicaSource(dictionary.getDictionary(this));
	}

	/**
	 * @param dictionary спарвочник всех загружаемых записей
	 * @return сохраненные в бд записи
	 */
	public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
	{
		//noinspection unchecked
		return new PFPDictionaryReplicaDestination(dictionary.getDictionary(this), dbInstanceName);
	}

	/**
	 * @return компаратор для записи
	 */
	public Comparator getComparator()
	{
		return ENTRIES_COMPARATOR;
	}

	/**
	 * @return имя справочника
	 */
	public String getEntriesDescription()
	{
		return entriesDescription;
	}
}