package com.rssl.phizic.security.crypto;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import static com.rssl.phizic.security.crypto.Environment.CRYPTO_PROVIDER_FACTORY_POOL;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.naming.NamingHelper;
import org.apache.commons.logging.Log;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Roshka
 * @ created 30.08.2006
 * @ $Author$
 * @ $Revision$
 */

public final class CryptoProviderHelper
{
	private static final Log log  = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	///////////////////////////////////////////////////////////////////////////

	private CryptoProviderHelper() {}

	/**
	 * ���������� "�������������" ������� ������-����������
	 * @return ������� CryptoProviderFactory
	 * @throws SecurityException ���� �� ���������� ������� "��-���������"
	 */
	public static CryptoProviderFactory getDefaultFactory() throws SecurityException
	{
		CryptoProviderFactoryPool pool = getCryptoProviderFactoryPool();
		if (pool == null)
			throw new SecurityException("�� ��������������� ��� ������ ������-�����������. " +
					"�������, ��� CryptoProviderService ������� ��������");

		CryptoProviderFactory defaultFactory = pool.getDefaultFactory();
		if (defaultFactory == null)
			throw new SecurityException("�� ���������� ������� ������-����������� \"��-���������\"");
		return defaultFactory;
	}

	/**
	 * ���������� ������� �� �������� ������-����������
	 * @param name - ��� (������� �����������) ������-����������
	 * @return �������, ������� ������������� ������-���������� � ��������� ������
	 */
	public static CryptoProviderFactory getFactory(String name)
	{
		if (StringHelper.isEmpty(name))
			throw new IllegalArgumentException("�������� 'name' �� ����� ���� ������");

		CryptoProviderFactoryPool pool = getCryptoProviderFactoryPool();
		if (pool == null)
			log.error("�� ��������������� ��� ������ ������-�����������. " +
					"�������, ��� CryptoProviderService ������� ��������");
		return pool.getFactory(name);
	}

	private static CryptoProviderFactoryPool getCryptoProviderFactoryPool()
	{
		InitialContext context = null;
		try
		{
			context = NamingHelper.getInitialContext();
			return (CryptoProviderFactoryPool) context.lookup(CRYPTO_PROVIDER_FACTORY_POOL);
		}

		catch (NamingException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			try
			{
				if (context != null)
					context.close();
			}
			catch (NamingException ignored) {}
		}
	}
}