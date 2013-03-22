$(function(){
    var success = "Thanks! We will contact you as quickly as possible!";
    var error= "Name , Email , Fax , Message may be empty, please check.";
    
	$("#submit").click(function(){
		$("#msg").text("");
		if ($("input[name='message.visitor.name']").val() == "Name:" 
		 || $("input[name='message.visitor.mail']").val() == "Email:"
	     || $("input[name='message.visitor.fax']").val() == "Fax:"
	     || $("textarea[name='message.content']").text() == ""){
			$("#msg").show(function(){	
				$("#msg").text(error);
				$("#msg").fadeOut(4000);				
			});
			resetform();			
			return false;		
		}
		
		$.ajax({ 
			type: "post",
			data: $("#messageform").formSerialize(),
			url: "/messagemanager/addMessage.action",
			success:function(t){
				$("#msg").show(function(){					
					$("#msg").text(success);
					$("#msg").fadeOut(2000);					
				});
				$("#messageform").clearForm();
				resetform();
			}
		}); 				   
	});
	  
	$("input[name*='message.']").focus(function(){				   
	   $(this).clearFields();	   
	}); 
	
	function resetform(){
		$("input[name='message.visitor.name']").val("Name:");
		$("input[name='message.visitor.mail']").val("Email:");
		$("input[name='message.visitor.fax']").val("Fax:");
	}
})