function myAjax(url, data, successFn) {
    reqwest({
        url: url
        , type: 'json'
        //              ,contentType: 'application/json'
        , method: 'post'
        , data: data
        //              ,form : data
        , error: function (err) {
        }
        , success: successFn
    })
}
