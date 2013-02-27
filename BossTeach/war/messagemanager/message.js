$(function(){
	$("#submit").click(function(){				      
		$.ajax({ 
			type: "post",
			data: $("form").formSerialize(),
			url: "/messagemanager/addMessage.action",
			success:function(t){
				alert("Your message sent successfully!");
				$("form").clearForm();
			}
		}); 				   
	});
	  
	$("input[name*='message.']").focus(function(){				   
	   $(this).clearFields();
	}); 
})