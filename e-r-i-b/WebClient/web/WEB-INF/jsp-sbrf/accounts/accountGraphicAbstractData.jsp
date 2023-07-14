<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="showBottomData" value="false" scope="request"/>
<c:set var="showInMainCheckBox" value="true" scope="request"/>
<c:set var="detailsPage" value="true" scope="request"/>

<c:set var="nameOrNumber">
    <c:choose>
        <c:when test="${empty accountLink.name}">
            ${phiz:getFormattedAccountNumber(account.number)}
        </c:when>
        <c:otherwise>
            «${accountLink.name}»
        </c:otherwise>
    </c:choose>
</c:set>

<c:set var="pattern">
    <c:choose>
        <c:when test="${not empty accountLink.name}">
            «${accountLink.patternForFavouriteLink}»
        </c:when>
        <c:otherwise>
            ${accountLink.patternForFavouriteLink}
        </c:otherwise>
    </c:choose>
</c:set>

<div class="abstractContainer3">
    <c:set var="baseInfo" value="${type} "/>
    <div class="favouriteLinksButton">
        <tiles:insert definition="addToFavouriteButton" flush="false">
            <tiles:put name="formName"><c:out value='${baseInfo}${nameOrNumber}'/></tiles:put>
            <tiles:put name="patternName"><c:out value='${baseInfo}${pattern}'/></tiles:put>
            <tiles:put name="typeFormat">ACCOUNT_LINK</tiles:put>
            <tiles:put name="productId">${form.id}</tiles:put>
        </tiles:insert>
    </div>
</div>

<div class="clear"></div>

<%@ include file="accountTemplate.jsp" %>
<br/>

<div class="tabContainer">
    <tiles:insert definition="paymentTabs" flush="false">
        <c:set var="showTemplates" value="${phiz:showTemplatesForProduct('account')}"/>
        <c:set var="count">
            <c:choose>
                <c:when test="${!showTemplates}">
                    <c:out value="3"/>
                </c:when>
                <c:otherwise>
                    <c:out value="4"/>
                </c:otherwise>
            </c:choose>
        </c:set>
        <c:if test="${phiz:impliesService('MoneyBoxManagement')}">
            <c:set var="count" value="${count+1}"/>
        </c:if>
        <tiles:put name="count" value="${count}"/>
        <tiles:put name="tabItems">
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="position" value="first"/>
                <tiles:put name="active" value="false"/>
                <tiles:put name="title" value="Последние операции"/>
                <tiles:put name="action" value="/private/accounts/operations?id=${accountLink.id}"/>
            </tiles:insert>
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="active" value="false"/>
                <tiles:put name="title" value="Информация по вкладу"/>
                <tiles:put name="action" value="/private/accounts/info.do?id=${accountLink.id}"/>
            </tiles:insert>
            <c:if test="${showTemplates}">
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="active" value="false"/>
                    <tiles:put name="title" value="Шаблоны и платежи"/>
                    <tiles:put name="action" value="/private/account/payments.do?id=${accountLink.id}"/>
                </tiles:insert>
            </c:if>
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="position" value="last"/>
                <tiles:put name="active" value="true"/>
                <tiles:put name="title" value="Графическая выписка"/>
                <tiles:put name="action" value="/private/accounts/graphic-abstract.do?id=${accountLink.id}"/>
            </tiles:insert>
            <c:if test="${phiz:impliesService('MoneyBoxManagement')}">
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="position" value="last"/>
                    <tiles:put name="active" value="false"/>
                    <tiles:put name="title">
                        <div style="float:left">
                            <div style="display:inline-block ;vertical-align:middle;"><c:out value="Копилка"/></div>
                            <div class="il-newIconMoneyBoxSmall" style="display:inline-block ;vertical-align:middle;"></div>
                        </div>
                    </tiles:put>
                    <tiles:put name="action" value="/private/accounts/moneyBoxList.do?id=${accountLink.id}"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>

    <div class="clear"></div>

    <c:set var="periodName" value="Period"/>
    <c:set var="curType"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(type${periodName})"/></c:set>
    <c:if test="${empty form.abstractMsgError}">
        <div class="abstractContainer2">
            <tiles:insert definition="filterDataPeriod" flush="false">
                <tiles:put name="week" value="false"/>
                <tiles:put name="month" value="false"/>
                <tiles:put name="name" value="${periodName}"/>
                <tiles:put name="buttonKey" value="button.filter"/>
                <tiles:put name="buttonBundle" value="accountInfoBundle"/>
                <tiles:put name="needErrorValidate" value="false"/>
                <tiles:put name="isNeedTitle" value="false"/>
            </tiles:insert>
        </div>
    </c:if>
    <div class="clear"></div>

    <c:choose>
        <c:when test="${not empty form.abstractMsgError}">
            <div class="emptyText">
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="greenBold"/>
                    <tiles:put name="data">
                        <c:out value="${form.abstractMsgError}"/>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:when>
        <c:when test="${not empty form.accountAbstract && not empty form.accountAbstract.transactions}">

            <tiles:insert page="/WEB-INF/jsp/graphics/balanceGraphic.jsp" flush="false">
                <tiles:put name="title" value="по счету"/>
                <tiles:put name="dateFrom"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(from${periodName})" format="MM/dd/yyyy"/></tiles:put>
                <tiles:put name="dateTo"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(to${periodName})" format="MM/dd/yyyy"/></tiles:put>
                <tiles:put name="resourceAbstract" beanProperty="accountAbstract" beanName="form"/>
                <tiles:put name="descriptionBoth">На этой странице Вы можете посмотреть движение средств по Вашему счету: столбцы показывают сумму поступивших или списанных средств, линия - изменение баланса счета. Вы можете просмотреть графики отдельно, а также задать другой период отображения графической выписки.</tiles:put>
                <tiles:put name="descriptionLine">На этой странице Вы можете посмотреть изменение баланса Вашего счета. Для того чтобы получить подробную информацию, наведите курсор на интересующую Вас точку графика.</tiles:put>
                <tiles:put name="descriptionBar">На этой странице Вы можете посмотреть поступление и списание средств со счета. Для того чтобы получить подробную информацию о движении средств, наведите курсор на интересующий Вас столбик.</tiles:put>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <div class="emptyText greenBlock">
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="greenBold"/>
                    <tiles:put name="data">
                        <c:choose>
                            <c:when test="${empty form.accountAbstract}">
                                Графическая выписка временно недоступна. Пожалуйста, повторите попытку позже.
                            </c:when>
                            <c:otherwise>
                                По данному счету/вкладу Вы не совершили ни одной операции.
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:otherwise>
    </c:choose>
</div>


