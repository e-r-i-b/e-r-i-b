<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="frm" %>
<%@ taglib  uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>

<html:form action="/private/finances/show">
    <tiles:insert definition="financesStructure" flush="false">
        <tiles:put name="data">            
        </tiles:put>
    </tiles:insert>
</html:form>