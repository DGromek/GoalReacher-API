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
<td>/users</td>
<td>
Authorization Bearer Token
</td>
<td>JSON z zalogowanym uzytkownikiem lub 403</td>
</tr>
<tr>
<td>DELETE</td>
<td>/users</td>
<td>
Authorization Bearer Token
</td>
<td>200 jeżeli użytkownik poprawnie usunięty lub 403</td>
</tr>
<tr>
<td>PUT</td>
<td>/users</td>
<td>
Authorization Bearer Token
<code>JSON
{
    "firstName": string optional,
    "lastName": string optional,
    "email": string optional,
    "password": string optional
}
</code>
</td>
<td>Json z zaktualizowanym użytkownikiem lub 422</td>
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
<td>JSON z zaktualizowanym UserGroup lub 422 jeżeli coś jest nie tak</td>
</tr>
<tr>
<td>DELETE</td>
<td>/users/leaveGroup</td>
<td>Authorization Bearer Token, Body:<code>JSON
{
    "targetGroupGuid": string,
    "targetUserEmail": string
}
</code></td>
<td>200 lub 422</td>
</tr>
<tr>
<td>POST</td>
<td>/invitations/td>
<td>Authorization Bearer Token, Body:<code>JSON
{
    "guid": string,
    "invitedEmail": string
}
</code></td>
<td>Nowo utworzone zaproszenie, 401 jeżeli token jest nieważny lub dany użytkownik nie ma uprawnień do zapraszania lub 422 gdy inne dane są błędne</td>
</tr>
<tr>
<td>POST</td>
<td>/joinFromInvitation</td>
<td>Authorization Bearer Token, param: invitationId long
</td>
<td>UserGroup po dołączeniu, 401 jeżeli token nie zgadza się z użytkownikiem którego było to zaproszenie lub 422 gdy inne dane są błędne</td>
</tr>

</table>

<h3>Dodatkowe informacje</h3>
Od momentu zalogowania każdy request podbijający do API w zwrotce będzie otrzymywał header <zaraz uzupełnie> z nowym tokenem. Każdy token jest ważny 15 minut. 
