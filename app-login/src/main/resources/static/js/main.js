(function () {  
    if (!axios) {  
        return;  
    }  
    //请求拦截器
    axios.interceptors.request.use(function (config){  
        if (!config.headers['X-Requested-With']) {  
            config.headers['X-Requested-With'] = 'XMLHttpRequest';  
        }  
        return config;  
    });  
  
    var oldRequest = axios.Axios.prototype.request;  
  
    axios.Axios.prototype.request = function request(config) {  
        var self = this;  
        return new Promise(function (resolve, reject) {  
            oldRequest.call(self, config).then(  
                function (response) {  
                    if (response.data && response.data.casError == '403' && response.data.redirect) {  
                        console.log('原始请求- 发现 CAS 客户端未登录');  
                        var url = response.data.redirect + "&method=POST";  
                        console.log(url);
                        axios.get(url, {  
                            withCredentials: true,  
                            responseType: 'document',  
                        }).then(function (ssoResponse) {  
                            console.log(ssoResponse);
                            var form = ssoResponse.data.getElementsByTagName('form');  
                            if (form) {  
                                form = form[0];  
                                if (form.getAttribute('id') === 'fm1') {  
                                    console.log('获取ticket 失败 ，CAS 服务端未登录');  
                                    ssoResponse.message = '获取ticket 失败 ，CAS 服务端未登录';  
                                    reject(ssoResponse);  
                                    alert('请在弹出窗口完成登录后，再进行操作');  
                                    window.open(response.data.redirect.substring(0, response.data.redirect.indexOf('?')));  
                                }  
                                else if (form.getAttribute('name') === 'acsForm' && form.getElementsByTagName('input')) {  
                                    var join = config.url.indexOf('?') > 0 ? '&' : '?';  
                                    config.url = config.url + join + 'ticket=' + form.getElementsByTagName('input')[0].value;  
                                    axios.request(config).then(function (withLoginResponse) {  
                                        console.log('获取ticket 成功，再次请求数据');  
                                        resolve(withLoginResponse);  
                                    }).catch(function (err) {  
                                        reject(err);  
                                    });  
                                }  
                            }  
                        })  
                    } else {  
                        resolve(response);  
                    }  
                },  
                function (error) {  
                    reject(error);  
                }  
            );  
        });  
    };  
})();  