package com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.condition;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProductService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductTypeService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Moshenko
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 * �������� �������������� ������� �� ��������� ���������.
 */
public class EditCreditProductConditionOperation extends OperationBase implements EditEntityOperation
{
	private static final CreditProductConditionService  conditionService = new CreditProductConditionService();
	private static final CreditProductService productService = new CreditProductService();
	private static final CreditProductTypeService typeService = new CreditProductTypeService();
	private static final DepartmentService departmentService = new DepartmentService();
	private CreditProductCondition entity;
	private Set<CurrencyCreditProductCondition> remCurrCond;
	/**
	 *
	 * �������������� ���������� �������.
	 * @param id ���������� �������.
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		entity = conditionService.findeById(id);

		if (entity == null)
			throw new BusinessException("������� �� ���������� �������� � Id = " + id + " �� �������.");

	}
	/**
	 * �������� ������ ���������� �������.
	 * @throws BusinessException
	 */
	public void initializeNew() throws BusinessException
	{
		entity = new CreditProductCondition();
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		//������������ � ��� �� ������ ������������.
		if (entity.isPublished() && !LoanClaimHelper.hasActiveCurrencyCondition(entity))
			throw new BusinessLogicException("��� ��������������� �������� ������ ���� ������ ���� �� ���� " +
					"������� � ������� �����");
		if (entity.getMinDuration().isEmpty())
			throw new BusinessLogicException("����� �������� �� ����������� ��� ���������� ");
		if (entity.getMaxDuration().isEmpty())
			throw new BusinessLogicException("����� �������� �� ����������� ��� ���������� ");
		//�������� ����� ��������.
		if (entity.getMinDuration().getDuration() >= entity.getMaxDuration().getDuration())
		throw new BusinessLogicException("����� �������� �� ������ ���� ������ ������ ��������  ��." +
				" ����������, ��������� ������ � ��������� ����� �������� �������. ");
		//�������� ��������������� ������.
		if(entity.getMinInitialFee() != null && entity.getMaxInitialFee() != null &&
				entity.getMinInitialFee().compareTo(entity.getMaxInitialFee()) >= 0)
			throw new BusinessLogicException("������ ��������������� ������ �� � �� ����� ���� ������ ������ ������������� ������ ��." +
					" ����������, ��������� ����� ������������ � ����� ������������� ��������������� ������.");
		//�������� ����������� ��������������.
		if(StringHelper.isEmpty(entity.getDepartmentsStr()))
			throw new BusinessLogicException("����������, ������� ���� �� ���� �������������, � ������� ����� �������� ������ ��������� �������.");
		 //�������� ����������� ������������ ����� TransactSM
		if (entity.isTransactSMPossibility())
		{
			if (StringHelper.isEmpty(entity.getCreditProductType().getCode())||
				StringHelper.isEmpty(entity.getCreditProduct().getCode()))
			throw new BusinessLogicException("��������� ������ ����� ������� �Transact SM� ����� ������ �� ��������� ���������, ��� ������� ������ ��� ���� �������� � ��� ��������.");

		}

		conditionService.addOrUpdate(entity);

		if (!remCurrCond.isEmpty())
			conditionService.remove(remCurrCond);

	}

	/**
	 * //����������  ����������� �������� ����� ���������� ��������� � �����.
	 * @param currCondSet ����� ���������� �������
	 */
	public void currencyConditionTests(Set<CurrencyCreditProductCondition> currCondSet) throws BusinessException, BusinessLogicException
	{
		for(CurrencyCreditProductCondition cond:currCondSet)
		{
			//������� ����� ��������.
			Money minLimit = cond.getMinLimitAmount();
			Money maxLimit = cond.getMaxLimitAmount();
			if (maxLimit != null &&	minLimit.getDecimal().compareTo(maxLimit.getDecimal()) >= 0)
				throw new BusinessLogicException("������ �������� �� �� ����� ���� ������ ������ �������� ��. ���������� ��������� ����������� � ������������ ����� ��������.");
			//�������� ���������� �����.
			if (cond.getMinPercentRate().compareTo(cond.getMaxPercentRate()) > 0)
				throw new BusinessLogicException("����������� ������ �� �� ����� ���� ������ ����������� ������ �� ����������, ��������� ����������� � ������������ ���������� ������.");
		}
	}
	/**
	 * @param remCurrCond �� �������� ������� �� ��������.
	 */
	public void setRemoveCurrCond(Set<CurrencyCreditProductCondition> remCurrCond)
	{
		this.remCurrCond = remCurrCond;
	}

