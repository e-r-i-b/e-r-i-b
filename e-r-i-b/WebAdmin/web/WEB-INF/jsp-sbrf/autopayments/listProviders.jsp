<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/autopayment/providers">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="autoSubscriptions">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="left.menu.autopayments" bundle="autopaymentsBundle"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="AutoSubscriptions"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" service="EmployeeFreeDetailAutoSubManagement">
                <tiles:put name="commandTextKey" value="button.addFreeDetailClaim"/>
                <tiles:put name="commandHelpKey" value="button.addFreeDetailClaim.help"/>
                <tiles:put name="bundle"         value="autopaymentsBundle"/>
                <tiles:put name="action" value="/autopayment/freeDetatilAutoSub"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
            <c:set var="subMenu" value="AutoSubscriptions"/>
            <c:set var="serviceName" value="AutoSubscriptionManagment"/>
            <c:set var="linkButtonBackToList" value="/autopayment/list.do"/>
            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/backToSubscriptionListButton.jsp" %>
        </tiles:put>

        <tiles:put name="filter" type="string">
            <%-- строка поиска --%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"  value="filter.row.search"/>
                <tiles:put name="bundle" value="autopaymentsBundle"/>
                <tiles:put name="name"   value="searchRow"/>
                <tiles:put name="size"   value="50"/>
                <tiles:put name="maxlength"  value="255"/>
            </tiles:insert>

            <%-- выбор региона --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.region"/>
                <tiles:put name="bundle" value="providerBundle"/>
                <tiles:put name="data">
                    <nobr>
                        <c:set var="regionId"><bean:write name="form" property="filter(regionId)"/></c:set>
                        <c:choose>
                            <c:when test="${form.fromStart or regionId == '-1'}">
                                <html:text styleId="regionName" property="filter(regionName)" value="все регионы" readonly="true" style="color:silver; width:200px"/>
                            </c:when>
                            <c:otherwise>
                                <html:text styleId="regionName" property="filter(regionName)" readonly="true" style="width:200px"/>
                            </c:otherwise>
                        </c:choose>
                        <html:hidden property="filter(regionId)" value="${not empty regionId?regionId:-1}"/>
                        <input type="button" class="buttWhite" onclick="openRegionsDictionary(setRegionInfo, 'filter');" value="..."/>
                        <script type="text/javascript">
                           function setRegionInfo(result)
                           {
                               setElement("filter(regionId)",   result['id']);
                               setElement("filter(regionName)", result['name']);
                               ensureElement('regionName').style.color = 'black';
                           }
                       </script>
                    </nobr>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <c:set var="result" value="${form.searchResults}"/>
            <c:set var="searchPage" value="${form.searchPage}"/>
            <c:set var="itemsPerPage" value="${form.itemsPerPage}"/>
            <c:set var="sizeResult" value="${fn:length(result)}"/>
            <%-- фильтр должен быть открыт --%>
            <script type="text/javascript">
                switchFilter(this);
                
                function clearFilter()
                {
                    //неовозможно использовать стандартный метод clearFilter т.к. regionName не поменяет цвет
                    setElement("filter(regionId)",   '-1');
                    setElement("filter(regionName)", "все регионы");
                    setElement("filter(searchRow)", "");
                    ensureElement('regionName').style.color = 'silver';
                }

            </script>

            <div id="listProviders">
                <c:forEach var="element" items="${form.searchResults}" varStatus="stat">
                    <c:if test="${stat.count le itemsPerPage}">

                        <c:set var="providerId" value="${element[0]}"/>
                        <c:set var="nameProvider" value="${element[1]}"/>
                        <c:set var="innProvider" value="${element[2]}"/>
                        <c:set var="accountProvider" value="${element[3]}"/>
                        <c:set var="imageId" value="${element[4]}"/>

                        <div class="cardProvider">
                            <c:choose>
                                <c:when test="${not empty imageId}">
                                    <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                                    <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="image" value="${globalUrl}/images/logotips/IQWave/IQWave-other.jpg" />
                                </c:otherwise>
                            </c:choose>

                            <tiles:insert definition="simpleProviderTemplate" flush="false">
                                <tiles:put name="image" value="${image}"/>
                                <tiles:put name="data">
                                    <phiz:link action="/autopayment/servicesPayments" styleClass="fastSearchLink bold"
                                               operationClass="CreateESBAutoPayOperation" serviceId="CreateEmployeeAutoPayment">
                                        <span class="word-wrap">
                                            <phiz:param name="recipient" value="${providerId}"/>
                                            <c:out value="${nameProvider}"/>
                                        </span>
                                    </phiz:link>
                                    <div>ИНН: ${innProvider}</div>
                                    <div>Расчетный счет: ${accountProvider}</div>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:if>

                </c:forEach>
                <c:if test="${fn:length(form.searchResults) == 0}">
                    <table width="100%" cellpadding="4">
                        <tr>
                            <td class="messageTab" align="center">
                                <c:choose>
                                    <c:when test="${form.fromStart}">
                                        Для поиска поставщиков в системе предусмотрен фильтр. Чтобы им воспользоваться, в полях фильтра задайте критерии поиска и нажмите на кнопку «Применить».
                                    </c:when>
                                    <c:otherwise>
                                        Не найдено ни одного получателя, cоответствующего заданному фильтру!
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </table>
                </c:if>
            </div>
            <div class="clear"></div>

            <%-- рисуем таблицу пагинации вручную, т.к. список не гридовый --%>
            <c:if test="${searchPage > 0 || sizeResult > itemsPerPage}">
                <div class="pagination">
                    <%-- запоминаем текущую страницу --%>
                    <input type="hidden" name="searchPage" id="searchPage">
                    <table cellspacing="0" cellpadding="0" class="autoTableheigth tblNumRec">
                        <tr>
                            <td nowrap="nowrap">
                                Записи с <span class="tblNumRecIns">${searchPage*itemsPerPage + 1}</span> по <span>${searchPage*itemsPerPage + sizeResult}</span>
                            </td>

                             <c:if test="${searchPage > 0}">
                                <td nowrap="nowrap">
                                    <img border="0" alt="" src="${imagePath}/iconSm_triangleLeft.gif">
                                    <a onclick="setElement('searchPage', ${searchPage - 1}); callOperation(event,'button.filter'); return false" href="#">
                                        Предыдущие ${itemsPerPage}
                                    </a>
                                </td>
                            </c:if>

                            <c:if test="${itemsPerPage < sizeResult}">
                                <td nowrap="nowrap">
                                    <a onclick="setElement('searchPage', ${searchPage + 1}); callOperation(event,'button.filter'); return false" href="#">
                                        Следующие ${itemsPerPage}
                                    </a>
                                    <img border="0" alt="" src="${imagePath}/iconSm_triangleRight.gif">
                                </td>
                            </c:if>

                            <td nowrap="nowrap" align="right" width="100%">
                                Записей на странице
                                <html:select property="itemsPerPage" onchange="callOperation(event,'button.filter');">
                                    <html:option value="20">20</html:option>
                                    <html:option value="50">50</html:option>
                                    <html:option value="100">100</html:option>
                                </html:select>
                            </td>
                        </tr>
                    </table>
                </div>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>