package com.rssl.phizic.web.news;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.news.Important;
import com.rssl.phizic.business.news.NewsState;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.news.News;
import com.rssl.phizic.business.news.NewsType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.news.EditLoginPageNewsOperation;
import com.rssl.phizic.operations.news.EditNewsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.util.DepartmentViewUtil;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Zhuravleva
 * Date: 21.07.2006
 * Time: 13:42:08
 */
public class EditNewsAction extends EditActionBase
{
   protected static final String FORWARD_VIEW = "View";
   protected static final String FORWARD_SUCCESS_MAIN = "SuccessMainPageNews";
   protected static final String FORWARD_SUCCESS_LOGIN = "SuccessLoginPageNews";

	protected Map<String, String> getAdditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAdditionalKeyMethodMap();
		map.put("button.publish", "publish");
		map.put("button.remove_publish", "removePublish");
		return map;
	}

	protected EditNewsOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditNewsOperation operation = null;
		EditNewsForm form = (EditNewsForm) frm;
		if (form.isMainNews())
			operation = createOperation(EditNewsOperation.class, "NewsManagment");
		else
			operation = createOperation(EditLoginPageNewsOperation.class, "NewsLoginPageManagment");
		Long id = form.getId();
		if(id != null)
			operation.initialize(id);
		else
		    operation.initializeNew(form.getTemplate());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditNewsForm.EDIT_FORM;
	}

	/**
	 * ѕри логических исключени€х вызываетс€ именно этот метод.
	 * во врем€ редактировани€ может изменитьс€ статус новости,
	 * поэтому в updateEntity бросаетс€ BusinessLogicException("Ќевозможно отредактировать новость со статусом 'Cн€та с публикации'");
	 * и поэтому значение статуса на форме мен€ем (дл€ активизации режима просмотра).
	 *
	 * @param frm форма дл€ обновлени€
	 * @param operation операци€ дл€ получени€ данных.
	 * @throws Exception
	 */
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		News news = ((EditNewsOperation) operation).getEntity();
		frm.setField("state", news.getState());
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws BusinessException, BusinessLogicException
	{
		News news = (News) entity;
		//на случай, если во врем€ редактировани€ новость сн€лась с публикации
		if (NewsState.NOT_PUBLISHED == news.getState())
			throw new BusinessLogicException("Ќевозможно отредактировать событие со статусом 'Cн€то с публикации'");

		news.setTitle((String) data.get("title"));
		news.setShortText((String) data.get("shortText"));
		news.setText((String) data.get("text"));
		news.setImportant(Enum.valueOf(Important.class,(String)data.get("important")));
		news.setType((NewsType) data.get("type"));
		news.setState(NewsState.valueOf((String) data.get("state")));
		news.setDepartments(getDepartments((String) data.get("TB")));
		Date date = (Date) data.get("newsDate");
		Date time = (Date) data.get("newsDateTime");

		Calendar fullDate = DateHelper.toCalendar(date);
		Calendar fullTime = DateHelper.toCalendar(time);

		fullDate.set(Calendar.HOUR_OF_DAY, fullTime.get(Calendar.HOUR_OF_DAY));
		fullDate.set(Calendar.MINUTE, fullTime.get(Calendar.MINUTE));
		fullDate.set(Calendar.SECOND, fullTime.get(Calendar.SECOND));

		news.setNewsDate(fullDate);

		if ((Boolean) data.get("automaticPublish"))
		{
			Date datePublish = (Date) data.get("automaticPublishDate");
			Date timePublish = (Date) data.get("automaticPublishTime");

			Calendar fullDatePublish = DateHelper.toCalendar(datePublish);
            Calendar fullTimePublish = DateHelper.toCalendar(timePublish);

			fullDatePublish.set(Calendar.HOUR_OF_DAY, fullTimePublish.get(Calendar.HOUR_OF_DAY));
			fullDatePublish.set(Calendar.MINUTE, fullTimePublish.get(Calendar.MINUTE));
			fullDatePublish.set(Calendar.SECOND, fullTimePublish.get(Calendar.SECOND));
			
			news.setStartPublishDate(fullDatePublish);
		}
		else
			news.setStartPublishDate(null);
		if ((Boolean) data.get("cancelPublish"))
		{
			Date dateCancel = (Date) data.get("cancelPublishDate");
			Date timeCancel = (Date) data.get("cancelPublishTime");

			Calendar fullDateCancel = DateHelper.toCalendar(dateCancel);
			Calendar fullTimeCancel = DateHelper.toCalendar(timeCancel);

			fullDateCancel.set(Calendar.HOUR_OF_DAY, fullTimeCancel.get(Calendar.HOUR_OF_DAY));
			fullDateCancel.set(Calendar.MINUTE, fullTimeCancel.get(Calendar.MINUTE));
			fullDateCancel.set(Calendar.SECOND, fullTimeCancel.get(Calendar.SECOND));

			news.setEndPublishDate(fullDateCancel);
		}
		else
			news.setEndPublishDate(null);
	}

	private Set<String>  getDepartments(String tbs)  throws BusinessException
	{
		if (tbs == null)
			return null;
		String[] tempTbs = tbs.split(",");
		Set<String> departments = new HashSet<String>();
		for (int i = 0; i < tempTbs.length; i++)
		{
			departments.add(tempTbs[i]);
		}
		return departments;
	}
	protected void updateForm(EditFormBase frm, Object entity)
	{
		News news = (News) entity;
		frm.setField("title", news.getTitle());
		frm.setField("shortText", news.getShortText());
		frm.setField("text", news.getText());
		frm.setField("newsDate", news.getNewsDate().getTime());
		frm.setField("newsDateTime", news.getNewsDate().getTime());
		frm.setField("important", news.getImportant());
		frm.setField("type", news.getType());
		if (news.getStartPublishDate() != null)
		{
			frm.setField("automaticPublish", true);
			frm.setField("automaticPublishDate", news.getStartPublishDate().getTime());
			frm.setField("automaticPublishTime", news.getStartPublishDate().getTime());
		}
		if (news.getEndPublishDate() != null)
		{                                                                   
			frm.setField("cancelPublish", true);
			frm.setField("cancelPublishDate", news.getEndPublishDate().getTime());
			frm.setField("cancelPublishTime", news.getEndPublishDate().getTime());
		}
		Set<String> departments = news.getDepartments();
		String name= "";
		String tbs = "";
		if (departments != null)
		{
			for (String department: departments)
			{
				if (!name.equals("")) name = name +", ";
				name = name + DepartmentViewUtil.getDepartmentName(department, null, null);
				if (!tbs.equals("")) tbs = tbs +",";
				tbs = tbs + department;
			}
		}
		frm.setField("TB", tbs);
		frm.setField("departmentsName", name);

	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		EditNewsOperation op = (EditNewsOperation) operation;
		News news = op.getEntity();
		EditNewsForm form = (EditNewsForm) frm;
		return new ActionForward(getCurrentMapping().findForward(FORWARD_VIEW).getPath() +"&id=" + news.getId() + "&mainNews="+form.isMainNews(), true);
	}

	private ActionForward updateNewsState(NewsState state, EditNewsForm form) throws BusinessException, BusinessLogicException
	{
		EditNewsOperation operation = createEditOperation(form);
		News news = operation.getEntity();
		news.setState(state);
		if (state == NewsState.PUBLISHED)
			news.setStartPublishDate(operation.getCurrentDate());
		else
			news.setEndPublishDate(operation.getCurrentDate());

		operation.save();

		if (form.isMainNews())
			return getCurrentMapping().findForward(FORWARD_SUCCESS_MAIN);

		return getCurrentMapping().findForward(FORWARD_SUCCESS_LOGIN);
	}

	//мен€ем статус новости на "ќпубликована"
	public ActionForward publish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return updateNewsState(NewsState.PUBLISHED, (EditNewsForm) form);
	}

	//мен€ем статус новости на "—н€та с публикации"
	public ActionForward removePublish (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return updateNewsState(NewsState.NOT_PUBLISHED, (EditNewsForm) form);
	}
}
