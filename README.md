# DDD-Schach

Im Rahmen unserer DDD-Schulung besteht die Aufgabe hier darin, einen **Schachserver** zu
implementieren. Der Server soll beliebig viele Schach-Partien parallel verarbeiten 
können. 

Eine REST-Schnittstelle ist bereits vorhanden (package <tt>com.iks.dddschach.api</tt>).
Diese leitet an die Implementierung der API-Schnittstelle <tt>SchachspielApi</tt>
weiter.

Diese **API-Schnittstelle** ist der **zentrale Zugang** zur Schach-Domäne! In der 
Zwiebelring-Darstellung umschließt sie die von Euch zu erstellende Domäne.

In <tt>SchachspielApiImpl</tt> befinden sie einige TODOs. Hier müssen die empfangenen Parameter an die 
Domain-Klassen weitergeleitet und die Ergebnisse letztlich an die 
REST-Schnittstelle zurückgegeben werden. 
 
Im Package <tt>com.iks.dddschach.domain</tt> befinden sich bereits einige vorgefertigte
Value-Objects, die vorzugsweise (wieder-) zu verwenden sind. 
 
### Technische Voraussetzungen
1. Installationen von Java 8 und Maven
2. Eine IDE wie z.B. Eclipse
3. Von Vorteil: Ein Tool wie Postman zum Ausführen von REST-Calls.
 
## Aufgabe(n)
### Implementierung der Schachdomäne. 
Dazu zählen Objekte

1. zum Verwalten von (beliebig vielen) Schachspielen, 
2. zum Entgegennehmen und Überprüfen von Schach(halb)zügen, 
3. und zum Liefern des aktuellen Spielfeldes.

Die Aufgaben befinden sich nochmals in den JavaDoc-Kommentaren der Schnittstelle 
<tt>SchachspielApi</tt>.

### Testen

Die Richtigkeit der Implementierung kann durch Ausführen des Tests 
<tt>SchachspielApiTest</tt> überpüft werden.

### Starten der Web-Applikation
Der REST-Service lässt sich mit <tt>mvn tomcat7:run</tt> starten. 

#### isalive-Check der REST-Services
<a href="http://localhost:8080/dddschach/api/isalive">http://localhost:8080/dddschach/api/isalive</a>

### Dokumentation der REST-Schnittstelle
Unter 
<a href="http://localhost:8080/dddschach/doc">http://localhost:8080/dddschach/doc</a>
findet Ihr eine übersichtliche Dokumentation der REST-Schnittstelle (erstellt mit Enunciate). 

### REST-Aufrufe, vorzugweise mit Postman ###
#### Neues Spiel
**POST** auf <a href="http://localhost:8080/dddschach/api/games/">http://localhost:8080/dddschach/api/games/</a><br/>
Body (x-www-form-urlencoded):
<pre>
note:Ein kleiner Vermerk zum Spiel
</pre>
Erwartetes Ergebnis (exemplarisch): 
<pre>
{
    id": "1234567"
}
</pre>

#### Abfrage des Spielbretts
**GET** auf <a href="http://localhost:8080/dddschach/api/games/0/board">http://localhost:8080/dddschach/api/games/0/board</a><br/>
Erwartetes Ergebnis (im Fall einer ungültigen Spiel-Id, hier 0): 
<pre>
{
  "error code": "INVALID_GAMEID",
  "INVALID_GAMEID": "0"
}
</pre>
**GET** auf <a href="http://localhost:8080/dddschach/api/games/1234567/board">http://localhost:8080/dddschach/api/games/1234567/board</a><br/>
Erwartetes Ergebnis (im Fall einer gültigen Spiel-Id):
<pre>
{
  "board": [
    [
      {
        "figure": "R",
        "color": "w"
      },
      {
        "figure": "P",
        "color": "w"
      ...
</pre>

#### Ausführen eines Halbzugs
**POST** auf <a href="http://localhost:8080/dddschach/api/games/1234567/moves">http://localhost:8080/dddschach/api/games/1234567/moves</a><br/>
Body (x-www-form-urlencoded):
<pre>
move:b1-c3
</pre>
Erwartetes Ergebnis
<pre>
{
  "index": 1
}
</pre>

### Spielen mit ChessGUI
Auf Github gibt es das Projekt
<a href="https://github.com/domainevent/chessgui">ChessGUI</a> zum Download.
Es lässt sich starten mit <tt>mvn compile exec:java</tt>. Startet man ChessGUI zweimal, 
lässt sich eine Schachpartie gegeneinander spielen
- natürlich erst nachdem dddschach richtig implementiert worden ist :-)

#### Wie paaren sich zwei Spieler?
Beim Start von ChessGUI wird gefragt, ob es sich um eine neue Partie handelt 
oder ob man sich über die Eingabe einer Spiel-ID "paaren" möchten. 
Der Initiator einer neuen Partie wählt hier "Neues Spiel". Anschließend
ermittelt er über den Menüpunkt <it>Spiel -> Spiel-ID</it> die eindeutige ID
dieser Partie und teilt sie seinem Mitspieler mit. Dieser kann die ID dann bei
beim Start seiner ChessGUI eingeben. Anschließend synchronisieren die 
beiden ChessGUIs in regelmäßigen Abständen ihre Stellungen.

**Hinweis:** ChessGUI verbindet sich per Default mit dem 
dddschach-Server unter <tt>http://localhost:8080/dddschach/api</tt>. 
Dies lässt sich ggf. anpassen mit <tt>mvn exec:java -Dserver=...</tt> 

Viel Spaß...