<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insert definition="main">
	<tiles:put name="pageTitle" type="string">Ошибка</tiles:put>
	<tiles:put name="data" type="string">
		<table width="100%" cellspacing="0" cellpadding="0" class="MaxSize" border="0">
			<tr>
				<td valign="middle" align="center">
					<font size="4" color="red">
                        <bean:message key="error.errorHeader" bundle="commonBundle"/>
					</font>
				</td>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>
