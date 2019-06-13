const addChildrenToRoot = (methodInfo) => {
	const section = document.getElementById("calledMethodsSection");
	section.classList.remove("hidden-lg");

    const rootLu = document.getElementById("root-list");
    console.log(rootLu);

    const rootLi = document.createElement("li");
    rootLi.setAttribute("class", "list-group-item");
    const methodNameSpan = document.createElement("a");
    methodNameSpan.setAttribute("href", "#a")
    methodNameSpan.setAttribute("class", "methodName");
    const methodNamTextNode = document.createTextNode(methodInfo.methodName + '('+ methodInfo.classPath + ')');
    methodNameSpan.appendChild(methodNamTextNode);

    rootLi.appendChild(methodNameSpan);
    rootLi.setAttribute("title", methodInfo.signature);
    rootLi.setAttribute('isExpanded', 'false');

    rootLu.removeChild(rootLu.firstChild);
    rootLu.appendChild(rootLi);

    rootLi.addEventListener("click", e => {
        updateActiveNode(rootLi);
        updateInjectFailureBtn(methodInfo);
        updateInjectLatencyBtn(methodInfo);
        updateInjectRestoreBtn(methodInfo);
        e.stopPropagation();

        getFromPerses({
            url: '/getInvoked',
            classPath: methodInfo.classPath,
            methodName: methodInfo.methodName,
            signature: methodInfo.signature
        }).then(res => addChildrenToNode(rootLi, res.data))
            .catch(error => alert("Could not find method " + error));
    });
};

const addChildrenToNode = (parentLi, data) => {
    if (parentLi.getAttribute('isExpanded')) {
        const isExpanded = parentLi.getAttribute('isExpanded');
        if (isExpanded === 'true') {
            parentLi.setAttribute('isExpanded', 'false');
            const children = parentLi.children;
            for (let i = 0; i < children.length; i++) {
                const child = children[i];
                if (child.tagName === 'UL') {
                    parentLi.removeChild(child)
                }
            }
            return;
        } else {
            parentLi.setAttribute('isExpanded', 'true');
        }
    }
    const nestedUl = document.createElement("ul");
    parentLi.appendChild(nestedUl);

    data.forEach(methodInfo => {
        console.log(methodInfo);
        const nestedLi = document.createElement("li");
        nestedLi.setAttribute("class", "list-group-item");
        nestedLi.setAttribute("style", "border: 0");

        nestedUl.appendChild(nestedLi);
        const methodNameSpan = document.createElement("a");
        methodNameSpan.setAttribute("href", "#a")
        methodNameSpan.setAttribute("class", "methodName");
        const methodNamTextNode = document.createTextNode(methodInfo.methodName + '('+ methodInfo.classPath + ')');
        methodNameSpan.appendChild(methodNamTextNode);

        nestedLi.appendChild(methodNameSpan);
        nestedLi.setAttribute('isExpanded', 'false');
        nestedLi.setAttribute('title', methodInfo.signature);
        nestedLi.addEventListener("click", e => {
            updateActiveNode(nestedLi);
            updateInjectFailureBtn(methodInfo);
            updateInjectLatencyBtn(methodInfo);
            updateInjectRestoreBtn(methodInfo);
            e.stopPropagation();
            getFromPerses({
                url: '/getInvoked',
                classPath: methodInfo.classPath,
                methodName: methodInfo.methodName,
                signature: methodInfo.signature
            }).then(res => addChildrenToNode(nestedLi, res.data))
                .catch(error => alert("Could not find method " + error));
        });
    });
};

let activeNode;
const updateActiveNode = (node) => {
   if(activeNode !== node) {
       console.log(node);
       if(activeNode) {
           activeNode.firstChild.classList.remove("list-group-item-danger");
       }
       node.firstChild.classList.add("list-group-item-danger");
       activeNode = node;
   }
};

