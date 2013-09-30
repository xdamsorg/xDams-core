<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
</head>
<body>
	<script type="text/javascript">
		try {
			window.opener.xDamsMiniAlert('documento salvato con successo');
		} catch (e) {
			alert('documento salvato con successo');
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

