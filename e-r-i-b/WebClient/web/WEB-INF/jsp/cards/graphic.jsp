<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/cards/info"  onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="accountInfo">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="cardLink" value="${form.cardLink}" scope="request"/>
    <c:set var="page" value="cardsDetail" scope="request" />
    <c:set var="cardInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/cards/info.do?id=')}"/>
    <tiles:put name="mainmenu" value="Cards"/>
    <tiles:put name="menu" type="string"/>
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Карты"/>
            <tiles:put name="action" value="/private/cards/list.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name">${cardLink.description} ${phiz:getCutCardNumber(cardLink.number)}</tiles:put>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
     </tiles:put>
    <tiles:put name="data" type="string">
        <html:hidden property="id"/>
        <tiles:insert definition="roundBorder" flush="false">
            <tiles:put name="data">
            <tiles:put name="title" value="Карты" />
                <c:set var="showInMainCheckBox" value="true" scope="request"/>
                <%@ include file="/WEB-INF/jsp-sbrf/accounts/cardTemplate.jsp"%>
                <div class="tabContainer">
                    <tiles:insert definition="tabs" flush="false">
                        <tiles:put name="tabItems">
                            <tiles:insert definition="tabItem" flush="false">
                                <tiles:put name="style" value="inactive"/>
                                <tiles:put name="title" value="Последние операции"/>
                            </tiles:insert>
                            <tiles:insert definition="tabItem" flush="false">
                                <tiles:put name="style" value="inactive"/>
                                <tiles:put name="title" value="Детальная информация"/>
                                <tiles:put name="action" value="/private/cards/detail.do?id=${cardLink.id}"/>
                            </tiles:insert>
                            <tiles:insert definition="tabItem" flush="false">
                                <tiles:put name="style" value="inactive"/>
                                <tiles:put name="title" value="Шаблоны и платежи с карты"/>
                                <tiles:put name="action" value="/private/cards/payments.do?id=${cardLink.id}"/>
                            </tiles:insert>
                            <tiles:insert definition="tabItem" flush="false">
                                <tiles:put name="style" value="active"/>
                                <tiles:put name="title" value="Графики"/>                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                </div>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>
