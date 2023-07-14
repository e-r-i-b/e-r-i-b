package com.rssl.phizic.operations.offerNotification;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.advertising.Constants;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.personalOffer.*;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.SaveImageOperationBase;

import java.util.*;

/**
 * @author lukina
 * @ created 20.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditOfferNotificationOperation    extends SaveImageOperationBase implements EditEntityOperation
{
	public static final String IMAGE_AREA_IMAGE_ID = "ImageArea";
	public static final String BUTTON_IMAGE_ID_PREFIX = "Button";

	private static final PersonalOfferNotificationService notificationService = new  PersonalOfferNotificationService();

	private PersonalOfferNotification offerNotification;
	private List<String> allowedDepartments;

	private List<PersonalOfferNotificationButton> createButtons() throws BusinessException
	{
		List<PersonalOfferNotificationButton> buttons = new ArrayList<PersonalOfferNotificationButton>();
		for(long i = 0L; i < Constants.NUMBER_OF_BUTTONS; i++)
		{
			buttons.add(new PersonalOfferNotificationButton(i));
			addImage(BUTTON_IMAGE_ID_PREFIX + i, null);
		}
		return buttons;
	}

	private void updateSavedButtons(PersonalOfferNotification offerNotification1) throws BusinessException
	{
		//ƒобавл€ем кнопки дл€ заполнени€.
		Set<Long> orderIndxs = new HashSet<Long>();
		List<PersonalOfferNotificationButton> buttons = offerNotification1.getButtons();
		for(PersonalOfferNotificationButton button : buttons)
		{
			orderIndxs.add(button.getOrderIndex());
			addImage(BUTTON_IMAGE_ID_PREFIX + button.getOrderIndex(), button.getImageId());
		}

		for(long i = 0L; i < Constants.NUMBER_OF_BUTTONS; i++)
		{
			if(!orderIndxs.contains(i))
			{
				buttons.add(new PersonalOfferNotificationButton(i));
				addImage(BUTTON_IMAGE_ID_PREFIX + i, null);
			}
		}
	}


	private List<PersonalOfferNotificationArea> createAreas() throws BusinessException
	{
		List<PersonalOfferNotificationArea> areas = new ArrayList<PersonalOfferNotificationArea>();
		Long i = 1L;
		areas.add(new PersonalOfferNotificationArea("title", i++));
		areas.add(new PersonalOfferNotificationArea("text", i++));
		areas.add(new PersonalOfferNotificationArea("image", i++));
		addImage(IMAGE_AREA_IMAGE_ID, null);
		areas.add(new PersonalOfferNotificationArea("buttons", i));
		return areas;
	}

	public void initialize() throws BusinessException, BusinessLogicException
	{
		offerNotification = new PersonalOfferNotification();
		offerNotification.setButtons(createButtons());
		offerNotification.setAreas(createAreas());
		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();
	}

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		offerNotification = notificationService.findById(id);

		if (offerNotification == null)
			throw new BusinessLogicException("–екламный блок с id = " + id + " не найден");

		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

		if(!AllowedDepartmentsUtil.isAllowedTerbanksNumbers(offerNotification.getDepartments()))
		{
			throw new AccessException("¬ы не можете отредактировать данное уведомление,"+
					" так как не имеете доступа ко всем подразделени€м банка, дл€ которых оно было создано.");
		}

		updateSavedButtons(offerNotification);
		prepareAdvertisingBlock(offerNotification);
		//добавл€ем в обработку картинку дл€ баннера
		addImage(IMAGE_AREA_IMAGE_ID, offerNotification.getImageId());
	}

	/**
	 * ”станавливает депертаменты по departmentIds
	 * @param departmentIds идентификаторы департаментов
	 * @throws BusinessException
	 */
	public void setDepartments(String[] departmentIds) throws BusinessException
	{
		offerNotification.setDepartments(getDepartments(departmentIds));
	}

	/**
	 * ѕолучает департаменты по departmentIds
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

	@Transactional
	public void save() throws BusinessException
	{
		//обновл€ем список кнопок кнопки.
		List<PersonalOfferNotificationButton> buttons = offerNotification.getButtons();
		for(Iterator<PersonalOfferNotificationButton> it = buttons.iterator(); it.hasNext();)
		{
			PersonalOfferNotificationButton button = it.next();
			if(!button.needSave())
			{
				//”дал€ем незаполненные кнопки.
				removeImage(BUTTON_IMAGE_ID_PREFIX + button.getOrderIndex());
				it.remove();
			}
			else
			{
				//сохран€ем изображени€, прив€занные к кнопке
				Image image = saveImage(BUTTON_IMAGE_ID_PREFIX + button.getOrderIndex());
				button.setImageId(image != null ? image.getId() : null);
			}
		}
		//сохран€ем картинку банера
		Image image = saveImage(IMAGE_AREA_IMAGE_ID);
		offerNotification.setImageId(image != null ? image.getId() : null);

		notificationService.addOrUpdate(offerNotification);
	}

	public Object getEntity()
	{
		return offerNotification;
	}

	//сортируем обласи баннера в пор€дке отображени€
	private void prepareAdvertisingBlock(PersonalOfferNotification personalOfferNotification)
	{
		PersonalOfferOrderedFieldComparator comparator = new PersonalOfferOrderedFieldComparator();
		Collections.sort(personalOfferNotification.getButtons(), comparator);
		Collections.sort(personalOfferNotification.getAreas(), comparator);
	}
}
