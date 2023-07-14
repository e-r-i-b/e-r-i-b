package com.rssl.phizic.web.offerNotification;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.personalOffer.*;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.offerNotification.EditOfferNotificationOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import com.rssl.phizic.web.image.ImageEditFormBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.*;

/**
 * @author lukina
 * @ created 20.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditOfferNotificationAction  extends ImageEditActionBase
{
	public static final int NUMBER_OF_BUTTONS = 3;

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditOfferNotificationOperation editOperation = createOperation(EditOfferNotificationOperation.class, "OfferNotificationManagment");
		Long id = frm.getId();
		if(id != null && id != 0)
		{
			editOperation.initialize(id);
			return editOperation;
		}
		editOperation.initialize();
		return editOperation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditOfferNotificationForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Calendar periodFrom = DateHelper.createCalendar(data.get("periodFrom"), null);


		Calendar periodTo = DateHelper.createCalendar(data.get("periodTo"), null);
		if (periodTo != null)
		{
			//Дата окончания периода отображения (до 23:59:59)
			periodTo.setTime(DateUtils.addDays(periodTo.getTime(), 1));
			periodTo.setTime(DateUtils.truncate(periodTo.getTime(),Calendar.DATE));
			periodTo.setTime(DateUtils.addSeconds(periodTo.getTime(),-1));
		}

		PersonalOfferNotification personalOfferNotification = (PersonalOfferNotification) entity;
		personalOfferNotification.setState(PersonalOfferState.valueOf((String) data.get("state")));
		personalOfferNotification.setName((String) data.get("name"));
		personalOfferNotification.setPeriodFrom(periodFrom);
		personalOfferNotification.setPeriodTo(periodTo);
		personalOfferNotification.setTitle((String) data.get("title"));
		personalOfferNotification.setText((String) data.get("text"));
		personalOfferNotification.setShowTime(Long.parseLong((String) data.get("showTime")));
		personalOfferNotification.setOrderIndex(Long.parseLong((String) data.get("orderIndex")));
		personalOfferNotification.setProductType(PersonalOfferProduct.valueOf((String) data.get("productType")));
		String frequencyDisplayDay = (String) data.get("frequencyDisplayDay");
		personalOfferNotification.setDisplayFrequencyDay(StringHelper.isEmpty(frequencyDisplayDay)? null : Long.parseLong(frequencyDisplayDay));
		personalOfferNotification.setDisplayFrequency(PersonalOfferDisplayFrequency.valueOf((String) data.get("frequencyDisplay")));

		int i = 0;
		for(PersonalOfferNotificationButton button : personalOfferNotification.getButtons())
		{
			button.setTitle((String)data.get("buttonTitle"+i));
			button.setUrl((String)data.get("buttonURL"+i));
			button.setShow((Boolean)data.get("buttonShow"+i));
			button.setOrderIndex(Long.parseLong((String)data.get("buttonOrder"+i)));
			i++;
		}

		for(PersonalOfferNotificationArea area : personalOfferNotification.getAreas())
		{
			area.setOrderIndex(Long.parseLong((String)data.get(area.getAreaName() + "AreaOrder")));
		}
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditOfferNotificationForm form    = (EditOfferNotificationForm) frm;
		ActionMessages msgs              = new ActionMessages();
		EditOfferNotificationOperation op = (EditOfferNotificationOperation) operation;

		//Проверяем добавлены ли департаменты.
		if(ArrayUtils.isEmpty(form.getDepartments()) || op.getDepartments(form.getDepartments()).isEmpty())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Добавьте департаменты!", false));
			return msgs;
		}

		// Если статус "активный", то баннер не может быть "пустым"
		PersonalOfferState state = PersonalOfferState.valueOf((String)form.getField("state"));
		if (state == PersonalOfferState.ACTIVE && notificationBlockIsEmpty(form))
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Пожалуйста, создайте области баннера. Например, заголовок, текст, кнопки баннера или изображение.", false));
			return msgs;
		}

		msgs.add(validateImageFormData(frm, operation));
		return msgs;
	}

	protected ActionMessages validateDiscSource(ImageEditFormBase form, String imageId)
	{
		String imageLinkURL = (String) form.getField("imageLinkURL");
		if (EditOfferNotificationOperation.IMAGE_AREA_IMAGE_ID.equals(imageId) && StringHelper.isNotEmpty(imageLinkURL) && form.isEmptyImage(imageId))
		{
			return getImageActionMessages("Пожалуйста, укажите изображение, на которое должна вести ссылка.", imageId);
		}
		if (StringHelper.isNotEmpty(imageId) && imageId.startsWith(EditOfferNotificationOperation.BUTTON_IMAGE_ID_PREFIX))
		{
			String buttonId = imageId.substring(EditOfferNotificationOperation.BUTTON_IMAGE_ID_PREFIX.length(), imageId.length());
			String buttonURL = (String) form.getField("buttonURL" + buttonId);
			String buttonTitle = (String) form.getField("buttonTitle" + buttonId);
			if (StringHelper.isEmpty(buttonURL) && (StringHelper.isNotEmpty(buttonTitle) || !form.isEmptyImage(imageId)))
			{
				return getImageActionMessages("Пожалуйста, укажите URL-адрес для кнопки "+ (Long.parseLong(buttonId) + 1) +".", imageId);
			}
		}
		return super.validateDiscSource(form, imageId);
	}

	protected ActionMessages validateExternalSource(String url, String imageId, ImageEditFormBase form)
	{
		String imageLinkURL = (String) form.getField("imageLinkURL");
		if (EditOfferNotificationOperation.IMAGE_AREA_IMAGE_ID.equals(imageId) && StringHelper.isNotEmpty(imageLinkURL) && StringHelper.isEmpty(url))
		{
			return getImageActionMessages("Пожалуйста, укажите изображение, на которое должна вести ссылка.", imageId);
		}
		return super.validateExternalSource(url, imageId, form);
	}

	protected void updateImageAdditionalData(Image image, String imageId, ImageEditFormBase form)
	{
		if (EditOfferNotificationOperation.IMAGE_AREA_IMAGE_ID.equals(imageId))
			image.setLinkURL((String) form.getField("imageLinkURL"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		updateOperationImageData(editOperation, editForm, validationResult);

		EditOfferNotificationForm form    = (EditOfferNotificationForm) editForm;
		EditOfferNotificationOperation op = (EditOfferNotificationOperation) editOperation;

		op.setDepartments(form.getDepartments());
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		EditOfferNotificationForm form =  (EditOfferNotificationForm) frm;
		PersonalOfferNotification personalOfferNotification = (PersonalOfferNotification) entity;


		Date periodFrom = DateHelper.toDate(personalOfferNotification.getPeriodFrom());
		if (periodFrom == null)
			periodFrom =  DateHelper.toDate(DateHelper.getCurrentDate());
		Date periodTo =   DateHelper.toDate(personalOfferNotification.getPeriodTo());

		form.setField("state",       personalOfferNotification.getState().name());
		form.setField("productType",       personalOfferNotification.getProductType().name());
		form.setField("name", personalOfferNotification.getName());
		form.setField("periodFrom",  periodFrom);
		form.setField("periodTo", periodTo);
		form.setField("title",       personalOfferNotification.getTitle());
		form.setField("text",        personalOfferNotification.getText());
		form.setField("showTime",    personalOfferNotification.getShowTime());
		form.setField("orderIndex", personalOfferNotification.getOrderIndex());
		form.setField("frequencyDisplay",    personalOfferNotification.getDisplayFrequency().name());
		form.setField("frequencyDisplayDay", personalOfferNotification.getDisplayFrequencyDay());

		for(PersonalOfferNotificationButton button : personalOfferNotification.getButtons())
		{
			long i = button.getOrderIndex();
			form.setField("buttonTitle"+i, button.getTitle());
			form.setField("buttonURL"+i,   button.getUrl());
			form.setField("buttonShow"+i,  button.getShow());
			form.setField("buttonOrder"+i, button.getOrderIndex());
		}
	}

	protected void updateFormAdditionalImageData(EditFormBase form, String imageId, Image image)
	{
		if (EditOfferNotificationOperation.IMAGE_AREA_IMAGE_ID.equals(imageId))
		{
			if(StringHelper.isNotEmpty(image.getLinkURL()))
				form.setField("imageLinkURL", image.getLinkURL());
		}
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormImageData(frm, operation);

		EditOfferNotificationForm form =  (EditOfferNotificationForm) frm;
		EditOfferNotificationOperation editOperation = (EditOfferNotificationOperation) operation;
		PersonalOfferNotification personalOfferNotification = (PersonalOfferNotification) editOperation.getEntity();

		//области баннера
		if (!form.isFromStart())
		{
			for(PersonalOfferNotificationArea area : personalOfferNotification.getAreas())
			{
				area.setOrderIndex(Long.parseLong((String)frm.getField(area.getAreaName() + "AreaOrder")));
			}
		}
		Collections.sort(personalOfferNotification.getAreas(), new PersonalOfferOrderedFieldComparator());
		form.setAreas(personalOfferNotification.getAreas());

		// департаменты
		if (form.isFromStart() && CollectionUtils.isNotEmpty(personalOfferNotification.getDepartments()))
		{
			form.setDepartments(personalOfferNotification.getDepartments().toArray(new String[personalOfferNotification.getDepartments().size()]));
		}
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		EditOfferNotificationOperation editOperation = (EditOfferNotificationOperation) operation;
		PersonalOfferNotification  offerNotification = (PersonalOfferNotification) editOperation.getEntity();
		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Данные успешно сохранены.", false), null);
		return  new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath() +"?id=" + offerNotification.getId(), true);
	}

	private boolean notificationBlockIsEmpty(EditOfferNotificationForm form)
	{
		String title = (String) form.getField("title");
		if (StringUtils.isNotEmpty(title))
			return false;

		String text = (String) form.getField("text");
		if (StringUtils.isNotEmpty(text))
			return false;

		for(int i = 0; i < NUMBER_OF_BUTTONS; i++)
		{
			String buttonURL = (String) form.getField("buttonURL" + i);
			String buttonTitle = (String) form.getField("buttonTitle" + i);
			if (StringHelper.isNotEmpty(buttonTitle) || StringHelper.isEmpty(buttonURL) || !form.isEmptyImage(EditOfferNotificationOperation.BUTTON_IMAGE_ID_PREFIX + i))
				return false;
		}

		if(!form.isEmptyImage(EditOfferNotificationOperation.IMAGE_AREA_IMAGE_ID))
			return false;

		return true;
	}
}
