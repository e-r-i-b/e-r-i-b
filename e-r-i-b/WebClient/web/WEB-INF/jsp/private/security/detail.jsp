<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="detailsPage" value="true" scope="request"/>
<html:form action="/private/security/view" onsubmit="return setEmptyAction(event)">

    <tiles:insert definition="securityAccountInfo">
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>
        <c:set var="productUrl" value="${globalUrl}/commonSkin/images/products"/>

        <tiles:put name="menu" type="string"/>
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Сберегательные сертификаты"/>
                <tiles:put name="action" value="/private/security/list.do"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="securityLink" value="${form.link}" scope="request"/>
            <c:set var="securityAccount" value="${form.securityAccount}"/>
            <c:if test="${!form.backError}">
            <c:set var="periodExpired" value="${securityAccount.termStartDt <= phiz:currentDate()}" />
            <c:set var="securityInfoLink" value="false"/>
            <html:hidden property="id"/>
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="data">
                    <script type="text/javascript">
                        function printDetail(event)
                        {
                            var url = "${phiz:calculateActionURL(pageContext,'/private/security/print')}?id=${form.id}";
                            openWindow(event, url, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=0,width=1040,height=700");
                        }
                    </script>

                    <%@include file="securityAccountTemplate.jsp" %>

                    <div class="tabContainer">
                        <tiles:insert definition="paymentTabs" flush="false">
                            <tiles:put name="count" value="1"/>
                            <tiles:put name="tabItems">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="first-last"/>
                                    <tiles:put name="active" value="true"/>
                                    <tiles:put name="title" value="Детальная информация"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>

                        <div class="productTitleDetailInfo">
                            <div id="productNameText" name="productNameText" class="word-wrap">
                                <span class="productTitleDetailInfoText">
                                    <c:out value="${form.fields.securityName}"/>
                                    <a class="productTitleDetailInfoEditBullet" onclick="showEditProductName();"></a>
                                </span>
                            </div>
                            <div id="productNameEdit" name="productNameEdit" class="productTitleDetailInfoEditBlock">
                                <html:text property="field(securityName)" size="30" maxlength="100" styleId="fieldSecurityName" styleClass="productTitleDetailInfoEditTextBox"/>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.saveSecurityName"/>
                                    <tiles:put name="commandHelpKey" value="button.saveSecurityName"/>
                                    <tiles:put name="bundle" value="securitiesBundle"/>
                                </tiles:insert>
                                <div class="errorDiv clear" style="display:none;"></div>
                            </div>
                        </div>
                        <script type="text/javascript">
                            function showEditProductName() {
                                $("#productNameText").hide();
                                $("#productNameEdit").show();
                                $("#fieldSecurityName")[0].selectionStart = $("#fieldSecurityName")[0].selectionEnd = $("#fieldSecurityName").val().length;
                            }
                        </script>


                        <div class="abstractContainer2">
                            <div class="printButtonRight">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.print"/>
                                    <tiles:put name="commandHelpKey" value="button.print"/>
                                    <tiles:put name="bundle" value="insuranceBundle"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="image"    value="print-check.gif"/>
                                    <tiles:put name="imageHover"     value="print-check-hover.gif"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                    <tiles:put name="onclick">printDetail(event)</tiles:put>
                                </tiles:insert>
                            </div>
                        </div>
                        <div class="clear"></div>
                        <fieldset>
                            <table class="additional-product-info firstColumnFix">
                                <tr>
                                    <td class="align-right field fixColumn">Наименование ценной бумаги:</td>
                                    <td><span class="bold"><bean:message key="security.name" bundle="securitiesBundle"/> </span></td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Идентификатор ценной бумаги:</td>
                                    <td><span class="bold word-wrap"><c:out value="${securityAccount.serialNumber}"/></span></td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Размер вклада, оформленного сертификатом:</td>
                                    <c:set var="securityAmount" value="${phiz:getSecurityAmount(securityAccount)}"/>
                                    <td><span class="bold word-wrap">${phiz:formatAmount(securityAmount)}</span></td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Реквизиты филиала, выдавшего сертификат:</td>
                                    <td><span class="bold"><c:out value="${securityAccount.issuerBankId}"/> <c:out value="${securityAccount.issuerBankName}"/></span></td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Дата выдачи ценной бумаги:</td>
                                    <td><span class="bold word-wrap">${phiz:сalendarToString(securityAccount.composeDt)}</span></td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Срок обращения(дней):</td>
                                    <td><span class="bold word-wrap">${phiz:getPeriodInDays(securityAccount.composeDt, securityAccount.termStartDt)}</span></td>
                                </tr>

                               <tr>
                                    <td class="align-right field">Дата востребования суммы по сертификату:</td>
                                    <td><span class="bold word-wrap">${phiz:сalendarToString(securityAccount.termStartDt)}</span></td>
                                </tr>
                               <tr>
                                    <td class="align-right field">Ставка процентов за пользование вкладом:</td>
                                    <td>
                                        <span class="bold word-wrap">
                                            <c:choose>
                                                <c:when test="${periodExpired == true}">
                                                    0%
                                                </c:when>
                                                <c:otherwise>
                                                    ${securityAccount.incomeRate}%
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </td>
                               </tr>

                               <tr>
                                    <td class="align-right field">Сумма причитающихся процентов:</td>
                                    <td>
                                        <span class="bold word-wrap">
                                            <c:choose>
                                                <c:when test="${periodExpired == true}">
                                                    0
                                                </c:when>
                                                <c:otherwise>
                                                    ${phiz:formatAmount(securityAccount.incomeAmt)}
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </td>
                                </tr>

                               <c:set var="registrarData">
                                    <c:if test="${not empty securityAccount.docNum and not empty securityAccount.docDt}">
                                        <tr>
                                            <td class="align-right field">Договор ответственного хранения:</td>
                                            <td><span class="bold">№${securityAccount.docNum} от ${phiz:сalendarToString(securityAccount.docDt)}</span></td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${not empty securityAccount.bankName}">
                                        <tr>
                                            <td class="align-right field">Реквизиты филиала, принявшего сертификат на хранение:</td>
                                            <td><span class="bold"><c:out value="${securityAccount.bankId}"/> <c:out value="${securityAccount.bankName}"/></span></td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${not empty securityAccount.bankPostAddr}">
                                        <tr>
                                            <td class="align-right field">Адрес филиала, принявшего сертификат на хранение:</td>
                                            <td><span class="bold"><c:out value="${securityAccount.bankPostAddr}"/></span></td>
                                        </tr>
                                    </c:if>
                                </c:set>
                                <c:if test="${securityLink.onStorageInBank && not empty registrarData}">
                                    <tr>
                                        <td colspan="2"> ОТВЕТСТВЕННОЕ ХРАНЕНИЕ</td>
                                    </tr>
                                    ${registrarData}
                                </c:if>
                            </table>
                        </fieldset>
                    </div>
                </tiles:put>
            </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
