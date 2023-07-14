package com.rssl.phizic.operations.finances.targets;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.business.profile.images.ImageContainer;
import com.rssl.phizic.business.profile.images.ImageInfo;
import com.rssl.phizic.business.profile.images.NotLoadedImage;
import com.rssl.phizic.business.profile.images.UserImageService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.EditEntityOperation;
import org.apache.commons.lang.BooleanUtils;

import java.awt.image.BufferedImage;

/**
 * @author lepihina
 * @ created 23.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditAccountTargetOperation extends BaseTargetOperation implements EditEntityOperation
{
	private static final AccountTargetService targetService = new AccountTargetService();
	private AccountTarget target;
	private byte[] imageData;
	private String fileType;
	private Boolean removeImage;

	/**
	 * ������������� �� id
	 * @param id �������������
	 * @throws BusinessException
	 */
	public void initializeById(Long id) throws BusinessException
	{
		target = targetService.findTargetById(id);

		if (target == null)
			throw new ResourceNotFoundBusinessException("���� ��� ������ � id=" + id + " �� �������.", AccountTarget.class);

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if (!login.getId().equals(target.getLoginId()))
			throw new AccessException("������ � id = " + login.getId() + " �� ����� ������� � ���� � id = " + target.getId());
	}

	/**
	 * ������������� ��������
	 * @param id - ������������� ����� ������, � �������� ���� ����
	 * @throws BusinessException
	 */
	public void initializeByAccountLink(Long id) throws BusinessException
	{
		target = targetService.findTargetByAccountId(id);

		if (target == null)
			throw new ResourceNotFoundBusinessException("���� ��� ������ � id=" + id + " �� �������.", AccountTarget.class);

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if (!login.getId().equals(target.getLoginId()))
			throw new AccessException("������ � id = " + login.getId() + " �� ����� ������� � ���� � id = " + target.getId());
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		if (fileType != null && imageData.length>0)
		{
			NotLoadedImage img = upload();
			ImageContainer info = UserImageService.get().createImage(img, fileType);
			UserImageService.get().saveImage(info, null, false);
			deleteImage();
			target.setUserImage(info.getImageInfo().getId());
		}
		else if (BooleanUtils.isTrue(removeImage))
		{
			deleteImage();
		}
		targetService.addOrUpdate(target);
	}

	public AccountTarget getEntity()
	{
		return target;
	}

	/**
	 *
	 * @param id �������������
	 * @param imageData ��������
	 * @param fileType ��� �����
	 * @throws BusinessException
	 */
	@SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
	public void initialize(Long id, byte[] imageData, String fileType) throws BusinessException
	{
		target = targetService.findTargetById(id);

		if (target == null)
			throw new ResourceNotFoundBusinessException("���� ��� ������ � id=" + id + " �� �������.", AccountTarget.class);

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if (!login.getId().equals(target.getLoginId()))
			throw new AccessException("������ � id = " + login.getId() + " �� ����� ������� � ���� � id = " + target.getId());
		this.imageData = imageData;
		this.fileType = fileType;
	}

	/**
	 * @return ����������� ��������
	 * @throws BusinessException,BusinessLogicException
	 */
	private NotLoadedImage upload() throws BusinessException, BusinessLogicException
	{
		return UserImageService.get().upload(imageData);
	}

	/**
	 * ������� ���������������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void deleteImage() throws BusinessException, BusinessLogicException
	{
		if(target.getUserImage()== null)
			return;
		ImageInfo imageInfo = UserImageService.get().getImageInfo(target.getUserImage());
		target.setUserImage(null);
		if (imageInfo == null)
			return;
		UserImageService.get().deleteImageInfo(imageInfo,null,false);

	}

	/**
	 * ���������� ���� ������������� �������� ��������
	 * @param removeImage ����
	 */
	public void setRemoveImage(Boolean removeImage)
	{
		this.removeImage = removeImage;
	}
}


