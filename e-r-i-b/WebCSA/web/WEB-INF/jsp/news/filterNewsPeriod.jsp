<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="periodName" value="Date"/>
<c:set var="curType"><bean:write name="form"  property="filter(type${periodName})"/></c:set>
<c:set var="name" value="Date"/>
<c:set var="title" value="Показать события"/>
<c:set var="id" value=""/>
<c:set var="windowId" value=""/>
<c:set var="onclick" value=""/>
<c:set var="action" value=""/>
<c:set var="buttons" value=""/>
<c:set var="needErrorValidate" value="true"/>
<c:set var="periodFormat" value="dd/MM/yyyy"/>
<c:set var="isSimpleTrigger" value="false"/>

<c:set var="oneClickFilterData">
    <html:hidden name='form' styleId="filter(type${name})" property="filter(type${name})" onchange="filterDateChange('${name}')"/>
    <c:set var="curType"><bean:write name="form" property="filter(type${name})"/></c:set>
    <table class="date-filter" onkeypress="onCsaFilterEnterKey(event);">
        <tr>
            <td class="filterDataTitle">
                <nobr>
                    ${title}
                </nobr>
            </td>

            <c:set var="ajaxUrl" value="${csa:calculateActionURL(pageContext, '/async/news/list')}"/>
            <c:set var="className" value="date-filter-active"/>


            <td class="${className}" id="type${name}period" onclick="oneClickFilterDateChange ('${name}', 'period', ${isSimpleTrigger}); return false;">
                <nobr>
                    <div>
                       за период
                    </div>
                    <span id="type${name}periodDetail">
                        &nbsp;

                        <div>
                            c
                        </div>
                        <input value='<bean:write name="form" property="filter(from${name})" format="${periodFormat}"/>'
                               size="10" name="filter(from${name})" id="filter(from${name})" class="${name}date-pick" />
                        <div>
                            по
                        </div>
                        <input value='<bean:write name="form" property="filter(to${name})" format="${periodFormat}"/>'
                               size="10" name="filter(to${name})" id="filter(to${name})" class="${name}date-pick endOfPeriod"/>
                        <div id="findButton" class="commandButton" onclick="callOperation(null, 'button.filter'); return false;">
                            <img class="iconButton" src="${skinUrl}\skins\sbrf\images\period-find.png" alt="Построить список" title="Построить список">
                        </div>
                    </span>
                </nobr>
            </td>

        </tr>
    </table>
</c:set>
${oneClickFilterData}

