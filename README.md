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
<td>DELETE</td>
<td>/groups/{guid}</td>
<td><code> guid: string </code></td>
<td>200 lub 403</td>
</tr>
<tr>
<td>PUT</td>
<td>/groups/block/{guid}</td>
<td></td>
<td>200 lub 403</td>
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
<td>JSON z zaktualizowanym userem, 422 jeżeli coś jest nie tak lub 418 jeżeli grupa jest zablokowana</td>
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
<td>/invitations</td>
<td>Authorization Bearer Token, Body:<code>JSON
{
    "guid": string,
    "invitedEmail": string
}
</code></td>
<td>Nowo utworzone zaproszenie, 403 jeżeli token jest nieważny lub dany użytkownik nie ma uprawnień do zapraszania, 422 gdy inne dane są błędne lub 418 gdy grupa jest zablokowana</td>
</tr>
<tr>
<td>DELETE</td>
<td>/invitations/{invitationId}</td>
<td>Authorization Bearer Token, Body:<code>
id: Long 
</code></td>
<td>Nowo utworzone zaproszenie, 403 jeżeli token jest nieważny lub dany użytkownik nie ma uprawnień do zapraszania lub 422 gdy inne dane są błędne</td>
</tr>
<tr>
<td>POST</td>
<td>/joinFromInvitation</td>
<td>Authorization Bearer Token, param: invitationId long
</td>
<td>UserGroup po dołączeniu, 403 jeżeli token nie zgadza się z użytkownikiem którego było to zaproszenie lub 422 gdy inne dane są błędne</td>
</tr>
<tr>
<td>POST</td>
<td>/events</td>
<td>
<code>
JSON: 
{
    "guid": string,
    "name": string,
    "description": string,
    "datetime": string format:"dd/MM/yyyy HH:mm:ss"
}
</code>
</td>
<td>Nowo utworzony event lub 422</td>
</tr>
<tr>
<td>PUT</td>
<td>/events</td>
<td>
<code>
JSON: 
{
    "name": string,
    "description": string,
    "datetime": string format:"dd/MM/yyyy HH:mm:ss"
}
</code>
</td>
<td>Zupdatowany event lub 422</td>
</tr>
<tr>
<td>DELETE</td>
<td>/events</td>
<td>
param: eventId long
</td>
<td>200 lub 422</td>
</tr>

<tr>
<td>GET</td>
<td>/events/{groupId}/{from}/{to}</td>
<td>
<code>
groupId: long,
from: String "dd-MM-YYYY",
to: String "dd-MM-YYYY"
</code>
</td>
<td>posortowana lista eventow z danego miesiaca i roku</td>
</tr>
<tr>
<td>POST</td>
<td>/notes</td>
<td>
<code> JSON
{
    "title": String,
    "content": String,
    "guid": String
}
</code>
</td>
<td>Nowo utworzony event, FORBIDDEN lub UNPROCESSABLE ENTITY</td>
</tr>
<tr>
<td>GET</td>
<td>/notes/{guid}</td>
<td>
<code> 
guid: String
</code>
</td>
<td>Lista eventow dla danej grupy</td>
</tr>
<tr>
<td>PUT</td>
<td>/notes/{id}</td>
<td>
<code>
id: String, 
Body: JSON
{
    "title": String,
    "content": String,
}
</code>
</td>
<td>Zaktualizowany event lub UNPROCESSABLE ENTITY</td>
</tr>
<tr>
<td>DELETE</td>
<td>/notes/{id}</td>
<td>
<code> 
id: long
</code>
</td>
<td>OK lub UNPROCESSABLE ENTITY</td>
</tr>

</table>

<h3>Dodatkowe informacje</h3>
Od momentu zalogowania każdy request podbijający do API w zwrotce będzie otrzymywał header <Refreshed-token> z nowym tokenem. Każdy token jest ważny 15 minut. <br/>
Wszystkie Requesty z wyjątkiem logowania i rejestracji muszą mieć token
