<%--
  Created by IntelliJ IDEA.
  User: Zhuravleva
  Date: 19.01.2009
  Time: 18:41:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<table cellpadding="0" cellspacing="0" width="100%" class="leftMenu" border="0">
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0" width="100%" border="0">
				<tr>
					<td class="lmHdrLftCorner"><img src="${skinUrl}/images/lm_headerLeftCorner.gif" alt="" border="0"></td>
					<td width="100%" class="lmBgHeader"></td>
					<td class="lmHdrRghtCorner"><img src="${skinUrl}/images/lm_headerRightCorner.gif" alt="" border="0"></td>
				</tr>
			</table>
		</td>
	</tr>
<!-- Левое меню -->
    <tr>
        <td width="100%">
            <div class="lmInformationArea" id="lmInformationArea" width="100%">
            <table cellpadding="0" cellspacing="0" border="0" width="100%" class="lmBgRight">
            <tr>
                <td class="subMenu" id="lmArea">
                    <c:if test="${leftMenu != ''}">
                        <%@ include file="leftMenu.jsp"%>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>
                    <table cellpadding="0" cellspacing="0" width="100%" border="0">
                        <tr>
                            <td><img src="${skinUrl}/images/lm_bottomLeftCorner.gif" alt="" border="0"></td>
                            <td width="100%" class="lmBgBottom"></td>
                            <td><img src="${skinUrl}/images/lm_bottomRightCorner.gif" alt="" border="0"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            </table>
            </div>
        </td>
    </tr>
</table>
</div>