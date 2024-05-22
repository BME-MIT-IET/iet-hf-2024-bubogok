# Behavior Driven Development a Cucumber eszközzel

A projektben a Gherkin nyelvben megfogalmaztunk bizonyos követlményeket a Unitok működésével kapcsolatban, majd azokhoz java step definition-öket is létrehoztunk, így a leírt követlemények egyben unit tesztekként is szolgálnak. Az eszköz célja, hogy a requierment-ek "élő dokumentációként" szolgálhassanak, így garantálva a a szoftver minőségét és a requirement-ek teljesülését generációkon keresztül.

## Gherkin

A Gherkin nyelv lényege, hogy `Feature`-öket adhatunk meg, azon belül pedig példákat (`Example` vagy `Scenario`, a kettő ekvivalens) fogalmazhatunk meg. A nyelv legnagyobb előnye, hogy a leírt példák hozzá nem értő emberek által is könnyen megérthetők, hiszen egyszerű emberi nyelven íródnak. A soroknak a `Given`, `When`, `Then`, `And`, `But` kulcsszavak egyikével kell kezdődnie. Ezután egy teszőleges kifejezés következhet, melyben szöveges vagy szám paraméterek is szerepelhetnek. Így egy éles projektben ezeket a követelményeket a megrendelőtől a projekten dolgozó, kevesebb technikai tudással rendelkező kollégákon keresztül a fejlesztőkig és a tesztelőkig mindenki érti. A Cucumber eszköz segítségével ezután ezekről a mindenki által érthető követleményekről tudjuk bemutatni, hogy azokat a kódunk teljesíti.

## Cucumber
A Gherkinben megírt követelmények kóddá való átalakításáért a Cucumber eszköz, valamint egy megfelelő java (illetve a projektben használt nyelv) osztály. Itt a metóduok a mondatkezdő kulcsszavakkal, valamint az utána található kifejezésekre illeszkedő regex-ekkel vannak annotálva. A `Given` jellezmzően beállításokat végez, a `When` műveleteket hajt végre, a `Then` pedig `assert`-eket fogalaz meg arról, hogy milyen állapotba került a rendszer. Így egy ilyen Cucumber követelmény futtatása tulajdonképpen egy tesztnek felel meg.
