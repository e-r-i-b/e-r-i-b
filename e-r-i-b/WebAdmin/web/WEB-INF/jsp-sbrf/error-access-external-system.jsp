<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:insert definition="main">
    <tiles:put name="pageTitle" type="string">Ошибка</tiles:put>
    <tiles:put name="needShowMessages" value="false"/>
    <tiles:put name="data" type="string">
        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>

        <jsp:useBean id="inactiveExternalSystemMessages" scope="request" class="java.util.ArrayList" />
        <phiz:messages id="inactiveES" bundle="${bundle}" field="field" message="inactiveExternalSystem">
            <c:set var="prepareMessage" value="${phiz:processBBCode(inactiveES)}" scope="request"/>
            <c:if test="${!phiz:contains(inactiveExternalSystemMessages, prepareMessage)}">
                <% inactiveExternalSystemMessages.add(request.getAttribute("prepareMessage")); %>
                <c:set var="inactiveESMessages">${inactiveESMessages}<div class = "itemDiv">${phiz:processBBCode(inactiveES)} </div></c:set>
            </c:if>
        </phiz:messages>
        <table  width="100%">
            <tr>
				<td align="center" class="messageTab">
                    <bean:write name="inactiveESMessages" filter="false"/>
				</td>
			</tr>
		</table>
    </tiles:put>
</tiles:insert>
