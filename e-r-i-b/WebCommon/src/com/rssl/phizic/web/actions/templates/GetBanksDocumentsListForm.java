package com.rssl.phizic.web.actions.templates;

import org.apache.struts.action.ActionForm;

import java.util.List;
import java.util.ArrayList;

import com.rssl.phizic.web.common.ListFormBase;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 01.02.2007
 * Time: 13:23:10
 * To change this template use File | Settings | File Templates.
 */
public class GetBanksDocumentsListForm extends ListFormBase
{
	private List     forms       = new ArrayList();

	public List getForms()
	{
		return forms;
	}

	public void setForms(List forms)
	{
		this.forms = forms;
	}
}
