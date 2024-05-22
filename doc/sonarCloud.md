# Statikus analízis 

## Manuális kódvizsgálás
Manuális kódvizsgálás során a kód nehezebben érthető részeit részletesen, csapatszinten átolvastuk, részben, hogy mindenki gyorsan és viszonylag akadálymentesen megértse a már meglevő kódokat, részben, hogy egy alap manuális kódátvizsgálást is végezzünk.

## Automatikus kódvizsgálás
Míg a manuális kóvizsgálás elengedhetetlen része a jól működő kódnak (hiszen egy gép nem, vagy csak kevéssé érti meg, hogy az adott kódrészelet mit szeretne csinálni), addig a clean code és egyéb elvek betartását ma már nagy nyugalommal bízhatjuk gépekre, hiszen így a fejlesztők valós időben kaphatnak visszajelzést arról, hogy az éppen írt kód milyen minőségű. \
Ebben nyújt segítséget a pl. a Sonar termékcsalád is, ami a teljes körű CI/DI kódellenőrzést végez. \
A házi feladat során a SonarCloud szolgáltatást és SonarLint kiegészítőt használtuk, előbbit github actions segítségével automatizáltuk. Először a már meglévő kódbázist vizsgáltattuk, majd a kapott hibákat súlyosság szerinti prioritás alapján rendeztük és szűrtük. Ezek közül a legfontosabbakat kijavítottuk. A kódon eszközölt további javítások, tesztek és bővítések pedig már a SonarCloud és a SonarLint használata mellett készültek. A legmeglepőbb számomra a "cognitive complexity" volt, ami a kezdeti kódbázisban egy alkalommal 85 volt az elfogadható 15 helyett, azonban a harmincas-negyvenes complexity-k is gyakoriak voltak. Java oldalon minden ilyen jellegű hibát kijavítottunk, valamint számos más, a SonarLint által javasolt dolgot is módosítottunk, hogy minél inkább kövesse a projekt a clean code elveket.
