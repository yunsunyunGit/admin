function myAjax(url,data,successFn){
	reqwest({
			    url: url
			  , type: 'json'
			  , method: 'post'
			  ,data:data
			  , error: function (err) { }
			  , success:successFn
			})
}