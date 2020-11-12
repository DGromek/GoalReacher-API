# GoalReacher-API

<h2>Link do API:</h2>
https://goal-reacher-api.herokuapp.com/

<h2>Endpointy</h2>

<table>
<tr>
<th>Metoda</th>
<th>Mapping</th>
<th>Wymagane parametry</th>
<th>Co zwraca</th>
</tr>
<tr>
<td>POST</td>
<td>/login</td>
<td>JSON 
<code>
{
"email": string,
"password" : string
}
</code>
</td>
<td>JSON
<code>
{
"token" : string
}
</code></td>
</tr>
<tr>
<td>GET</td>
<td>/events/{groupId}</td>
<td><code>groupId : string</code></td>
<td>JSON z wszystkimi eventami dla danej grupy</td>
</tr>
</table>

<h3>Dodatkowe informacje</h3>
Od momentu zalogowania każdy request podbijający do API w zwrotce będzie otrzymywał header <zaraz uzupełnie> z nowym tokenem. Każdy token jest ważny 15 minut. 
