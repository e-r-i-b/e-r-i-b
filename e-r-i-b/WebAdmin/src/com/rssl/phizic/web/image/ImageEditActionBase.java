package com.rssl.phizic.web.image;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageSourceKind;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.SaveImageOperationBase;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.validators.FileValidator;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import java.util.Map;

/**
 * @author akrenev
 * @ created 03.05.2012
 * @ $Author$
 * @ $Revision$
 *
 * Базовый экшен для загрузки картинок
 */
public abstract class ImageEditActionBase extends EditActionBase
{
	protected ActionMessages getImageActionMessages(String message, String imageId)
	{
		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ImageEditFormBase.IMAGE_NAME_DISC_SOURCE_FIELD_NAME_PREFIX + imageId, new ActionMessage(message, false)));
		return msgs;
	}

	protected ActionMessages validateDiscSource(ImageEditFormBase form, String imageId)
	{
		try
		{
			if (form.isEmptyImage(imageId))
				return new ActionMessages();

			FormFile image = form.getImage(imageId);
			String imageName = image.getFileName();
			FieldValidator fileNameValidator = form.getImageFileNameValidator(imageId);
			if (!fileNameValidator.validate(imageName))
				return getImageActionMessages(fileNameValidator.getMessage(), imageId);

			FileValidator imageFileValidator = form.getImageFileValidator(imageId);
			if (imageFileValidator == null)
				return new ActionMessages();
			return imageFileValidator.validate(image);
		}
		catch (Exception e)
		{
			log.error("Ошибка валидации картинки", e);
			return new ActionMessages();
		}
	}

	protected ActionMessages validateExternalSource(String url, String imageId, ImageEditFormBase frm)
	{
		try
		{
			FieldValidator validator = frm.getImageUrlValidator(imageId);
			if (!validator.validate(url))
				return getImageActionMessages(validator.getMessage(), imageId);
		}
		catch (Exception e)
		{
			log.error("Ошибка валидации картинки", e);
		}
		return new ActionMessages();
	}

	private ActionMessages validateImage(ImageEditFormBase form, String imageId)
	{
		Object sourceKind = form.getField(ImageEditFormBase.FILE_SOURCE_KIND_FIELD_NAME_PREFIX + imageId);
		if (ImageSourceKind.DISC.name().equals(sourceKind))
			return validateDiscSource(form, imageId);

		if (ImageSourceKind.EXTERNAL.name().equals(sourceKind))
			return validateExternalSource((String) form.getField(ImageEditFormBase.IMAGE_EXTERNAL_SOURCE_FIELD_NAME_PREFIX + imageId), imageId, form);

		return new ActionMessages();
	}

	protected ActionMessages validateImageFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ImageEditFormBase imageEditForm = (ImageEditFormBase) frm;
		for (String imageId: imageEditForm.getImageIds())
		{
			ActionMessages actionMessages = validateImage(imageEditForm, imageId);
			if (!actionMessages.isEmpty())
				return actionMessages;
		}

		return new ActionMessages();
	}

	protected void updateImageAdditionalData(Image image, String imageId, ImageEditFormBase form)
	{}

	protected void updateOperationImageData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		SaveImageOperationBase operation = (SaveImageOperationBase) editOperation;
		ImageEditFormBase form = (ImageEditFormBase) editForm;
		for (String imageId: form.getImageIds())
		{
			FormFile image = form.getImage(imageId);

			ImageSourceKind imageSourceKind = ImageSourceKind.valueOf((String) validationResult.get(ImageEditFormBase.FILE_SOURCE_KIND_FIELD_NAME_PREFIX + imageId));
			Boolean setNewFile = (Boolean) validationResult.get(ImageEditFormBase.SET_NEW_FILE_FIELD_NAME_PREFIX + imageId);
			String imageExternalSource = (String) validationResult.get(ImageEditFormBase.IMAGE_EXTERNAL_SOURCE_FIELD_NAME_PREFIX + imageId);
			String fileName = image.getFileName();
			byte[] data = image.getFileData();

			if (ImageSourceKind.DISC == imageSourceKind)
				operation.updateDiskImage(imageId, setNewFile, fileName, data);
			else
				operation.updateExternalImage(imageId, imageExternalSource);

			updateImageAdditionalData(operation.getImage(imageId), imageId, form);
		}
	}

	protected void updateFormAdditionalImageData(EditFormBase frm, String imageId, Image image)
	{}
	
	protected void updateFormImageData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		SaveImageOperationBase op = (SaveImageOperationBase) operation;
		ImageEditFormBase form = (ImageEditFormBase) frm;
		for (String imageId: form.getImageIds())
		{
			Image image = op.getImage(imageId);
			if (image == null)
				continue;
			ImageSourceKind imageSourceKind = image.getSourceKind();
			frm.setField(ImageEditFormBase.FILE_SOURCE_KIND_FIELD_NAME_PREFIX + imageId, imageSourceKind);
			frm.setField(ImageEditFormBase.SET_NEW_FILE_FIELD_NAME_PREFIX + imageId, false);
			if (ImageSourceKind.DISC == imageSourceKind)
				frm.setField(ImageEditFormBase.IMAGE_NAME_DISC_SOURCE_FIELD_NAME_PREFIX + imageId, image.getName());
			else
				frm.setField(ImageEditFormBase.IMAGE_EXTERNAL_SOURCE_FIELD_NAME_PREFIX + imageId, image.getExtendImage());

			updateFormAdditionalImageData(form, imageId, image);
		}
	}
}
