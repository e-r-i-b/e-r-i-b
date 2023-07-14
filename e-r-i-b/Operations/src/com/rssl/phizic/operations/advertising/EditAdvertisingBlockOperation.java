package com.rssl.phizic.operations.advertising;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.advertising.*;
import com.rssl.phizic.business.advertising.locale.AdvertisingButtonResources;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourcesBaseService;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.dictionaries.productRequirements.AccTypesRequirement;
import com.rssl.phizic.business.dictionaries.productRequirements.ProductRequirement;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityWithImageOperationBase;
import org.hibernate.Session;

import java.util.*;

/**
 * @author komarov
 * @ created 20.12.2011
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования баннера
 */

public class EditAdvertisingBlockOperation extends EditDictionaryEntityWithImageOperationBase
{
	public static final String IMAGE_AREA_IMAGE_ID = "ImageArea";
	public static final String BUTTON_IMAGE_ID_PREFIX = "Button";

	private static final AdvertisingService advertisingService = new  AdvertisingService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final DepositProductService depositProductService = new DepositProductService();
	private static final LanguageResourcesBaseService<AdvertisingButtonResources> ADVERTISING_BUTTON_SERVICE = new LanguageResourcesBaseService<AdvertisingButtonResources>(AdvertisingButtonResources.class);

	private AdvertisingBlock  advertising;
	private List<String> allowedDepartments;

	private List<AdvertisingButton> createButtons() throws BusinessException
	{
		List<AdvertisingButton> buttons = new ArrayList<AdvertisingButton>();
		for(Long i = 0L; i < Constants.NUMBER_OF_BUTTONS; i++)
		{
			buttons.add(new AdvertisingButton(i));
			addImage(BUTTON_IMAGE_ID_PREFIX + i, null);
		}
		return buttons;
	}

	private void updateSavedButtons(AdvertisingBlock advertising) throws BusinessException
	{
		//Добавляем кнопки для заполнения.
		Set<Long> orderIndxs = new HashSet<Long>();
		List<AdvertisingButton> buttons = advertising.getButtons();
		for(AdvertisingButton button : buttons)
		{
			orderIndxs.add(button.getOrderIndex());
			if (button.getImage() != null)
				addImage(BUTTON_IMAGE_ID_PREFIX + button.getOrderIndex(), button.getImage().getId());
			else
				addImage(BUTTON_IMAGE_ID_PREFIX + button.getOrderIndex(), null);
		}

		for(Long i = 0L; i < Constants.NUMBER_OF_BUTTONS; i++)
		{			
			if(!orderIndxs.contains(i))
			{
				buttons.add(new AdvertisingButton(i));
				addImage(BUTTON_IMAGE_ID_PREFIX + i, null);
			}
		}
	}


	private List<AdvertisingArea> createAreas() throws BusinessException
	{
		List<AdvertisingArea> areas = new ArrayList<AdvertisingArea>();
		Long i = 1L;
		areas.add(new AdvertisingArea("title", i++));
		areas.add(new AdvertisingArea("text", i++));
		areas.add(new AdvertisingArea("image", i++));
		addImage(IMAGE_AREA_IMAGE_ID, null);
		areas.add(new AdvertisingArea("buttons", i));
		return areas;
	}

	public void initialize() throws BusinessException, BusinessLogicException
	{
		advertising = new AdvertisingBlock();
		advertising.setButtons(createButtons());
		advertising.setAreas(createAreas());
		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();
	}

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		advertising = advertisingService.findById(id, getInstanceName());

		if (advertising == null)
			throw new BusinessLogicException("Рекламный блок с id = " + id + " не найден");

		Iterator<String> it = advertising.getDepartments().iterator();
		for(;it.hasNext();)
		{
			if(departmentService.getDepartment(it.next(), null, null, getInstanceName()) == null)
				it.remove();
		}

		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

