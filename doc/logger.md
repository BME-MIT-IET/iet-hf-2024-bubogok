# Logger osztályok

A programban a naplózásokat megvalósítandó **Logger** osztályok állnak rendelkezésre, melyekkel lehet `file`-ba, `console`-ra, illetve egyéb `PrintStream` típusokba naplózást írni.

## Naplózási szintek
Négyféle naplózási szintet különböztetünk meg:
- debug
- info
- warning
- error

## Naplózott információ
Naplózás során vannak statikus (dátum, naplózási szint, hívó osztály és függvénye) és dinamikus (`label`, ami a naplózás célját foglalja össze, illetve `message`, mely egy akár többsoros üzenetet reprezentál) adatok, amiket kiírunk.

## Naplózás menete
A naplózáshoz a `Log` osztály használandó, mely szinkronizáltan több forrásból is tud naplózási kéréseket fogadni, illetve cserélhetően tárolja az aktív Logger implementációt.