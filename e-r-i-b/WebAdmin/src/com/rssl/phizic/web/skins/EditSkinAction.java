package com.rssl.phizic.web.skins;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.skins.Category;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.skins.EditSkinsOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.util.SkinHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author egorova
 * @ created 23.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditSkinAction  extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditSkinForm frm = (EditSkinForm) form;
		EditSkinsOperation operation = createOperation("EditSkins", "CurrentSkin");
		if (frm.getId()==null)
			operation.initializeNew();
		else
			operation.initialize(frm.getId());
		return operation;
	}

	protected  Form getEditForm(EditFormBase frm)
	{
		return EditSkinForm.FORM;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		Skin skin = (Skin) entity;
		Category category = skin.getCategory();

		frm.setField("name", skin.getName());
		frm.setField("path", skin.getUrl());

		frm.setField("admin", null);
		if(category != null)
		{
			if (category.isAdmin())
				frm.setField("admin", true);
			if (category.isClient())
				frm.setField("client", true);
		}

		frm.setField("common", skin.isCommon());
		((EditSkinForm) frm).setReadonly(SkinHelper.skinIsMobile(skin.getName()));
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation op) throws BusinessException
	{
		EditSkinForm form = (EditSkinForm) frm;
		EditSkinsOperation operation = (EditSkinsOperation) op;

		Set<Long> skinGroupIds = operation.getSkinGroupIds();

		form.setGroups(operation.getGroups());
		form.setSkinGroupIds(skinGroupIds.toArray(new Long[skinGroupIds.size()]));

		form.setChangeAdminSkinAllowed(operation.doesChangeAdminSkinAllowed());
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		Skin skin = (Skin) entity;

		skin.setName((String) data.get("name"));

		SkinUrlValidator urlValidator = new SkinUrlValidator();
		String newSkinUrl = "";
		try
		{
			if (urlValidator.validate((String) data.get("path")))
				newSkinUrl+="http://";
		}
		catch (TemporalDocumentException ignored)
		{
			newSkinUrl+="http://";
		}
		newSkinUrl += (String) data.get("path");
		skin.setUrl(newSkinUrl);

		boolean isAdmin = (Boolean) data.get("admin");
		boolean isClient = (Boolean) data.get("client");
		if (isAdmin && isClient)
			skin.setCategory(Category.BOTH);
		else if (isAdmin)
			skin.setCategory(Category.ADMIN);
		else if (isClient)
			skin.setCategory(Category.CLIENT);
		else skin.setCategory(Category.NONE);

		skin.setCommon((Boolean) data.get("common"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation op, EditFormBase editForm, Map<String, Object> validationResult) throws BusinessLogicException
	{
		EditSkinForm form = (EditSkinForm) editForm;
		EditSkinsOperation operation = (EditSkinsOperation) op;
		Skin skin = operation.getEntity();
		List<Long> skinGroupIds = Arrays.asList(form.getSkinGroupIds());

		if (skin.getCategory().isClient() && !skin.isCommon()) {
			if (CollectionUtils.isEmpty(skinGroupIds))
				throw new BusinessLogicException(getResourceMessage("skinsBundle", "message.require-at-least-one-group"));
		}

		operation.setSkinGroupIds(new HashSet<Long>(skinGroupIds));
	}
}
