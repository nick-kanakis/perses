
const searchMethod = () => {
    const classpath = document.getElementById("classPath-input").value;
    const methodName = document.getElementById("methodName-input").value;
    const signature = document.getElementById("signature-input").value;
    if(!classpath)
        alert("Classpath  must not be empty");
    if(!methodName){
        const methodInfo = {
            url: '/getMethodOfClass',
            classPath:  classpath
        };
        getFromPersesAllMethods(methodInfo).then(response => addChildrenToNode(null, response.data)).catch(e => resultNotOk(e));
    } else {
        const methodInfo = {
            url: '/getInvoked',
            classPath:  classpath,
            methodName:  methodName,
            signature: signature
        };
        getFromPerses(methodInfo).then(() => addChildrenToRoot(methodInfo)).catch(e => resultNotOk(e));
    }
};

const getFromPerses = (target) => {
    return axios.get(target.url, {
        params: {
            classPath: target.classPath,
            methodName: target.methodName,
            signature: target.signature
        },
        baseURL: 'http://localhost:8777'
    });
};

const getFromPersesAllMethods = (target) => {
    return axios.get(target.url, {
        params: {
            classPath: target.classPath
        },
        baseURL: 'http://localhost:8777'
    });
};