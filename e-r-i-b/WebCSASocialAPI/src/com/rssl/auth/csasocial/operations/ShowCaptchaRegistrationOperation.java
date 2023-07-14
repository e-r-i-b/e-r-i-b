package com.rssl.auth.csasocial.operations;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.phizic.captcha.Captcha;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * �������� ������ ����������� ����������� ����������
 * ��� ����������� � ������
 * @author Dorzhinov
 * @ created 29.08.13
 * @ $Author$
 * @ $Revision$
 */
public class ShowCaptchaRegistrationOperation implements Operation
{
	private static final Log LOG = PhizICLogFactory.getLog(LogModule.Core);

	private String captchaBase64String; //Base64 ������ �����
	private String captchaCode; //��� �����

	public void initialize(Map<String, Object> data) throws FrontLogicException, FrontException
	{
	}

	public void execute() throws FrontLogicException, FrontException
	{
		//��������� �����
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
			throw new FrontException(e);
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
				LOG.error("������ ��� �������� ������.", e);
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
