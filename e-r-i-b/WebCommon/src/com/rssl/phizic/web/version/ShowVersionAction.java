package com.rssl.phizic.web.version;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 24.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class ShowVersionAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    PropertyReader propertyReader = ConfigFactory.getReaderByFileName("version.properties");
		String revision = propertyReader.getProperty("com.rssl.iccs.revision");
		String version = propertyReader.getProperty("com.rssl.iccs.version");

	    ShowVersionForm frm = (ShowVersionForm)form;
	    frm.setRevision(revision);
	    frm.setVersion(version);
		return mapping.findForward(FORWARD_START);
    }
}
