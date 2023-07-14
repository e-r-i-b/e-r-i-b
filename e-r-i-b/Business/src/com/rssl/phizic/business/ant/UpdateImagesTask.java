package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

/**
 * @author bogdanov
 * @ created 29.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class UpdateImagesTask extends SafeTaskBase implements ExternalDbSettingsTask
{
	private ImageService imageService;
	private String login;
	private String password;
	private String initByParams;
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void safeExecute() throws Exception
	{
		imageService = new ImageService();

		log.debug("Getting number of empty md5 images");
		for (Long id : imageService.getEmptyMD5ImageId())
		{
			Image image = imageService.findByIdForUpdate(id, getInstanceName());
			log.debug("updating image " + id);
			imageService.addOrUpdate(image, getInstanceName());
		}
	}

	private String getInstanceName()
	{
		return null;
	}

	public String getInitByParams()
	{
		return initByParams;
	}

	public void setInitByParams(String initByParams)
	{
		this.initByParams = initByParams;
	}
	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
