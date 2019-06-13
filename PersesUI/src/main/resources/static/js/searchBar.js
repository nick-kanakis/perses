
const searchMethod = () => {
    const classpath = document.getElementById("classPath-input").value;
    const methodName = document.getElementById("methodName-input").value;
    const signature = document.getElementById("signature-input").value;
    if(!classpath || !methodName)
        alert("Classpath & methodName must not be empty");
    const methodInfo = {
        url: '/getInvoked',
        classPath:  classpath,
        methodName:  methodName,
        signature: signature
    };
    getFromPerses(methodInfo).then(() => addChildrenToRoot(methodInfo)).
    catch(e => resultNotOk(e));
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