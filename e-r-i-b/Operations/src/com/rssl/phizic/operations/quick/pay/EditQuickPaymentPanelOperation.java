package com.rssl.phizic.operations.quick.pay;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.business.quick.pay.Constants;
import com.rssl.phizic.business.quick.pay.PanelBlock;
import com.rssl.phizic.business.quick.pay.QuickPaymentPanel;
import com.rssl.phizic.business.quick.pay.QuickPaymentPanelService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityWithImageOperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.*;

/**
 * @author komarov
 * @ created 09.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditQuickPaymentPanelOperation extends EditDictionaryEntityWithImageOperationBase
{
	public static final String IMAGE_ID_PREFIX = "icon";

	private static final QuickPaymentPanelService quickPaymentPanelService = new QuickPaymentPanelService();
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final ImageService imageService = new ImageService();
	private QuickPaymentPanel panel;
	private List<String> allowedDepartments;
	private Long[] imageIds;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		for(int i = 0; i < Constants.MAX_NUM_OF_PROVIDERS; i++)
			addImage(IMAGE_ID_PREFIX + i, null);
		panel = new QuickPaymentPanel();
		panel.setPanelBlocks(new ArrayList<PanelBlock>());
		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();
	}

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		panel = quickPaymentPanelService.findById(id, getInstanceName());

		if(panel == null)
			throw new BusinessLogicException("Панель быстрой оплаты с id="+id+" не найдена.");

		List<PanelBlock> blocks = panel.getPanelBlocks();
		for(int i = 0; i < blocks.size(); i++)
		{
			Image image = blocks.get(i).getImage();
			if (image == null)
				addImage(IMAGE_ID_PREFIX + i, null);
			else
				addImage(IMAGE_ID_PREFIX + i, image.getId());
		}

		for(int i = blocks.size(); i < Constants.MAX_NUM_OF_PROVIDERS; i++)
		{
			addImage(IMAGE_ID_PREFIX + i, null);
		}
		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

		if(!AllowedDepartmentsUtil.isAllowedTerbanksNumbers(new ArrayList<String>(panel.getDepartments())))
 	    {
		   throw new AccessException("Вы не можете отредактировать данную панель быстрой оплаты,"+
				     " так как не имеете доступа ко всем подразделениям банка, для которых она была создана.");
	    }
	}

	/**
	 *
	 */
	public void setImageIds(Long[] imageIds)
	{
		this.imageIds = imageIds;
	}


	/**
	 * Устанавливает депертаменты по departmentIds
	 * @param departmentIds идентификаторы департаментов
	 * @throws BusinessException
	 */
	public void setDepartments(String[] departmentIds) throws BusinessException
	{
		panel.setDepartments(getDepartments(departmentIds));
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
	 * Удаляет все блоки поставщиков.
	 */
	public void clearProviders()
	{
		panel.getPanelBlocks().clear();
	}

	/**
	 * Поиск тербанков для которых уже созданы ПБО.
	 * @return Тербанки
	 * @throws BusinessException
	 */
	private String getDublicateTRB() throws BusinessException
	{
		List<Department> departmentsWithPanels = quickPaymentPanelService.findTRBwithQPP(getInstanceName());
		departmentsWithPanels.retainAll(panel.getDepartments());
		StringBuilder s = new StringBuilder((departmentsWithPanels.size() > 1 ? "ов": "а"));
		boolean first = true;
		for(Department department : departmentsWithPanels)
		{
			s.append((first ? " " : ", ") +department.getName());
			first = false;
		}		
		return s.toString();
	}

	private Image getProviderImage(Long providerId) throws BusinessException
	{
		Long imageId = providerService.findImageIdById(providerId, getInstanceName());
		if (imageId == null)
			return null;
		return imageService.findByIdForUpdate(imageId,getInstanceName());
	}

	public void doSave() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					List<PanelBlock> blocks = panel.getPanelBlocks();
					for (int i = 0; i < blocks.size(); i++)
					{
						PanelBlock block = blocks.get(i);
						Image image = saveImage(IMAGE_ID_PREFIX + imageIds[i]);
						if (image == null)
							image = getProviderImage(block.getProviderId());
						block.setImage(image);
					}
					quickPaymentPanelService.addOrUpdate(panel, getInstanceName());
					return null;							
				}
			});
		}
		catch(ConstraintViolationException cve)
		{
			if(cve.getCause().getMessage().toUpperCase().contains("DEPARTMENT_ID"))
			{
			    throw new BusinessLogicException("Панель для тербанк"+ getDublicateTRB() +" уже назначена. Пожалуйста, измените список тербанков.", cve);
			}
			if(cve.getCause().getMessage().toUpperCase().contains("NAME_UNIQUE"))
			{
				throw new BusinessLogicException("Панель с таким названием уже существует. Пожалуйста, введите другое название.", cve);
			}
			throw new BusinessException(cve);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public QuickPaymentPanel getEntity()
	{
		return panel;
	}
}
