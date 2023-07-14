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
 * ���� � �������������� ��� �������� ������������
 */
public enum PFPDictionaryConfig
{
	productTypes("productType", "���������� ���������� ����� ���������", ProductTypeParameters.class)
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
	loanKinds("loanKind", "���������� ��������� ���������", LoanKindProduct.class)
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
	targets("target", "���������� �����", Target.class)
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
	questions("question", "���������� ��������", Question.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new QuestionsAction();
				}
			},
	ageCategories("ageCategory", "���������� ������", AgeCategory.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new AgeCategoriesAction();
				}
			},
	riskProfiles("riskProfile", "���������� ����-��������", RiskProfile.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new RiskProfilesAction();
				}
			},
	risks("risk", "���������� ������", Risk.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new RisksAction();
				}
			},
	investmentPeriods("investmentPeriod", "���������� ���������� ��������������", InvestmentPeriod.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new InvestmentPeriodsAction();
				}
			},
	insuranceTypes("insuranceType", "���������� ����� ��������� ���������", InsuranceType.class)
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
	insuranceCompanies("insuranceCompany", "���������� ��������� ��������", InsuranceCompany.class)
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
	periodTypes("periodType", "���������� ����� ��������", PeriodType.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new PeriodTypesAction();
				}
			},
	accountProducts("accountProduct", "���������� �������", AccountProduct.class)
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
	fundProducts("fundProduct", "���������� �����", FundProduct.class)
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
	imaProducts("imaProduct", "���������� ���", IMAProduct.class)
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
	insuranceProducts("insuranceProduct", "���������� ��������� ���������", InsuranceProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new InsuranceProductsAction();
				}
			},
	complexInsuranceProducts("complexInsuranceProduct", "���������� ����������� ��������� ���������", ComplexInsuranceProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new ComplexInsuranceProductsAction();
				}
			},
	complexFundInvestmentProducts("complexFundInvestmentProduct", "���������� ����������� ��������� (�������+���)", ComplexFundInvestmentProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new ComplexFundInvestmentProductsAction();
				}
			},
	complexIMAInvestmentProducts("complexIMAInvestmentProduct", "���������� ����������� ��������� (�������+���+���)", ComplexIMAInvestmentProduct.class)
			{
				public DictionaryRecordsActionBase getAction()
				{
					return new ComplexIMAInvestmentProductsAction();
				}
			},
	cards("card", "���������� ����", Card.class)
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
	trustManagingProducts("trustManagingProduct", "���������� �������������� ����������", TrustManagingProduct.class)
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
	pensionFunds("pensionFund", "���������� ���������� ������", PensionFund.class)
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
	pensionProducts("pensionProduct", "���������� ���������� ���������", PensionProduct.class)
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

	private final String entriesName;                                // ���� ������
	private final String entriesDescription;                         // ��� �����������
	private final Class<? extends PFPDictionaryRecord> entriesClass; // ����� ������

	//������ ���������� (��������� ��� ������)
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
	 * @return ���� ������
	 */
	public String getEntriesName()
	{
		return entriesName;
	}

	/**
	 * @return ����� ��� ��������� ������
	 */
	public DictionaryRecordsActionBase getAction()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * @return ����� ������
	 */
	public Class<? extends PFPDictionaryRecord> getDictionaryClass()
	{
		return entriesClass;
	}

	/**
	 * @param dictionary ���������� ���� ����������� �������
	 * @return xml ����� ��� ����������
	 */
	public PFPDictionaryReplicaSource getReplicaSource(PFPDictionary dictionary) throws BusinessException
	{
		//noinspection unchecked
		return new PFPDictionaryReplicaSource(dictionary.getDictionary(this));
	}

	/**
	 * @param dictionary ���������� ���� ����������� �������
	 * @return ����������� � �� ������
	 */
	public PFPDictionaryReplicaDestination getReplicaDestination(PFPDictionary dictionary, String dbInstanceName) throws BusinessException
	{
		//noinspection unchecked
		return new PFPDictionaryReplicaDestination(dictionary.getDictionary(this), dbInstanceName);
	}

	/**
	 * @return ���������� ��� ������
	 */
	public Comparator getComparator()
	{
		return ENTRIES_COMPARATOR;
	}

	/**
	 * @return ��� �����������
	 */
	public String getEntriesDescription()
	{
		return entriesDescription;
	}
}