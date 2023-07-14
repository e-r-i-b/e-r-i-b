<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<tiles:insert definition="erkcMain">
    <tiles:put type="string" name="leftMenu" value=""/>
    <tiles:put type="string" name="additionalInfoBlock" value=""/>
	<tiles:put name="data" type="string">
        <table width="100%" cellspacing="0" cellpadding="0" class="MaxSize">
            <tr>
                <td valign="middle" align="center">
                    <span class="erkcError">
                        <c:out value="${exceptionMessage}"/>
                    </span>
                </td>
            </tr>
        </table>
	</tiles:put>
</tiles:insert>