		if(!AllowedDepartmentsUtil.isAllowedTerbanksNumbers(new ArrayList(advertising.getDepartments())))
 	    {
		   throw new AccessException("Вы не можете отредактировать данный рекламный блок,"+
				     " так как не имеете доступа ко всем подразделениям банка, для которых он был создан.");
	    }

		updateSavedButtons(advertising);
		prepareAdvertisingBlock(advertising);
		//добавляем в обработку картинку для баннера
		if (advertising.getImage() != null)
			addImage(IMAGE_AREA_IMAGE_ID, advertising.getImage().getId());
		else
			addImage(IMAGE_AREA_IMAGE_ID, null);
	}

	/**
	 * Устанавливает депертаменты по departmentIds
	 * @param departmentIds идентификаторы департаментов
	 * @throws BusinessException
	 */
	public void setDepartments(String[] departmentIds) throws BusinessException
	{
		advertising.setDepartments(getDepartments(departmentIds));
	}

	/**
	 * Получает департаменты по departmentIds
	 * @param departmentIds идентификаторы департаментов
	 * @return множество департаментов
	 * @throws BusinessException
	 */
	public Set<String> getDepartments(String[] departmentIds) throws BusinessException
	{
		Set<String> departments = new HashSet<String>();

		List<String> departmentsIdsList = Arrays.asList(departmentIds);
		for (String department : allowedDepartments)
		{
			if(departmentsIdsList.contains(department))
				departments.add(department);
		}

		return departments;
	}

	/**
	 * Возвращает текущий список требований к продуктам клиента
	 * @return список требований к продуктам клиента
	 */
	public Set<ProductRequirement> getRequirements()
	{
		return advertising.getRequirements();
	}

	/**
	 * Возвращает текущий список требований к видам вкладов клиента
	 * @return список требований к видам вкладов
	 */
	public Set<AccTypesRequirement> getAccTypesRequirement()
	{
		return advertising.getReqAccTypes();
	}

	public void doSave() throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(getInstanceName());
			trnExecutor.execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					//обновляем список кнопок кнопки.
					List<AdvertisingButton> buttons = advertising.getButtons();
					for(Iterator<AdvertisingButton> it = buttons.iterator(); it.hasNext();)
					{
						AdvertisingButton button = it.next();
						if(!button.needSave())
						{
							//Удаляем незаполненные кнопки.
							removeImage(BUTTON_IMAGE_ID_PREFIX + button.getOrderIndex());
							ADVERTISING_BUTTON_SERVICE.removeRes(button.getUuid(), getInstanceName());
							it.remove();
						}
						else
						{
							//сохраняем изображения, привязанные к кнопке
							button.setImage(saveImage(BUTTON_IMAGE_ID_PREFIX + button.getOrderIndex()));
						}
					}
					//сохраняем картинку банера
					advertising.setImage(saveImage(IMAGE_AREA_IMAGE_ID));

					advertisingService.addOrUpdate(advertising, getInstanceName());
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Object getEntity() 
	{
		return advertising;
	}
	
	//сортируем обласи баннера в порядке отображения
	private void prepareAdvertisingBlock(AdvertisingBlock advertisingBlock)
	{
		AdvertisingOrderedFieldComparator comparator = new AdvertisingOrderedFieldComparator();
		Collections.sort(advertisingBlock.getButtons(), comparator);
		Collections.sort(advertisingBlock.getAreas(), comparator);
	}

	/**
	 * @return Список типов вкладов
	 * @throws BusinessException
	 */
	public List<DepositProduct> getAccountTypes() throws BusinessException
	{
		return depositProductService.getAllProducts(getInstanceName());
	}

	/**
	 * @param depositProductId идентификатор депозитного продукта
	 * @return депозитный продукт
	 * @throws BusinessException
	 */
	public DepositProduct getDepositProduct(Long depositProductId) throws BusinessException
	{
		return depositProductService.findByProductId(depositProductId, getInstanceName());
	}

}
