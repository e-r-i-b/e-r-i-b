package com.rssl.phizic.web.client.newProfile;

import com.rssl.phizic.business.profile.images.AvatarInfo;
import com.rssl.phizic.business.profile.images.AvatarService;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.io.File;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Удаление всех файлов аватаров пользователя, которые не удалось удалить или временных аватаров.
 *
 * @author bogdanov
 * @ created 08.05.14
 * @ $Author$
 * @ $Revision$
 */

public class DeleteAllNotDeletedUserImagesEvent implements HttpSessionListener
{
	private Log log = PhizICLogFactory.getLog(LogModule.Web);

	public void sessionCreated(HttpSessionEvent httpSessionEvent)
	{
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent)
	{
		boolean hasApplication = ApplicationInfo.isDefinedCurrentApplication();
		if (!hasApplication)
			ApplicationInfo.setCurrentApplication(Application.PhizIC);
		try
		{
			deleteFiles();
		}
		catch(Exception e)
		{
			log.error("Ошибка во время удаления файлов", e);
		}
		finally
		{
			if (!hasApplication)
				ApplicationInfo.setDefaultApplication();
		}
	}

	private void deleteFiles()
	{
		if (!PersonContext.isAvailable())
			return;

		try
		{
			AvatarInfo avatarInfo = PersonContext.getPersonDataProvider().getPersonData().getAvatarInfoByType(AvatarType.TEMP);
			if (avatarInfo != null)
				AvatarService.get().deleteImageInfo(avatarInfo, null);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}

		for (File file: PersonContext.getPersonDataProvider().getPersonData().getMarkedDeleteFiles())
		{
			if (!file.delete())
			{
				log.warn("Не удалось удалить файл " + file.getAbsolutePath());
			}
		}
	}
}
