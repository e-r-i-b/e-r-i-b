package com.rssl.phizic.operations.security;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.captcha.Captcha;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Операция начала регистрации мобильного приложения
 * Для регистрации с капчей
 *
 * @author khudyakov
 * @ created 16.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowCaptchaSocialRegistrationOperation extends OperationBase
{
	private static final Log LOG = PhizICLogFactory.getLog(LogModule.Core);

	private String captchaBase64String; //Base64 строка капчи
	private String captchaCode; //код капчи

	public ShowCaptchaSocialRegistrationOperation(Map<String, Object> data) throws BusinessLogicException, BusinessException
	{

		//генерация капчи
		ByteArrayOutputStream stream = null;
		try
		{
			Captcha captcha = new Captcha();
			stream = new ByteArrayOutputStream();
			ImageIO.write(captcha.getCaptcha(), "png", stream);
			captchaBase64String = new String(Base64.encodeBase64(stream.toByteArray()));
			captchaCode = captcha.getCode();
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		finally
		{
			try
			{
				if (stream != null)
					stream.close();
			}
			catch (IOException e)
			{
				LOG.error("Ошибка при закрытии потока.", e);
				//ignore
			}
		}
	}

	public String getCaptchaBase64String()
	{
		return captchaBase64String;
	}

	public String getCaptchaCode()
	{
		return captchaCode;
	}
}
