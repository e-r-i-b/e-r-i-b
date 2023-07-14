package com.rssl.phizic.web.client.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.menulinks.MenuLink;
import com.rssl.phizic.business.menulinks.MenuLinkInfo;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.operations.skins.ChangePersonalSkinOperation;
import com.rssl.phizic.operations.userprofile.EditUserSettingsOperation;
import com.rssl.phizic.operations.userprofile.ListMenuLinksOperation;
import com.rssl.phizic.operations.userprofile.SetupProductsMainViewOperation;
import com.rssl.phizic.operations.widget.SetupInterfaceModeOperation;
import com.rssl.phizic.web.common.client.widget.WidgetManager;
import com.rssl.phizic.web.util.SkinHelper;
import org.apache.commons.lang.ObjectUtils;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 28.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class PersonalSettingsAction extends EditUserProfileActionBase
{
	protected final Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("button.remove", "remove");
		map.put("button.saveMainMenuSettings", "saveMainMenuSettings");
		map.put("button.saveSettingsView", "saveSettingsView");
		map.put("button.saveSettingsViewNewProfile", "saveSettingsViewNewProfile"); //сохранение настроек главной страницы в новом профиле
		map.put("button.setDefaultValue", "setDefaultValue");
		map.put("button.setInterfaceMode", "setInterfaceMode");
		map.put("button.saveSkin", "saveSkin");
		return map;
	}

	protected String getSkinUrl(ActionForm form) throws BusinessException
	{
		PersonalSettingsForm frm = (PersonalSettingsForm) form;
		Skin currentSkin = frm.getCurrentSkin();
		if (currentSkin == null) //“ака€ ситуаци€ может произойти, когда метод удалени€ шаболона со страницы настройки интерфейса завершилс€ с исключением.
		    return super.getSkinUrl(form);
		return SkinHelper.updateSkinPath(currentSkin.getUrl());
	}

	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonalSettingsForm frm = (PersonalSettingsForm) form;

		updateFormMenuLinks(frm);

		updateProducts(frm);

		updateSkins(frm);
		if (checkAccess(SetupInterfaceModeOperation.class))
			updateInterfaceMode(frm);
		if (PersonHelper.availableNewProfile())
			return mapping.findForward(FORWARD_START_NEW);
		return mapping.findForward(FORWARD_START);
	}

	protected void updateFormSelectedMenuLinks(PersonalSettingsForm form) throws BusinessException
	{
		List<MenuLinkInfo> list =  form.getMenuLinks();
		List<String> selectedIds = new ArrayList<String>();
		for(MenuLinkInfo linkInfo:list)
		{
			if(linkInfo.getLink().isUse())
				selectedIds.add(linkInfo.getLink().getId().toString());
		}
		String[] selectedIdsArray = new String[selectedIds.size()];
		selectedIds.toArray(selectedIdsArray);
		form.setSelectedMenuLinks(selectedIdsArray);
	}

	private void updateSkins(PersonalSettingsForm form) throws BusinessException, BusinessLogicException
	{
		ChangePersonalSkinOperation operation = createOperation(ChangePersonalSkinOperation.class);
		operation.initialize(form.getPreviewSkin());
		form.setSkins(operation.getSkins());
		form.setCurrentSkin(operation.getCurrentSkin());
	}
	private void updateInterfaceMode(PersonalSettingsForm form) throws BusinessException, BusinessLogicException
	{
		SetupInterfaceModeOperation operation = createOperation(SetupInterfaceModeOperation.class);
		operation.initialize();
		form.setUseWidget(operation.isShowWidget());
	}

	//восстанавливаем значени€ по умолчанию (номер ссылки = пор€дковый номер, отображать в системе)
	public ActionForward setDefaultValue(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListMenuLinksOperation linkOperation = createOperation(ListMenuLinksOperation.class);
		linkOperation.initialize(true);
		for (MenuLinkInfo linkInfo: linkOperation.getMenuLinksInfo())
		{
			MenuLink link = linkInfo.getLink();
			link.setOrderInd(link.getLinkId());
			link.setUse(true);
		}
		linkOperation.save();
		return start(mapping, form, request, response);
	}

   	public ActionForward saveMainMenuSettings(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonalSettingsForm frm = (PersonalSettingsForm) form;
		String[] selectedMenuLinks = frm.getSelectedMenuLinks();
		List<String> selectedLinksList = Arrays.asList(selectedMenuLinks);

		ListMenuLinksOperation menuLinksOperation = createOperation(ListMenuLinksOperation.class);
		menuLinksOperation.initialize(true);

		boolean isChanged = menuLinksOperation.checkChanges(selectedLinksList, frm.getSortMenuLinks());
		if (isChanged)
		{
			menuLinksOperation.save(selectedMenuLinks, frm.getSortMenuLinks());
			setSuccessfulMessage(request);
		}
		else
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("¬ы не внесли никаких изменений в настройки главного меню.", false));
			saveErrors(request, message);
		}
		return start(mapping, form, request, response);
	}

	public Profile getProfile() throws BusinessException, BusinessLogicException
	{
		EditUserSettingsOperation op = createOperation(EditUserSettingsOperation.class);
		op.initialize();
		return op.getProfile();
	}

	public ActionForward saveSettingsView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonalSettingsForm frm = (PersonalSettingsForm) form;
		SetupProductsMainViewOperation operation = createOperation(SetupProductsMainViewOperation.class);
		operation.initialize();
		initForm(frm, operation);

		boolean isChanged = false;

		if (operation.saveLinksIfChanged(frm.getSortedAccountIds(), AccountLink.class))
			isChanged = true;
		if (operation.saveLinksIfChanged(frm.getSortedCardIds(), CardLink.class))
			isChanged = true;
		if (operation.saveLinksIfChanged(frm.getSortedLoanIds(), LoanLink.class))
			isChanged = true;
		if (operation.saveLinksIfChanged(frm.getSortedDepoAccountIds(), DepoAccountLink.class))
			isChanged = true;
		if (operation.saveLinksIfChanged(frm.getSortedIMAccountIds(), IMAccountLink.class))
			isChanged = true;

		if (operation.getClientPfrLink() != null && !ObjectUtils.equals(operation.getClientPfrLink().getShowInMain(), frm.getPfrLinkSelected()))
		{
			operation.updatePFRLink(frm.getPfrLinkSelected());
			isChanged = true;
		}

		if (isChanged)
		{
			try
			{
				operation.save();
				setSuccessfulMessage(request);
				return start(mapping, form, request, response);
			}
			catch (BusinessException e)
			{
				saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
				return start(mapping, form, request, response);
			}
		}
		else
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("¬ы не внесли никаких изменений в настройки видимости продуктов на главной странице.", false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}
	}

	public ActionForward saveSettingsViewNewProfile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonalSettingsForm frm = (PersonalSettingsForm) form;
		SetupProductsMainViewOperation operation = createOperation(SetupProductsMainViewOperation.class);
		operation.initialize();
		initForm(frm, operation);

		boolean isChanged = false;

		if (operation.saveLinksIfChanged(frm.getSelectedAccountIds(), frm.getSortedAccountIds(), AccountLink.class))
			isChanged = true;
		if (operation.saveLinksIfChanged(frm.getSelectedCardIds(), frm.getSortedCardIds(), CardLink.class))
			isChanged = true;
		if (operation.saveLinksIfChanged(frm.getSelectedLoanIds(), frm.getSortedLoanIds(), LoanLink.class))
			isChanged = true;
		if (operation.saveLinksIfChanged(frm.getSelectedDepoAccountIds(), frm.getSortedDepoAccountIds(), DepoAccountLink.class))
			isChanged = true;
		if (operation.saveLinksIfChanged(frm.getSelectedIMAccountIds(), frm.getSortedIMAccountIds(), IMAccountLink.class))
			isChanged = true;

		if (operation.getClientPfrLink() != null && !ObjectUtils.equals(operation.getClientPfrLink().getShowInMain(), frm.getPfrLinkSelected()))
		{
			operation.updatePFRLink(frm.getPfrLinkSelected());
			isChanged = true;
		}

		if (isChanged)
		{
			try
			{
				operation.save();
				setSuccessfulMessage(request);
				return start(mapping, form, request, response);
			}
			catch (BusinessException e)
			{
				saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
				return start(mapping, form, request, response);
			}
		}
		else
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("¬ы не внесли никаких изменений в настройки видимости продуктов на главной странице.", false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}
	}

	/**
	 * —охранить режим интерфейса
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward setInterfaceMode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonalSettingsForm frm = (PersonalSettingsForm) form;

		try
		{
			SetupInterfaceModeOperation setupInterfaceModeOperation = createOperation(SetupInterfaceModeOperation.class);
			setupInterfaceModeOperation.initialize();
			final boolean isTurningOn = !setupInterfaceModeOperation.isShowWidget() && frm.getUseWidget(); //переход "выкл" -> "вкл"

			setupInterfaceModeOperation.setShowWidget(frm.getUseWidget());
			setupInterfaceModeOperation.save();
			saveMessage(request, "ƒанные успешно сохранены");

			if (isTurningOn)
				WidgetManager.loadWidgets();
			
			return start(mapping, form, request, response);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FORWARD_START);
		}
	}

	/**
	 * —охранить текущий стиль
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveSkin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonalSettingsForm frm = (PersonalSettingsForm) form;

		try
		{
			ChangePersonalSkinOperation changeCurrentSkinOperation = createOperation(ChangePersonalSkinOperation.class);
			changeCurrentSkinOperation.initialize(frm.getPreviewSkin());
			changeCurrentSkinOperation.save();
			saveMessage(request, "ƒанные успешно сохранены");
			return start(mapping, form, request, response);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FORWARD_START);
		}
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		PersonalSettingsForm frm = (PersonalSettingsForm) form;

		return mapping.getPath() + "/" + frm.getOption();
	}

	private void updateFormMenuLinks(PersonalSettingsForm frm) throws BusinessException, BusinessLogicException
	{
		if (checkAccess(ListMenuLinksOperation.class))
		{
			ListMenuLinksOperation linkOperation = createOperation(ListMenuLinksOperation.class);
			linkOperation.initialize(true);
			frm.setMenuLinks(linkOperation.getMenuLinksInfo());
			updateFormSelectedMenuLinks(frm);
		}
	}

	private void updateProducts(PersonalSettingsForm frm) throws BusinessException, BusinessLogicException
	{
		SetupProductsMainViewOperation operation = createOperation(SetupProductsMainViewOperation.class);
		operation.initialize();
		initForm(frm, operation);
		frm.setSelectedAccountIds(getShowInMain(frm.getAccounts()));
		frm.setSelectedCardIds(getShowInMain(frm.getCards()));
		frm.setSelectedLoanIds(getShowInMain(frm.getLoans()));
		frm.setSelectedDepoAccountIds(getShowInMain(frm.getDepoAccounts()));
		frm.setSelectedIMAccountIds(getShowInMain(frm.getImAccounts()));
	}

	private String[] getShowInMain(List<? extends EditableExternalResourceLink> list)
	{
		ArrayList<String> selectedIds = new ArrayList<String>();

		for (EditableExternalResourceLink link : list)
		{
			if (link.getShowInMain())
				selectedIds.add(link.getId().toString());
		}

		return selectedIds.toArray(new String[0]);
	}

	private PersonalSettingsForm initForm(PersonalSettingsForm frm, SetupProductsMainViewOperation operation) throws BusinessException
	{
		frm.setAccounts(operation.getClientAccounts());
		frm.setCards(operation.getClientCards());
		frm.setLoans(operation.getClientLoans());
		frm.setImAccounts(operation.getClientIMAccounts());
		frm.setDepoAccounts(operation.getClientDepoAccounts());
		frm.setPfrLink(operation.getClientPfrLink());
		return frm;
	}

	private void setSuccessfulMessage(HttpServletRequest request)
	{
		ActionMessages messages  = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("ƒанные успешно сохранены.", false));
		saveMessages(request, messages);
	}
}
