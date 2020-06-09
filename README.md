# Application example FinAppli
Application Spring boot avec une couche web et des tests unitaires et integrations
<br><br>
Une installation simple et rapide du projet.:<br>
<a href="https://github.com/mamadoufodebailo/example-finappli.git">
Tester une application Spring Boot RESTful APIs en utilisant MockMvc/Mockito et RestTemplate
</a>

Pour construire l'application basé sur Maven :
positionner sur le repetoire de base et taper: 
`./mvnw clean build`

Pour lancer les tests unitaires et d'integration:
`./mvnw clean test`

Pour lancer l'application:
`./mvnw start` il execute sur le port par default 8080

Les urls possibles sont:<br>
/signup : pour l'inscription <br>
/login : pour la connection <br>
/products : pour afficher le catalogue de produit<br>
/items : pour la gestion du panier<br>
<br>

Inscrivez vous et Connectez-vous pour visualiser ces informations ci-apres:
<br>

<b>Pour l'etat de Santé??</b> - <a>http://localhost:8080/actuator/health</a> <br>

<b>Pour voir les données des tables??</b> - <a>http://localhost:8080/h2-console/</a> <br>
