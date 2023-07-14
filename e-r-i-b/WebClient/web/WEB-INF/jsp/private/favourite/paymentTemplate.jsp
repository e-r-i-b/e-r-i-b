<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<c:set var="form" value="${ListFavouriteForm}"/>
<c:set var="templates" value="${form.templates}"/>
<c:set var="defaultCollectionSize" value="10"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=/private/receivers/list')}"/>
<c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'q19')}"/>
<c:if test="${empty fullList}">
    <c:set var="fullList" value="true"/>
</c:if>

<c:choose>
    <c:when test="${fullList}">
        <c:set var="model" value="simple-pagination"/>
        <c:set var="collectionSize" value="20"/>
        <c:set var="onclick" value="ajaxTableList('Templates', '%2$s', %1$d); return false;"/>
        <c:set var="description">
            На этой странице Вы можете просмотреть выбранный шаблон, для этого щелкните по его названию.
            Если Вы хотите вернуться на страницу «<bean:message key="label.mainMenu.payments" bundle="commonBundle"/>», то нажмите на кнопку «Отменить».
        </c:set>
    </c:when>
    <c:otherwise>
        <c:set var="model" value="no-pagination"/>
        <c:set var="collectionSize" value="${defaultCollectionSize}"/>
        <c:set var="description">
            Здесь отображается 10 шаблонов платежей, созданных Вами в системе.
            Чтобы просмотреть остальные шаблоны, нажмите на ссылку «Показать все шаблоны».
        </c:set>
    </c:otherwise>
</c:choose>

<h1>Шаблоны платежей</h1><br/>
<c:if test="${phiz:size(templates) gt defaultCollectionSize}">
    ${description}
</c:if>

<c:set var="templateUrl"
       value="${phiz:calculateActionURL(pageContext,'/private/templates/default-action.do?')}"/>
<tiles:insert definition="simpleTableTemplate" flush="false">
    <tiles:put name="grid">
        <sl:collection id="temp" model="${model}" name="templates" onClick="${onclick}"
                       collectionSize="${collectionSize}">
            <c:set var="businessDocument" value="${temp.businessDocument}"/>
            <c:set var="templateId" value="${businessDocument.id}"/>

            <sl:collectionItem styleClass="align-left">
                <a href='${templateUrl}id=<bean:write name='temp' property="id" ignore="true"/>&objectFormName=${businessDocument.formName}&stateCode=${businessDocument.state.code}'>
                    <bean:write name='temp' property="templateName" ignore="true"/>
                </a>
                <c:if test="${temp.businessDocument.state.code!='TEMPLATE'}">
                    <a href="#" onclick="javascript:openFAQ('${faqLink}')" class="decoration-none">
                        <span class="text-gray">(<span class="unaccept">не подтвержден</span>)</span>
                    </a>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem styleClass="align-right">
                <c:if test="${businessDocument.formName != 'SecuritiesTransferClaim' && businessDocument.chargeOffAmount != null}">
                    <b>${phiz:formatAmount(businessDocument.chargeOffAmount)}</b>
                </c:if>
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>
    <tiles:put name="isEmpty" value="${empty templates}"/>
    <tiles:put name="emptyMessage">
        Вы можете быстро и легко совершать операции с помощью шаблонов платежей. Пока у Вас нет ни одного
        шаблона. Для создания шаблона используйте кнопку <b>Сохранить шаблон</b> при выполнении
        платежей<br/>
        <a href="" onclick="openHelp('${helpLink}'); return false;"><b>Подробнее»</b></a>
    </tiles:put>
</tiles:insert>

<c:if test="${phiz:size(templates) gt defaultCollectionSize}">
    <c:choose>
        <c:when test="${fullList}">
            <div class="buttonArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.close"/>
                    <tiles:put name="commandHelpKey" value="button.close.help"/>
                    <tiles:put name="bundle" value="favouriteBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="onclick">win.close('showAllTemplates');</tiles:put>
                </tiles:insert>
            </div>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.showAllTemplates"/>
                <tiles:put name="commandHelpKey" value="button.showAllTemplates.help"/>
                <tiles:put name="bundle" value="favouriteBundle"/>
                <tiles:put name="viewType" value="boldLink"/>
                <tiles:put name="onclick">win.open('showAllTemplates'); </tiles:put>
            </tiles:insert>
        </c:otherwise>
    </c:choose>
</c:if>
