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
	 * Проинициализировать операцию
	 * @param id идентификатор
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
				throw new BusinessLogicException("Категория с id " + id + " не найдена.");
			if(category.getOwnerId() != null)
				throw new BusinessLogicException("Вы не можете редактировать клиентскую категорию");
		}
	}

	/**
	 * Сохранение данных
	 * @throws BusinessException, BusinessLogicException
	 */
	private void saveCategory() throws BusinessException, BusinessLogicException, ConstraintViolationException
	{
		//список кодов, которые тянутся из БД по порции кодов с формы
		List<MerchantCategoryCode> dbMCC = new ArrayList<MerchantCategoryCode>();
		//коды, которые изменились или создались при редактировании
		List<MerchantCategoryCode> updatedCodes = new ArrayList<MerchantCategoryCode>();
		//коды, которые были у операции в БД до редактирования
		List<Long> operationCodes = merchantCategoryCodeService.getCategoryMCCodes(category.getExternalId());

		//запомним коды, пришедшие с формы
		List<Long> temp = new ArrayList<Long>(mccCodes);
		//из кодов, пришедших с формы вычтем те, что уже лежат в БД
		mccCodes.removeAll(operationCodes);
		//из кодов, лежащих в БД вычтем коды, которые пришли с формы,
		//получим те коды, которые нужно будет удалить
		operationCodes.removeAll(temp);
		//если новая категория или список пуст, то удалять точно не надо
		if (!operationCodes.isEmpty() && category.getId() != null)
			merchantCategoryCodeService.updateOrDeleteMCCByCodes(operationCodes, category.isIncome());

		boolean categoryIsIncome = category.isIncome();

		int parts = mccCodes.size() / MAX_PART_LIST_SIZE + 1;//количество порций

		for (int j=0; j < parts; j++)
		{
			//выбираем кусок данных из списка кодов с формы
			List<Long> partList = mccCodes.subList(0, MAX_PART_LIST_SIZE > mccCodes.size() ? mccCodes.size() : MAX_PART_LIST_SIZE);
			//получаем эти коды в базе
			if (!partList.isEmpty())
				dbMCC = merchantCategoryCodeService.findMCCByCode(partList);
			//проверяем каждый код из базы
			for(MerchantCategoryCode code : dbMCC)
			{
				//если у данного кода категория не пуста и не соответствует нашей, то это ошибка, пора падать
				if (code.getOperationCategory(categoryIsIncome) != null  && !code.getOperationCategory(categoryIsIncome).equals(category))
				{
					throw new BusinessLogicException("MCC – код: " + code.getCode()+ " уже указан для категории " + code.getOperationCategory(categoryIsIncome).getName() + ".");
				}
				//а если категория пуста, то обновляем эту категорию, закидываем её в список для обновления и удаляем из порционного
				else if (code.getOperationCategory(categoryIsIncome) == null)
				{
					code.addOperationCategory(category);
					updatedCodes.add(code);
					partList.remove(code.getCode());
				}
			}
			//всё, что осталось в порционном списке - новые коды для категории, создаём сущности и закидываем их в порционный лист
			for (Long code : partList)
			{
				MerchantCategoryCode newCode = new MerchantCategoryCode();
				newCode.setCode(code);
				newCode.addOperationCategory(category);
				updatedCodes.add(newCode);
			}
			//чистим порционный список, чтобы в основном списке остались только те категории, которые мы еще не трогали
			partList.clear();
		}

		cardOperationCategoryService.addOrUpdate(category);
		if (!updatedCodes.isEmpty())
			merchantCategoryCodeService.addOrUpdateMCCList(updatedCodes);
	}

	/**
	 * Сохранить данные
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
			throw new BusinessLogicException("Категория с таким наименованием уже заведена.", e);
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
	 * Получить просматриваемую/редактируемую сущность
	 * @return просматриваемая/редактируемая сущность.
	 */
	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return category;
	}

	/**
	 * Проставить список МСС-кодов с формы
	 * @param mccCodes список кодов
	 */
	public void setMccCodes(List<Long> mccCodes)
	{
		this.mccCodes = mccCodes;
	}

	/**
	 * Список mcc-кодов, привязанных к категории
	 * @return список строкой
	 */
	public String getCategoryMccCodes()
	{
		return merchantCategoryCodeService.getByCategoryAsString(category.getExternalId());
	}

	/**
	 * Возвращает, есть ли у категории привязанные операции
	 * @return есть ли привязанные операции
	 */
	public boolean categoryContainsOperations()
	{
		try
		{
			if (category.getId() != null)  //если категория новая, то и привязано к ней ничего быть не может.
				return operationService.categoryContainsOperations(category.getId());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		return false;
	}
}
