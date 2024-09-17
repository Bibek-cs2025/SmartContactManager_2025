
var name="";
var date="";
var otpv="";
var email=""
var maxdate="";



const search=()=>{
	//console.log("searching");
    let query=$("#search-input").val();
    
    if(query!=null)
    {
		let url=`http://localhost:9007/search/${query}`;
		fetch(url).then((responce)=>{
			return responce.json();
		}).then((data)=>{
			if(data!=null){
			console.log(data);
			let text=`<div class='list-group'>`;
			   data.forEach((contact)=>{
				   text+=`<a href='/user/viewcon/${contact.conid}' class='list-group-item list-group-item-action'>
				     ${contact.name}
				   </a>`
				   
			   })
			
			text+=`</div>`;
			$(".search-result").html(text);
			$(".search-result").show();}
		});
		
	}
	else{
		$(".search-result").hide();
			}
}
const next=()=>{
	let query=$("#emd").val();
    if(query!="")
    {
			$(".emailvalid").hide();
	   $(".success").show();	
	   let url=`http://localhost:9007/sendotp/${query}`;	
	   fetch(url).then((responce)=>{
			return responce.json();
		}).then((data)=>{
			if(data.name!=null)
			{
			  name=data.name;
			  date=data.time;
			  otpv=data.otp;
			  email=data.email;
			  maxdate=data.maxtime;
			  console.log(name,date);
			  
			   $(".md").hide();	
			}
			else{
				console.log("find nothing");
				$(".success").hide();
				$(".emailvalid").show();
			}
		})
	}
   
}
const pre=()=>{
	 $(".md").show();
}

const otp=()=>{
	$(".success").hide();
	let value=$("#otpval").val();
	console.log(value);
 if(value!=""){	
    const d = new Date();
    let minutes = d.getMinutes();
    if(minutes<=maxdate)
    {
		if(value==otpv)
		{
			console.log("otp verified")
			$(".otp").hide();
			$(".pgc").show();
		}
		else{
		$(".emailvalid").show();
	}
	}
	else{
		$(".emailvalid").show();
	}
	}
}

const oncheck=()=>{
	let newpas=$("#password").val();
	let conpas=$("#conpas").val();
	if(newpas==conpas)
	{
		$(".passvalid").hide();
		let url=`http://localhost:9007/pas/${newpas}/${email}`;
		fetch(url).then((responce)=>{
			console.log("helo")
			return responce.json();
			
		}).then((data)=>{
			if(data.result == true)
			{
				console.log(data)
				$(".success").show();
				setTimeout(function() {
                refer();
                }, 10000);
				
			}
			else{
				const div = $("#chv");
                div.innerHTML = "";
                div.appendChild(document.createTextNode("Passowrd cant changed"));
				$(".passvalid").show();
			}
		})
	}
	else{
		$(".passvalid").show();
	}
}

function refer(){
	window.location.href = "http://localhost:9007/user_dash";
}