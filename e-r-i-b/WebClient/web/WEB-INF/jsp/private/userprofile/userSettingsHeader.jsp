<%--
  Created by IntelliJ IDEA.
  User: kichinova
  Date: 03.12.2012
  Time: 15:45:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>

<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
<tiles:insert definition="formHeader" flush="false">
    <tiles:put name="image" value="${imagePathGlobal}/icon_pmnt_profile.jpg"/>
    <tiles:put name="description">
        <h3>
            На этой странице можно отредактировать личную информацию, изменить внешний вид системы, настройки безопасности и параметры отправки оповещений. Для управления настройками перейдите на выбранную вкладку, внесите изменения и нажмите на кнопку «Сохранить».
        </h3>
    </tiles:put>
</tiles:insert>

<div class="tabContainer">
    <tiles:insert definition="paymentTabs" flush="false">
        <tiles:put name="count" value="4"/>
        <tiles:put name="tabItems">
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="position" value="first"/>
                <tiles:put name="active" value="${selectedTab == 'pesonalInfo'}"/>
                <tiles:put name="title" value="Профиль"/>
                <tiles:put name="action" value="/private/userprofile/userSettings"/>
            </tiles:insert>
            <tiles:insert definition="paymentTabItem" flush="false" service="ClientProfile">
                <tiles:put name="active" value="${selectedTab == 'interfaceSettings'}"/>
                <tiles:put name="title" value="Интерфейс"/>
                <tiles:put name="action" value="/private/favourite/list"/>
            </tiles:insert>
            <tiles:insert definition="paymentTabItem" flush="false" service="ClientProfile">
                <tiles:put name="active" value="${selectedTab == 'securetySettings'}"/>
                <tiles:put name="title" value="Безопасность"/>
                <tiles:put name="action" value="/private/userprofile/accountSecurity"/>
            </tiles:insert>
            <tiles:insert definition="paymentTabItem" flush="false" service="ClientProfile">
                <tiles:put name="position" value="last"/>
                <tiles:put name="active" value="${selectedTab == 'messagesSettings'}"/>
                <tiles:put name="title" value="Оповещения"/>
                <tiles:put name="action" value="/private/userprofile/userNotification"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</div>