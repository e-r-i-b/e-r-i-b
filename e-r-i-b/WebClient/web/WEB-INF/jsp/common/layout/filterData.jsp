<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 11:55:51
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<tiles:importAttribute name="filter" ignore="true" scope="request"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:if test="${filter  != ''}">
	<!--основная таблица-->
	<div style="position:relative;height:15px;">
	<table cellspacing="0" cellpadding="0" class="MaxSize filter" id="filterFields" style="display:none">
	<tr>
		<td class="fltrBgTopLeftCorner">&nbsp;</td>
		<td class="fltrBgTop">&nbsp;</td>
		<td class="fltrBgTopRightCorner">&nbsp;</td>
	</tr>
	<tr>
		<td class="fltrBgLeftCorner">&nbsp;</td>
		<td class="fltrBg" style="padding-top:4px;">
		<!--внешняя таблица стиля фильтра-->
		<table cellspacing="0" cellpadding="0"class="MaxSize fltrInfArea" onkeypress="onEnterKey(event);">
		<tr>
				<!-- фильтр -->
				<tiles:insert attribute="filter"/>
		</tr>
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
		<td class="fltrBgRightCorner">&nbsp;</td>
    </tr>
	<tr>
		<td class="fltrBgBtmLeftCorner">&nbsp;</td>		
		<td class="fltrBgBtmRightCorner">&nbsp;</td>
	</tr>
	</table>
	<table class="filterLink" style="text-align:left;"><tr><td>
           <a id="showFilterButton" href="" onClick="switchFilter(event)"
                       title="Показать/скрыть фильтр">
	            <image src='${imagePath}/iconSm_triangleDown.gif' border='0'/>
	            Показать фильтр</a>
	</td></tr>
	</table>
	</div>

</c:if>

