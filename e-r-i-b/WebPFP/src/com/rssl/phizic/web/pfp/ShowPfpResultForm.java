package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;

import java.util.List;

/**
 * @author lepihina
 * @ created 20.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowPfpResultForm extends EditPersonalFinanceProfileForm
{
	private RiskProfile riskProfile;
	private List<PersonPortfolio> portfolioList;
	private String pdfName;
	private boolean hasNotCoplitePFP; // � ������� �������� ��� (�������� ������������)

	public RiskProfile getRiskProfile()
	{
		return riskProfile;
	}

	public void setRiskProfile(RiskProfile riskProfile)
	{
		this.riskProfile = riskProfile;
	}

	public List<PersonPortfolio> getPortfolioList()
	{
		return portfolioList;
	}

	public void setPortfolioList(List<PersonPortfolio> portfolioList)
	{
		this.portfolioList = portfolioList;
	}

	public String getPdfName()
	{
		return pdfName;
	}

	public void setPdfName(String pdfName)
	{
		this.pdfName = pdfName;
	}

	public boolean getHasNotCoplitePFP()
	{
		return hasNotCoplitePFP;
	}

	public void setHasNotCoplitePFP(boolean hasNotCoplitePFP)
	{
		this.hasNotCoplitePFP = hasNotCoplitePFP;
	}

	/**
	 * @return ���������� �������� ����� ���� �������� ��� ������
	 */
	public ProductType getAccountType()
	{
		return ProductType.account;
	}

	/**
	 * @return ���������� �������� ����� ���� �������� ��� �����������
	 */
	public ProductType getInsuranceType()
	{
		return ProductType.insurance;
	}

	/**
	 * @return ���������� �������� ����� ���� �������� ��� ���-�
	 */
	public ProductType getFundType()
	{
		return ProductType.fund;
	}

	/**
	 * @return ���������� �������� ����� ���� �������� ��� ���
	 */
	public ProductType getImaType()
	{
		return ProductType.IMA;
	}

	/**
	 * @return ���������� �������� ����� ���� �������� ��� �������������� ����������
	 */
	public ProductType getTrustManagingType()
	{
		return ProductType.trustManaging;
	}
}
