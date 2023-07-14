<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<script type="text/javascript">
    emailLength = '<bean:message key="com.rssl.common.forms.validators.EmailFieldValidator.length" arg0="%0"/>';
    emailSpaces = '<bean:message key="com.rssl.common.forms.validators.EmailFieldValidator.spaces" arg0="%0"/>';
    emailAtHyphen = '<bean:message key="com.rssl.common.forms.validators.EmailFieldValidator.atHyphen" arg0="%0"/>';
    emailAtPoint = '<bean:message key="com.rssl.common.forms.validators.EmailFieldValidator.atPoint" arg0="%0"/>';
    emailPointHyphen = '<bean:message key="com.rssl.common.forms.validators.EmailFieldValidator.pointHyphen" arg0="%0"/>';
    emailDoublePoint = '<bean:message key="com.rssl.common.forms.validators.EmailFieldValidator.doublePoint" arg0="%0"/>';
    emailStartEndHyphen = '<bean:message key="com.rssl.common.forms.validators.EmailFieldValidator.startEndHyphen" arg0="%0"/>';
    emailStartEndPoint = '<bean:message key="com.rssl.common.forms.validators.EmailFieldValidator.startEndPoint" arg0="%0"/>';
    emailFormat = '<bean:message key="com.rssl.common.forms.validators.EmailFieldValidator.format" arg0="%0"/>';

    function validateEmail(field, description)
    {
        var address = field.val();
        var regexp = /^(([A-Za-z0-9])+(-|\.|_)*)*([A-Za-z0-9])+(_)*@(([A-Za-z0-9])+(-|\.))*([A-Za-z0-9])+$/;
        var regexp1 = /^.{0,35}$/;
        var regexp2 = /^\S*$/;
        var regexp3_1 = /(.*@-.*)|(.*-@.*)/;
        var regexp3_2 = /(.*@\..*)|(.*\.@.*)/;
        var regexp3_3 = /(.*-\..*)|(.*\.-.*)/;
        var regexp3_5 = /(^-.*)|(.*-@.*)/;
        var regexp3_6 = /(^\..*)|(.*\.@.*)/;
        if (!regexp1.test(address))
        {
            return emailLength.replace('%0', description);
        }
        if (!regexp2.test(address))
        {
            return emailSpaces.replace('%0', description);
        }
        if (regexp3_1.test(address))
        {
            return emailAtHyphen.replace('%0', description);
        }
        if (regexp3_2.test(address))
        {
            return emailAtPoint.replace('%0', description);
        }
        if (regexp3_3.test(address))
        {
            return emailPointHyphen.replace('%0', description);
        }
        if (regexp3_5.test(address))
        {
            return emailStartEndHyphen.replace('%0', description);
        }
        if (regexp3_6.test(address))
        {
            return emailStartEndPoint.replace('%0', description);
        }
        if (!regexp.test(address))
        {
            return emailFormat.replace('%0', description);
        }
        return null;
    }
</script>