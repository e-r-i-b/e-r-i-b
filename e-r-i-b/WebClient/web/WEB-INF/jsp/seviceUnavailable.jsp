<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:insert definition="blank">
	<tiles:put name="data" type="string">
		<table width="100%" cellspacing="0" cellpadding="0" class="MaxSize">
			<tr>
				<td valign="middle" align="center">
					<font size="4" color="red">
						<bean:message key="error.seviceUnavailable" bundle="commonBundle"/>
					</font>
				</td>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>
