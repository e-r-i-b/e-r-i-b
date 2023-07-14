<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<c:set var="form" value="${ListFavouriteForm}"/>
<c:set var="links" value="${form.links}"/>
<c:set var="defaultCollectionSize" value="10"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=/private/favourite/list')}"/>
<c:if test="${empty fullList}">
    <c:set var="fullList" value="true"/>
</c:if>

<c:choose>
    <c:when test="${fullList}">
        <c:set var="model" value="simple-pagination"/>
        <c:set var="collectionSize" value="20"/>
        <c:set var="onclick" value="ajaxTableList('Operations', '%2$s', %1$d); return false;"/>
        <c:set var="description">
            На этой странице Вы можете просмотреть избранную операцию, для этого щелкните по ее названию.
            Если Вы хотите вернуться на страницу «<bean:message key="label.mainMenu.payments" bundle="commonBundle"/>», то нажмите на кнопку «Отменить».
        </c:set>
    </c:when>
    <c:otherwise>
        <c:set var="model" value="no-pagination"/>
        <c:set var="collectionSize" value="${defaultCollectionSize}"/>
        <c:set var="description">
            Здесь отображается 10 операций, добавленных Вами в избранное.
            Чтобы просмотреть остальные избранные операции, нажмите на ссылку «Показать все операции».
        </c:set>
    </c:otherwise>
</c:choose>

<h1>Избранные операции</h1><br/>
<c:if test="${phiz:size(links) gt defaultCollectionSize}">
    ${description}
</c:if>

<tiles:insert definition="simpleTableTemplate" flush="false">
    <tiles:put name="grid">
        <sl:collection id="link" model="${model}" name="links" onClick="${onclick}"
                       collectionSize="${collectionSize}">
            <sl:collectionItem styleClass="align-left">
                <a href='<bean:write name='link' property="link" ignore="true"/>'>
                    <bean:write name='link' property="name" ignore="true"/>
                </a>
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>
    <tiles:put name="isEmpty" value="${empty links}"/>
    <tiles:put name="emptyMessage">
        Пока у Вас нет ни одной избранной операции. Добавьте сюда ссылки на часто выполняемые операции. Для этого используйте на страницах системы кнопку
        <b>Добавить в Избранное</b>.<br/>
        <a href="" onclick="openHelp('${helpLink}'); return false"><b>Подробнее»</b></a>
    </tiles:put>
</tiles:insert>

<c:if test="${phiz:size(links) gt defaultCollectionSize}">
    <c:choose>
        <c:when test="${fullList}">
            <div class="buttonArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.close"/>
                    <tiles:put name="commandHelpKey" value="button.close.help"/>
                    <tiles:put name="bundle" value="favouriteBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="onclick">win.close('showAllOperations');</tiles:put>
                </tiles:insert>
            </div>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.showAllOperations"/>
                <tiles:put name="commandHelpKey" value="button.showAllOperations.help"/>
                <tiles:put name="bundle" value="favouriteBundle"/>
                <tiles:put name="viewType" value="boldLink"/>
                <tiles:put name="onclick">win.open('showAllOperations');</tiles:put>
            </tiles:insert>
        </c:otherwise>
    </c:choose>
</c:if>