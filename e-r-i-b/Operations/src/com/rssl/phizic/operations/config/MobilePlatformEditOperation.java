package com.rssl.phizic.operations.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.SaveImageOperationBase;
import org.hibernate.Session;

/**
 * Настройки mAPI в разрезе платформ. редактирование.
 * @author Jatsky
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 */

public class MobilePlatformEditOperation extends SaveImageOperationBase implements EditEntityOperation
{
	public static final String ICON_IMAGE_ID = "Icon";
	private MobilePlatform platform;
	private Boolean isNew = null;
	private static final SimpleService service = new SimpleService();

	/**
	 * Cохранить сущность.
	 * @throws com.rssl.phizic.business.BusinessLogicException логическая ошибка
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		Image image = saveImage(ICON_IMAGE_ID);
		platform.setPlatformIcon(image != null ? image.getId() : null);
		if (isNew)
			createPlatform();
		else
			updatePlatform();
	}

	/**
	 * Получить просматриваемую/редактируемую сущность
	 * @return просматриваемая/редактируемая сущность.
	 */
	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return getPlatform();
	}

	private void createPlatform() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					service.add(getPlatform());
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void updatePlatform() throws BusinessException
	{
		service.update(getPlatform());
	}

	public MobilePlatform getPlatform()
	{
		return platform;
	}

	public void setPlatform(MobilePlatform platform)
	{
		this.platform = platform;
	}

	/**
	 * Инициализировать операцию новой платформой
	 */
	public void initializeNew() throws BusinessException
	{
		platform = new MobilePlatform();
		isNew = true;
		addImage(ICON_IMAGE_ID, platform.getPlatformIcon());
	}

	/**
	 * Инициализировать операцию платформой по ИД
	 * @param platformId ИД платформы
	 */
	public void initialize(Long platformId) throws BusinessException, BusinessLogicException
	{
		MobilePlatform temp = service.findById(MobilePlatform.class, platformId);
		if (temp == null)
			throw new BusinessLogicException("Платформа с id=" + platformId + " не найдена");

		platform = temp;
		isNew = false;
		addImage(ICON_IMAGE_ID, platform.getPlatformIcon());
	}
}
