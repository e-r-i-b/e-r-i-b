package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.MobilePlatformEditOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * Настройки mAPI в разрезе платформ: редактирование
 * @author Jatsky
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 */

public class MobilePlatformConfigEditAction extends ImageEditActionBase
{
	/**
	 * Создать и проинициализировать операцию (операция редактирования).
	 * @param frm форма
	 * @return созданная операция.
	 */
	@Override protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		MobilePlatformConfigEditForm form = (MobilePlatformConfigEditForm) frm;
		MobilePlatformEditOperation operation = createOperation(MobilePlatformEditOperation.class);
		return initializeOperation(form, operation);
	}

	/**
	 * Вернуть форму редактирования.
	 * В большинстве случаев(до исправления ENH00319) будет возвращаться frm.EDIT_FORM
	 * @param frm struts-форма
	 * @return форма редактирования
	 */
	@Override protected Form getEditForm(EditFormBase frm)
	{
		return MobilePlatformConfigEditForm.EDIT_FORM;
	}

	/**
	 * Обновить сущность данными.
	 * @param entity сущность
	 * @param data данные
	 */
	@Override protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		String platformName = data.get("platformName").toString();

		MobilePlatform platform = (MobilePlatform) entity;
		Object id = data.get("id");
		if (id!=null){
			platform.setId(Long.parseLong(data.get("id").toString()));
		}
		platform.setPlatformName(platformName);
		platform.setPlatformId(data.get("platformId").toString());
		platform.setSocial(Boolean.parseBoolean(data.get("social").toString()));
		platform.setVersion(data.get("version") == null ? null : Long.parseLong(data.get("version").toString()));
		platform.setErrorText(data.get("errText") == null ? null : data.get("errText").toString());
		Object scheme = data.get("scheme");
		if (scheme!=null)
		{
			platform.setLightScheme(Boolean.parseBoolean(data.get("scheme").toString()));
		}
		Object downloadFromSBRF = data.get("downloadFromSBRF");
		if (downloadFromSBRF!=null)
		{
			platform.setDownloadFromSBRF(Boolean.parseBoolean(data.get("downloadFromSBRF").toString()));
		}
		Object bankURL = data.get("bankURL");
		if (bankURL != null)
			platform.setBankURL(bankURL.toString());
		Object externalURL = data.get("externalURL");
		if (externalURL != null)
			platform.setExternalURL(externalURL.toString());
		Object isQR = data.get("isQR");
		platform.setUseQR((isQR != null));
		Object qrName = data.get("QRName");
		if (qrName != null)
			platform.setQrName(qrName.toString());
		Object isShownInApps = data.get("isShownInApps");
		platform.setShowInApps((isShownInApps != null));
		//Подтверждение одноразовым паролем
		Object isPasswordConfirm = data.get("isPasswordConfirm");
		platform.setPasswordConfirm(isPasswordConfirm != null);
		Object isShowSbAttribute = data.get("isShowSbAttribute");
		platform.setShowSbAttribute(isShowSbAttribute != null);
	}

	/**
	 * Проинициализировать/обновить struts-форму
	 * @param frm форма для обновления
	 * @param entity объект для обновления.
	 */
	@Override protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		MobilePlatform platform = (MobilePlatform) entity;
		MobilePlatformConfigEditForm form = (MobilePlatformConfigEditForm) frm;
		String platformName = platform.getPlatformName();

		form.setField("id", platform.getId());
		form.setField("platformName", platformName);
		form.setField("platformId", platform.getPlatformId());
		form.setField("social", platform.isSocial());
		form.setField("version", platform.getVersion());
		form.setField("errText", platform.getErrorText());
		form.setField("scheme", platform.isLightScheme());
		form.setField("downloadFromSBRF", platform.isDownloadFromSBRF());
		form.setField("bankURL", platform.getBankURL());
		form.setField("externalURL", platform.getExternalURL());
		form.setField("isQR", platform.isUseQR());
		form.setField("QRName", platform.getQrName());
		form.setField("imageId", platform.getPlatformIcon());
		form.setField("isShownInApps", platform.isShowInApps());
		form.setImageID(platform.getPlatformIcon());
		form.setField("isPasswordConfirm", platform.isPasswordConfirm());
		form.setField("isShowSbAttribute", platform.isShowSbAttribute());
	}

	protected EditEntityOperation initializeOperation(MobilePlatformConfigEditForm frm, MobilePlatformEditOperation operation) throws BusinessLogicException, BusinessException
	{
		Long deviceId = frm.getId();

		if (deviceId != null && deviceId != 0)
		{
			operation.initialize(deviceId);
		}
		else
		{
			operation.initializeNew();
		}

		return operation;
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return validateImageFormData(frm, operation);
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		updateOperationImageData(editOperation, editForm, validationResult);
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		updateFormImageData(frm, operation);
	}
	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return new ActionForward(getCurrentMapping().findForward(FORWARD_START).getPath()+"?id="+frm.getId());
	}
}
