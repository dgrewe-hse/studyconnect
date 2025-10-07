# Projektsetup und Auswahl des Technologie-Stacks

## Teammitglieder
- Matej Ivic
- Luka Strahlendorf
- Erik Sauer
- Dieter Grünke

## 1. Einführung
Zu Beginn des Projekts wurde der technische Rahmen für die Entwicklung der StudyConnect Webapp definiert.  
Das Ziel war es, eine modulare, leicht wartbare und zukunftssichere Architektur zu schaffen, die sich gut für Teamarbeit und iterative Weiterentwicklung eignet.

## 2. Frontend
Das Frontend wird mit React realisiert, um eine moderne, komponentenbasierte Benutzeroberfläche zu ermöglichen.  

Begründung:
- React bietet eine große Entwickler-Community und ein breites Ökosystem an Bibliotheken.  
- Durch den komponentenbasierten Aufbau können UI-Elemente wiederverwendet und leicht gepflegt werden.  
- Der deklarative Programmierstil vereinfacht die Entwicklung dynamischer Benutzeroberflächen.  

## 3. Backend
Das Backend basiert auf Python und Flask, wodurch eine flexible und leichtgewichtige REST-API-Struktur umgesetzt werden kann.  

Begründung:
- Flask ist minimalistisch, lässt sich gut an individuelle Anforderungen anpassen und integriert sich problemlos mit anderen Python-Tools.  
- Python bietet hohe Lesbarkeit, große Community-Unterstützung und viele verfügbare Bibliotheken (z. B. für Datenverarbeitung, API-Dokumentation oder Testing).  

## 4. Datenbank
Die Anwendung nutzt eine PostgreSQL-Datenbank zur persistenten Datenspeicherung.  

Begründung:
- PostgreSQL ist ein leistungsfähiges, stabiles und quelloffenes relationales Datenbanksystem.  
- Es unterstützt komplexe Abfragen, Transaktionen und lässt sich gut mit Flask und ORMs wie SQLAlchemy kombinieren.  

## 5. Authentifizierung und Autorisierung
Für Authentifizierung und Autorisierung wird Keycloak eingesetzt.  

Begründung:
- Keycloak ist eine ausgereifte Open-Source-Lösung, die Standardprotokolle wie OAuth2 und OpenID Connect unterstützt.  
- Es ermöglicht zentrale Benutzerverwaltung, Single Sign-On (SSO) und rollenbasierte Zugriffskontrolle.  
- Dadurch wird die Sicherheit erhöht und der Implementierungsaufwand im Backend reduziert.  

## 6. Containerisierung und Entwicklungsumgebung
Bereits zu Beginn des Projekts wird ein starker Fokus auf den Einsatz von Docker und Containerisierung gelegt.  
Sowohl Keycloak als auch die PostgreSQL-Datenbank werden vollständig containerisiert bereitgestellt, und auch für Frontend und Backend ist eine containerbasierte Entwicklung vorgesehen.

Der Einsatz von Containern bietet mehrere Vorteile:
- Konsistenz: Alle Teammitglieder arbeiten in identischen, reproduzierbaren Umgebungen.  
- Portabilität: Container können unabhängig vom Betriebssystem oder lokalen Setup ausgeführt werden.  
- Einfache Wartung und Skalierung: Updates, neue Services oder Deployments lassen sich schnell und kontrolliert durchführen.  
- Isolierung: Abhängigkeiten und Laufzeitumgebungen bleiben sauber voneinander getrennt, was Konflikte reduziert.
