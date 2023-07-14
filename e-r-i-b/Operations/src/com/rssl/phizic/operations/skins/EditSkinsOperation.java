package com.rssl.phizic.operations.skins;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.groups.GroupService;
import static com.rssl.phizic.business.schemes.AccessCategory.CATEGORY_CLIENT;
import com.rssl.phizic.business.skins.Category;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.EntityUtils;
import com.rssl.phizic.utils.MapUtil;
import com.rssl.phizic.config.ConfigFactory;

import java.util.*;

/**
 * @author egorova
 * @ created 19.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditSkinsOperation extends OperationBase implements EditEntityOperation
{
	private static final SkinsService skinsService = new SkinsService();

	private static final GroupService groupService = new GroupService();

	private Skin skin;

	/**
	 * Все группы (независимо от департамента)
	 */
	private List<Group> groups;

	/**
	 * ID групп, которым доступен стиль <skin>
	 */
	private Set<Long> skinGroupIds;

	///////////////////////////////////////////////////////////////////////////

	/**
	* Инициализация процедуры добавления нового стиля
	*/
	public void initializeNew() throws BusinessException
	{
		skin = new Skin();

		loadGroups();
	}

	/**
	 * Инициализация процедуры редактирования стиля
	 * @param id - ID скина
	 */
	public void initialize(Long id) throws BusinessException
	{
		skin = skinsService.getById(id);
		if (skin == null)
			throw new BusinessException("Скин с Id = " + id + " не найден");

		loadGroups();
	}

	private void loadGroups() throws BusinessException
	{
		// Стили доступны независимо от департамента => грузим все группы
		groups = groupService.getGroups(Collections.singletonList(CATEGORY_CLIENT));

		if (skin.getId() == null)
			skinGroupIds = Collections.emptySet();
		else skinGroupIds = EntityUtils.collectEntityIds(skin.getGroups());
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return true, если разрешено менять снин АРМ сотрудника
	 */
	public boolean doesChangeAdminSkinAllowed()
	{
		return ConfigFactory.getConfig(SkinsConfig.class).doesChangeAdminSkinAllowed();
	}
	
	public Skin getEntity()
	{
		return skin;
	}

	/**
	 * Возвращает все группы, доступные текущему пользователю
	 * @return список групп (немодифицируемый, never null)
	 */
	public List<Group> getGroups()
	{
		return Collections.unmodifiableList(groups);
	}

	/**
	 * Возвращает ID групп, которым доступен редактируемый стиль
	 * @return сет идентификаторов групп (немодифицируемый, never null)
	 */
	public Set<Long> getSkinGroupIds()
	{
		return Collections.unmodifiableSet(skinGroupIds);
	}

	/**
	 * Запоминает ID групп, которым доступен редактируемый стиль
	 * @param skinGroupIds - сет идентификаторов групп
	 */
	public void setSkinGroupIds(Set<Long> skinGroupIds)
	{
		if (skinGroupIds == null)
			throw new NullPointerException("Аргумент 'skinGroupIds' не может быть null");
		this.skinGroupIds = new HashSet<Long>(skinGroupIds);
	}

	/**
	 * Добавить или отредактировать скин
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		Category skinCategory = skin.getCategory();

		// 0. "Выравнивание" результатов редактирования стиля
		// Если не клиентское приложение, то стиль не общедоступный
		if (!skinCategory.isClient())
			skin.setCommon(false);
		// Если не клиентское приложение или стиль общедоступный, то привязка к группам не нужна
		if (!skinCategory.isClient() || skin.isCommon())
			skinGroupIds.clear();

		// 1. Обновим доступность стиля группам пользователей
		Map<Long, Group> groupsById = EntityUtils.mapEntitiesById(groups);
		List<Group> enabledGroups = MapUtil.lookup(groupsById, skinGroupIds);
		skin.setGroups(new HashSet<Group>(enabledGroups));

		// 2. Сохраним стиль
		skinsService.addOrUpdate(skin);
	}
}
