<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
</head>
<body>
	<script type="text/javascript">
		try {
			window.opener.xDamsMiniAlert('<spring:message code="documento_salvato_con_successo" text="documento salvato con successo"/>');
		} catch (e) {
			alert('<spring:message code="documento_salvato_con_successo" text="documento salvato con successo"/>');
		}; 
		try {
		  	window.opener.reloadLocation();
		} catch (e) {
		}
		try {
		    window.opener.afterSave();
		} catch (e) {
		}
		self.close();
	</script>
</body>
</html>

