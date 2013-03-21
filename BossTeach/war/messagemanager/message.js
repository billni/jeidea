$(function(){
    var success = "Thanks! We will contact you as quickly as possible!";
    var error= "Name , Email , Fax , Message may be empty, please check.";
    
	$("#submit").click(function(){
		
		if ($("input[name='message.visitor.name']").val() == "Name:" 
		 || $("input[name='message.visitor.mail']").val() == "Email:"
	     || $("input[name='message.visitor.fax']").val() == "Fax:"
	     || $("textarea[name='message.content']").text() == ""){
			$("#thanks").show(function(){	
				$("#thanks").text(error);
				$("#thanks").fadeOut(4000);
			});
			resetform();			
			return false;		
		}
		
		$.ajax({ 
			type: "post",
			data: $("#messageform").formSerialize(),
			url: "/messagemanager/addMessage.action",
			success:function(t){
				$("#thanks").show(function(){	
					$("#thanks").text(success);
					$("#thanks").fadeOut(2000);
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