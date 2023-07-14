package com.rssl.auth.csa.back.integration.ipas.store;

import com.rssl.auth.csa.back.HashProvider;
import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringUtils;
import org.hibernate.Session;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

/**
 * @author krenev
 * @ created 12.09.2013
 * @ $Author$
 * @ $Revision$
 * ������������ ������. ����������� �� ��������� SHA-1( SHA-1(password) + salt)
 */

public class StoredPassword extends ActiveRecord
{
	public static final String ALGORITHM = "SHA-1";
	private Long id;
	private String login;
	private byte[] hash;
	private byte[] salt;
	private Date changed;

	/**
	 * ����������� �� ���������
	 */
	public StoredPassword()
	{
	}

	/**
	 * �����������
	 * @param login �����
	 * @param password ������
	 */
	public StoredPassword(String login, String password) throws Exception
	{
		if (login == null)
		{
			throw new IllegalArgumentException("����� �� ����� ���� null");
		}
		if (password == null)
		{
			throw new IllegalArgumentException("������ �� ����� ���� null");
		}
		this.login = login;
		changePassword(password);
	}

	/**
	 * @return ������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ���������� ������������
	 * @param id ������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return �����
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * @param login �����
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * @return ��� ������
	 */
	public String getHash()
	{
		return StringUtils.toHexString(hash);
	}

	/**
	 * ���������� ��� ������
	 * @param hash ��� ������
	 */
	public void setHash(String hash)
	{
		this.hash = StringUtils.fromHexString(hash);
	}

	/**
	 * @return ����
	 */
	public String getSalt()
	{
		return StringUtils.toHexString(salt);
	}

	/**
	 * ���������� ����
	 * @param salt ����
	 */
	public void setSalt(String salt)
	{
		this.salt = StringUtils.fromHexString(salt);
	}

	/**
	 * @return ���� ��������� �����������
	 */
	public Date getChanged()
	{
		return changed;
	}

	/**
	 * ����������� ���� ��������� �����������
	 * @param changed ���� ��������� �����������
	 */
	public void setChanged(Date changed)
	{
		this.changed = changed;
	}


	private HashProvider getHashProvider()
	{
		return HashProvider.getInstance(ALGORITHM);
	}

	/**
	 * ��������� ������� ������ �� ����������� ������
	 * @param password ������ ��� ��������
	 * @return ��/���
	 */
	public boolean check(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		if (password == null)
		{
			throw new IllegalArgumentException("������ �� ����� ���� null");
		}
		return Arrays.equals(getHashProvider().hash(password.toUpperCase(), salt), hash);
	}

	/**
	 * �������� ������ ��� ������
	 * @param password ����� ������
	 */
	public void changePassword(String password) throws Exception
	{
		if (password == null)
		{
			throw new IllegalArgumentException("������ �� ����� ���� null");
		}
		HashProvider provider = getHashProvider();
		salt = HashProvider.generateSalt();
		hash = provider.hash(password.toUpperCase(), salt);
		save();
	}

	/**
	 * ������� � ��������� ��������
	 * @param login �����
	 * @param password ������
	 */
	public static void create(String login, String password) throws Exception
	{
		new StoredPassword(login, password).save();
	}

	/**
	 * ����� ����������� ������ �� ������
	 * @param login �����
	 * @return ����������� ������ ��� null
	 */
	public static StoredPassword findByLogin(final String login) throws Exception
	{
		if (login == null)
		{
			throw new IllegalArgumentException("����� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<StoredPassword>()
		{
			public StoredPassword run(Session session) throws Exception
			{
				return (StoredPassword) session.getNamedQuery("com.rssl.auth.csa.back.integration.ipas.store.StoredPassword.getByLogin")
						.setParameter("login", login)
						.uniqueResult();
			}
		});
	}
}
