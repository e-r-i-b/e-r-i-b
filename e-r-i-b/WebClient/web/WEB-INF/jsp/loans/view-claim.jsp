<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loan/claim/view" onsubmit="return setEmptyAction(event)">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="listFormName" value="${form.metadata.listFormName}"/>

	<tiles:insert definition="loanAnonymousMain">
		<!-- заголовок -->
		<tiles:put name="pageTitle" value="${form.title}"/>

		<!-- данные -->
		<tiles:put name="data" type="string">
            <tiles:importAttribute/>
            <c:set var="globalImagePath" value="${globalUrl}/images"/>
            <c:set var="imagePath" value="${skinUrl}/images"/>
			${form.html}

			<!-- TODO STATUS -->
			<c:if test="${form.document.state.code == 'DISPATCHED' || form.document.state.code=='STATEMENT_READY'}">
			<img src="${imagePath}/stamp.gif" border="0"
			     style="position:relative;top:-63px;left:295px;">
			<script type="text/javascript">
				try
				{
					if (document.getElementById("titleHelp"))
						getElement("titleHelp").innerHTML = "Ваша заявка принята к исполнению банком."
				}catch(e){}
			</script>
			</c:if>
		</tiles:put>

	</tiles:insert>

</html:form>