	/**
	 * @param currCode ��� ������
	 * @return ������
	 * @throws GateException
	 */
	public Currency getCurrency(String currCode) throws GateException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		return currencyService.findByNumericCode(currCode);
	}

	public CreditProductCondition getEntity()
	{
		return  entity;
	}
	/**
	 * @return ������ ���� ����� �������� �������������.
	 * * @throws BusinessException
	 */
	public List<String> getAllTb() throws BusinessException
	{
		return departmentService.getTerbanksNumbers();
	}
	/**
	 *
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public CreditProduct getCreditProduct(Long id) throws BusinessException
	{
		return productService.findById(id);
	}
	/**
	 *
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public CreditProductType getCreditProductType(Long id) throws BusinessException
	{
		return typeService.findById(id);
	}
	/**
	 *
	 * @param currCodeNumber �������� ��� ������.
	 * @return ���������� ����� ���������� ������� �� ������ �������, ���� ������� ����.
	 *  ����� ��������� ���������� ������� � ����� ������ �������� ������ �������.
	 *  ��� ������ ������ ������� ����� ���� ������ ���� ����� ��������� �������.
	 */
	public CurrencyCreditProductCondition getNewCurrCondition(String currCodeNumber) throws BusinessException
	{
	    return conditionService.getNewCurrCondition(entity,getCurrencyCodeNumberFromCode(currCodeNumber));
	}

	/**
	 *
	 * @param currCodeNumber �������� ��� ������.
	 * @return ���������� ������� ���������� ������� �� ������ �������, ���� ������� ���.
	 * @throws BusinessException
	 */
	public CurrencyCreditProductCondition getCurrentCurrCondition(String currCodeNumber) throws BusinessException
	{
		return conditionService.getActiveCurrCondition(entity,getCurrencyCodeNumberFromCode(currCodeNumber));
	}

	/**
	 *  ��������� ������� ����������� ��� �������� �� ��������� �������, ���� ����.
	 * @param available ������� ����������� ��� �������� �� ���������  �������.
	 * @param currCodeNumber �������� ��� ������.
	 */
	public void  updateClientAvaliable(boolean available,String currCodeNumber) throws BusinessException
	{
		CurrencyCreditProductCondition cond = conditionService.getActiveCurrCondition(entity,getCurrencyCodeNumberFromCode(currCodeNumber));
		if (cond != null)
			cond.setClientAvaliable(available);
	}

	/**
	 * @param startDate ���� ������ �������� ����������� �������
	 * @return ������ ����  ����������� ������ � ��������
	 */
	public Calendar getCorrectTime(Date startDate)
	{
		Calendar resultDate = Calendar.getInstance();
	 	int hour = resultDate.get(Calendar.HOUR_OF_DAY);
	 	int minut = resultDate.get(Calendar.MINUTE);
		resultDate.setTime(startDate);
		resultDate.set(Calendar.HOUR_OF_DAY,hour);
		resultDate.set(Calendar.MINUTE,minut);
		return resultDate;
	}

	private String getCurrencyCodeNumberFromCode(String code) throws BusinessException
	{
		if ("810".equals(code) || "643".equals(code))
			return "RUB";
		else if ("840".equals(code))
			return "USD";
		else if ("978".equals(code))
			return "EUR";
		throw new BusinessException("����������� ��� ������.");
	}
}
