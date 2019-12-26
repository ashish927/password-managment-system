function generatePassword() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 201) {
             document.getElementById("s-pass").value = this.responseText;
         }
    };
    var siteName = document.getElementById("site").value;
    var data = JSON.stringify({"siteName": siteName, "password": "101010"});

    xhttp.open("POST", "http://localhost:8080/api/generate", true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(data);

}

function savePassword() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
             document.getElementById("site").value = "";
             document.getElementById("s-pass").value = "Generated password will be shown here"
         }
    };
    var siteName = document.getElementById("site").value;
    var password = document.getElementById("s-pass").value;
    var data = JSON.stringify({"siteName": siteName, "password": password});

    xhttp.open("POST", "http://localhost:8080/api/save", true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(data);

}

function cancelPassword() {
   document.getElementById("site").value = "";
   document.getElementById("s-pass").value = "Generated password will be shown here"
}

function getAllData() {
        var data = [];
        var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
             if (this.readyState == 4 && this.status == 200) {
                     data = JSON.parse(this.responseText);
                     var myTableDiv = document.getElementById("dvTable");

                              var headerDiv = document.createElement('DIV');
                              headerDiv.setAttribute("class","tbl-header");

                              var table = document.createElement('TABLE');
                              table.border='1';
                              table.setAttribute("id", "data_table");
                              //table.setAttribute("align","center");

                              var tableBody = document.createElement('TBODY');
                              table.appendChild(tableBody);


                              var header = table.createTHead();
                              header.setAttribute("background-color", "#808080;");
                              var row = header.insertRow(0);
                              var cell1 = row.insertCell(0);
                              cell1.innerHTML = "<b></b>";


                              var cell2 = row.insertCell(1);
                              cell2.setAttribute("width", "250  px;")
                              cell2.innerHTML = "<b>Web Site Name</b>";

                              var cell3 = row.insertCell(2);
                              cell2.setAttribute("width", "250  px;")
                              cell3.innerHTML = "<b>Password</b>";



                              for (var i=0; i<data.length; i++){
                                 var tr = document.createElement('TR');
                                 tr.setAttribute("id", "row_"+i);
                                 tableBody.appendChild(tr);

                                 for (var j=0; j<3; j++){
                                     var td = document.createElement('TD');
                                     td.width='75';
                                     if(j == 0){
                                        td.setAttribute("id", "checkbox_row"+i);
                                       var checkbox = document.createElement('input');
                                       checkbox.type = "checkbox";
                                       checkbox.name = "name";
                                       checkbox.value = "value";
                                       checkbox.id = "id";
                                     td.appendChild(checkbox);
                                     }
                                     if(j == 1){
                                        td.setAttribute("id", "name_row"+i);

                                     td.appendChild(document.createTextNode((data[i].siteName)));
                                        }
                                    if(j == 2){
                                        td.setAttribute("id", "password_row"+i);

                                     td.appendChild(document.createTextNode(data[i].passwords[0].password));
                                        }

                                     tr.appendChild(td);
                                 }
                              }

                              myTableDiv.appendChild(table);





                 }
            };
            xhttp.open("GET", "http://localhost:8080/api/", true);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.send();

    }