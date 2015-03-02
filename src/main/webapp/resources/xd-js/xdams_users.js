jQuery(document).ready(function(){
	var userFieldVerify = $("#userFieldVerify");
	var passwordField = $("#passwordField");
	//var idPwd = $(passwordField).attr("id");
	 
	//$(passwordField).append("<input type=\"password\" value=\"\"/>")
	//passwordField.clone().attr('id', idPwd ).insertAfter( passwordField );
	//$(passwordField).attr('type','password');
	var accountField = $("#accountField");
	
	$(passwordField).focusout(function() {
		if($(passwordField).val()!="" && $(passwordField).attr("data-password")!='crypted'){
			var dataField = "passwordField="+$(passwordField).val();
			var urlHost = globalOption.contextPath + '/generatePassword.html';
				if(confirm(getLocalizedString('Vuoi_generare_la_password','Vuoi generare la password')+"?")){
					$.ajax({
						  url: urlHost,
						  type: "GET",
					  data: dataField,
					  success: function(data) {
						  $(passwordField).val(data);
						  $(passwordField).attr("data-password",'crypted');
						  if( $(userFieldVerify).attr('data-verify')=='verified'){
							  $("a[onclick*=submitForm]").show();
						  }
					  }
					});			
				}
		}
	});	
	
	$(passwordField).bind('keypress keyup keydown',function(e) {
		if($(this).attr("data-password")=='crypted'){
			$('#passwordField').val("");
			$("a[onclick*=submitForm]").hide();
		}
		$('#passwordField').removeAttr("data-password");
	});
 
	
	if(userFieldVerify.length>0){
		$("a[onclick*=submitForm]").hide();
	}	 
	$(userFieldVerify).bind('keypress keyup keydown focus blur',function(e) {
		verifyUserName($(this));
	});
	$(userFieldVerify).mask("AAAAAAAAAAAAAAAAAAAA", {});	
});		

function verifyUserName(myOgj){
	var dataField = "actionFlag=verifyUser&username="+$(myOgj).val()+"&accountVal="+$('input:[name=".user.@account"]').val();
	var urlHost = globalOption.contextPath + "/"+globalOption.theArch+"/"+'/ajax.html';
	 //if($(myOgj).val().length>=2 && $('input:[name=".user.@account"]').val()!=""){
	if(true){
		 $.ajax({
			  url: urlHost,
			  type: "GET",
		  data: dataField,
		  success: function(data) {
			  console.debug(data.result);
			  if(data.result=='notExist'){
				  if($('#passwordField').attr("data-password")=='crypted'){
					  $("a[onclick*=submitForm]").show();
				  }
				  $(myOgj).attr('data-verify','verified');
			  }else{
				  $("a[onclick*=submitForm]").hide();
				  $(myOgj).removeAttr('data-verify');
			  }
	 	  }
		});		 
	 }else{
		 $("a[onclick*=submitForm]").hide();
	 }
}