<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%--
��� �������� ������.
- ��� ������� �� ������ ���������� validationFunction (���� �����������)
- ���� ��������� ������ ������� ������������ confirmText (���� ����������)
- ���� ������������ ���������� ���������� ������� �� ������ ������������ ������

	bundle    - bundle � ������� ������ ����� ��� textKey � helpKey
	helpKey   - ���� ��� �������� �������
	textKey   - ���� ��� ��������� �������
	image	  - �������� ����� � ���������, �������� ������ ��������� � ����� �������� ������� ������������
	width	  - ������ ������, ���� �� ������� ������������ �������� ������
--%>                                                        

<c:set var="commandText"><bean:message key="${commandTextKey}" bundle="${bundle}"/></c:set>
<c:set var="commandHelp"><bean:message key="${commandHelpKey}" bundle="${bundle}"/></c:set>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="oldUrl" value="${phiz:getOldUrl()}"/>

<span class="buttDiv ${typeBtn}">
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="btnLeftCorner">&nbsp;</td>
			<td class="btnBase"
				<c:set var="url" value="${phiz:calculateActionURL(pageContext,oldUrl)}"/>
		      onclick="window.location='${url}'"

				align="center" style="white-space:noWrap" valign="middle"
                title="${commandHelp}"
	            <c:if test="${not empty width}">style="width:${width};"</c:if>
            >
				<c:choose>
					<c:when test="${not empty image}">
						<img src="${imagePath}/${image}" alt="${commandHelp}" width="12px" height="12px">&nbsp;
					</c:when>
					<c:otherwise>
						<img src="${globalImagePath}/1x1.gif" alt="${commandHelp}" width="3px" height="12px">&nbsp;
					</c:otherwise>
				</c:choose>
		        <span>${commandText}</span>
			<td class="btnRightCorner">&nbsp;</td>
		</tr>
	</table>
</span>
