<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="iFrameWidget" flush="false">
    <tiles:put name="digitClassname" value="widget.GenericWidget"/>
    <tiles:put name="cssClassname" value="facebook-widget"/>
    <tiles:put name="cssClassFrame" value="FacebookFrame"/>
    <tiles:put name="editable" value="false"/>
    <tiles:put name="url" value="//www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fbankdruzey&amp;width=700&amp;height=260&amp;colorscheme=light&amp;show_faces=true&amp;border_color&amp;stream=false&amp;header=false"/>
</tiles:insert>
