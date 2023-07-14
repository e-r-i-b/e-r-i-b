package com.rssl.phizic.operations.loans.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.loans.conditions.AmountType;
import com.rssl.phizic.business.loans.conditions.LoanCondition;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.kinds.LoanKindService;
import com.rssl.phizic.business.loans.products.*;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.loans.LoanProductsService;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * @author gladishev
 * @ created 21.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditLoanProductOperation extends OperationBase implements EditEntityOperation
{
	private static LoanProductsService loanProductsService;
	private static final LoanProductService loanProductService = new LoanProductService();
	private static final ModifiableLoanProductService modifiableLoanProductService = new ModifiableLoanProductService();
	private static final LoanKindService loanKindService = new LoanKindService();
	private static final DepartmentService departmentService = new DepartmentService();

	private LoanProductBase product;
	private List<Currency> currencies = new ArrayList<Currency>();

	/**
	 * Инициализация новым видом кредита
	 * @param modifiable
	 */
	public void initializeNew(boolean modifiable) throws BusinessException
	{
		if(modifiable)
		{
			product = new ModifiableLoanProduct();
			initCurrencies();
		}
		else
		{
			product = new LoanProduct();
			((LoanProduct)product).setDescription("<loan-product></loan-product>");
			initGateService();
		}
	}

	/**
	 * Инициализация
	 * @param productId ID кредита
	 * @param modifiable
	 */
	public void initialize(Long productId, boolean modifiable) throws BusinessException
	{
		if(modifiable)
		{
			product = modifiableLoanProductService.findById(productId);
			initCurrencies();
		}
		else
		{
			product = loanProductService.findById(productId);
			initGateService();
		}

		if (product == null)
			throw new BusinessException("Кредитный продукт с Id = " + productId + " не найден.");

	}

	public LoanProductBase getEntity()
	{
		return product;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		setChangeDate(product);
		if(product instanceof ModifiableLoanProduct)
		{
			ModifiableLoanProduct product = (ModifiableLoanProduct) this.product;

			if(product.getMinDuration().isEmpty() && product.getMaxDuration().isEmpty())
				throw new BusinessLogicException("Вы неправильно указали срок кредита. Пожалуйста, проверьте начало и окончание срока действия кредита.");
			else if(!product.getMinDuration().isEmpty()
					&& !product.getMaxDuration().isEmpty()
					&& product.getMinDuration().getDuration() >= product.getMaxDuration().getDuration())
				throw new BusinessLogicException("Вы неправильно указали срок кредита. Пожалуйста, проверьте начало и окончание срока действия кредита.");	

			if(product.isNeedInitialInstalment())
				if((product.getMinInitialInstalment() == null || product.getMinInitialInstalment().compareTo(BigDecimal.ZERO) == 0)
						&& (product.getMaxInitialInstalment() == null || product.getMaxInitialInstalment().compareTo(BigDecimal.ZERO) == 0))
					throw new BusinessLogicException("Вы неправильно указали первоначальный взнос. Пожалуйста, введите сумму минимального и сумму максимального первоначального взноса.");
				else if(product.getMinInitialInstalment() != null && product.getMaxInitialInstalment() != null
						&& product.getMinInitialInstalment().compareTo(product.getMaxInitialInstalment()) >= 0)
					throw new BusinessLogicException("Вы неправильно указали первоначальный взнос. Пожалуйста, проверьте сумму минимального и сумму максимального первоначального взноса.");

			if(product.getTerbanks().isEmpty())
				throw new BusinessLogicException("Пожалуйста, укажите хотя бы одно подразделение, в котором будет доступен данный кредитный продукт.");

			if(product.getPublicity() == Publicity.PUBLISHED && product.getConditions().isEmpty())
				throw new BusinessLogicException("Для опубликованного продукта должно быть задано хотя бы одно условие в разрезе валют.");

			if(product.getId() == null)
				modifiableLoanProductService.add(product);
			else
				modifiableLoanProductService.update(product);
		}
		else if(product instanceof LoanProduct)
		{
			loanProductService.addOrUpdate((LoanProduct)product);
		}
	}

	private void setChangeDate(LoanProductBase product) throws BusinessException
	{
		if (NumericUtil.isEmpty(product.getId()))
		{
			product.setChangeDate(Calendar.getInstance());
			return;
		}

		LoanProductBase old;
		if (product instanceof ModifiableLoanProduct)
			old = modifiableLoanProductService.findById(product.getId());
		else
			old = loanProductService.findById(product.getId());
		if (!LoanProductHelper.loanProductEquals(product, old))
			product.setChangeDate(Calendar.getInstance());
	}

	public Source getProductSource() throws BusinessException
	{
		LoanProduct product = (LoanProduct) this.product;
		String description = product.getDescription();
		try
		{
			Document document = XmlHelper.parse(description);
			Element root = document.getDocumentElement();
			root.setAttribute("id", (product.getId() == null) ? "" : Long.toString(product.getId()));
			root.setAttribute("name", (product.getName() == null) ? "" : product.getName());
			root.setAttribute("description", (product.getBriefDescription() == null) ? "" : product.getBriefDescription());
			root.setAttribute("package-template", (product.getPackageTemplateId() == null) ? "" : Long.toString(product.getPackageTemplateId()));
			root.setAttribute("loan-kind", (product.getLoanKind() == null) ? "" : Long.toString(product.getLoanKind().getId()));
			root.setAttribute("anonymous", (product.getAnonymousClaim() == null) ? "" : Boolean.toString(product.getAnonymousClaim()));
			return new DOMSource(document);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	public void setSelectedLoanTypes(String[] selectedLoanTypes) throws BusinessException, BusinessLogicException
	{
		final Map<String, Set<String>> conditions = new HashMap<String, Set<String>>();
		for (String selectedType : selectedLoanTypes)
		{
			if (selectedType == null || selectedType.trim().length() == 0)
			{
				continue;
			}
			String[] strings = selectedType.split("-");
			String officeId = strings[0];
			String conditionId = strings[1];

			Set<String> offices = conditions.get(conditionId);
			if (offices == null)
			{
				offices = new HashSet<String>();
				conditions.put(conditionId, offices);
			}
			offices.add(officeId);
		}
		Document resultDocument = getLoanProductDocument(new ArrayList<String>(conditions.keySet()));
		fillSelectedOffices(resultDocument, conditions);
		((LoanProduct)product).setDescription(dom2String(resultDocument));
		((LoanProduct)product).setGuarantorsCount(getGuaranteeCount(resultDocument));
	}

	public List<LoanKind> getLoanKinds() throws BusinessException
	{
		return loanKindService.getAll();
	}

	public List<Department> getAllTerbanks() throws BusinessException
	{
		return AllowedDepartmentsUtil.getAllowedTerbanks();
	}

	public List<Currency> getCurrencies()
	{
		return currencies;
	}

	public void setTerbanks(String[] terbankIds) throws BusinessException
	{
		if(!(product instanceof ModifiableLoanProduct))
			return;

		List<Department> newList = new ArrayList<Department>();
		for(String terbankId : terbankIds)
			newList.add(departmentService.findById(Long.valueOf(terbankId)));
		((ModifiableLoanProduct)product).setTerbanks(newList);
	}

	public LoanCondition createCondition(Map<String, Object> fields) throws BusinessException, BusinessLogicException
	{
		LoanCondition condition = new LoanCondition();

		//id продукта
		condition.setProductId(product.getId());

		//id условия
		Long conditionId = (Long) fields.get("conditionId");
		if(conditionId != null)
			condition.setId(conditionId);

		//валюта
		Currency currency = null;
		try{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			currency = currencyService.findByNumericCode((String)fields.get("currencyNumber"));
			condition.setCurrency(currency);
		}
		catch(GateException e){
			throw new BusinessException(e);
		}

		//сумма
		AmountType amountType = AmountType.valueOf((String)fields.get("amountType"));
		BigDecimal minAmountValue = (BigDecimal) fields.get("minAmount");
		Money minAmount = null;
		if (minAmountValue != null && currency != null)
			minAmount = new Money(minAmountValue, currency);

		BigDecimal maxAmountValue = (BigDecimal) fields.get("maxAmount");
		Money maxAmount = null;
		if (maxAmountValue != null && currency != null && amountType == AmountType.CURRENCY)
			maxAmount = new Money(maxAmountValue, currency);

		BigDecimal maxAmountPercentValue = (BigDecimal) fields.get("maxAmountPercent");
		BigDecimal maxAmountPercent = amountType == AmountType.PERCENT ? maxAmountPercentValue : null;

		if(minAmount == null
				&& ((amountType == AmountType.CURRENCY && maxAmount == null) || (amountType == AmountType.PERCENT && maxAmountPercent == null)))
			throw new BusinessLogicException("Вы неправильно указали сумму продукта. Пожалуйста, введите минимальную и максимальную сумму продукта. Должно быть задано хотя бы одно значение.");
		else if((minAmount != null && minAmount.getAsCents() == 0)
				|| (maxAmount != null && maxAmount.getAsCents() == 0)
				|| (maxAmountPercent != null && maxAmountPercent.compareTo(BigDecimal.ZERO) == 0))
			throw new BusinessLogicException("Сумма продукта не может быть равна нулю.");
		else if(amountType == AmountType.CURRENCY && minAmount != null && maxAmount != null && minAmount.getAsCents() >= maxAmount.getAsCents())
			throw new BusinessLogicException("Вы неправильно указали сумму продукта. Пожалуйста, проверьте минимальную и максимальную сумму продукта. Минимальная сумма должна быть меньше максимальной.");

		condition.setAmountType(amountType);
		condition.setMinAmount(minAmount);
		condition.setMaxAmount(maxAmount);
		condition.setMaxAmountPercent(maxAmountPercent);
		condition.setMaxAmountInclude((Boolean)fields.get("isMaxAmountInclude"));

		//% ставка
		BigDecimal minInterestRate = (BigDecimal) fields.get("minInterestRate");
		BigDecimal maxInterestRate = (BigDecimal) fields.get("maxInterestRate");

		if(minInterestRate == null && maxInterestRate == null)
			throw new BusinessLogicException("Вы неправильно указали процентную ставку кредита. Пожалуйста, введите минимальную и максимальную ставку. Должно быть задано хотя бы одно значение.");
		else if((minInterestRate != null && minInterestRate.compareTo(BigDecimal.ZERO) == 0)
				|| (maxInterestRate != null && maxInterestRate.compareTo(BigDecimal.ZERO) == 0))
			throw new BusinessLogicException("Процентная ставка не может быть равна нулю.");
		else if(minInterestRate != null && maxInterestRate != null && minInterestRate.compareTo(maxInterestRate) >= 0)
			throw new BusinessLogicException("Вы не правильно указали процентную ставку кредита. Пожалуйста, проверьте минимальную и максимальную ставку. Минимальная процентная ставка должна быть меньше максимальной.");

		condition.setMinInterestRate(minInterestRate);
		condition.setMaxInterestRate(maxInterestRate);
		condition.setMaxInterestRateInclude((Boolean)fields.get("isMaxInterestRateInclude"));

		return condition;
	}

	private void initCurrencies() throws BusinessException
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			currencies.add(currencyService.findByAlphabeticCode("RUB"));
			currencies.add(currencyService.findByAlphabeticCode("USD"));
			currencies.add(currencyService.findByAlphabeticCode("EUR"));
		}
		catch(GateException e)
		{
			throw new BusinessException(e);
		}
	}

	private void fillSelectedOffices(Document resultDocument, final Map<String, Set<String>> conditions) throws BusinessException
	{
		try
		{
			XmlHelper.foreach(resultDocument.getDocumentElement(), "//condition", new ForeachElementAction()
			{

				public void execute(Element element) throws Exception
				{
					XPath xpath = XPathFactory.newInstance().newXPath();
					XPathExpression pathExpression = xpath.compile("value[@name='conditionId']");
					Element conditionIdElement = (Element) pathExpression.evaluate(element, XPathConstants.NODE);
					if (conditionIdElement == null)
					{
						return;
					}
					String conditionId = conditionIdElement.getTextContent();
					Set<String> offices = conditions.get(conditionId);
					if (offices == null)
					{
						return;
					}
					for (String office : offices)
					{
						Element selectedOfficeElement = element.getOwnerDocument().createElement("value");
						selectedOfficeElement.setAttribute("name", "selected-office");
						selectedOfficeElement.setTextContent(office);
						element.appendChild(selectedOfficeElement);
					}
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private String dom2String(Document document) throws BusinessException
	{
		StringWriter writer = new StringWriter();
		InnerSerializer serializer = new InnerSerializer(writer);
		try
		{
			serializer.serialize(document);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		return writer.getBuffer().toString();
	}

	//Гейтовый сервис для немодифицируемых кредитных продуктов
	private void initGateService()
	{
		loanProductsService = GateSingleton.getFactory().service(LoanProductsService.class);
	}
	
	private Document getLoanProductDocument(List<String> conditions) throws BusinessException, BusinessLogicException
	{
		try
		{
			return loanProductsService.getLoanProduct(conditions);
		}
		catch (GateException ge)
		{
			throw new BusinessException("Ошибка при получении информации по кредитному продукту", ge);
		}
	}

	private long getGuaranteeCount(Document document) throws BusinessLogicException, BusinessException
	{
		try
		{
			GuaranteeCalculator quaranteeCalculator = new GuaranteeCalculator();
			if (document.getDocumentElement() != null )
			{
			   XmlHelper.foreach(document.getDocumentElement(), "//condition/value[@name='guaranteeCount']", quaranteeCalculator);
			   return quaranteeCalculator.getGuaranteeCount();
			}
			else
			   return 0L;
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private static class GuaranteeCalculator implements ForeachElementAction
	{
		private Integer guaranteeCount;

		public void execute(Element element) throws Exception
		{
			String nodeValue = element.getTextContent();
			if (nodeValue == null || nodeValue.trim().length() == 0)
			{
				nodeValue = "0";
			}
			int currentGuaranteeCount = Integer.valueOf(nodeValue);
			if (guaranteeCount == null)
			{
				guaranteeCount = currentGuaranteeCount;
			}
			if (guaranteeCount != currentGuaranteeCount)
			{
				throw new BusinessLogicException("Кредитный продукт содержит виды кредитов с разным количеством поручителей");
			}
		}

		public int getGuaranteeCount()
		{
			return guaranteeCount;
		}
	}
}
