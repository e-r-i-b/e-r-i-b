<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/ima/detail" onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

<tiles:insert definition="imaInfo">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imAccountLink" value="${form.imAccountLink}"/>
<c:set var="imAccountAbstract" value="${form.imAccountAbstract}"/>
<c:set var="imAccount" value="${imAccountLink.imAccount}"/>
<c:set var="showInMainCheckBox" value="true"/>
<c:set var="detailsPage" value="true"/>

<tiles:put name="mainmenu" value="IMAInfo"/>
<tiles:put name="menu" type="string"/>

<tiles:put name="breadcrumbs">
    <tiles:insert definition="breadcrumbsLink" flush="false">
        <tiles:put name="main" value="true"/>
        <tiles:put name="action" value="/private/accounts.do"/>
    </tiles:insert>
    <tiles:insert definition="breadcrumbsLink" flush="false">
        <tiles:put name="name" value="Металлические счета"/>
        <tiles:put name="action" value="/private/ima/list.do"/>
    </tiles:insert>
    <tiles:insert definition="breadcrumbsLink" flush="false">
        <tiles:put name="name"><bean:write name="imAccountLink" property="name"/> ${phiz:getFormattedAccountNumber(imAccountLink.number)}</tiles:put>
        <tiles:put name="last" value="true"/>
    </tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
