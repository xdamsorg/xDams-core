this.tooltip = function(){	
	xOffset = 10;
	yOffset = 20;		
 	jQuery("a.tp").hover(function(e){											  
		this.t = this.title;
		this.title = "";									  
	 	theTxt = this.t;
 		jQuery("body").append("<div id='tp'>"+ theTxt +"</div>");
		jQuery("#tp").css("top",(e.pageY - xOffset) + "px").css("left",(e.pageX + yOffset) + "px").fadeIn("fast");		
    },
	function(){
		this.title = this.t;		
		jQuery("#tp").remove();
    });	
	jQuery("a.tp").mousemove(function(e){
		jQuery("#tp").css("top",(e.pageY - xOffset) + "px").css("left",(e.pageX + yOffset) + "px");
	});	
	 	
 	jQuery("a.tps").hover(function(e){											  
		this.t = jQuery(this).prev('span').html();
		 								  
	 	theTxt = this.t;
 		jQuery("body").append("<div id='tp'></div>");
 		jQuery("#tp").append((theTxt)); 
		jQuery("#tp").css("top",(e.pageY - xOffset) + "px").css("left",(e.pageX + yOffset) + "px").fadeIn("fast");		
    },
	function(){
		 	
		jQuery("#tp").remove();
    });	
	jQuery("a.tps").mousemove(function(e){
		jQuery("#tp").css("top",(e.pageY - xOffset) + "px").css("left",(e.pageX + yOffset) + "px");
	});			
};