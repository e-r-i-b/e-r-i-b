package com.rssl.phizic.web.advertising;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.advertising.*;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.dictionaries.productRequirements.AccTypesRequirement;
import com.rssl.phizic.business.dictionaries.productRequirements.ProductRequirement;
import com.rssl.phizic.business.dictionaries.productRequirements.ProductRequirementType;
import com.rssl.phizic.business.dictionaries.productRequirements.RequirementState;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.advertising.EditAdvertisingBlockOperation;
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
 * @author komarov
 * @ created 20.12.2011
 * @ $Author$
 * @ $Revision$
 *
 * Экшен редактирования баннера
 */

public class EditAdvertisingBlockAction extends ImageEditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditAdvertisingBlockOperation editOperation = createOperation(EditAdvertisingBlockOperation.class, "AdvertisingBlockManagment");
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
		return EditAdvertisingBlockForm.ADVERTISING_BLOCK_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Calendar periodFrom = DateHelper.createCalendar(data.get("periodFrom"), null);
		

		Calendar periodTo = DateHelper.createCalendar(data.get("periodTo"), null);
		if (periodTo != null)
		{
			//Дата окончания периода отображения (до 23:59:59)
			periodTo.setTime(DateUtils.addDays(periodTo.getTime(),1));
			periodTo.setTime(DateUtils.truncate(periodTo.getTime(),Calendar.DATE));
			periodTo.setTime(DateUtils.addSeconds(periodTo.getTime(),-1));
		}

		AdvertisingBlock advertising = (AdvertisingBlock) entity;
		advertising.setState(AdvertisingState.valueOf((String) data.get("state")));
        advertising.setAvailability(AdvertisingAvailability.valueOf((String) data.get("available")));
		advertising.setName((String)data.get("name"));
		advertising.setPeriodFrom(periodFrom);
		advertising.setPeriodTo(periodTo);
		advertising.setTitle((String)data.get("title"));
		advertising.setText((String)data.get("text"));
		advertising.setShowTime(Long.parseLong((String)data.get("showTime")));
		advertising.setOrderIndex(Long.parseLong((String)data.get("orderIndex")));

		int i = 0;
		for(AdvertisingButton button : advertising.getButtons())
		{
			button.setTitle((String)data.get("buttonTitle"+i));
			button.setUrl((String)data.get("buttonURL"+i));
			button.setShow((Boolean)data.get("buttonShow"+i));
			button.setOrderIndex(Long.parseLong((String)data.get("buttonOrder"+i)));
			i++;
		}

		for(AdvertisingArea area : advertising.getAreas())
		{
			area.setOrderIndex(Long.parseLong((String)data.get(area.getAreaName() + "AreaOrder")));
		}
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
	    EditAdvertisingBlockForm form    = (EditAdvertisingBlockForm) frm;
		ActionMessages msgs              = new ActionMessages();
		EditAdvertisingBlockOperation op = (EditAdvertisingBlockOperation) operation;

		//Проверяем добавлены ли департаменты.
		if(ArrayUtils.isEmpty(form.getDepartments()) || op.getDepartments(form.getDepartments()).isEmpty())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Добавьте департаменты!", false));
			return msgs;
		}

		// Если статус "активный", то баннер не может быть "пустым"
		AdvertisingState state = AdvertisingState.valueOf((String)form.getField("state"));
		if (state == AdvertisingState.ACTIVE && advBlockIsEmpty(form, op))
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
		if (EditAdvertisingBlockOperation.IMAGE_AREA_IMAGE_ID.equals(imageId) && StringHelper.isNotEmpty(imageLinkURL) && form.isEmptyImage(imageId))
		{
			return getImageActionMessages("Пожалуйста, укажите изображение, на которое должна вести ссылка.", imageId);
		}
		if (StringHelper.isNotEmpty(imageId) && imageId.startsWith(EditAdvertisingBlockOperation.BUTTON_IMAGE_ID_PREFIX))
		{
			String buttonId = imageId.substring(EditAdvertisingBlockOperation.BUTTON_IMAGE_ID_PREFIX.length(), imageId.length());
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
		if (EditAdvertisingBlockOperation.IMAGE_AREA_IMAGE_ID.equals(imageId) && StringHelper.isNotEmpty(imageLinkURL) && StringHelper.isEmpty(url))
		{
			return getImageActionMessages("Пожалуйста, укажите изображение, на которое должна вести ссылка.", imageId);
		}
		return super.validateExternalSource(url, imageId, form);
	}

	protected void updateImageAdditionalData(Image image, String imageId, ImageEditFormBase form)
	{
		if (EditAdvertisingBlockOperation.IMAGE_AREA_IMAGE_ID.equals(imageId))
			image.setLinkURL((String) form.getField("imageLinkURL"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		updateOperationImageData(editOperation, editForm, validationResult);

		EditAdvertisingBlockForm form    = (EditAdvertisingBlockForm) editForm;
		EditAdvertisingBlockOperation op = (EditAdvertisingBlockOperation) editOperation;

		op.setDepartments(form.getDepartments());

		Set<ProductRequirement> requirements = op.getRequirements();
		requirements.clear();
		List<String> selectedRequirements = Arrays.asList(form.getSelectedRequirements());
		for (ProductRequirementType type : ProductRequirementType.values())
		{
			if (selectedRequirements.contains(type.name()))
			{
				String reqState = (String)form.getField("requirementState" + type);
				if (StringUtils.isNotEmpty(reqState))
				{
					RequirementState state = RequirementState.valueOf(reqState);
					ProductRequirement requirement = new ProductRequirement(type, state);
					requirements.add(requirement);
				}
			}
		}

		Set<AccTypesRequirement> accTypesRequirement = op.getAccTypesRequirement();
		accTypesRequirement.clear();
		Long[] selectedAccTypes = form.getSelectedAccountTypes();
		for(Long productId: selectedAccTypes)
		{
			DepositProduct product = op.getDepositProduct(productId);
			String reqState = (String)form.getField("accTypeState" + productId);
			if (!reqState.equals(""))
			{
				RequirementState productState = RequirementState.valueOf(reqState);
				accTypesRequirement.add(new AccTypesRequirement(product, productState));
			}
		}
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		EditAdvertisingBlockForm form =  (EditAdvertisingBlockForm) frm;
		AdvertisingBlock advertising = (AdvertisingBlock) entity;

		Date periodFrom = DateHelper.toDate(advertising.getPeriodFrom());
		if (periodFrom == null)
			periodFrom =  DateHelper.toDate(DateHelper.getCurrentDate());
		Date periodTo =   DateHelper.toDate(advertising.getPeriodTo());

		form.setField("state",       advertising.getState().name());
        form.setField("available",   advertising.getAvailability().name());
		form.setField("name",        advertising.getName());
		form.setField("periodFrom",  periodFrom);
		form.setField("periodTo",    periodTo);
		form.setField("title",       advertising.getTitle());
		form.setField("text",        advertising.getText());
		form.setField("showTime",    advertising.getShowTime());
		form.setField("orderIndex",  advertising.getOrderIndex());

		for(AdvertisingButton button : advertising.getButtons())
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
		if (EditAdvertisingBlockOperation.IMAGE_AREA_IMAGE_ID.equals(imageId))
		{
			if(StringHelper.isNotEmpty(image.getLinkURL()))
				form.setField("imageLinkURL", image.getLinkURL());
		}
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormImageData(frm, operation);

		EditAdvertisingBlockForm form =  (EditAdvertisingBlockForm) frm;
		EditAdvertisingBlockOperation editOperation = (EditAdvertisingBlockOperation) operation;
		AdvertisingBlock advertising = (AdvertisingBlock) editOperation.getEntity();

		//области баннера
		if (!form.isFromStart())
		{
			for(AdvertisingArea area : advertising.getAreas())
			{
				area.setOrderIndex(Long.parseLong((String)frm.getField(area.getAreaName() + "AreaOrder")));
			}
		}
		Collections.sort(advertising.getAreas(), new AdvertisingOrderedFieldComparator());
		form.setAreas(advertising.getAreas());

		// департаменты
		if (form.isFromStart() && CollectionUtils.isNotEmpty(advertising.getDepartments()))
		{
			form.setDepartments(advertising.getDepartments().toArray(new String[advertising.getDepartments().size()]));
		}


		// требования к продуктам клиента
		if (!form.isFromStart())
		{
			Set<ProductRequirement> requirements = new HashSet<ProductRequirement>();
			List<String> selectedRequirements = Arrays.asList(form.getSelectedRequirements());
			for (ProductRequirementType type : ProductRequirementType.values())
			{
				if (selectedRequirements.contains(type.name()))
				{
					String reqState = (String)form.getField("requirementState" + type);
					if (StringUtils.isNotEmpty(reqState))
					{
						RequirementState state = RequirementState.valueOf(reqState);
						ProductRequirement requirement = new ProductRequirement(type, state);
						requirements.add(requirement);
					}
				}
			}
			form.setRequirements(requirements);

			Set<AccTypesRequirement> accTypesRequirement = new HashSet<AccTypesRequirement>();
			Long[] selectedAccTypes = form.getSelectedAccountTypes();
			DepositProductService depositProductService = new DepositProductService();
			for(Long productId: selectedAccTypes)
			{
				DepositProduct product = depositProductService.findByProductId(productId);
				String reqState = (String)form.getField("accTypeState" + productId);
				if (!reqState.equals(""))
				{
					RequirementState productState = RequirementState.valueOf(reqState);
					accTypesRequirement.add(new AccTypesRequirement(product, productState));
				}
				else
					accTypesRequirement.add(new AccTypesRequirement(product, null));
			}
			form.setAccountTypes(accTypesRequirement);
		}
		else
		{
			Set<ProductRequirement> requirements = advertising.getRequirements();
			Set<AccTypesRequirement> accTypes = advertising.getReqAccTypes();
			if (accTypes.isEmpty())
				for(ProductRequirement req : requirements)
				{
					if (req.getRequirementType() == ProductRequirementType.ACCOUNT)
					{
						List<DepositProduct> products = editOperation.getAccountTypes();
						for(DepositProduct product: products)
							accTypes.add(new AccTypesRequirement(product, null));
					}
				}
			else
				requirements.add(new ProductRequirement(ProductRequirementType.ACCOUNT, null));
			
			form.setRequirements(requirements);
			form.setAccountTypes(accTypes);
		}

	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		EditAdvertisingBlockOperation editOperation = (EditAdvertisingBlockOperation) operation;
		AdvertisingBlock  advertising = (AdvertisingBlock) editOperation.getEntity();
		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Данные успешно сохранены.", false), null);
		return  new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath() +"?id=" + advertising.getId(), true);
	}

	private boolean advBlockIsEmpty(EditAdvertisingBlockForm form, EditAdvertisingBlockOperation operation)
	{
		String title = (String) form.getField("title");
		if (StringUtils.isNotEmpty(title))
			return false;

		String text = (String) form.getField("text");
		if (StringUtils.isNotEmpty(text))
			return false;

		for(int i = 0; i < Constants.NUMBER_OF_BUTTONS; i++)
		{
			String buttonURL = (String) form.getField("buttonURL" + i);
			String buttonTitle = (String) form.getField("buttonTitle" + i);
			if (StringHelper.isNotEmpty(buttonTitle) || StringHelper.isEmpty(buttonURL) || !form.isEmptyImage(EditAdvertisingBlockOperation.BUTTON_IMAGE_ID_PREFIX + i))
				return false;
		}

		if(!form.isEmptyImage(EditAdvertisingBlockOperation.IMAGE_AREA_IMAGE_ID))
			return false;

		return true;
	}
}
