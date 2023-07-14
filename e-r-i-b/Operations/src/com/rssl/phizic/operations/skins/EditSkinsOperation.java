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
	 * ��� ������ (���������� �� ������������)
	 */
	private List<Group> groups;

	/**
	 * ID �����, ������� �������� ����� <skin>
	 */
	private Set<Long> skinGroupIds;

	///////////////////////////////////////////////////////////////////////////

	/**
	* ������������� ��������� ���������� ������ �����
	*/
	public void initializeNew() throws BusinessException
	{
		skin = new Skin();

		loadGroups();
	}

	/**
	 * ������������� ��������� �������������� �����
	 * @param id - ID �����
	 */
	public void initialize(Long id) throws BusinessException
	{
		skin = skinsService.getById(id);
		if (skin == null)
			throw new BusinessException("���� � Id = " + id + " �� ������");

		loadGroups();
	}

	private void loadGroups() throws BusinessException
	{
		// ����� �������� ���������� �� ������������ => ������ ��� ������
		groups = groupService.getGroups(Collections.singletonList(CATEGORY_CLIENT));

		if (skin.getId() == null)
			skinGroupIds = Collections.emptySet();
		else skinGroupIds = EntityUtils.collectEntityIds(skin.getGroups());
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return true, ���� ��������� ������ ���� ��� ����������
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
	 * ���������� ��� ������, ��������� �������� ������������
	 * @return ������ ����� (����������������, never null)
	 */
	public List<Group> getGroups()
	{
		return Collections.unmodifiableList(groups);
	}

	/**
	 * ���������� ID �����, ������� �������� ������������� �����
	 * @return ��� ��������������� ����� (����������������, never null)
	 */
	public Set<Long> getSkinGroupIds()
	{
		return Collections.unmodifiableSet(skinGroupIds);
	}

	/**
	 * ���������� ID �����, ������� �������� ������������� �����
	 * @param skinGroupIds - ��� ��������������� �����
	 */
	public void setSkinGroupIds(Set<Long> skinGroupIds)
	{
		if (skinGroupIds == null)
			throw new NullPointerException("�������� 'skinGroupIds' �� ����� ���� null");
		this.skinGroupIds = new HashSet<Long>(skinGroupIds);
	}

	/**
	 * �������� ��� ��������������� ����
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		Category skinCategory = skin.getCategory();

		// 0. "������������" ����������� �������������� �����
		// ���� �� ���������� ����������, �� ����� �� �������������
		if (!skinCategory.isClient())
			skin.setCommon(false);
		// ���� �� ���������� ���������� ��� ����� �������������, �� �������� � ������� �� �����
		if (!skinCategory.isClient() || skin.isCommon())
			skinGroupIds.clear();

		// 1. ������� ����������� ����� ������� �������������
		Map<Long, Group> groupsById = EntityUtils.mapEntitiesById(groups);
		List<Group> enabledGroups = MapUtil.lookup(groupsById, skinGroupIds);
		skin.setGroups(new HashSet<Group>(enabledGroups));

		// 2. �������� �����
		skinsService.addOrUpdate(skin);
	}
}
