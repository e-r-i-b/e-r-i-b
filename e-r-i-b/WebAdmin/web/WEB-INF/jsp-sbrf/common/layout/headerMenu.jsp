<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:if test="${headerGroup == 'true'}">
<div style="z-index:0;padding-top:55px;width:100%;">
	<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<!-- ¬кладки -->
		<td align="center">
	    	<table cellpadding="0" cellspacing="0">
		    <tr><td>
				<div id="mmInsBSep1"></div>
                <ul id="menu">
                    <tiles:insert definition="mainMenu">
                        <tiles:importAttribute name="mainmenu" ignore="true"/>
                        <tiles:put name="mainmenu" value="${mainmenu}"/>
                    </tiles:insert>
                </ul>
			</td></tr>
			</table>
		</td>
	</tr>
	</table>
</div>
</c:if>

