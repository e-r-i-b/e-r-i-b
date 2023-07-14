<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<%--showFull - флаг отображения элементов на форме детальной информации о карточке ПФР--%>
<%-- param.hideShowInMainCheckBox - надо ли скрывать галку "показывать на главной" --%>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="pfrLink" value="${form.pfrLink}"/>
<c:set var="bundle" value="pfrBundle"/>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images/products"/>

<script type="text/javascript">
    function openStatement(claimId)
    {
        var url = "${phiz:calculateActionURL(pageContext,'/private/pfr/statement.do')}";
        var params = "?claimId=" + claimId;
        var winpar = "resizable=1,menubar=1,toolbar=0,scrollbars=1"+
                ",width=750" +
                ",height=0" + (screen.height - 200) +
                ",left=0" + ((screen.width) / 2 - 400) +
                ",top=0" + 50;
        window.open(url + params, "PFRStatement", winpar);
        return false;
    }
</script>

<div class="pfrProduct">
    <tiles:insert definition="productTemplate" flush="false">
        <tiles:put name="productViewBacklight" value="false"/>
        <c:if test="${detailsPage}">
            <tiles:put name="operationsBlockPosition" value="topPosition"/>
        </c:if>
        <tiles:put name="img" value="${imagePath}/icon_pfr.jpg"/>

        <c:if test="${!showFull}">
            <c:set var="listUrl" value="${phiz:calculateActionURL(pageContext,'/private/pfr/list.do')}"/>
            <tiles:put name="src" value="${listUrl}"/>
        </c:if>

        <tiles:put name="title">Выписка  о состоянии  индивидуального лицевого счета</tiles:put>
        <c:choose>
            <c:when test="${detailsPage}">
                <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
            </c:otherwise>
        </c:choose>


        <tiles:put name="leftData">
            <bean:write name="form" property="user.SNILS"/>
        </tiles:put>

        <tiles:put name="centerData">
            &nbsp;
        </tiles:put>

        <tiles:put name="rightData">
            <tiles:insert definition="listOfOperation" flush="false">
                <tiles:put name="productOperation" value="true"/>
                <c:if test="${detailsPage}">
                    <tiles:put name="nameOfOperation">Операции с пенсионным фондом</tiles:put>
                </c:if>

                <tiles:putList name="items">
                    <c:if test="${phiz:impliesOperation('CreateFormPaymentOperation', 'PFRStatementClaim')}">
                        <tiles:add>
                            <phiz:link action="/private/payments/payment" serviceId="PFRStatementClaim">
                                <phiz:param name="form" value="PFRStatementClaim"/>
                               <bean:message bundle="pfrBundle" key="button.sendClaim"/>
                            </phiz:link>
                        </tiles:add>
                    </c:if>
                </tiles:putList>
            </tiles:insert>
        </tiles:put>

        <c:if test="${!showFull}">
            <tiles:put name="bottomData">
                <tiles:insert definition="simpleTableTemplate" flush="false">
                    <tiles:put name="id" value="${pfrLink.id}"/>
                    <tiles:put name="productType" value="pfr"/>
                    <tiles:put name="hideable" value="true"/>
                    <tiles:put name="show" value="${phiz:isShowOperations(pfrLink)}"/>
                    <c:choose>
                        <c:when test="${not form.pfrClaimsInitialized}">
                            <tiles:put name="ajaxDataURL">${phiz:calculateActionURL(pageContext, '/private/async/extract.do')}?type=pfr&id=${pfrLink.id}</tiles:put>
                        </c:when>
                        <c:when test="${form.pfrDown}">
                            <tiles:put name="grid">
                                <div class="emptyMiniAbstract">
                                    <bean:message key="pfr.claims.error" bundle="pfrBundle"/>
                                </div>
                            </tiles:put>
                        </c:when>
                        <c:when test="${empty form.pfrClaims}">
                            <tiles:put name="grid">
                                <div class="emptyMiniAbstract">
                                    <bean:message key="pfr.claims.main.empty" bundle="pfrBundle"/>
                                </div>
                            </tiles:put>
                        </c:when>
                        <c:otherwise>
                            <tiles:put name="grid">
                                <tiles:insert page="miniAbstract.jsp" flush="false">
                                    <tiles:put name="pfrClaims" beanName="form" beanProperty="pfrClaims"/>
                                </tiles:insert>
                            </tiles:put>
                        </c:otherwise>
                    </c:choose>
                </tiles:insert>
            </tiles:put>
        </c:if>

            <tiles:put name="id" value="${pfrLink.id}"/>
            <c:if test="${not param.hideShowInMainCheckBox}">
                <tiles:put name="showInMainCheckBox" value="true"/>
                <tiles:put name="inMain" value="${pfrLink.showInMain}"/>
            </c:if>
            <tiles:put name="productType" value="pfr"/>
    </tiles:insert>
</div>