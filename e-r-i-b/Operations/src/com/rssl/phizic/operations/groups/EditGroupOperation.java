package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.groups.DublicateGroupNameException;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsService;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.utils.EntityUtils;

import java.util.List;
import java.util.Collections;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 10.11.2006 Time: 15:12:05 To change this template use
 * File | Settings | File Templates.
 */
public class EditGroupOperation  extends OperationBase implements RemoveEntityOperation, EditEntityOperation
{
	protected final static GroupService groupService = new GroupService();
	protected final static DepartmentService departmentService = new DepartmentService();
	private static final SkinsService skinService = new SkinsService();

	protected Group group;
	protected boolean    isNew;
	private List<Skin> skins;
	private Long skinId;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализация операции добавления новой группы
	 * @param category
	 */
	public void initializeNew(String category) throws BusinessException
	{
		group = new Group();
		group.setCategory(category);
		group.setDepartment( getCurrentDepartment() );
		skinId = null;
		skins = skinService.getGroupAvailableSkins(null);
		isNew = true;
	}

	/**
	 * Инициализация операции редактирования либо удаления существующей группы
	 * @param groupId
	 */
	public void initialize(Long groupId) throws BusinessException
	{
		Group temp = groupService.findGroupById(groupId);
		if (temp == null)
			throw new BusinessException("группа с id " + groupId + " не найдена");
		group = temp;
		skinId = (group.getSkin() != null) ? group.getSkin().getId() : null;
		skins = skinService.getGroupAvailableSkins(groupId);
		isNew = false;
	}

	public Group getEntity()
	{
		return group;
	}

	public Boolean isNew()
	{
		return isNew;
	}

	/**
	 * @return умолчательный стиль группы
	 */
	public Long getSkinId()
	{
		return skinId;
	}

	public void setSkinId(Long skinId)
	{
		this.skinId = skinId;
	}

	/**
	 * @return стили, доступные группе
	 */
	public List<Skin> getSkins()
	{
		return Collections.unmodifiableList(skins);
	}

	@Transactional
	public void save() throws BusinessException, DublicateGroupNameException
	{
		// 1. Проверка и установка умолчательного стиля группы
		Skin skin = null;
		if (skinId != null)
		{
			skin = EntityUtils.findById(skins, skinId);
			if (skin == null) {
				throw new BusinessException("Скин не доступен группе. " +
						"SKIN_ID=" + skinId + ", " +
						"GROUP_ID=" + group.getId());
			}
		}
		group.setSkin(skin);

		// 2. Сохранение группы
		if(group.getId() == null)
		{
			groupService.createGroup(group);
		}
		else
			groupService.modifyGroup(group);
	}

	@Transactional
	public void remove() throws BusinessLogicException, BusinessException
	{
		if(group == null)
			throw new BusinessException("Не установлена группа для удаления");

		if (group.getSystemName() != null)
			throw new SystemGroupRemoveException(group);
		if(!groupService.hasClients(group))
				groupService.deleteGroup(group);
		else
			throw new BusinessLogicException("В группе " + group.getName() + " найдены пользователи!");
	}

	protected Department getCurrentDepartment() throws BusinessException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
	    Long departmentId = employeeData.getEmployee().getDepartmentId();
		return departmentService.findById( departmentId );
	}
}
