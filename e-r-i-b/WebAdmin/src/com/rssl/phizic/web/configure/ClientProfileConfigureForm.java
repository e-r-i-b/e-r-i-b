package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ����� ��� �������� ������� �������
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ClientProfileConfigureForm extends EditPropertiesFormBase
{
	private static Form EDIT_FORM = createForm();
	private static final String FIELD_NAME_PROPERTY_PREFIX = "settings.clientProfile.field.";
	private static final String KADR_AVATAR_FILE_MAX_SIZE_MAX_VALUE = "300";
	private static final String MAX_LOADED_IMAGE_LONG_SIDE_SIZE_MAX_VALUE = "1600";
	private static final String AVATAR_FILE_MAX_SIZE_MAX_VALUE = "1024";
	private static final String MAX_AVATAR_LONG_SIZE_MAX_VALUE = "960";

	private boolean checkedJPG;
	private boolean checkedGIF;
	private boolean checkedPNG;

	/**
	 * @return ������� ��������� "png" � �������� ���������
	 */
	public boolean isCheckedPNG()
	{
		return checkedPNG;
	}

	/**
	 * �������� ��������� "png" � �������� ���������
	 * @param checkedPNG - �������: ������� �� ������
	 */
	public void setCheckedPNG(boolean checkedPNG)
	{
		this.checkedPNG = checkedPNG;
		updateAvatarAvailableFilesField(checkedPNG, "png");
	}

	/**
	 * @return ������� ��������� "gif" � �������� ���������
	 */
	public boolean isCheckedGIF()
	{
		return checkedGIF;
	}

	/**
	 * �������� ��������� "gif" � �������� ���������
	 * @param checkedGIF - �������: ������� �� ������
	 */
	public void setCheckedGIF(boolean checkedGIF)
	{
		this.checkedGIF = checkedGIF;
		updateAvatarAvailableFilesField(checkedGIF, "gif");
	}

	/**
	 * @return ������� ��������� "jpeg/jpg" � �������� ���������
	 */
	public boolean isCheckedJPG()
	{
		return checkedJPG;
	}

	/**
	 * �������� ��������� "jpeg/jpg" � �������� ���������
	 * @param checkedJPG - �������: ������� �� ������
	 */
	public void setCheckedJPG(boolean checkedJPG)
	{
		this.checkedJPG = checkedJPG;
		updateAvatarAvailableFilesField(checkedJPG, "jpg");
		updateAvatarAvailableFilesField(checkedJPG, "jpeg");
	}

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	/**
	 * ��������� ������� ��������� ������� � �������� ���������
	 * @param add - true: �������� ������, false: ������� ������
	 * @param extension - ������
	 */
	private void updateAvatarAvailableFilesField(boolean add, String extension)
	{
		String enabledFilesSetting = new String(this.getField(ProfileConfig.AVATAR_AVAILABLE_FILES) != null ? (String)this.getField(ProfileConfig.AVATAR_AVAILABLE_FILES) : "");

		String[] availableFiles = enabledFilesSetting.split(",");
		Set<String> avatarAvailableFiles = new HashSet<String>();
		if (ArrayUtils.isNotEmpty(availableFiles))
			avatarAvailableFiles.addAll(Arrays.asList(availableFiles));

		if (add && !avatarAvailableFiles.contains(extension))
			avatarAvailableFiles.add(extension);
		else if (!add && avatarAvailableFiles.contains(extension))
			avatarAvailableFiles.remove(extension);

		enabledFilesSetting = StringUtils.join(avatarAvailableFiles, ",");
		this.setField(ProfileConfig.AVATAR_AVAILABLE_FILES, enabledFilesSetting);
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		//���������� ���� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "avatarAvailableFiles", "configureBundle"));
		fieldBuilder.setName(ProfileConfig.AVATAR_AVAILABLE_FILES);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		//������������ ������ ������������ ����������� �� ������� ������� (� ��������)
		fieldBuilder = new FieldBuilder();
		NumericRangeValidator maxLoadedImageLongSizeValidator = new NumericRangeValidator();
		maxLoadedImageLongSizeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		maxLoadedImageLongSizeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, MAX_LOADED_IMAGE_LONG_SIDE_SIZE_MAX_VALUE);
		String description = StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "maxLoadedImageLongSize", "configureBundle");
		maxLoadedImageLongSizeValidator.setMessage("����������, ������� �������� ���� �" + description + "� � ��������� 0.." + MAX_LOADED_IMAGE_LONG_SIDE_SIZE_MAX_VALUE);
		fieldBuilder.setDescription(description);
		fieldBuilder.setName(ProfileConfig.MAX_LOADED_IMAGE_LONG_SIDE_SIZE);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(maxLoadedImageLongSizeValidator, new RegexpFieldValidator("\\d+", "������� ������������� �������� � ���� \"" + description + "\""));
		fb.addField(fieldBuilder.build());

		//����������� ���������� ������ ����� ����������� (� ��)
		fieldBuilder = new FieldBuilder();
		NumericRangeValidator avatarFileMaxSizeValidator = new NumericRangeValidator();
		avatarFileMaxSizeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		avatarFileMaxSizeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, AVATAR_FILE_MAX_SIZE_MAX_VALUE);
		description = StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "avatarFileMaxSize", "configureBundle");
		avatarFileMaxSizeValidator.setMessage("����������, ������� �������� ���� �" + description + "� � ��������� 0.." + AVATAR_FILE_MAX_SIZE_MAX_VALUE);
		fieldBuilder.setDescription(description);
		fieldBuilder.setName(ProfileConfig.AVATAR_FILE_MAX_SIZE);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(avatarFileMaxSizeValidator, new RegexpFieldValidator("\\d+", "������� ������������� �������� � ���� \"" + description + "\""));
		fb.addField(fieldBuilder.build());

		//������������ ������ ����������� �� ������� ������� ����� ��������� ������� (� ��������)
		fieldBuilder = new FieldBuilder();
		NumericRangeValidator maxAvatarLongSizeValidator = new NumericRangeValidator();
		maxAvatarLongSizeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		maxAvatarLongSizeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, MAX_AVATAR_LONG_SIZE_MAX_VALUE);
		description = StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "maxAvatarLongSize", "configureBundle");
		maxAvatarLongSizeValidator.setMessage("����������, ������� �������� ���� �" + description + "� � ��������� 0.." + MAX_AVATAR_LONG_SIZE_MAX_VALUE);
		fieldBuilder.setDescription(description);
		fieldBuilder.setName(ProfileConfig.MAX_AVATAR_LONG_SIZE);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(maxAvatarLongSizeValidator, new RegexpFieldValidator("\\d+", "������� ������������� �������� � ���� \"" + description + "\""));
		fb.addField(fieldBuilder.build());

		//������������ ������ ������������ ����������� (� ��)
		NumericRangeValidator kadrAvatarFileMaxSizeValidator = new NumericRangeValidator();
		kadrAvatarFileMaxSizeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		kadrAvatarFileMaxSizeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, KADR_AVATAR_FILE_MAX_SIZE_MAX_VALUE);
		description = StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "kadrAvatarFileMaxSize", "configureBundle");
		kadrAvatarFileMaxSizeValidator.setMessage("����������, ������� �������� ���� �" + description + "� � ��������� 0.." + KADR_AVATAR_FILE_MAX_SIZE_MAX_VALUE);
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(description);
		fieldBuilder.setName(ProfileConfig.KADR_AVATAR_FILE_MAX_SIZE);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(kadrAvatarFileMaxSizeValidator, new RegexpFieldValidator("\\d+", "������� ������������� �������� � ���� \"" + description + "\""));
		fb.addField(fieldBuilder.build());

		//���������� P2P ��������� ��� ��������, ��� ������� ���������� ���������� �������� � �������� �����
		fieldBuilder = new FieldBuilder();
		description = StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "amountP2PToAdd", "configureBundle");
		fieldBuilder.setDescription(description);
		fieldBuilder.setName(AddressBookConfig.AMOUNT_P2P_TO_ADD);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d+", "������� ������������� �������� � ���� \"" + description + "\""));
		fb.addField(fieldBuilder.build());

		//��������� �� ��� P2P ���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "fillFromP2P", "configureBundle"));
		fieldBuilder.setName(AddressBookConfig.FILL_FROM_P2P);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		//����������� ������ ��� ������������� �������� ��� Android-���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "showLinkConfirmContactAndroid", "configureBundle"));
		fieldBuilder.setName(AddressBookConfig.SHOW_LINK_CONFORM_CONTACT_ANDROID);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		//��������� ������������� ����������� ������� ���� ��� �� ����� ���������� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "allowOneConfirm", "configureBundle"));
		fieldBuilder.setName(AddressBookConfig.ALLOW_ONE_CONFIRM);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		//��������� �� ��� ������ �����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "fillFromServicePayments", "configureBundle"));
		fieldBuilder.setName(AddressBookConfig.FILL_FROM_SERVICE_PAYMENTS);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
