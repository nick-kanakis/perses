
const searchMethod = () => {
    const classpath = document.getElementById("classPath-input").value;
    const methodName = document.getElementById("methodName-input").value;
    const signature = document.getElementById("signature-input").value;
    const methodInfo = {
        url: '/getInvoked',
        classPath:  classpath,
        methodName: methodName,
        signature: signature
    };
    getFromPerses(methodInfo).then(() => addChildrenToRoot(methodInfo)).
    catch(error => alert("Could not find method "+ error));
};

const getFromPerses = (target) => {
    return axios.get(target.url, {
        params: {
            classPath: target.classPath,
            methodName: target.methodName,
            signature: target.signature
        },
        baseURL: 'http://localhost:8080'
    });
};