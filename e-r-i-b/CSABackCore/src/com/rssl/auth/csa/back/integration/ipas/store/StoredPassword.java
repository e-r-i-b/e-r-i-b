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
 * Прихраненный пароль. хеширование по алгоритму SHA-1( SHA-1(password) + salt)
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
	 * конструктор по умолчанию
	 */
	public StoredPassword()
	{
	}

	/**
	 * Конструктор
	 * @param login логин
	 * @param password пароль
	 */
	public StoredPassword(String login, String password) throws Exception
	{
		if (login == null)
		{
			throw new IllegalArgumentException("Логин не может быть null");
		}
		if (password == null)
		{
			throw new IllegalArgumentException("Пароль не может быть null");
		}
		this.login = login;
		changePassword(password);
	}

	/**
	 * @return идентифкатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Установить идентифкатор
	 * @param id идентифкатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return логин
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * @param login логин
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * @return хеш пароля
	 */
	public String getHash()
	{
		return StringUtils.toHexString(hash);
	}

	/**
	 * Установить хеш пароля
	 * @param hash хеш пароля
	 */
	public void setHash(String hash)
	{
		this.hash = StringUtils.fromHexString(hash);
	}

	/**
	 * @return соль
	 */
	public String getSalt()
	{
		return StringUtils.toHexString(salt);
	}

	/**
	 * Установить соль
	 * @param salt соль
	 */
	public void setSalt(String salt)
	{
		this.salt = StringUtils.fromHexString(salt);
	}

	/**
	 * @return дата последней модификации
	 */
	public Date getChanged()
	{
		return changed;
	}

	/**
	 * Усатановить дату последней модификации
	 * @param changed дата полседней модификации
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
	 * проверить входную строку на соотвествие пароля
	 * @param password пароль для проверки
	 * @return да/нет
	 */
	public boolean check(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		if (password == null)
		{
			throw new IllegalArgumentException("Пароль не может быть null");
		}
		return Arrays.equals(getHashProvider().hash(password.toUpperCase(), salt), hash);
	}

	/**
	 * Изменить пароль для логина
	 * @param password новый пароль
	 */
	public void changePassword(String password) throws Exception
	{
		if (password == null)
		{
			throw new IllegalArgumentException("Пароль не может быть null");
		}
		HashProvider provider = getHashProvider();
		salt = HashProvider.generateSalt();
		hash = provider.hash(password.toUpperCase(), salt);
		save();
	}

	/**
	 * Создать и сохранить сужность
	 * @param login логин
	 * @param password пароль
	 */
	public static void create(String login, String password) throws Exception
	{
		new StoredPassword(login, password).save();
	}

	/**
	 * найти сохраненный пароль по логину
	 * @param login логин
	 * @return сохраненный пароль или null
	 */
	public static StoredPassword findByLogin(final String login) throws Exception
	{
		if (login == null)
		{
			throw new IllegalArgumentException("Логин не может быть null");
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
