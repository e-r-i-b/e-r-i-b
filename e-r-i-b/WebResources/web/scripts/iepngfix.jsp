<%@ page contentType="text/x-component;charset=UTF-8" language="java" %>
<public:component>
<script type="text/javascript">
try
{
IEPNGFix.hook = function() {
	if (IEPNGFix.hook.enabled) {
		IEPNGFix.process(element, 0);
	}
};

IEPNGFix.process(element, 1);
};
catch(e) { alert("htc: " + e); }
</script>
</public:component>
