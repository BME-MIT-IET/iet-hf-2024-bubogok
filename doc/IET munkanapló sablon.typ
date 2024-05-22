#set text(lang: "hu")

#let todo(x) = text(red)[#x]

#show link: set text(rgb(0,0,255))
#let gh(x) = link("https://github.com/" + x)[#x]


= Házi feladat munkanapló: Bubogók
Integrációs és ellenőrzési technikák (VIMIAC04)

#block(breakable: false)[
== Csapattag 1 (Koczó Attila István, ITWQ3V, #gh("koczoa"))
=== Statikus analízis (összesen kb. 5 óra):

- A kód összevetése a követelményekkel.

=== Hibajavítás (összesen kb. 5 óra):

- A kódban előzetesen ismert lényegesebb hibák javítása.

=== Code review (összesen kb. 5 óra):

- A többi csapattag által írt kód felülvizsgálata.

]

#block(breakable: false)[
== Csapattag 2 (Sandle Nátán, SGGDSK, #gh("darpleon"))
=== Python unit tesztek (összesen kb. 6 óra):

- Unit tesztelési lehetőségek felmérése
- Unit tesztek implementálása

=== Java unit tesztek (összesen kb. 7 óra):

- Unit teszteléshez kapcsolódó függőségek kezelése
- Mockito használatának elsajátítása
- Unit tesztek tervezése
- Unit tesztek implementálása

=== Code review (összesen kb. 2 óra):
- A többi csapattag által írt kód felülvizsgálata.
]

#block(breakable: false)[
== Csapattag 3 (Szarkowicz Dániel, FK0IEH, #gh("daniel-szarkowicz"))
=== Python github actions (összesen kb. 2 óra):

- Példa tesztelhető modul és példa teszt elkészítése
- Github actions megírása

=== Szálak helyes leállítása (összesen kb. 7 óra):

- Probléma megismerése
- Különböző Java módszerek összehasonlítása
- Shutdown hookok implementálása

=== Code review (összesen kb. 2. óra):

- A többi csapattag által írt kód felülvizsgálata
- Kód elutasítása javítási javaslatokkal

=== Logger (összesen kb 3. óra):

- Apró hibák kijavítása
- Színes labelek implementálása
- Print-ek loggerre cserélése

=== Pair programming (összesen kb 3. óra):

- Gradle fáljok írása (cucumber)
]


#block(breakable: false)[
== Csapattag 4 (Szombati Olivér, P37PLU, #gh("Szombatioi"))
=== Github Actions (összesen kb. 3 óra)
- gradle.yaml fájl elkészítése

=== Logger (összesen kb. 6 óra):
- Osztályhierarchia megtervezése
- Implementáció elkészítése
- Naplózási formátum megtervezése
- Konzolra írások átírása naplózásra

=== Java unit tesztek (összesen kb. 4 óra):
- FieldTest tesztesetek megtervezése + leírása

=== Code review (összesen kb. 2 óra):
- Csapattársak PR-jainak ellenőrzése
- Módosítási javaslatok tétele

]

#block(breakable: false)[
== Csapattag 5 (Tamási Máté, ONYGW3, #gh("matetamasi"))
=== Cucumber (összesen kb. 9 óra):

- Specifikáció megfogalmazása
- Java lépésdefiníciók leírása
- Gradle task létrehozása és hozzáadása github actions-höz

=== SonarCloud beállítása (4 óra):

- Gradle task létrehozása
- Github action létrehozása
- Élő statisztikák hozzáadása a README-hez
- Coverage ellenőrzés kikapcsolása

=== Code review (összesen kb. 2 óra):

- PR-beli kód statikus vizsgálata
- PR-beli kód helyi futtatása
]

2024-05-22

#v(1cm)

#(28 * ".") #math.quad
#(28 * ".") #math.quad
#(28 * ".") #math.quad
#(28 * ".") #math.quad
#(28 * ".") #math.quad
