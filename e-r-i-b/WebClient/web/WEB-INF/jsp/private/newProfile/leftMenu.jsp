<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="showTrainingInfo" value="${phiz:getShowTrainingInfo()}"/>
<c:set var="newPersonalInfo" value="${phiz:getNewPersonalInfo()}"/>
<c:set var="newBillsToPay" value="${phiz:getNewBillsToPay()}"/>
<c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/async/userprofile/profileNovelties')}"/>

<script type="text/javascript">
    function closeBubble(){
        $('.closeBubble').parent('.bubble').css('display', 'none');
        callAjaxActionMethod('${url}', 'closeNoveltyState');
    }
</script>

<c:if test="${showTrainingInfo}">
    <div class="bubble relative">
        <div class="innerBubble">
            <bean:message bundle="userprofileBundle" key="noveltyState.text"/>
        </div>
        <div class="closeBubble" onclick="closeBubble()"></div>
    </div>
</c:if>

<ul class="profileMenu">
    <tiles:insert definition="clientLeftMenuLink" flush="false" service="NewClientProfile">
        <tiles:put name="name" value="Личная информация" />
        <tiles:put name="action" value="/private/userprofile/userSettings" />
        <tiles:put name="active" value="${activeItem == 'profile'}" />
        <c:if test="${newPersonalInfo}">
            <tiles:put name="newItem" value="true" />
        </c:if>
    </tiles:insert>

    <tiles:insert definition="clientLeftMenuLink" flush="false" service="AddressBook">
        <tiles:put name="name">
            <bean:message key="menu.addressbook" bundle="userprofileBundle"/>
        </tiles:put>
        <tiles:put name="action" value="/private/userprofile/addressbook" />
        <tiles:put name="active" value="${activeItem == 'addressbook'}" />
        <c:if test="${newPersonalInfo}">
            <tiles:put name="newItem" value="true" />
        </c:if>
    </tiles:insert>

    <c:if test="${phiz:impliesService('ClientPromoCode') or phiz:impliesService('CreatePromoAccountOpeningClaimService') or phiz:impliesService('ShowClientPromoCodeList')}">
        <tiles:insert definition="clientLeftMenuLink" flush="false">
            <tiles:put name="name" value="Промо-коды" />
            <tiles:put name="action" value="/private/userprofile/promoCodes" />
            <tiles:put name="active" value="${activeItem == 'promoCodes'}" />
            <c:if test="${newPersonalInfo}">
                <tiles:put name="newItem" value="true" />
            </c:if>
        </tiles:insert>
    </c:if>

    <li class="profileMenuDevider"></li>

    <c:if test="${phiz:impliesOperation('ViewPaymentsBasketOperation', 'PaymentBasketManagment')}">
        <tiles:insert definition="clientLeftMenuLink" flush="false">
            <tiles:put name="name">Автопоиск<br/> счетов к оплате</tiles:put>
            <tiles:put name="action" value="/private/userprofile/basket" />
            <tiles:put name="active" value="${activeItem == 'searchAccounts'}" />
            <c:if test="${newBillsToPay}">
                <tiles:put name="newItem" value="true" />
            </c:if>
            <tiles:put name="checkEnabledLink" value="true"/>
        </tiles:insert>
    </c:if>
    <tiles:insert definition="clientLeftMenuLink" flush="false" service="ShowConnectedMobileDevicesService">
        <tiles:put name="name" value="Мобильные приложения" />
        <tiles:put name="action" value="/private/mobileApplications/view" />
        <tiles:put name="active" value="${activeItem == 'mobileApplications'}" />
    </tiles:insert>
    <tiles:insert definition="clientLeftMenuLink" flush="false" service="ShowConnectedSocialAppService">
        <tiles:put name="name" value="Приложения социальных сетей" />
        <tiles:put name="action" value="/private/socialApplications/view" />
        <tiles:put name="active" value="${activeItem == 'socialApplications'}" />
    </tiles:insert>
    <tiles:insert definition="clientLeftMenuLink" flush="false" service="MobileBank">
        <tiles:put name="name" value="Мобильный банк" />
        <tiles:put name="action" value="/private/mobilebank/main" />
        <tiles:put name="active" value="${activeItem == 'mobilebank'}" />
    </tiles:insert>
    <li class="profileMenuDevider"></li>
    <tiles:insert definition="clientLeftMenuLink" flush="false"  service="NewClientProfile">
        <tiles:put name="name" value="Безопасность и доступы" />
        <tiles:put name="action" value="/private/userprofile/accountSecurity" />
        <tiles:put name="active" value="${activeItem == 'security'}" />
    </tiles:insert>
    <tiles:insert definition="clientLeftMenuLink" flush="false" service="NewClientProfile">
        <tiles:put name="name" value="Оповещения" />
        <tiles:put name="action" value="/private/userprofile/userNotification" />
        <tiles:put name="active" value="${activeItem == 'notification'}" />
    </tiles:insert>
    <tiles:insert definition="clientLeftMenuLink" flush="false"  service="NewClientProfile">
        <tiles:put name="name" value="Интерфейс" />
        <tiles:put name="action" value="/private/favourite/list" />
        <tiles:put name="active" value="${activeItem == 'interface'}" />
    </tiles:insert>
</ul>