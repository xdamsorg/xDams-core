<%@page import="java.util.Map.Entry"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.xdams.user.bean.Archive"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>xDams - creazione archivi</title>
<script src="${frontUrl}/bootstrap-3/assets/jquery.min.js"></script>
<link href="${frontUrl}/bootstrap-3/css/bootstrap.min.css" rel="stylesheet" />
<link href="${frontUrl}/css/signin.css" rel="stylesheet">
<script src="${frontUrl}/bootstrap-3/js/bootstrap.min.js"></script>
<script src="${frontUrl}/bootstrap-3/assets/form-validator/jquery.form-validator.min.js"></script>
<script src="${frontUrl}/bootstrap-3/assets/jquery-ui.js"></script>
<link rel="stylesheet" href="${frontUrl}/bootstrap-3/assets/jquery-ui.css">
<script src="${frontUrl}/bootstrap-3/assets/bootbox.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="page-header">
			<h1 class="text-center">
				<img src="${frontUrl}/img/logo.gif" />
			</h1>
			<h3>Creazione nuovo archivio</h3>
		</div>
		<form id="signupform" action="${contextPath}/admin/${account}/executeCreateArchive" method="POST">
			<!--<div class="form-group">
				<label for="exampleInputEmail1">nome gruppo</label> <select class="form-control" name="groupName" required>
					<c:forEach items="${archiveByGroup}" var="archiveA">
						<option>${archiveA.key}</option>
					</c:forEach>
				</select>
			</div>-->
			<div class="form-group">
				<label for="tags">nome gruppo</label> <input type="text" id="nomeGruppo" name="groupName" class="form-control" required>
			</div>
			<div class="form-group">
				<label>nome archivio</label> <input type="text" class="form-control" name="archiveDescr" placeholder="nome archivio" required>
			</div>
			<div class="form-group">
				<label>tipologia archivio</label> <select id="typeArchive" class="form-control" name="type" required>
					<option value="xDamsHist"> Archivio Storico</option>
					<option value="xDamsPhoto"> Archivio Fotografico</option>
					<option value="xDamsAV">Archivio AudioVideo</option>
					<option value="xDamsOA">Iconografia</option>
					<option value="xDamsBiblio">Biblioteca</option>
					<option value="xDamsLido">Archivio Oggetti Museali</option>
				</select>
			</div>
			<div class="form-group">
				<label>host</label> <input type="text" class="form-control" name="host" placeholder="hostname: localhost" value="localhost" required>
			</div>
			<div class="form-group">
				<label>pne</label> <input type="text" id="pneSel" class="form-control" name="pne" placeholder="pne: c/eac-cpf/mods/lido" value="c" required>
			</div>
			<div class="form-group">
				<label>port</label> <input type="text" class="form-control" name="port" placeholder="port: -1" value="-1" required>
			</div>
			<div class="form-group">
				<label>directory archivi extraway</label> <input type="text" class="form-control" name="dbDir" placeholder="directory archivi extraway" value="${dbDir}" required>
			</div>			
			<div class="form-group">
				<label>ico</label> <select class="form-control" name="ico" id="icoSel" required>
					<option value="storico.gif">Archivio Storico</option>
					<option value="fotografico.gif">Archivio Fotografico</option>
					<option value="video.gif">Archivio AudioVideo</option>
					<option value="disegni.gif">Iconografia / Archivio Oggetti Museali</option>
					<option value="biblioteca.gif">Biblioteca</option>
				</select>
			</div>
			<div class="form-group text-center">
				<button type="submit" class="btn btn-warning text-uppercase" id="subMite">crea nuovo archivio</button>
			</div>
			<div class="page-header">
				<a class="btn btn-default text-uppercase text-center" onclick="window.close()">CLOSE</a>
			</div>			
		</form>
		<script>
		pneSel
		$( "#typeArchive" ).change(function () {
		    var str = "";
		    $(this).each(function() {
		    	//console.debug("dddd "+$( this ).val());
		      if($( this ).val()=="xDamsBiblio"){
		    	  $( "#pneSel" ).val("mods");
		    	  $( "#icoSel" ).val("biblioteca.gif"); 
		      }else if($( this ).val()=="xDamsLido"){
		    	  $( "#pneSel" ).val("lido");
		    	  $( "#icoSel" ).val("disegni.gif"); 
		      }else if($( this ).val()=="xDamsHist"){
		    	  $( "#pneSel" ).val("c");
		    	  $( "#icoSel" ).val("storico.gif"); 
		      }else if($( this ).val()=="xDamsPhoto"){
		    	  $( "#pneSel" ).val("c");
		    	  $( "#icoSel" ).val("fotografico.gif"); 
		      }else if($( this ).val()=="xDamsAV"){
		    	  $( "#pneSel" ).val("c");
		    	  $( "#icoSel" ).val("video.gif"); 
		      }else if($( this ).val()=="xDamsOA"){
		    	  $( "#pneSel" ).val("c");
		    	  $( "#icoSel" ).val("disegni.gif"); 
		      } else{
		    	  $( "#pneSel" ).val("c");
		      }
		    });
		    
		  }).change();
		
		
		
			var myList = [<c:forEach items="${archiveByGroup}" var="archiveA">"${archiveA.key}",</c:forEach>];			
			$('#nomeGruppo').autocomplete({
			    minLength: 0,
			    source: function(request, response) {            
			        var data = $.grep(myList, function(value) {
			            return value.substring(0, request.term.length).toLowerCase() == request.term.toLowerCase();
			        });            
			        response(data);
			    }
			}).focus(function () {
			    $(this).autocomplete("search", "");
			});	
			$("#nomeGruppo").attr("placeholder", "nome gruppo");			

			var myLanguage = {
				errorTitle : 'Form submission failed!',
				requiredFields : 'campo obbligatorio',
				badTime : 'You have not given a correct time',
				badEmail : 'Indirizzo email non valido',
				badTelephone : 'You have not given a correct phone number',
				badSecurityAnswer : 'You have not given a correct answer to the security question',
				badDate : 'You have not given a correct date',
				lengthBadStart : 'You must give an answer between ',
				lengthBadEnd : ' caratteri',
				lengthTooLongStart : 'You have given an answer longer than ',
				lengthTooShortStart : 'il valore deve essere minimo di ',
				notConfirmed : 'attenzione il valore non coincide',
				badDomain : 'Incorrect domain value',
				badUrl : 'The answer you gave was not a correct URL',
				badCustomVal : 'You gave an incorrect answer',
				badInt : 'The answer you gave was not a correct number',
				badSecurityNumber : 'Your social security number was incorrect',
				badUKVatAnswer : 'Incorrect UK VAT Number',
				badStrength : 'la password non è valida',
				badNumberOfSelectedOptionsStart : 'You have to choose at least ',
				badNumberOfSelectedOptionsEnd : ' answers',
				badAlphaNumeric : 'The answer you gave must contain only alphanumeric characters ',
				badAlphaNumericExtra : ' and ',
				wrongFileSize : 'The file you are trying to upload is too large',
				wrongFileType : 'The file you are trying to upload is of wrong type',
				groupCheckedRangeStart : 'Please choose between ',
				groupCheckedTooFewStart : 'Please choose at least ',
				groupCheckedTooManyStart : 'Please choose a maximum of ',
				groupCheckedEnd : ' item(s)'
			};			

			var ready = false;
			$.validate({
				form : '#signupform',
				language : myLanguage,
				modules : 'security',
				onValidate: function($form) {
					if(!ready){
						if(!groupExists()){
							bootbox.confirm("Stai per creare un archivio e il gruppo "+ $("#nomeGruppo").val()  +", procedere?", function(result) {
								if(result){
									ready = true;
									$form.submit();
								}
							});	
						}
						else{
							bootbox.confirm("Stai per creare un nuovo archivio, procedere?", function(result) {
								if(result){
									ready = true;
									$form.submit();
								}
							});	
						}						
					}
				},
				onSuccess : function($form) {
					if(!ready){
						return false;	
					}
					//else{
						//$('.bs-example-modal-lg').modal();
					//}
					//$('.bs-example-modal-lg').modal();
	
					//$('.bs-example-modal-lg').show();
					//
					//$('#signupform').get(0).submit();
				},
				onError : function($form) {					
					//$('.bs-example-modal-lg').modal();
				},
				validation: function($form) {
					//$('.bs-example-modal-lg').modal();
				}
			});		
			
			function groupExists(){
				var nomeGruppo = $("#nomeGruppo").val();
				for (var i = 0; i < myList.length; i++) {
				    if(myList[i] == nomeGruppo){
				    	return true;
				    }
				}
				return false;
			}
			
		</script>
	</div>
	<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg">
			<div class="alert alert-success">
				<strong>Creazione Archivio in corso!</strong>
			</div>
		</div>
	</div>
</body>
</html>