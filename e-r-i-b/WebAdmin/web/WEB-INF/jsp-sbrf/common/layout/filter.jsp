<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 11:55:51
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute name="filter" ignore="true" scope="request"/>
<c:if test="${filter  != ''}">
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <!--основная таблица-->
    <div style="position:relative;">
        <table cellspacing="0" cellpadding="0" id="filterFields" class="filter"
               style="display:none;position:relative;">
            <tr>
                <td class="fltrBgTopLeftCorner"><image src='${globalImagePath}/1x1.gif' height="15" width="15" border='0'/></td>
                <td class="fltrBgTop"><image src='${globalImagePath}/1x1.gif' height="15" border='0'/></td>
                <td class="fltrBgTopRightCorner"><image src='${globalImagePath}/1x1.gif' height="15" width="15" border='0'/></td>
            </tr>
            <tr>
                <td class="fltrBgLeftCorner"><image src='${globalImagePath}/1x1.gif' width="15" border='0'/></td>
                <td class="MaxSize fltrBg">
                    <!--внешняя таблица стиля фильтра-->
                    <table cellspacing="0" cellpadding="0" class="MaxSize fltrInfArea"
                           onkeypress="onEnterKey(event);">
                        <tr>
                            <!-- фильтр -->
                            <tiles:insert attribute="filter"/>
                        </tr>
                    </table>
                </td>
                <td class="fltrBgRightCorner"><image src='${globalImagePath}/1x1.gif' width="14" border='0'/></td>
            </tr>
            <tr>
                <td class="fltrBgLeftCorner"><image src='${globalImagePath}/1x1.gif' width="15" height="25" border='0'/></td>
                <c:if test="${empty bundleName or bundleName==''}">
                    <c:set var="bundleName" value="commonBundle"/>
                </c:if>
                <!--стандартные кнопки -->
                <td align="center" class="fltrBgBtm" rowspan="2">
                    <table cellpadding="0" cellspacing="0">
                        <tr>
                            <td>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.filter"/>
                                    <tiles:put name="commandHelpKey" value="button.filter.help"/>
                                    <tiles:put name="bundle" value="${bundleName}"/>
                                    <tiles:put name="isDefault" value="true"/>
                                </tiles:insert>
                                <tiles:insert definition="clientButton" flush="fase">
                                    <tiles:put name="commandTextKey" value="button.clear"/>
                                    <tiles:put name="commandHelpKey" value="button.clear.help"/>
                                    <tiles:put name="bundle" value="${bundleName}"/>
                                    <tiles:put name="onclick" value="clearFilter(event);"/>
                                </tiles:insert>
                                <!--дополнительные кнопки-->
                                <c:if test="${additionalFilterButtons  != ''}">
                                    <tiles:insert attribute="additionalFilterButtons"/>
                                </c:if>
                            </td>
                        </tr>
                    </table>
                </td>
                <td class="fltrBgRightCorner"><image src='${globalImagePath}/1x1.gif' width="15" height="25" border='0'/></td>
            </tr>
            <tr>
                <td class="fltrBgBtmLeftCorner"><image src='${globalImagePath}/1x1.gif' height="15" width="15" border='0'/></td>
                <td class="fltrBgBtmRightCorner"><image src='${globalImagePath}/1x1.gif' height="15" width="15" border='0'/></td>
            </tr>
        </table>
        <table class="filterLink" style="position:relative;">
            <tr>
                <td>
                    <a id="showFilterButton" href="" onClick="switchFilter(event)"
                       title="Показать/скрыть фильтр">
                        <image src='${imagePath}/iconSm_triangleDown.gif' border='0'/>
                        Показать фильтр
                    </a>
                </td>
            </tr>
        </table>
    </div>
</c:if>