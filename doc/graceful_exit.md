# A szálak helyes bezárása

A program a kommunikációhoz és a rajzoláshoz számos szálat használ. Ha a szálak rosszul állnak le, akkor az inkonzisztens fáljokhoz vezet, ha a program ír fáljokba.

A szálak garantált leállásához a Java shutdown hook használható. A shutdown hookok a JVM leállításakor automatikusan meghívódnak. Sajnos a Gradle nem állítja le rendesen a JVM-et, ezért a shutdown hookok nem hívódnak meg. Ez szerencsére élesben nem lesz probléma, mert a kész szoftvert a `java -jar` parancssal kell majd futtatni és az rendesen fogja leállítani a JVM-et.
