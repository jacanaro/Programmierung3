Beleg des Moduls Programmierung 3 im Sommersemester 2021
Entwicklung einer mehrschichtigen und getesteten Anwendung
Geschäftslogik
Erstellen Sie eine Geschäftslogik zur Verwaltung der Inhalte eines
Verkaufsautomaten1. Die möglichen Inhalte sind bereits als Interfaces in
prog3_beleg definiert. Berücksichtigen Sie, dass später weitere Kuchenarten
hinzukommen können. Außerdem sollen auch die Hersteller verwaltet werden.
Die Geschäftslogik muss folgende Funktionalität realisieren:
 Anlegen von Herstellern; dabei muss sichergestellt sein, dass kein Name
mehr als einmal vorkommt
 Einfügen von Kuchen:
o unterstützt werden alle Typen die sowohl von Kuchen als auch
Verkaufsobjekt ableiten
o es ist zu prüfen, dass der Kuchen zu einem bereits existierenden
Hersteller gehört, ansonsten wird er nicht eingefügt
o beim Einfügen wird eine Fachnummer vergeben (fachnummer); zu
keinem Zeitpunkt können mehrere Kuchen innerhalb des Automaten
die gleiche Fachnummer haben
o es wird ein Einfügedatum vergeben
 Abruf aller Hersteller mit der Anzahl der ihrer Kuchen
 Abruf vorhandener Kuchen; wird ein Typ angegeben werden nur Ku[Beleg.PZR1.pdf](https://github.com/jacanaro/Programmierung3/files/9102804/Beleg.PZR1.pdf)
chen
von diesem Typ aufgelistet
 Abruf aller vorhandenen bzw. nicht vorhandenen Allergene im Automaten
 Setzen des Datums der letzten Überprüfung (inspektionsdatum)
 Löschen eines Herstellers
 Entfernen eines Kuchens
CLI
Implementieren Sie eine Benutzeroberfläche. Die Kommunikation zwischen
Oberfläche und Geschäftslogik soll dabei über events erfolgen.
Weiterhin sollen nach dem Beobachterentwurfsmuster 2 Beobachter realisiert
werden: der Erste soll eine Meldung produzieren wenn 90% der Kapazität
überschritten werden, der Zweite über Änderungen an den vorhandenen
1 https://en.wikipedia.org/wiki/Automat
Allergenen informieren. Beachten Sie, dass diese erweiterte Funktionalität nicht
zur Geschäftslogik gehört.
Das UI soll als zustandsbasiertes (Einfüge-, Anzeige-, Lösch- und Änderungs-
Modus, ...) command-line interface (CLI) realisiert werden.
Stellen Sie sicher, dass Bedienfehler in der Eingabe keine unkontrollierten
Zustände in der Applikation erzeugen.
Beim Starten der Anwendung sollen die Argumente ausgelesen werden. Ist eine
Zahl angegeben ist dies die Kapazität.
Befehlssatz
 :c Wechsel in den Einfügemodus
 :d Wechsel in den Löschmodus
 :r Wechsel in den Anzeigemodus
 :u Wechsel in den Änderungsmodus
 :p Wechsel in den Persistenzmodus
Einfügemodus:
 [Herstellername] fügt einen Hersteller ein
 [Kuchen-Typ] [Herstellername] [Preis] [Nährwert]
[Haltbarkeit] [kommaseparierte Allergene, einzelnes
Komma für keine] [[Obstsorte]] [[Kremsorte]] fügt einen
Kuchen ein; Beispiele:
o Kremkuchen Hersteller1 4,50 386 36
Gluten,Erdnuss Butter
o Obsttorte Hersteller2 7,50 632 24 Gluten Apfel
Sahne
Anzeigemodus:
 hersteller Anzeige der Hersteller mit der Anzahl der Kuchen
 kuchen [[Typ]] Anzeige der Kuchen - ggf. gefiltert nach Typ - mit
Fachnummer, Inspektionsdatum-und verbleibender Haltbarkeit. Die
verbleibende Haltbarkeit ergibt sich aus der Differenz zwischen der
definierten Haltbarkeit des Kuchens und der Zeit die seit dem Einfügen
(Einfügedatum) vergangen ist. Diese Darstellung muss nur so aktuell wie der
letzte Abruf sein.
 allergene [enthalten(i)/nicht enthalten(e)] Anzeige der
vorhandenen bzw. nicht vorhandenen Tags
Löschmodus:
 [Herstellername] löscht den Produzenten
 [Fachnummer] entfernt den Kuchen
Änderungsmodus:
 [Fachnummer] setzt das Datum der Inspektion
Persistenzmodus:
 saveJOS speichert mittels JOS
 loadJOS lädt mittels JOS
 saveJBP speichert mittels JBP
 loadJBP lädt mittels JBP
alternatives CLI
Erstellen sie ein alternatives CLI mit eigener main-Methode in dem 2
Funktionalitäten (vorzugsweise Löschen von Herstellern und Auflisten der
Allergene) deaktiviert sind und nur ein Beobachter aktiv ist. Dieses CLI soll sich
vom eigentlichen CLI nur durch die Konfiguration unterscheiden, nicht durch die
Implementierung. Dieses CLI muss nicht über das Netzwerk funktionieren.
Simulation
Stellen Sie sicher, dass die Geschäftslogik thread-sicher ist. Erstellen Sie dafür eine
Simulation, die die Verwendung der Geschäftslogik im Produktivbetrieb testet. Die
Abläufe in der Simulation sollen auf der Konsole dokumentiert werden. Jeder
thread muss für jede ändernde Interaktion an der Geschäftslogik eine Ausgabe
produzieren. Jede Änderung an der Geschäftslogik muss eine Ausgabe
produzieren, sinnvollerweise über Beobachter.
Zur Entwicklung darf Thread.sleep o.Ä. verwendet werden, in der Abgabe
muss dies deaktiviert sein bzw. darf nur mit Null-Werten verwendet werden.
Simulation 1
Erstellen Sie einen thread der kontinuierlich versucht einen zufällig erzeugten
Kuchen einzufügen. Erstellen Sie einen weiteren thread der kontinuierlich die Liste
der enthaltenen Kuchen abruft, daraus zufällig einen auswählt und entfernt. Diese
Simulation sollte nicht terminieren.
Simulation 2
Erstellen Sie einen weiteren thread der kontinuierlich die Liste der enthaltenen
Kuchen abruft, daraus zufällig einen auswählt und die Inspektion auslöst.
Modifizieren Sie den löschenden thread so, dass er den Kuchen mit dem ältesten
Inspektionsdatum entfernt. Sorgen Sie mit wait/notify (bzw. await/signal) dafür,
das Einfügen und Löschen miteinander synchronisiert sind. D.h. wenn das
Einfügen wegen Kapazitätsmangel nicht möglich ist wird der löschende thread
benachrichtigt und während dieser arbeitet wartet der einfügende thread.
Umgekehrt arbeitet auch der löschende thread nicht während der Ausführung des
Einfügens.
Simulation 3
Modifizieren Sie den löschenden thread aus der zweiten Simulation so dass er
eine zufällige Anzahl (inklusive 0) von Kuchen löscht, wobei diese weiterhin das
Kriterium zu dem ältesten Inspektionsdatum erfüllen. Starten Sie die Simulation
mit mindesten je zwei einfügenden und löschenden threads und dem Inspektions-
thread.
GUI
Realisieren Sie eine skalierbare graphische Oberfläche mit JavaFX für die
Verwaltung. Sie soll den gleichen Funktionsumfang wie das CLI haben, abzüglich
der Beobachter. Die Auflistung der Hersteller und Kuchen soll immer sichtbar sein
und nach Benutzeraktionen automatisch aktualisiert werden.
Die Auflistung der Kuchen soll sortierbar nach Fachnummer, Hersteller,
Inspektionsdatum und verbleibender Haltbarkeit sein.
Ermöglichen Sie die Änderung der Fachnummer mittels drag&drop.
I/O
Realisieren Sie die Funktionalität den Zustand der Geschäftslogik zu laden und zu
speichern.
Die Anwender_ können wählen ob die Persistierung mit JOS oder JBP erfolgt.[Beleg.PZR1.pdf](https://github.com/jacanaro/Programmierung3/files/9102810/Beleg.PZR1.pdf)
