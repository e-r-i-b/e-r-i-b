package com.rssl.phizic.operations.basket.fieldFormula;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.basketident.*;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.fields.FieldDescriptionService;
import com.rssl.phizic.business.fields.FieldDescriptionShortcut;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author osminin
 * @ created 03.07.15
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������������� ������ ����� �� � ���������� ��������������(���������) �������
 */
public class EditFieldFormulaOperation extends OperationBase
{
	private static BasketIdentifierService basketIdentifierService = new BasketIdentifierService();
	private static FieldFormulaService fieldFormulaService = new FieldFormulaService();
	private static ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static FieldDescriptionService fieldDescriptionService = new FieldDescriptionService();

	private List<FieldDescriptionShortcut> fieldDescriptions;
	private Set<AttributeForBasketIdentType> attributes;
	private List<FieldFormula> formulas;
	private ServiceProviderShort serviceProvider;

	/**
	 * ������������� �������� ��� ����� ������
	 * @param externalId ������� ������������� ���������� �����
	 * @param identId ������������� ���������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initializeNew(String externalId, Long identId) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(externalId))
		{
			throw new BusinessException("������� ������������� ���������� ����� �� ����� ���� null.");
		}
		if (identId == null)
		{
			throw new BusinessException("������������� ��������� �� ����� ���� null.");
		}

		initializeServiceProvider(externalId);
		initializeFieldDescription();
		initializeAttributes(identId);
		initializeFormulas(identId);
	}

	/**
	 * ������������� ��������
	 * @param id ������������� ���������� �����
	 * @param identId ������������� ���������
	 */
	public void initialize(Long id, Long identId) throws BusinessException, BusinessLogicException
	{
		if (id == null)
		{
			throw new BusinessException("������������� ���������� ����� �� ����� ���� null.");
		}
		if (identId == null)
		{
			throw new BusinessException("������������� ��������� �� ����� ���� null.");
		}

		initializeServiceProvider(id);
		initializeFieldDescription();
		initializeAttributes(identId);
		initializeFormulas(identId);
	}

	/**
	 * ��������� ����������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		fieldFormulaService.addOrUpdate(formulas);
	}

	/**
	 * ������� ��������� ������ ����������
	 * @param formulasToRemove ��������� ������
	 * @throws BusinessException
	 */
	public void remove(List<FieldFormula> formulasToRemove) throws BusinessException
	{
		if (CollectionUtils.isEmpty(formulasToRemove))
		{
			fieldFormulaService.remove(formulas);
		}
		else
		{
			fieldFormulaService.remove(formulasToRemove);
		}
	}

	/**
	 * �������� ����� ������
	 * @param newFormulas ������
	 * @throws BusinessException
	 */
	public void add(List<FieldFormula> newFormulas) throws BusinessException, BusinessLogicException
	{
		fieldFormulaService.addOrUpdate(newFormulas);
	}

	/**
	 * @return ������ ������
	 */
	public List<FieldFormula> getFormulas()
	{
		return formulas;
	}

	/**
	 * @return ����� �������� ����� ��
	 */
	public List<FieldDescriptionShortcut> getFieldDescriptions()
	{
		return fieldDescriptions;
	}

	/**
	 * @return ����� ��������� �������������� �������
	 */
	public Set<AttributeForBasketIdentType> getAttributes()
	{
		return attributes;
	}

	/**
	 * @return ��������� �����
	 */
	public ServiceProviderShort getServiceProvider()
	{
		return serviceProvider;
	}

	private void initializeFormulas(Long identId) throws BusinessException
	{
		formulas = fieldFormulaService.getByIdentIdAndProviderId(identId, serviceProvider.getId());
		if (CollectionUtils.isNotEmpty(formulas))
		{
			Collections.sort(formulas, new Comparator<FieldFormula>()
			{
				public int compare(FieldFormula formula, FieldFormula formula2)
				{
					return formula.getId().compareTo(formula2.getId());
				}
			});
		}
	}

	private void initializeFieldDescription() throws BusinessException, BusinessLogicException
	{
		fieldDescriptions = fieldDescriptionService.getByRecipientId(serviceProvider.getId());
		if (CollectionUtils.isEmpty(fieldDescriptions))
		{
			throw new BusinessLogicException("��������� ����� �� ����� ����� ��� ��������.");
		}
	}

	private void initializeAttributes(Long identId) throws BusinessException, BusinessLogicException
	{
		BasketIndetifierType basketIndetifierType = basketIdentifierService.getInedtifier(identId);
		if (basketIndetifierType == null)
		{
			throw new BusinessException("������������� ������� �� id " + identId + " �� ������.");
		}

		attributes = (Set<AttributeForBasketIdentType>) basketIndetifierType.getAttributes().values();
		if (CollectionUtils.isEmpty(attributes))
		{
			throw new BusinessLogicException("������������� ������� �� ����� ��������� ��� ��������.");
		}
	}

	private void initializeServiceProvider(String externalId) throws BusinessException
	{
		serviceProvider = serviceProviderService.findShortProviderBySynchKey(externalId);
		if (serviceProvider == null)
		{
			throw new BusinessException("��������� ����� �� �������������� " + externalId + " �� ������.");
		}
	}

	private void initializeServiceProvider(Long id) throws BusinessException
	{
		serviceProvider = serviceProviderService.findShortProviderById(id);
		if (serviceProvider == null)
		{
			throw new BusinessException("��������� ����� �� �������������� " + id + " �� ������.");
		}
	}
}
