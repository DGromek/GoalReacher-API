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
<td>JSON z zalogowanym uzytkownikiem</td>
</tr>
<tr>
<td>GET</td>
<td>/events/{groupId}</td>
<td><code>groupId : string</code></td>
<td>JSON z wszystkimi eventami dla danej grupy</td>
</tr>
<tr>
<td>GET</td>
<td>/users/all</td>
<td></td>
<td>JSON ze wszystkimi uzytkownikami</td>
</tr>
<tr>
<td>POST</td>
<td>/users</td>
<td>
<code>JSON
{
    "firstName": string,
    "lastName": string,
    "email": string,
    "password": string
 }
</code></td>
<td>JSON z utworzonym uzytkownikiem lub kod 422 jeżeli email jest zajęty</td>
</tr>
<tr>
<td>GET</td>
<td>/groups/all</td>
<td></td>
<td>JSON ze wszystkimi grupami</td>
</tr>
<tr>
<td>GET</td>
<td>/groups/{guid}</td>
<td><code>guid : string</code></td>
<td>JSON grupą o danym guid lub 404 jeżeli takiej nie ma</td>
</tr>
<tr>
<td>POST</td>
<td>/groups</td>
<td><code>JSON
{
    "name": string,
    "description": string
}
</code></td>
<td>JSON z utworzoną grupą</td>
</tr>
<tr>
<td>POST</td>
<td>/joinGroup</td>
<td><code>JSON
{
    "targetGroupGuid": string,
    "userId": string
}
</code></td>
<td>JSON z zaktualizowanym userem lub 422 jeżeli coś jest nie tak</td>
</tr>
<tr>
<td>PUT</td>
<td>/users/changeStatus</td>
<td>Authorization Bearer Token, Body:<code>JSON
{
    "targetGroupGuid": string,
    "targetUserEmail": string,
    "newRole": string //USER, ADMIN, PENDING, CREATOR
}
</code></td>
<td>JSON z zaktualizowanym userem lub 422 jeżeli coś jest nie tak</td>
</tr>
<tr>
<td>DELETE</td>
<td>/users/leaveGroup</td>
<td>Authorization Bearer Token, Body:<code>JSON
{
    "targetGroupGuid": "GGGGGG",
    "targetUserEmail": "j.wick@gmail.com"
}
</code></td>
<td>200 lub 422</td>
</tr>


</table>

<h3>Dodatkowe informacje</h3>
Od momentu zalogowania każdy request podbijający do API w zwrotce będzie otrzymywał header <zaraz uzupełnie> z nowym tokenem. Każdy token jest ważny 15 minut. 
