package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.business.profile.images.ImageService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.userprofile.UploadAvatarOperation;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Редактирование аватара (загрузка, сохранение, удаление)
 *
 * @author EgorovaA
 * @ created 26.06.14
 * @ $Author$
 * @ $Revision$
 */
public class LoadAvatarAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>(3);
		keyMethodMap.put("upload", "start");
		keyMethodMap.put("save", "save");
		keyMethodMap.put("delete", "delete");
		keyMethodMap.put("cancel", "cancel");
		return keyMethodMap;
	}

	/**
	 * Загрузить изображение для аватара
	 */
	public ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoadAvatarForm form = (LoadAvatarForm) frm;
		FormFile file = form.getAttach();
		form.setAttach(null);

		if (file.getFileSize() == 0)
		{
			throw new BusinessLogicException("Файл нулевого размера");
		}

		if (file.getFileSize() > 2 * (ConfigFactory.getConfig(ProfileConfig.class).getAvatarFileMaxSize() << 10))
		{
			throw new BusinessLogicException("Превышен допустимый размер файла");
		}

		if (!ConfigFactory.getConfig(ProfileConfig.class).getAvatarAvailableFiles().contains(ImageService.getFileExtention(file.getFileName())))
		{
			throw new BusinessLogicException("Неверный формат файла");
		}

		UploadAvatarOperation op = createOperation(UploadAvatarOperation.class);
		op.initialize(file.getFileData(), ImageService.getFileExtention(file.getFileName()));
		op.upload();
		form.setFilePath(op.getAvatarPath(AvatarType.TEMP));

		return mapping.findForward(FORWARD_SHOW);
	}

	/**
	 * Сохранить аватар
	 */
	public ActionForward save(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoadAvatarForm form = (LoadAvatarForm) frm;
		UploadAvatarOperation op = createOperation(UploadAvatarOperation.class);
		op.setAvatar(form.getX(), form.getY(), form.getWidth(), form.getHeight());

		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Удаление аватара клиента
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UploadAvatarOperation op = createOperation(UploadAvatarOperation.class);
		op.delete();
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Удаление временного аватара
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		UploadAvatarOperation op = createOperation(UploadAvatarOperation.class);
		op.deleteTemp();
		return mapping.findForward(FORWARD_START);
	}
}
