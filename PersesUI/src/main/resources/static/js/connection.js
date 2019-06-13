const createConnection = () => {
	const appName = document.getElementById("appName-input").value;
	const pid = document.getElementById("pid-input").value;
	if (!appName && !pid)
		alert("You need to inform at least one");
	const methodInfo = {
		url: '/connect',
		appName: appName,
		pid: pid
	};
	connect(methodInfo);
};

const connect = (methodInfo) => {
	console.log(methodInfo);
	axios.post(methodInfo.url, {
		appName: methodInfo.appName,
		pid: methodInfo.pid
	}, {
		baseURL: 'http://localhost:8777'
	}).then(r => {
		openInjectFailureSection(methodInfo);
	}).catch(e => {
		resultNotOk(e);
	});
};

const closeConnection = () => {
	axios.delete("/connect", {
		baseURL: 'http://localhost:8777'
	}).then(r => {
		closeInjectFailureSection();
	}).catch(e => {
		resultNotOk(e);
	});
};

const checkConnection = () => {
	axios.get("/checkConnection", {
		baseURL: 'http://localhost:8777'
	}).then(r => {
		openInjectFailureSection();
	}).catch(e => {
		resultNotOk(e);
	});
};

const openInjectFailureSection = () => {
	const section = document.getElementById("injectFailureSection");
	section.classList.remove("hidden-lg");
	const conSection = document.getElementById("connectionSection");
	conSection.classList.add("hidden-lg");
	const close = document.getElementById("closeConnectionLi");
	close.classList.remove("hidden-lg");
}

const closeInjectFailureSection = () => {
	const section = document.getElementById("injectFailureSection");
	section.classList.add("hidden-lg");
	const conSection = document.getElementById("connectionSection");
	conSection.classList.remove("hidden-lg");
	const close = document.getElementById("closeConnectionLi");
	close.classList.add("hidden-lg");
}