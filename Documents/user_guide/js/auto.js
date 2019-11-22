window.addEventListener('DOMContentLoaded', (event) => {
    console.log('DOM fully loaded and parsed');

    const main = document.getElementById("main");
    const nav = document.getElementById("nav");
    const header = document.getElementById("header");

    const nTable = document.createElement("table");
    const section = main.getElementsByTagName("section");
    Array.from(section).filter(element =>element.parentElement.getAttribute("id") == "main").forEach(element => {
        element.setAttribute("id", element.getAttribute("name"))
        const videoCell = document.getElementById("video"+element.getAttribute("name"));
        videoCell.rowSpan = 0;
        const nRow = nTable.insertRow();
        const nCell = nRow.insertCell();
        nCell.colSpan = 2;

        const nLink = document.createElement("a");
        nLink.setAttribute("href", "#" + element.getAttribute("name"));
        nLink.innerHTML = element.getAttribute("name");

        nCell.appendChild(nLink);
        let elem = element.getElementsByTagName("section");
        Array.from(elem).forEach(e => {
            videoCell.rowSpan += 1;
            e.setAttribute("id", e.getAttribute("name"));
            const nRow = nTable.insertRow();
            const nCell1 = nRow.insertCell();
            nCell1.innerHTML = ">>";
            const nCell2 = nRow.insertCell();
            const nLink = document.createElement("a");

            nLink.setAttribute("href", "#" + e.getAttribute("name"));
            nLink.innerHTML = e.getAttribute("name");

            nCell2.appendChild(nLink);
        })
    });

    nav.appendChild(nTable);
});