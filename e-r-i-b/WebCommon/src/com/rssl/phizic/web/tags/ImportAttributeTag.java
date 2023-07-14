package com.rssl.phizic.web.tags;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.util.SkinHelper;
import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.tiles.ComponentContext;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Egorova
 * @ created 10.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class ImportAttributeTag extends org.apache.struts.taglib.tiles.ImportAttributeTag
{
	public int doStartTag() throws JspException
	{
		//��������� ������� ComponentContext, �.�. ��� ���������� ����������� �����
		//��� ���������� ��������� ��������.
		ComponentContext compContext = (ComponentContext)pageContext.getAttribute(ComponentConstants.COMPONENT_CONTEXT, PageContext.REQUEST_SCOPE);
        if( compContext != null )
        {
			super.doStartTag();
        }

		try
		{
			ServletRequest request = pageContext.getRequest();
			ApplicationConfig applicationConfig = ApplicationConfig.getIt();
			Application loginContextApplication = applicationConfig.getLoginContextApplication();

			// 1. ���������� ���������� ���������
			String globalUrl = (String) request.getAttribute("globalUrl");
			if (StringHelper.isEmpty(globalUrl))
				globalUrl = SkinHelper.getGlobalSkinUrl();

			// 2. ���������� ���� ��� ������������
			String skinUrl;
			// 2.A ���� ��������� ����������, �� ���� ��� ������������ �������������
			switch (loginContextApplication)
			{
				case PhizIA:
				case PhizIC:
				case WebPFP:
					skinUrl = (String) request.getAttribute("skinUrl");
					if (StringHelper.isEmpty(skinUrl))
						skinUrl = SkinHelper.getSkinUrl();
					break;
				case atm:
					skinUrl = globalUrl + "/atm";
					break;
				case CSA:
				case WebTest:
				case socialApi:
					// ���������� �� ������������ �����
					return SKIP_BODY;
				default:
					if(applicationConfig.getApplicationInfo().isMobileApi())
						return SKIP_BODY;
					throw new UnsupportedOperationException("���������� " + loginContextApplication + " �� ������������ �����");
			}

			if (StringHelper.isEmpty(globalUrl) || StringHelper.isEmpty(skinUrl))
				throw new JspException("Url ���� ����� ���� ����������� ���� �� �����");

			// 3. ��������� � �������
			request.setAttribute("globalUrl", globalUrl);
			request.setAttribute("skinUrl", skinUrl); 
		}
		catch (BusinessException e)
		{
			throw new JspException(e);
		}

		return SKIP_BODY;
	}
}
