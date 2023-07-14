<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>
 <%--
 bottomDataInfo - данные низа. Если не пустое то отображается содержимое данной переменной а опереции не отображаются.
 showInMainCheckBox - признак, указывающий на необходимость отображения checkbox'а отвечающего за отображение
                        данного вклада на главной странице. Значение по умолчанию false
--%>

<!-- Информация по счету -->
<c:if test="${depoAccountLink != null}">
    <c:set var="depoAccount" value="${depoAccountLink.depoAccount}"/>
    <c:set var="depoAccountInfoUrl" value="${phiz:calculateActionURL(pageContext, '/private/depo/info/position')}"/>
    <c:set var="depoDocumentsUrl" value="${phiz:calculateActionURL(pageContext, '/private/depo/documents')}"/>
    <c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>

    <div class="depoAccountTemplate <c:if test="${detailsPage}">detailTemplate</c:if>">
        <tiles:insert definition="productTemplate" flush="false">
            <tiles:put name="productViewBacklight" value="false"/>
            <c:if test="${detailsPage}">
                <tiles:put name="operationsBlockPosition" value="rightPosition"/>
            </c:if>
            <c:set var="isLock" value="${depoAccount.state != 'open'}"/>
            <c:choose>
                <c:when test="${isLock}">
                    <tiles:put name="img" value="${imagePath}/icon_depositariyBlocked.jpg"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="img" value="${imagePath}/icon_depositariy.jpg"/>
                </c:otherwise>
            </c:choose>

            <tiles:put name="alt" value="Счет депо"/>

            <tiles:put name="productNumbers">
                <div class="productNumber decoration-none">№ договора&nbsp;${depoAccount.agreementNumber}</div>
            </tiles:put>
            <tiles:put name="leftData">
                <c:if test="${isLock}">
                    <table>
                        <tr>
                            <td>
                                <span class="detailStatus">${depoAccount.state.description}</span>
                            </td>
                        </tr>
                    </table>
                </c:if>
                <table class="productDetail">
                    <tr>
                        <td>
                            <div class="availableReight">Открыт: &nbsp;</div>
                        </td>
                        <td>
                            <div class="bold availableReight">${phiz:formatDateWithStringMonth(depoAccount.agreementDate)}</div>
                        </td>
                    </tr>
                </table>
            </tiles:put>

            <tiles:put name="title">
                <c:choose>
                    <c:when test="${empty depoAccountLink.name}">
                        <bean:write name="depoAccountLink" property="accountNumber"/>
                    </c:when>
                    <c:otherwise>
                        <bean:write name="depoAccountLink" property="name"/>(<bean:write name="depoAccountLink" property="accountNumber"/>)
                    </c:otherwise>
                </c:choose>
            </tiles:put>
            <c:choose>
                <c:when test="${detailsPage}">
                    <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
                </c:otherwise>
            </c:choose>


            <%--На странице просмотра позиции, списка документов и для закрытых счетов ссылка неактивна--%>

            <c:if test="${not detailsPage and !isLock and not depoAccDocList}">
                <tiles:put name="src" value="${depoAccountInfoUrl}?id=${depoAccountLink.id}"/>
            </c:if>
            <c:if test="${isLock and not depoAccDocList}">
                <tiles:put name="src" value="${depoDocumentsUrl}?id=${depoAccountLink.id}"/>
            </c:if>
            <tiles:put name="centerData">
                <c:if test="${!isLock && phiz:impliesService('DepoDebtInfo')}">
                    <c:set var="debt" value="${depoAccount.debt}"/>
                    <c:if test="${not empty debt}">
                        <c:set var="amountinfo" value="${phiz:formatAmount(debt)}"/>
                        <c:if test="${debt.decimal<0}">
                            <c:set var="amountinfo">
                                &minus;${fn:substring(amountinfo, 1, -1)}
                            </c:set>
                        </c:if>
                        <c:choose>
                            <c:when test="${isLock || debt.decimal<0}">
                                <c:if test="${detailsPage}">
                                    <span class="detailAmount negativeAmount">${amountinfo}</span>
                                </c:if>
                                <c:if test="${not detailsPage}">
                                    <span class="overallAmount negativeAmount">${amountinfo}</span>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${detailsPage}">
                                    <span class="detailAmount">${amountinfo}</span>
                                </c:if>
                                <c:if test="${not detailsPage}">
                                    <span class="overallAmount">${amountinfo}</span>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                        <br />
                        <span class="amountStatus">Задолженность</span>
                    </c:if>
                </c:if>
            </tiles:put>
            <tiles:put name="rightData">
                 <tiles:insert definition="listOfOperation" flush="false">
                     <tiles:put name="productOperation" value="true"/>
                     <c:if test="${detailsPage}">
                        <tiles:put name="nameOfOperation">Операции по счету депо</tiles:put>
                     </c:if>

                    <tiles:putList name="items">

                        <c:if test="${!isLock && phiz:impliesTemplateOperation('DepositorFormClaim')}">
                             <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/depo/info/form')}?linkId=${depoAccountLink.id}</c:set>
                            <tiles:add> <a href="${url}">Анкета депонента</a></tiles:add>
                        </c:if>

                        <c:if test="${!isLock && phiz:impliesService('DepoDebtInfo')}">
                            <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/depo/info/debt')}?id=${depoAccountLink.id}</c:set>
                            <tiles:add><a href="${url}">Задолженность</a></tiles:add>
                        </c:if>

                        <c:if test="${not detailsPage and !isLock}">
                            <c:set var="url">${depoAccountInfoUrl}?id=${depoAccountLink.id}</c:set>
                            <tiles:add><a href="${url}">Позиции</a></tiles:add>
                        </c:if>

                        <c:if test="${not depoAccDocList}">
                            <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/depo/documents')}?id=${depoAccountLink.id}</c:set>
                            <tiles:add><a href="${url}">Список документов</a></tiles:add>
                        </c:if>
                    </tiles:putList>
                </tiles:insert>

            </tiles:put>

            <c:if test="${showInMainCheckBox}">
                <tiles:put name="id" value="${depoAccountLink.id}" />
                <tiles:put name="showInMainCheckBox" value="true" />
                <tiles:put name="inMain" value="${depoAccountLink.showInMain}" />
                <tiles:put name="productType" value="depaccount"/>
            </c:if>
            <c:if test="${isLock}">
                <tiles:put name="status" value="error"/>
            </c:if>
            
        </tiles:insert>
     </div>
</c:if>