<%--
  User: Zhuravleva
  Date: 07.07.2009
  Time: 11:46:02  
--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<%--
Шаблон для фильтра.

	id                            - id фильтра ???
	name                          - заголовок фильтра
	additionalFilterButtons       - дополнительные кнопки фильтра
	hidden                        - линк скрытия/показа фильтра
	data                          - содержание фильтра
	composie
--%>

<div style="position:relative;height:15px;">
	<table cellspacing="0" cellpadding="0" class="filter" id="filterFields" border="0"
        <c:if test="${hidden}">style="display:none"</c:if>        
    >
	<tr>
		<td class="fltrBgTopLeftCorner"><image src='${globalImagePath}/1x1.gif' height="5" width="1" border='0'/></td>
		<td class="fltrBgTop"><image src='${globalImagePath}/1x1.gif' height="5" width="1"  border='0'/></td>
		<td class="fltrBgTopRightCorner"><image src='${globalImagePath}/1x1.gif' height="5" width="1"  border='0'/></td>
	</tr>
	<tr>
		<td class="fltrBgLeftCorner"><image src='${globalImagePath}/1x1.gif' height="5" width="1"  border='0'/></td>
		<td class="fltrBg">
		<!--внешняя таблица стиля фильтра-->
		<table cellspacing="0" cellpadding="0"class="MaxSize fltrInfArea" onkeypress="onEnterKey(event);">
        <c:if test="${data}">
		    <tr>
			<!-- фильтр -->

                ${data}
			<%--<tiles:insert attribute="filter"/>--%>
		    </tr>
        </c:if>
		</table>
		</td>
		<td class="fltrBgRightCorner">&nbsp;</td>
	</tr>
	<tr>
		<td class="fltrBgLeftCorner">&nbsp;</td>
	        <c:if test="${empty bundleName or bundleName==''}">
		        <c:set var="bundleName" value="commonBundle"/>
	        </c:if>
	        <!--стандартные кнопки -->
            <td align="center" class="fltrBgBtm" rowspan="2">
	            <table cellpadding="0" cellspacing="0"><tr><td>
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
	            </td></tr></table>
            </td>
		<td class="fltrBgRightCorner"><image src='${globalImagePath}/1x1.gif' height="5" width="1"  border='0'/></td>
    </tr>
	<tr>
		<td class="fltrBgBtmLeftCorner"><image src='${globalImagePath}/1x1.gif' height="5" width="1"  border='0'/></td>
		<td class="fltrBgBtmRightCorner"><image src='${globalImagePath}/1x1.gif' height="5" width="1"  border='0'/></td>
	</tr>
	</table>
    <c:if test="${hidden}">
        <table class="filterLink"><tr><td>
              <a id="showFilterButton" href="" onClick="switchFilter(event)"
                       title="Показать/скрыть фильтр">
                    <image src='${imagePath}/iconSm_triangleDown.gif' border='0'/>
                    Показать фильтр</a>
        </td></tr>
        </table>
    </c:if>
</div>
