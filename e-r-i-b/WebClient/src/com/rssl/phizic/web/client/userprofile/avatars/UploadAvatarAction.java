package com.rssl.phizic.web.client.userprofile.avatars;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.business.profile.images.ImageService;
import com.rssl.phizic.business.profile.images.NotLoadedImage;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.userprofile.UploadAvatarOperation;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Загрузка аватара.
 *
 * @author bogdanov
 * @ created 25.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UploadAvatarAction extends OperationalActionBase
{
	private static final String FORWARD_SHOW_PROFILE = "ShowProfile";
	private static final Map<String, String> mimeTypes = new HashMap<String, String>();
	private static final int BORDER_FOR_CLIPPING = 30;
	private static final Log log0 = PhizICLogFactory.getLog(LogModule.Web);

	static
	{
		mimeTypes.put("gif", "image/gif");
		mimeTypes.put("jpg", "image/jpeg");
		mimeTypes.put("jpeg", "image/jpeg");
		mimeTypes.put("png", "image/x-png,image/png");
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UploadAvatarForm frm = (UploadAvatarForm) form;
		initForm(frm);
		if (StringHelper.isEmpty(frm.getState()) || frm.getState().equals("load") || frm.getState().equals("loadOpen"))
		{
			if (StringHelper.isEmpty(frm.getState()))
				frm.setState("load");
			return mapping.findForward(FORWARD_START);
		}
		else if (frm.getState().equals("upload"))
		{
			return upload(mapping, frm, request, response);
		}
		else if (frm.getState().equals("save"))
		{
			return setAvatar(mapping, frm, request, response);
		}
		else if (frm.getState().equals("delete"))
		{
			return deleteAvatar(mapping, frm, request, response);
		}
		else if (frm.getState().equals("cancel"))
		{
			return cancel(mapping, frm, request, response);
		}

		return mapping.findForward(FORWARD_START);
	}

	private void initForm(UploadAvatarForm frm) throws BusinessException
	{
		ProfileConfig profileConfig = ConfigFactory.getConfig(ProfileConfig.class);
		StringBuilder res = new StringBuilder();
		StringBuilder mime = new StringBuilder();
		int i = 0;
		int numOfAvailableTypes = profileConfig.getAvatarAvailableFiles().size();
		for (String type : profileConfig.getAvatarAvailableFiles())
		{
			res.append(".").append(type);
			mime.append(mimeTypes.get(type));
			i++;
			if (i == numOfAvailableTypes - 1)
			{
				res.append(" или ");
				mime.append(",");
			}
			else if (i != numOfAvailableTypes)
			{
				res.append(", ");
				mime.append(",");
			}
		}
		frm.setFileTypes(res.toString());
		frm.setMimeTypes(mime.toString());

		frm.setMaxFileSize(profileConfig.getAvatarFileMaxSize());
		frm.setHasAvatar(UploadAvatarOperation.hasAvatar());
	}

	private ActionForward cancel(ActionMapping mapping, UploadAvatarForm frm, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		try
		{
			UploadAvatarOperation op = createOperation(UploadAvatarOperation.class);
			op.deleteTemp();
		}
		catch (Exception e)
		{
			log0.error("Ошибка отмены загрузки аватара", e);
		}
		return mapping.findForward(FORWARD_SHOW_PROFILE);
	}

	private ActionForward upload(ActionMapping mapping, UploadAvatarForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			FormFile file = form.getAvatarFile();
			form.setFileName(file.getFileName());
			form.setAvatarFile(null);
			form.setState("save");

			if (file.getFileSize() == 0)
			{
				throw new BusinessLogicException("Пожалуйста, загрузите изображение большего размера");
			}

			if (file.getFileSize() > 2*(ConfigFactory.getConfig(ProfileConfig.class).getAvatarFileMaxSize() << 10))
			{
				throw new BusinessLogicException("Превышен допустимый размер файла");
			}

			if (!ConfigFactory.getConfig(ProfileConfig.class).getAvatarAvailableFiles().contains(ImageService.getFileExtention(file.getFileName())))
			{
				throw new BusinessLogicException("Неверный формат файла");
			}

			UploadAvatarOperation op = createOperation(UploadAvatarOperation.class);
			op.initialize(file.getFileData(), ImageService.getFileExtention(file.getFileName()));
			NotLoadedImage image = op.upload();

			double scale = UploadAvatarOperation.getImageScale(image);
			//чтобы квадрат был по середине изображения.
			form.setImageWidth(image.getWidth(null)/scale);
			form.setImageHeight(image.getHeight(null)/scale);
			double minWidth = Math.min(form.getImageWidth(), form.getImageHeight());
			form.setX((int)(BORDER_FOR_CLIPPING + (form.getImageWidth() - minWidth)/2));
			form.setY((int)(BORDER_FOR_CLIPPING + (form.getImageHeight() - minWidth)/2));
			form.setWidth((int)(minWidth - BORDER_FOR_CLIPPING));
			form.setHeight(form.getWidth());
		}
		catch (BusinessLogicException e)
		{
			form.setError(e.getMessage());
			form.setState("error");
		}
		catch(Exception e)
		{
			log0.error("Ошибка при загрузке изображения", e);
			form.setError("Ошибка при загрузке изображения");
			form.setState("error");
		}

		return mapping.findForward(FORWARD_START);
	}

	private ActionForward deleteAvatar(ActionMapping mapping, UploadAvatarForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			UploadAvatarOperation op = createOperation(UploadAvatarOperation.class);
			op.delete();
			form.setState("load");
			form.setHasAvatar(UploadAvatarOperation.hasAvatar());
		}
		catch (Exception e)
		{
			log0.error("Ошибка при удалении аватара", e);
			form.setState("load");
			form.setHasAvatar(UploadAvatarOperation.hasAvatar());
			form.setError("Ошибка при удалении фотографии");
		}

		return mapping.findForward(FORWARD_START);
	}

	private ActionForward setAvatar(ActionMapping mapping, UploadAvatarForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UploadAvatarOperation op = createOperation(UploadAvatarOperation.class);
		try
		{
			op.setAvatar(form.getX(), form.getY(), form.getWidth(), form.getHeight());
		}
		catch (BusinessLogicException e)
		{
			form.setError(e.getMessage());
			form.setState("error");
			return mapping.findForward(FORWARD_START);
		}
		catch(Exception e)
		{
			log0.error("Ошибка при загрузке изображения", e);
			form.setError("Загрузка файла временно недоступна. <br/>Повторите попытку позже.");
			form.setState("error");
			return mapping.findForward(FORWARD_START);
		}
		return mapping.findForward(FORWARD_SHOW_PROFILE);
	}
}
