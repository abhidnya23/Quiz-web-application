<%@ page import="java.util.*, model.Question, model.Option" %>

<html>
<head>
<title>Online Test</title>

<style>
body { font-family: Arial; margin:0; background:#f5f6fa; }

.header {
    display:flex;
    justify-content:space-between;
    background:#1e272e;
    color:#fff;
    padding:15px 25px;
}

#timer {
    font-size:18px;
    font-weight:bold;
}

.container {
    display:flex;
}

.left {
    width:70%;
    padding:25px;
}

.right {
    width:30%;
    background:#dcdde1;
    padding:20px;
}

.question {
    display:none;
}

.active {
    display:block;
}

.option {
    display:block;
    padding:10px;
    margin:8px 0;
    background:#fff;
    border-radius:5px;
    cursor:pointer;
}

.option:hover {
    background:#dff9fb;
}

.q-num {
    display:inline-block;
    width:35px;
    height:35px;
    line-height:35px;
    text-align:center;
    margin:5px;
    background:#bdc3c7;
    border-radius:5px;
    cursor:pointer;
}

.q-active { background:#0984e3; color:#fff; }
.answered { background:#00b894; color:#fff; }

.btn {
    padding:10px 15px;
    margin:5px;
    border:none;
    cursor:pointer;
    border-radius:5px;
}

.next { background:#0984e3; color:white; }
.prev { background:#636e72; color:white; }
.submit { background:#00b894; color:white; }
</style>

</head>

<body>

<div class="header">
    <h3>Online Test</h3>
    <div id="timer">Loading...</div>
</div>

<%
List<Question> list = (List<Question>) request.getAttribute("questions");
Map<Integer,Integer> saved = (Map<Integer,Integer>)request.getAttribute("savedAnswers");

if(list == null) list = new ArrayList<>();
if(saved == null) saved = new HashMap<>();
%>

<div class="container">

<!-- LEFT -->
<div class="left">

<form id="examForm" action="<%=request.getContextPath()%>/submitExam" method="post">

<%
for(int i=0;i<list.size();i++){
    Question q = list.get(i);
%>

<div class="question" id="q<%=i%>">

    <h3>Q<%=i+1%>. <%=q.getQuestion_text()%></h3>

    <%
    for(Option o : q.getOptions()){
        Integer selected = saved.get(q.getQuestion_id());
    %>

    <label class="option">
        <input type="radio"
            name="q_<%=q.getQuestion_id()%>"
            value="<%=o.getOption_id()%>"
            <%= (selected != null && selected == o.getOption_id()) ? "checked" : "" %>
            onclick="saveAnswer(<%=q.getQuestion_id()%>, <%=o.getOption_id()%>)">

        <%=o.getOption_text()%>
    </label>

    <% } %>

</div>

<% } %>

<button type="button" class="btn prev" onclick="prevQ()">Previous</button>
<button type="button" class="btn next" onclick="nextQ()">Next</button>
<button type="submit" class="btn submit">Submit</button>

</form>

</div>

<!-- RIGHT -->
<div class="right">
<h4>Questions</h4>

<%
for(int i=0;i<list.size();i++){
%>
<span class="q-num" onclick="goToQ(<%=i%>)"><%=i+1%></span>
<% } %>

</div>

</div>

<script>

let current = 0;
let total = <%=list.size()%>;

function showQ(index){

    document.querySelectorAll(".question").forEach(q => q.classList.remove("active"));

    let el = document.getElementById("q"+index);
    if(el) el.classList.add("active");

    updateNav();
}

function nextQ(){
    if(current < total-1){
        current++;
        showQ(current);
    }
}

function prevQ(){
    if(current > 0){
        current--;
        showQ(current);
    }
}

function goToQ(i){
    current = i;
    showQ(current);
}

function updateNav(){
    let items = document.querySelectorAll(".q-num");

    items.forEach((el,i)=>{
        el.classList.remove("q-active");

        if(i===current){
            el.classList.add("q-active");
        }
    });
}

// SAVE
function saveAnswer(qid, oid){

    fetch("<%=request.getContextPath()%>/saveAnswer", {
        method:"POST",
        headers:{"Content-Type":"application/x-www-form-urlencoded"},
        body:"question_id="+qid+"&option_id="+oid
    });
}

// TIMER (SERVER BASED)
let time = 1800;

setInterval(()=>{

    let min = Math.floor(time/60);
    let sec = time%60;

    document.getElementById("timer").innerHTML =
        "Time Left: "+min+":"+(sec<10?"0"+sec:sec);

    time--;

    if(time < 0){
        alert("Time up!");
        document.getElementById("examForm").submit();
    }

},1000);

// INIT
showQ(0);

</script>

</body>
</html>