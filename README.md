# DDD-Schach
### Implementierungsumgebung für Erstellung eines Schach-Servers
Im Rahmen unserer DDD-Schulung besteht die Aufgabe hier darin, einen Schachserver zu
implementieren. Der Server soll beliebig viele Schach-Partien parallel verarbeiten 
können. 

Eine REST-Schnittstelle ist bereits vorhanden (package <tt>com.iks.dddschach.api</tt>).
Diese leitet an die Implementierung der API-Schnittstelle <tt>SchachspielApi</tt>
weiter.

Die API-Schnittstelle ist der **zentrale Zugang** zur Schach-Domäne! In der 
Zwiebelring-Darstellung umschließt sie die Domain.

In <tt>SchachspielApiImpl</tt> befinden sie einige TODOs. Hier müssen die empfangenen Parameter an die 
(zu erstellenden) Domain-Klassen weitergeleitet und die Ergebnisse letzlich an die 
REST-Schnittstelle zurückgegeben werden. 
 
Im Package <tt>com.iks.dddschach.domain</tt> befinden sich bereits einige 
Value-Objects, die vorzugsweise zu verwenden sind. 
 
### Technische Voraussetzungen
1. Installationen von Java 8 und Maven
2. Von Vorteil: Ein Tool wie z.B. Postman zum Ausführen und Verwalten von REST-Calls.
 
### Aufgabe(n)
Implementierung der Schachdomäne. Dazu zählen Objekte

1. zum Verwalten von (beliebig vielen) Schachspielen, 
2. zum Entgegennehem und Überprüfen von Schach(halb)zügen, 
3. und zum Liefern des aktuellen Spielfeldes.

Die Aufgaben befinden sich nochmals in den JavaDoc-Kommentaren der Schnittstelle 
<tt>SchachspielApi</tt>.

### Testen

Die Richtigkeit der Implementierung kann durch Ausführen des Tests 
<tt>SchachspielApiTest</tt> überpüft werden.

### Starten der Web-Applikation
Der REST-Service läast sich mit <tt>mvn tomcat7:run</tt> starten. 

##### isalive-Check der REST-Services
<a href="http://localhost:8080/dddschach/api/isalive">http://localhost:8080/dddschach/api/isalive</a>

##### Dokumentation der REST-Schnittstelle
<a href="http://localhost:8080/dddschach/doc">http://localhost:8080/dddschach/doc</a>

### Spielen mit der ChessGUI
Auf Github gibt es das Projekt
<a href="https://github.com/domainevent/chessgui">ChessGUI</a> zum Download.
Es lässt sich starten mit <tt>mvn exec:java</tt> und verbindet sich automatisch mit dem 
dddschach-Server unter <tt>http://localhost:8080/dddschach/api</tt>. 
Startet man den ChessGUI zweimal, lässt sich eine Schachpartie gegeneinander spielen 
- natürlich erst nachdem dddschach richtig implementiert worden ist :-)

Viel Spaß...