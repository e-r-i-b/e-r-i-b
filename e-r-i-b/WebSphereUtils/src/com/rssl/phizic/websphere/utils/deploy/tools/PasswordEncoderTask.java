package com.rssl.phizic.websphere.utils.deploy.tools;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;


/**
 * @author Omeliyanchuk
 * @ created 07.05.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Таск шифрования пароля для дескрипторов WebSphere
 * Алгоритм xor encoding
 * необходимо задать параметры
 * dbPassword - пароль, которые необходимо зашифровать.
 * returnPropertyName - имя свойства, в которое попадет результат.
 */
public class PasswordEncoderTask extends Task
{
	private static String PASSWORD_PREFIX = "{xor}";
	private String dbPassword;
	private String returnPropertyName;

	public void execute() throws BuildException
	{
		if(dbPassword==null)
			throw new BuildException("Не установлен пароль для шифрования");

		if(returnPropertyName==null)
			throw new BuildException("Не установлено имя свойства для результат");

		getProject().setNewProperty(returnPropertyName, PASSWORD_PREFIX + encodePassword());
		super.execute();
	}

	private String encodePassword() throws BuildException
	{
		byte[] oldbytes =dbPassword.getBytes();
		int size=oldbytes.length;
		byte[] newbytes =new byte[size] ;

		for(int i=0;i<size;i++)
		{
			int c = oldbytes[i];
			int r = c ^ '_';
			newbytes[i] = (byte)r;
		}

		byte[] bytes = Base64.encodeBase64(newbytes);

		String str;

		try
		{
			str = new String(bytes, 0, bytes.length, "ASCII");
			return str;
		}
		catch (UnsupportedEncodingException e)
		{
			throw new BuildException(e);
		}
	}

	public String getDbPassword()
	{
		return dbPassword;
	}

	public void setDbPassword(String dbPassword)
	{
		this.dbPassword = dbPassword;
	}

	public String getReturnPropertyName()
	{
		return returnPropertyName;
	}

	public void setReturnPropertyName(String returnPropertyName)
	{
		this.returnPropertyName = returnPropertyName;
	}
}
