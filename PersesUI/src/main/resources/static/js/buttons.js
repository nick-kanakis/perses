const injectFailure = btn => {
    console.log(btn);
	const rate = document.getElementById("rate-input").value;
	const exception = document.getElementById("selectException").value;
	console.log(exception);
    const classPath = btn.getAttribute("classpath");
	const methodName = btn.getAttribute("methodName");
    const methodInfo = {
        url: '/failure',
        classPath:  classPath,
        methodName: methodName,
		rate: rate,
		exception: exception,
        signature: btn.getAttribute("signature")
    };
    postToPerses(methodInfo, "injectFailure");
};

const injectLatency = btn => {
    const methodInfo = {
        url: '/latency',
        classPath:  btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature"),
        latency: document.getElementById("latencyInput").value,
		rate: document.getElementById("rate-input-latency").value,
    };
    postToPerses(methodInfo, "injectLatency")
};

const restoreMethod = btn => {
    const methodInfo = {
        url: '/restore',
        classPath:  btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature")
    };
    postToPerses(methodInfo, "restoreMethod")
};

const updateInjectFailureBtn = data => {
    const failureBtn = document.getElementById("failureBtn");
    failureBtn.setAttribute("classPath", data.classPath);
    failureBtn.setAttribute("methodName", data.methodName);
    failureBtn.setAttribute("signature", data.signature);
};
const updateInjectLatencyBtn = data => {
    const latencyBtn = document.getElementById("latencyBtn");
    latencyBtn.setAttribute("classPath", data.classPath);
    latencyBtn.setAttribute("methodName", data.methodName);
    latencyBtn.setAttribute("signature", data.signature);
};
const updateInjectRestoreBtn = data => {
    const restoreBtn = document.getElementById("restoreBtn");
    restoreBtn.setAttribute("classPath", data.classPath);
    restoreBtn.setAttribute("methodName", data.methodName);
    restoreBtn.setAttribute("signature", data.signature);
};

function postToPerses(target, action) {
    axios.post(target.url, {
        classPath: target.classPath,
        methodName: target.methodName,
        signature: target.signature,
        latency: target.latency,
		rate: target.rate,
		exception: target.exception
    }, {
        baseURL: 'http://localhost:8777'
    }).then(r => {
        resultOk(target, action);
    }).catch(e =>{
        resultNotOk(e, "You need to choose one method!");
    });
}

function resultOk(target, action) {
	let messageText = document.createTextNode('Failure injected in the method ' + target.classPath + '.' + target.methodName);
	console.log(action);
	if(action === "injectLatency"){
		messageText = document.createTextNode('Latency injected in the method ' + target.classPath + '.' + target.methodName);
	} else if(action === "restoreMethod"){
		messageText = document.createTextNode('The default behavior of the methods was restored');
	}

	const titleParagraph = $('#titleParagraph');
	titleParagraph.html("<strong>Done!</strong>");
	titleParagraph.removeClass("text-danger");

	const messageParagraph = $('#messageParagraph');
	messageParagraph.html(messageText);
	messageParagraph.removeClass("text-danger");

	$('#messageModal').modal('show');

	setTimeout(() => {
		$('#messageModal').modal('hide');
    }, 3000)
}

function resultNotOk(error, message) {
	console.log(error);
	const titleParagraph = $('#titleParagraph');
	titleParagraph.html("<strong>Ops!</strong>");
	titleParagraph.addClass("text-danger");

	const messageParagraph = $('#messageParagraph');
	messageParagraph.html(message);
	messageParagraph.addClass("text-danger");

	$('#messageModal').modal('show');

	setTimeout(() => {
		$('#messageModal').modal('hide');
	}, 3000)
}

function removeChildNodes(node) {
    while (node.firstChild) {
        node.removeChild(node.firstChild);
    }
}