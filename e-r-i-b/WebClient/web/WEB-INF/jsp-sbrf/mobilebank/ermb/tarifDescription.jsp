<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="bundle" value="userprofileBundle"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

<h1>��� ������</h1>
<br />
<div class="tarif1" >
<tiles:insert definition="roundBorder" flush="false">
    <tiles:put name="color" value="gray"/>
    <tiles:put name="data">
        <div class="float tarif" align="center">
            <div style="padding:15px">
                <img class="ico" src="${image}/icon_mb_econom.jpg" width="64" height="64" border="0"/>
            </div>
            <br/>
        </div>
        <div>
            <h2>��������� �����</h2>
            <h3>
                ����� �������� ����������, ����������� �������� �����������,
                ��������� �������� �� ������ ������� � �������.<br/>
                �� �������� SMS-���������� �� ��������� �� �����.
            </h3>
        </div>
        <div class="clear"></div>
    </tiles:put>
</tiles:insert>
</div>
<br />
<div class="tarif1">
<tiles:insert definition="roundBorder" flush="false">
    <tiles:put name="color" value="gray"/>
    <tiles:put name="data">
        <div class="float tarif" align="center">
            <div style="padding:15px">
                <img class="ico" src="${image}/icon_mb_full.jpg" width="64" height="64" border="0"/>
            </div>
            <br/>
        </div>
        <div>
            <h2>������ �����</h2>
            <h3>
                ����������� �������� ���������� �� 0 �� 60 ���. � ����������� �� ���� �����.
                ������ "��������� ����" �������� ���������� ��� ���� ����� ����������� ��������� ����
                (Visa Gold/Platinum/Infinite; MasterCard Gold/Platinum), � ����� ��� ���� ����� ��������� ���� ���������.
                �������� ��� ���� SMS-����������, � ��� ����� ����������� �� ��������� �� �����.
            </h3>
        </div>
        <div class="clear"></div>
    </tiles:put>
</tiles:insert>
</div>
<div class="buttonsArea">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.closeWindow"/>
        <tiles:put name="commandHelpKey" value="button.closeWindow"/>
        <tiles:put name="bundle" value="commonBundle"/>
        <tiles:put name="onclick" value="win.close('tarifDescription')"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>
    <div class="clear"></div>
</div>
