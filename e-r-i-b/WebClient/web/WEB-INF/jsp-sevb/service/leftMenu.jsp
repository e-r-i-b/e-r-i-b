<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<script type="text/javascript">
   document.imgPath="${imagePath}/";
</script>
<br/>
<span class="infoTitle backTransparent">Сервис</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
    <td>
        <phiz:menuLink action="private/service" serviceId="Service" id="f1">
         Смена пароля<br>
        </phiz:menuLink>
    </td>
</tr>
</table>
