package com.rssl.phizic.web.common.mobile.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.io.IOException;
import javax.servlet.*;

/**
 * ������ ��� �������� ���������� ������ ���.
 * @author Pankin
 * @ created 19.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class CheckCSASessionFilter implements Filter
{
	private static final Log LOG = PhizICLogFactory.getLog(LogModule.Web);

	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		// ���� ������ �� ����������������, ������� �������� �� �����
		if (PersonContext.isAvailable() && SecurityUtil.isAuthenticationComplete())
		{
			// ������ �� ����� ��� �������� (������ � ��� ������ �� ���� ���� � ������)
			if (PersonContext.getPersonDataProvider().getPersonData().isTimeToCheckCSASession())
			{
				AuthenticationContext context = AuthenticationContext.getContext();
				if (context != null && StringHelper.isNotEmpty(context.getCSA_SID()))
				{
					try
					{
						CSABackRequestHelper.sendCheckSessionRq(context.getCSA_SID());
					}
					catch (Exception e)
					{
						// �� �����, ����� ������ (�������� � ��������� � ��� ��� ������ ��� ���������), ������������ http ������
						Store store = StoreManager.getCurrentStore();
						store.clear();
						//������ ����� ������������
						LOG.error("������ ��� �������� ������ CSABack. ������������ ������.", e);
					}
				}
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy()
	{
	}
}
