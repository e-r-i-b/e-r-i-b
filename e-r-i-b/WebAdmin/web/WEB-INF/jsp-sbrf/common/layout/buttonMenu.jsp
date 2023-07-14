<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td class="btnMLeftCorner">&nbsp;</td>
		<td class="btnMBg context"><nobr><tiles:getAsString name="pageTitle"/>&nbsp;</nobr></td>
		<td class="btnMBg">
			<div style="float:right;white-space:nowrap;">
				<tiles:insert attribute="menu"/>
			</div>
		</td>
		<td class="btnMRightCorner">&nbsp;</td>
	</tr>
</table>