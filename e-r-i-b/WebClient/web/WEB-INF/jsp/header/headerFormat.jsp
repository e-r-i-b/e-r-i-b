<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/private/async/header/format">
    <div data-dojo-type="widget.WebPageFormat" direction="left">
    </div>

    <div data-dojo-type="widget.WebPageFormat" direction="right">
    </div>

    <div class="editSkins">
        Здесь Вы можете изменить положение Личного меню на странице, перетащив его в рабочей области. Для сохранения изменений нажмите на кнопку «Сохранить»
    </div>

</html:form>