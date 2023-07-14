package com.rssl.phizic.operations.pfp;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PfpConfig;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProductService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.MultiInstanceEmployeeService;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PortfolioProduct;
import com.rssl.phizic.business.pfp.portfolio.product.BaseProduct;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.chart.ChartBuilder;
import com.rssl.phizic.utils.chart.DocumentOrientation;
import com.rssl.phizic.utils.pdf.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author mihaylov
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowPfpFinancePlanOperation extends GeneratePfpPdfDocumentOperation
{
	private static final State FINANCE_PLAN = new State("FINANCE_PLAN");
	private static final MultiInstanceEmployeeService multiInstanceEmployeeService = new MultiInstanceEmployeeService();
	private static final LoanKindProductService loanKindProductService = new LoanKindProductService();

	protected static final int PDF_CHART_WIDTH = 600;
	protected static final int PDF_CHART_HEIGHT= 300;
	
	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException
	{
		checkState(FINANCE_PLAN);
	}

	/**
	 * ���������� ������� �������
	 * @return �������
	 */
	public Integer getPersonAge()
	{
		return PersonHelper.getPersonAge(person);
	}

	/**
	 * ����� ��� ���������� PDF � ��������� ������� ��������� ��������
	 * @throws PDFBuilderException
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public byte[] createCalculationPDF() throws PDFBuilderException, BusinessException,BusinessLogicException
	{
		String resoursePath = ConfigFactory.getConfig(PfpConfig.class).getResourcePath();
		HeaderFooterBuilder headerFooterBuilder = new HeaderFooterBuilder();
		headerFooterBuilder.addOnlyTitleInstance("���������: " + DateHelper.formatDateToStringWithPoint(personalFinanceProfile.getStartPlaningDate()));
		headerFooterBuilder.addImageInstance(resoursePath + "logoSBRFNew.png", LOGO_WIDTH, LOGO_HEIGHT, Alignment.right);
		headerFooterBuilder.addPageNumberInstance();
		PDFBuilder builder = PDFBuilder.getInstance(headerFooterBuilder, resoursePath + PDFBuilder.FONT_NAME, 12, DocumentOrientation.HORIZONTAL);
		ChartBuilder chartBuilder = new ChartBuilder();

		// ��������� ����
		builder.addEmptyParagraph(new ParagraphStyle(15f, 50f, 0f, 0f));
		builder.addImage(resoursePath + "sbrfTitulLogo.png", 551, 91, Alignment.left);
		builder.addParagraph("������ ������� ��������� ��������", PDFBuilder.TITLE_FILE_PARAGRAPH, PDFBuilder.TITLE_LIST_FONT);

		builder.addEmptyParagraph();
		builder.addPhrase("������: ", PDFBuilder.ITALIC_BOLD_FONT);
		builder.addPhrase(getPerson().getFirstPatrName());
		builder.addEmptyParagraph();

		if (StringHelper.isNotEmpty(getPerson().getManagerId()) && StringHelper.isNotEmpty(getProfile().getEmployeeFIO()))
		{
		    Employee employee = multiInstanceEmployeeService.findByManagerId(getPerson().getManagerId(), null);
			if (employee != null)
			{
				builder.addPhrase("������������ ��������: ", PDFBuilder.ITALIC_BOLD_FONT);
				builder.addPhrase(employee.getFullName());
			}
		}
		builder.newPage();

		builder.addParagraph("������ ������� ��������� ��������", PDFBuilder.TITLE_PARAGRAPH, PDFBuilder.TITLE_FONT);
		builder.addParagraph("� ������ ������ �������� ������ ������� ��������� ������ ��������� ��������.", PDFBuilder.BASE_PARAGRAPH);
		drawFuturePortfolioSum(builder);

		builder.addParagraph("�� ������� �� ������ ����������� ������� ��������� ������ �������� �� ��������� ������.", PDFBuilder.BASE_PARAGRAPH);
		drawFinancePlanChart(builder, chartBuilder, "������� 1. ������ ������� ��������� \"��������� ��������\"", PDF_CHART_WIDTH, PDF_CHART_HEIGHT);

		return builder.build();
	}

	public void nextStep() throws BusinessException, BusinessLogicException
	{
		getExecutor().fireEvent(new ObjectEvent(DocumentEvent.COMPLETE,ObjectEvent.CLIENT_EVENT_TYPE));
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	/**
	 * �������� ����� �������� � ��������
	 * @param portfolioType - ��� �������� ��� ���������.
	 * @param addAmount - ����� �� ������� �������� �������� � ��������
	 * ��������� ������ ��� ���������(�������� ��������� ���������, ���� ��������) ��� ������ ��������� � ������� ���
	 */
	public void changeStartAmount(PortfolioType portfolioType, Money addAmount) throws BusinessException
	{
		PersonPortfolio changedPortfolio = personalFinanceProfile.getPortfolioByType(portfolioType);
		Money productSum = changedPortfolio.getProductSum();
		Money wantedAmount = productSum.add(addAmount);
		for(PortfolioProduct portfolioProduct: changedPortfolio.getPortfolioProductList())
		{
			for(BaseProduct baseProduct: portfolioProduct.getBaseProductList())
			{
				Money currentAmount = baseProduct.getAmount();
				BigDecimal newAmount = currentAmount.getDecimal().multiply(wantedAmount.getDecimal()).divide(productSum.getDecimal(),2, RoundingMode.UP);
				baseProduct.setAmount(new Money(newAmount,currentAmount.getCurrency()));
			}
			PortfolioHelper.updateAdditionalPortfolioAmount(personalFinanceProfile,portfolioProduct,changedPortfolio);
		}
		//�������� ����� ��������� ��������� � ��������
		productSum = changedPortfolio.getProductSum();
		if(changedPortfolio.getStartAmount().compareTo(productSum) < 0)
			changedPortfolio.setStartAmount(productSum);
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	/**
	 * ���������� ������� ���������� ��������
	 * @param portfolioType - ��� �������� � ������������
	 * @param wantedIncome - ���������� �� ������� ���������� ��������� ���������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void changePortfolioIncome(PortfolioType portfolioType,BigDecimal wantedIncome) throws BusinessException,BusinessLogicException
	{
		PersonPortfolio changedPortfolio = personalFinanceProfile.getPortfolioByType(portfolioType);
		changedPortfolio.setWantedIncome(wantedIncome);

		fireEvent(DocumentEvent.BACK);
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	/**
	 * @return ���������� ��������
	 */
	public List<LoanKindProduct> getLoanKindDictionaryList() throws BusinessException
	{
		return loanKindProductService.getAll();
	}
}
