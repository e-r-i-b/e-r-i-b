package com.rssl.phizic.web.security;

import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Chegodaev
 * @ created 14.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Фильтр сбрасывает сессионные данные пользователя
 * Не завист, авторизирован ли пользователь
 * Применяется для страниц входа в систему через внешнее приложение
 */

public class ExternalLoginUrlFilter extends LoginUrlFilter
{
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException
	{
		Store store = StoreManager.getCurrentStore();
		store.clear();

		chain.doFilter(req, resp);
	}
}
