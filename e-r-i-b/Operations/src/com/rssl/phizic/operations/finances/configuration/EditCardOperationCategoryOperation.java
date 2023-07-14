package com.rssl.phizic.operations.finances.configuration;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryService;
import com.rssl.phizic.business.dictionaries.finances.MerchantCategoryCode;
import com.rssl.phizic.business.dictionaries.finances.MerchantCategoryCodeService;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Koptyaev
 * @ created 17.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EditCardOperationCategoryOperation extends OperationBase implements EditEntityOperation
{
	protected static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private static final CardOperationCategoryService cardOperationCategoryService = new CardOperationCategoryService();
	private static final MerchantCategoryCodeService merchantCategoryCodeService = new MerchantCategoryCodeService();
	private static final CardOperationService operationService = new CardOperationService();

	private final int MAX_PART_LIST_SIZE = ConfigFactory.getConfig(IPSConfig.class).getDatabaseMaxParams();

	private List<Long> mccCodes = new ArrayList<Long>();
	private CardOperationCategory category;

	/**
	 * ������������������� ��������
	 * @param id �������������
	 * @throws BusinessException, BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		if (id == null)
		{
			category = new CardOperationCategory();
			category.setCash(true);
			category.setCashless(true);
		}
		else
		{
			category = cardOperationCategoryService.findById(id);
			if(category == null)
				throw new BusinessLogicException("��������� � id " + id + " �� �������.");
			if(category.getOwnerId() != null)
				throw new BusinessLogicException("�� �� ������ ������������� ���������� ���������");
		}
	}

	/**
	 * ���������� ������
	 * @throws BusinessException, BusinessLogicException
	 */
	private void saveCategory() throws BusinessException, BusinessLogicException, ConstraintViolationException
	{
		//������ �����, ������� ������� �� �� �� ������ ����� � �����
		List<MerchantCategoryCode> dbMCC = new ArrayList<MerchantCategoryCode>();
		//����, ������� ���������� ��� ��������� ��� ��������������
		List<MerchantCategoryCode> updatedCodes = new ArrayList<MerchantCategoryCode>();
		//����, ������� ���� � �������� � �� �� ��������������
		List<Long> operationCodes = merchantCategoryCodeService.getCategoryMCCodes(category.getExternalId());

		//�������� ����, ��������� � �����
		List<Long> temp = new ArrayList<Long>(mccCodes);
		//�� �����, ��������� � ����� ������ ��, ��� ��� ����� � ��
		mccCodes.removeAll(operationCodes);
		//�� �����, ������� � �� ������ ����, ������� ������ � �����,
		//������� �� ����, ������� ����� ����� �������
		operationCodes.removeAll(temp);
		//���� ����� ��������� ��� ������ ����, �� ������� ����� �� ����
		if (!operationCodes.isEmpty() && category.getId() != null)
			merchantCategoryCodeService.updateOrDeleteMCCByCodes(operationCodes, category.isIncome());

		boolean categoryIsIncome = category.isIncome();

		int parts = mccCodes.size() / MAX_PART_LIST_SIZE + 1;//���������� ������

		for (int j=0; j < parts; j++)
		{
			//�������� ����� ������ �� ������ ����� � �����
			List<Long> partList = mccCodes.subList(0, MAX_PART_LIST_SIZE > mccCodes.size() ? mccCodes.size() : MAX_PART_LIST_SIZE);
			//�������� ��� ���� � ����
			if (!partList.isEmpty())
				dbMCC = merchantCategoryCodeService.findMCCByCode(partList);
			//��������� ������ ��� �� ����
			for(MerchantCategoryCode code : dbMCC)
			{
				//���� � ������� ���� ��������� �� ����� � �� ������������� �����, �� ��� ������, ���� ������
				if (code.getOperationCategory(categoryIsIncome) != null  && !code.getOperationCategory(categoryIsIncome).equals(category))
				{
					throw new BusinessLogicException("MCC � ���: " + code.getCode()+ " ��� ������ ��� ��������� " + code.getOperationCategory(categoryIsIncome).getName() + ".");
				}
				//� ���� ��������� �����, �� ��������� ��� ���������, ���������� � � ������ ��� ���������� � ������� �� �����������
				else if (code.getOperationCategory(categoryIsIncome) == null)
				{
					code.addOperationCategory(category);
					updatedCodes.add(code);
					partList.remove(code.getCode());
				}
			}
			//��, ��� �������� � ���������� ������ - ����� ���� ��� ���������, ������ �������� � ���������� �� � ���������� ����
			for (Long code : partList)
			{
				MerchantCategoryCode newCode = new MerchantCategoryCode();
				newCode.setCode(code);
				newCode.addOperationCategory(category);
				updatedCodes.add(newCode);
			}
			//������ ���������� ������, ����� � �������� ������ �������� ������ �� ���������, ������� �� ��� �� �������
			partList.clear();
		}

		cardOperationCategoryService.addOrUpdate(category);
		if (!updatedCodes.isEmpty())
			merchantCategoryCodeService.addOrUpdateMCCList(updatedCodes);
	}

	/**
	 * ��������� ������
	 * @throws BusinessException, BusinessLogicException
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		try
        {
	        HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
	        {
		        public Void run(Session session) throws Exception
		        {
	                saveCategory();
			        return null;
		        }
	        }
	        );
        }
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("��������� � ����� ������������� ��� ��������.", e);
		}
        catch(BusinessLogicException e)
        {
	        throw e;
        }
        catch (BusinessException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
	}

	/**
	 * �������� ���������������/������������� ��������
	 * @return ���������������/������������� ��������.
	 */
	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return category;
	}

	/**
	 * ���������� ������ ���-����� � �����
	 * @param mccCodes ������ �����
	 */
	public void setMccCodes(List<Long> mccCodes)
	{
		this.mccCodes = mccCodes;
	}

	/**
	 * ������ mcc-�����, ����������� � ���������
	 * @return ������ �������
	 */
	public String getCategoryMccCodes()
	{
		return merchantCategoryCodeService.getByCategoryAsString(category.getExternalId());
	}

	/**
	 * ����������, ���� �� � ��������� ����������� ��������
	 * @return ���� �� ����������� ��������
	 */
	public boolean categoryContainsOperations()
	{
		try
		{
			if (category.getId() != null)  //���� ��������� �����, �� � ��������� � ��� ������ ���� �� �����.
				return operationService.categoryContainsOperations(category.getId());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		return false;
	}
}
