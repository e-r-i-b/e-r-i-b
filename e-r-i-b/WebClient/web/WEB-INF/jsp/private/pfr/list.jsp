<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>

<html:form action="/private/pfr/list">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="pfrLink" value="${form.pfrLink}"/>
    <c:set var="detailsPage" value="true"/>
    <tiles:insert definition="pfr">
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Пенсионные программы"/>
                <tiles:put name="action" value="/private/npf/list.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Пенсионный фонд"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <c:if test="${!pfrLink.showInSystem}">

                <script type="text/javascript">
                    <c:set var="textMessage"><bean:message bundle="pfrBundle" key="pfr.attach.message"/></c:set>
                    addMessage('${textMessage}');
                </script>
            </c:if>
             <c:if test="${pfrLink.showInSystem}">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="data">
                        <c:set var="showFull" value="true"/>
                        <div class="abstractContainer3">
                            <div class="favouriteLinksButton">
                                <tiles:insert definition="addToFavouriteButton" flush="false">
                                    <tiles:put name="formName">Последние операции с ПФР</tiles:put>
                                    <tiles:put name="typeFormat">PFR_LINK</tiles:put>
                                </tiles:insert>
                            </div>
                        </div>
                        <%@include file="pfrTemplate.jsp" %>
                        <div class="tabContainer">
                            <tiles:insert definition="paymentTabs" flush="false">
                                <tiles:put name="count" value="1"/>
                                <tiles:put name="tabItems">
                                    <tiles:insert definition="paymentTabItem" flush="false">
                                        <tiles:put name="position" value="first-last"/>
                                        <tiles:put name="active" value="true"/>
                                        <tiles:put name="title" value="Последние операции"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>

                            <div class="clear separateAbstract">&nbsp;</div>
                            <div class="abstractContainer2">
                                <c:set var="periodName" value="Date"/>
                                <c:set var="curType"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(type${periodName})"/></c:set>
                                <tiles:insert definition="filterDataPeriod" flush="false">
                                    <tiles:put name="week" value="false"/>
                                    <tiles:put name="month" value="false"/>
                                    <tiles:put name="id" value="detailFilterButton"/>
                                    <tiles:put name="name" value="${periodName}"/>
                                    <tiles:put name="buttonKey" value="button.filter"/>
                                    <tiles:put name="buttonBundle" value="accountInfoBundle"/>
                                    <tiles:put name="needErrorValidate" value="false"/>
                                    <tiles:put name="isNeedTitle" value="false"/>
                                </tiles:insert>
                            </div>
                            <div class="clear"></div>

                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                <tiles:put name="id" value="detailInfoTable"/>
                                <tiles:put name="productType" value="pfr"/>
                                <tiles:put name="show" value="true"/>
                                <c:choose>
                                    <c:when test="${form.error}">
                                        <tiles:put name="isEmpty" value="true"/>
                                        <tiles:put name="emptyMessage"><bean:message key="pfr.claims.error" bundle="pfrBundle"/></tiles:put>
                                    </c:when>
                                    <c:otherwise>
                                        <tiles:put name="grid">
                                            <sl:collection id="claim" name="form" property="data" model="simple-pagination">
                                                <sl:collectionItem title=" ">
                                                    <c:choose>
                                                        <c:when test="${claim.state.code == 'DRAFT'}">
                                                            <c:set var="claimActionUrl" value="/private/payments/payment"/>
                                                        </c:when>
                                                        <c:when test="${claim.state.code == 'SAVED'}">
                                                            <c:set var="claimActionUrl" value="/private/payments/confirm"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="claimActionUrl" value="/private/payments/view"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <phiz:link action="${claimActionUrl}" serviceId="PFRService">
                                                        <phiz:param name="id" value="${claim.id}"/>
                                                        запрос на получение выписки
                                                    </phiz:link>
                                                </sl:collectionItem>
                                                <sl:collectionItem title="Дата и время">
                                                    ${phiz:formatDateDependsOnSysDate(claim.dateCreated, true, false)}
                                                </sl:collectionItem>
                                                <sl:collectionItem title="Статус запроса">
                                                    <bean:message key="state.${claim.state.code}" bundle="pfrBundle"/>

                                                    <c:if test="${claim.state.code=='EXECUTED'}">
                                                        &nbsp;
                                                        (<a href="" onclick="return openStatement(${claim.id});">посмотреть выписку</a>)
                                                    </c:if>
                                                </sl:collectionItem>
                                            </sl:collection>
                                        </tiles:put>
                                        <tiles:put name="isEmpty" value="${empty form.data}"/>
                                        <tiles:put name="emptyMessage"><bean:message key="pfr.claims.full.empty" bundle="pfrBundle"/></tiles:put>
                                    </c:otherwise>
                                </c:choose>
                            </tiles:insert>
                            
                            <c:if test="${phiz:impliesServiceRigid('PFRStatementClaim')}">
                                <div class="buttonsArea">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.sendClaim"/>
                                        <tiles:put name="commandHelpKey" value="button.sendClaim"/>
                                        <tiles:put name="bundle"         value="pfrBundle"/>
                                        <tiles:put name="action"         value="/private/payments/payment.do?form=PFRStatementClaim"/>
                                    </tiles:insert>
                                </div>
                                <div class="clear"></div>
                            </c:if>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </c:if>
        </tiles:put>

    </tiles:insert>
</html:form>