<%@include file="scripts.jsp" %>    
<html:hidden property="id"/>
<div id="cards">
    <div id="card-detail">
        <c:if test="${imAccountLink != null}">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="data">

                    <c:set var="model"    value="simple-pagination"/>
                    <c:set var="hideable" value="false"/>
                    <c:set var="recCount" value="8"/>


                    <c:set var="nameOrNumber">
                        <c:choose>
                            <c:when test="${not empty imAccountLink.name}">
                                «${imAccountLink.name}»
                            </c:when>
                            <c:otherwise>
                                ${imAccountLink.number}
                            </c:otherwise>
                        </c:choose>
                    </c:set>

                    <c:set var="pattern">
                        <c:choose>
                            <c:when test="${not empty imAccountLink.name}">
                                «${imAccountLink.patternForFavouriteLink}»
                            </c:when>
                            <c:otherwise>
                                ${imAccountLink.patternForFavouriteLink}
                            </c:otherwise>
                        </c:choose>
                    </c:set>

                    <div class="abstractContainer3">
                        <c:set var="baseInfo">
                            <bean:message key="favourite.link.ima" bundle="paymentsBundle"/>
                        </c:set>
                        <div class="favouriteLinksButton">
                            <tiles:insert definition="addToFavouriteButton" flush="false">
                                <tiles:put name="formName"><c:out value='${baseInfo} ${nameOrNumber}'/></tiles:put>
                                <tiles:put name="patternName"><c:out value='${baseInfo} ${pattern}'/></tiles:put>
                                <tiles:put name="typeFormat">IMA_LINK</tiles:put>
                                <tiles:put name="productId">${form.id}</tiles:put>
                            </tiles:insert>
                        </div>
                    </div>

                    <%@include file="imaTemplate.jsp" %>
                   
                    <div class="tabContainer">
                        <tiles:insert definition="paymentTabs" flush="false">
                            <tiles:put name="count" value="2"/>
                            <tiles:put name="tabItems">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="first"/>
                                    <tiles:put name="active" value="false"/>
                                    <tiles:put name="title" value="Последние операции"/>
                                    <tiles:put name="action" value="/private/ima/info.do?id=${imAccountLink.id}"/>
                                </tiles:insert>
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="last"/>
                                    <tiles:put name="active" value="true"/>
                                    <tiles:put name="title" value="Детальная информация"/>
                                    <tiles:put name="action" value="/private/ima/detail.do?id=${imAccountLink.id}"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>

                        <div class="productTitleDetailInfo">
                            <div id="productNameText" name="productNameText" class="word-wrap">
                                <span class="productTitleDetailInfoText">
                                    <c:out value="${form.fields.imAccountName}"/>
                                    <a class="productTitleDetailInfoEditBullet" onclick="showEditProductName();"></a>
                                </span>
                            </div>
                            <div id="productNameEdit" name="productNameEdit" class="productTitleDetailInfoEditBlock">
                                <html:text property="field(imAccountName)" size="30" maxlength="56" styleId="fieldInsName" styleClass="productTitleDetailInfoEditTextBox"/>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.save"/>
                                    <tiles:put name="commandHelpKey" value="button.save"/>
                                    <tiles:put name="bundle" value="imaBundle"/>
                                </tiles:insert>
                                <div class="errorDiv clear" style="display:none;"></div>
                            </div>
                            <div class="product-type uppercase">
                                <bean:message key="title.imaccount.type" bundle="imaBundle"/> ${imAccount.name}
                            </div>
                        </div>

                        <script type="text/javascript">
                            function showEditProductName() {
                                $("#productNameText").hide();
                                $("#productNameEdit").show();
                                $("#fieldInsName")[0].selectionStart = $("#fieldInsName")[0].selectionEnd = $("#fieldInsName").val().length;
                            }
                        </script>

                        <div class="abstractContainer2">

                            <div class="inlineBlock">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.show.tarif"/>
                                    <tiles:put name="commandHelpKey" value="button.show.tarif.help"/>
                                    <tiles:put name="bundle" value="imaBundle"/>
                                    <tiles:put name="onclick" value="openWindow(event, 'http://sberbank.ru/ru/person/investments/omsc/');"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="image"    value="revizity.gif"/>
                                    <tiles:put name="imageHover"     value="revizityHover.gif"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                </tiles:insert>
                            </div>
                            <div class="printButtonRight">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.print"/>
                                    <tiles:put name="commandHelpKey" value="button.print"/>
                                    <tiles:put name="bundle" value="imaBundle"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="image"    value="print-check.gif"/>
                                    <tiles:put name="imageHover"     value="print-check-hover.gif"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                    <tiles:put name="onclick">printIMAPayments(event, 'info', 'printMoreInfo')</tiles:put>
                                </tiles:insert>
                            </div>
                        </div>
                        <div class="clear"></div>

                        <fieldset>
                            <table class="additional-product-info firstColumnFix">
                                <tr>
                                    <td class="align-right field fixColumn">Номер счёта ОМС:</td>
                                    <td><span class="bold">${phiz:getFormattedAccountNumber(imAccountLink.number)}</span></td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Тип счёта ОМС:</td>
                                    <td>
                                        <span class="bold">
                                            <c:if test="${not empty imAccount.currency}">
                                                <c:out value="${imAccount.currency.name} (${phiz:normalizeCurrencyCode(imAccount.currency.code)})"/>
                                            </c:if>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Текущий остаток:</td>
                                    <td>
                                        <span class="bold">
                                            <c:if test="${not empty imAccount.balance}">
                                                ${phiz:formatAmount(imAccount.balance)}
                                            </c:if>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Номер договора:</td>
                                    <td>
                                        <span class="bold">
                                            <c:choose>
                                                <c:when test="${not empty imAccount.agreementNumber && imAccount.agreementNumber != ''}">
                                                    ${imAccount.agreementNumber}
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${not empty imAccount.number}">
                                                        ${imAccount.number}
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Дата открытия:</td>
                                    <td>
                                        <span class="bold">
                                            <c:if test="${not empty imAccount.openDate}">
                                                ${phiz:formatDateWithStringMonth(imAccount.openDate)}
                                            </c:if>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Текущее состояние:</td>
                                    <td>
                                        <span class="bold">
                                            <c:if test="${not empty imAccount.state}">
                                                ${imAccount.state != 'closed' ? 'открыт' : 'закрыт' }
                                            </c:if>
                                        </span>
                                    </td>
                                </tr>
                                <c:if test="${imAccount.state == 'closed'}">
                                    <tr>
                                        <td class="align-right field">Дата закрытия:</td>
                                        <td>
                                            <span class="bold">
                                                <c:if test="${not empty imAccount.closingDate}">
                                                    ${phiz:formatDateWithStringMonth(imAccount.closingDate)}
                                                </c:if>
                                            </span>
                                        </td>
                                    </tr>
                                </c:if>
                            </table>
                        </fieldset>
                    </div>
                </tiles:put>
            </tiles:insert>
        </c:if>

        <c:if test="${not empty form.additionalIMAccountLink}">
            <div id="another-cards">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="data">
                        <c:set var="imAccountCount" value="${phiz:getClientProductLinksCount('IM_ACCOUNT')}"/>
                        <tiles:put name="title">
                            Остальные металлические счета
                            (<a href="${phiz:calculateActionURL(pageContext, '/private/ima/list')}" class="blueGrayLink">показать все ${imAccountCount}</a>)
                        </tiles:put>
                        <div class="another-items">
                            <c:set var="imaInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/ima/info.do?id=')}"/>
                            <c:forEach items="${form.additionalIMAccountLink}" var="additional">
                                <c:set var="additionalIMAccount" value="${additional.imAccount}"/>
                                <div class="another-container">
                                    <a href="${imaInfoUrl}${additional.id}">
                                        <img src="${image}/ima_type/imaccount32.jpg" alt="${additional.name}"/>
                                    </a>
                                    <a class="another-name" href="${imaInfoUrl}${additional.id}">
                                        <c:choose>
                                            <c:when test="${not empty additional.name}">
                                                <bean:write name="additional" property="name"/>
                                            </c:when>
                                            <c:when test="${not empty additionalIMAccount.currency}">
                                                <c:out value="${additionalIMAccount.currency.name} (${phiz:normalizeCurrencyCode(additionalIMAccount.currency.code)})"/>
                                            </c:when>
                                        </c:choose>
                                    </a>
                                    <div class="another-number">
                                        <a class="another-number decoration-none" href="${imaInfoUrl}${additional.id}">${phiz:getFormattedAccountNumber(additional.number)}</a>
                                        <c:set var="state" value="${not empty additionalIMAccount.state ? additionalIMAccount.state : ''}"/>
                                        <c:set var="className">
                                            <c:if test="${state eq 'closed'}">
                                                red
                                            </c:if>
                                        </c:set>

                                        <span class="${className}">
                                            <span class="prodStatus status" style="font-weight:normal;">
                                                <c:if test="${not empty className}">
                                                    <nobr>(${state.description})</nobr>
                                                </c:if>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </c:forEach>
                            &nbsp;
                            <div class="clear"></div>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:if>
    </div>
</div>
</tiles:put>
</tiles:insert>
</html:form>
