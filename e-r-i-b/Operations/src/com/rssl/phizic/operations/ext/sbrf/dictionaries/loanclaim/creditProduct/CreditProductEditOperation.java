package com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProductService;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditSubProductType;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author Moshenko
 * @ created 07.01.2014
 * @ $Author$
 * @ $Revision$
 * Операция редактирования кредитных продуктов.
 */
public class CreditProductEditOperation extends OperationBase implements EditEntityOperation
{
	private static final CreditProductService creditProductSrvice = new CreditProductService();
	private static final DepartmentService departmentService = new DepartmentService();
	private CreditProduct entity;
	private Set<CreditSubProductType> addSubProducts = new HashSet<CreditSubProductType>();;

	/**
	 * Инициализируем операцию.
	 * @param id id Кредитного продукта.
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		this.entity = creditProductSrvice.findById(id);
		if (entity == null)
			throw new BusinessException("Кредитный продукт с id:" + id + "не найден.");
	}

	/**
	 * Инициализируем операцию для нового продутка.
	 * @throws BusinessException
	 */
	public void initializeNew() throws BusinessException
	{
		this.entity = new CreditProduct();
		entity.setCreditSubProductTypes(new TreeSet<CreditSubProductType>());
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		Set<CreditSubProductType> subProducts =  entity.getCreditSubProductTypes();
		Set<CreditSubProductType> remSubProducts =  new HashSet<CreditSubProductType>();
		for (CreditSubProductType addSubProduct: addSubProducts)
		{
			if (StringHelper.isEmpty(addSubProduct.getCode()))
				remSubProducts.add(getCurrentSubProduct(addSubProduct));
			else
				subProducts.add(getCurrentSubProduct(addSubProduct));
		}
		subProducts.removeAll(remSubProducts);
		creditProductSrvice.addOrUpdate(entity);
	}

	/**
	 * @param addSubProduct новый суб продукт
	 * @return Либо новый субродукт если его еще нет по связки TB/Валюта в продукте либо актуализированный старый
	 */
	private CreditSubProductType getCurrentSubProduct(CreditSubProductType addSubProduct)
	{
		for(CreditSubProductType subProduct:entity.getCreditSubProductTypes())
		{
			int comareTb = subProduct.getTerbank().compareTo(addSubProduct.getTerbank());
			boolean comareCurrency = StringHelper.equals(subProduct.getCurrency().getCode(), addSubProduct.getCurrency().getCode());
			//Если это один тб, и одинаковая валюта //если есть то просто обноляем код
			if (comareTb == 0 && comareCurrency)
			{
				subProduct.setCode(addSubProduct.getCode());
				return subProduct;
			}
		}
		return  addSubProduct;
	}

	public Object getEntity()
	{
		return entity;
	}

	/**
	 * @param alphabeticCode Алфавитный код валюты
	 * @return Валюта
	 * @throws GateException
	 */
	public Currency getCurrency(String alphabeticCode) throws GateException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		return currencyService. findByAlphabeticCode(alphabeticCode);
	}

	/**
	 * @param tb Тб
	 * @return Наименование ТБ.
	 * @throws BusinessException
	 */
	public String getTbName(String tb) throws BusinessException
	{
		Code tbCode = new ExtendedCodeImpl(tb,null,null);
		Department tbDepartment = departmentService.findByCode(tbCode);
		if (tbDepartment == null)
			throw new BusinessException("Для тербанка с кодом:" + tb + " не обнаружено подразделение в системе");
		return tbDepartment.getName();
	}

	/**
	 * @param tb тб
	 * @param curr Алфавитный код валюты
	 * @return код субпродукта , либо "" если не нашли.
	 */
	public String getCodeByTbAndCurrency(String tb,String curr)
	{
	 	String code = "";
		for (CreditSubProductType subProduct: entity.getCreditSubProductTypes())
		{
			if (subProduct.getTerbank().equals(tb)  && subProduct.getCurrency().getCode().equals(curr))
				return subProduct.getCode();
		}
		return code;
	}

	/**
	 * @return  список номеров ТБ
	 * @throws BusinessException
	 */
	public Set<String> getTbNumbers() throws BusinessException
	{
		return new HashSet<String>(departmentService.getTerbanksNumbers());
	}

	/**
	 * @return Добавленные суб продукты.
	 */
	public  Set<CreditSubProductType> getAddSubProducts()
	{
		return this.addSubProducts;
	}
}
