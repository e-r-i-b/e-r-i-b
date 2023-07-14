<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<br/>
<html:form action="/internetSecurity" show="true" onsubmit="return setEmptyAction(event)">
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<script type="text/javascript">
	function clickBox(obj)
	{
        var opacityBlock = $("#opacityBlock");
        if (obj.checked)
        {
            opacityBlock.hide();
            return;
        }

        opacityBlock.show();
	}

	function checkSelected()
	{
		if(document.getElementById('agree').checked)
			return true;

		return false;
	}

    $(document).ready(function()
    {
        var button = $('#confirmButtons');
        $('#opacityBlock').css("width", button.width()+"px");
        $('#opacityBlock').css("height", button.height()+"px")
    });
</script>

    <c:set var="titleText"><bean:message bundle="securityBundle" key="internetSecurity.title"/></c:set>
    <tiles:insert definition="roundBorder" flush="false">
            <tiles:put name="color" value="greenTop"/>
            <tiles:put name="title" value="${titleText}"/>
            <tiles:put name="data" type="string">
                <br/>
                <div class="align-center"><h2 class="grayStyleText">��������� ������!</h2></div>
                <br/>
                <bean:message bundle="securityBundle" key="internetSecurity.informationSecurity.notification"/>
                <ul class="list">
                    <li>
                        <bean:message bundle="securityBundle" key="internetSecurity.authPage.notification"/>
                    </li>
                    <li>
                        <b>������ �� ���������</b> ���������� � ����� SMS-������ � ������� � ����, ���� �����������
                        �����. � ������ ������ ��� ����� ���� � �������� ���������� � ���� ��� ������������ ����� ���
                        � ���������.
                    </li>
                    <li>
                        ����� ������ ������������ SMS-������ ������ <b>����������� ���������</b> ����������� ����
                        �������� � ������� SMS-���������. ���� ��������� �� ���������, �� � ���� ������ ��
                        ����������� ���� ������ � ���������� � ������ ����������� ��������� ����� �� ��������
                        +7 (495) 500 5550.
                    </li>
                    <li>
                        <bean:message bundle="securityBundle" key="internetSecurity.cancellationPasswords.notification"/>
                    </li>
                    <li>
                        <bean:message bundle="securityBundle" key="internetSecurity.useDifferentDevices"/>
                    </li>
                    <li>
                        ��� <b>������ ���������� ��������</b>, �� ������� �� ��������� ��������� � SMS-�������, �����
                        �� ���������� � ��������� ������� ����� � <b>������������ SIM-�����</b>.
                    </li>
                    <li>
                        <bean:message bundle="securityBundle" key="internetSecurity.visibilitySettings.notification"/>
                    </li>
                    <li>
                        <b>�� �������������� �� �������</b>, �� ������� �������� SMS-��������� �� �����, <b>����������</b>, ���������� ��
                        ������������� ����������. �������, ��� <b>���� �� ���������</b> ����� �������� ������ ��� �������� �� ���������
                        ���������� ����� <b>SMS/MMS/Email - ���������</b>.
                    </li>
                    <li>
                        � ������ ������ � �������� ���������, ��� <b>���������� ���������� �����������</b> ������ <b>�
                        ����������� ������</b> ������ (<a href="https://esk.sberbank.ru" target="_blank">https://esk.sberbank.ru</a> ��� <a target="_blank" href="https://online.sberbank.ru">https://online.sberbank.ru</a>).
                        ���������� �� ������ �������� ���������� �� ������ ������ � ����������� ��������
                        �����, ��������, <a href="http://www.sberbank.ru" target="_blank">www.sberbank.ru</a> ��� <a target="_blank" href="http://www.sberbank.ru">www.sberbank.ru</a>. �� ����������� �������� � ������
                        ������� ��� ��������, ������������ �� ����������� �����.
                    </li>
                    <li>
                        ������� ���� � ������������ ��������, ������������ � ���������, ��� ��, ��� ���-����
                        ���������� ����: ����� ����� ��� �� ������ ����� ������ � ���� � ������������ ��������.
                        <b>� ������ �� ������</b> ��� ����� ��������������� ���������� � ���� ��� ������������
                        ����� ��� � ���������.
                    </li>
                    <li>
                        ����������� <b>����������� ������������ ���������</b>, ������� �� �� <b>�����������</b> � ���������
                        <b>���������� ������������ ��������</b> �� ����� ����������.
                    </li>
                    <li>
                        <b>������������ �������������� ����������</b> ������������ ������� ������ ����������,
                        ������������� ���������-��������������.
                    </li>
                    <li>
                        ����������� ��� ������������ <b>�������������� ����������� �����������</b>, ����������� 
                        �������� ������� ������ ������ ����������, ��������, ��������� ������ ���������
                        ���������, ��������� ������ �� ����̻ � �������� � ��.
                    </li>
                    <li>
                        ��� ����������� ���������� ������ � �������� ���������� �������� �� ������ <b>�����</b>, � ��
                        ��������� ���� ��������.
                    </li>
                </ul>
                <br />
                <b> ���� � ��� ���� ����������, ��� ���-���� ���������� ��� ������, ��� ����������� ��������, 
                ������� �� �� ���������, �� ���������� ���������� � ����!</b>

                <div class="noTitle">
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="data">
                            <label><html:checkbox property="field(selectAgreed)" styleId="agree"
                                                  onclick="clickBox(this)"/>
                                &nbsp;� �������� � ��������� ���������� � ���� �� ���������</label>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div class="relative">
                    <div class="opacityBlock opacity65" id="opacityBlock"></div>
                    <div class="buttonsArea" id="confirmButtons">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.confirm"/>
                            <tiles:put name="commandTextKey" value="button.confirmOfert"/>
                            <tiles:put name="commandHelpKey" value="button.confirmOfert.help"/>
                            <tiles:put name="bundle" value="securityBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="validationFunction" >checkSelected();</tiles:put>
                        </tiles:insert>
                    </div>
                </div>

         </tiles:put>
    </tiles:insert>
</html:form>