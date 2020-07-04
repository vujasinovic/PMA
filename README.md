# PMA
Aplikacija Transportivo

Tim 1:

Pavle Janković E2 3/2019

Aleksandar Vujasinović E2 4/2019

Jovana Grabež E2 5/2019


Namjena aplikacije:

U domenu transporta robe nastaje problem nakon što se prevoz obavi tako što prevoznik u većini slučajeva biva prinuđen da se sa odredišta vrati „prazan“, odnosno da prilikom povratka na mjesto odakle je krenuo ne prevozi nikakvu robu. Ideja aplikacije jeste prevazilaženje ovog problema i to na način da će prevoznik biti u mogućnosti da ponudi prevoz robe sa tačke odredišta za određeni datum ili da pretraži prethodno objavljene ponude prevoza robe od strane preduzeća kojima je potrebna usluga prevoza i u skladu sa time formira dalju strategiju prevoza.
Aplikacija je namijenjena svim korisnicima koji posjeduju sredstvo za prevoz robe i tereta (kombi, kamion i slično), bio to određeni individualac kao nezavisni prevoznik ili preduće koje se bavi prevozom robe.

Funkcionalnosti:

Aplikaciju mogu da koriste isključivo autentifikovani korisnici. Prilikom pokretanja aplikacije vrši se provjera da li je korisnik autentifikovan. Ukoliko to nije slučaj, korisniku se prikazuje prozor sa Login formom gdje se od korisnika traži da unese svoje kredencijale.U slučaju da korisnik nije registrovan, registraciju je moguće izvršiti odabirom odgovarajuće opcije. Funkcionalnostima aplikacije se pristupa preko početne strane i navigationDrawera. Svaki korisnik može
da vidi svoj profil, kao i da ga izmjeni. Pored osnovnih informacija o korisniku na profilu su prikazani i komentari.
Glavna funkcionalnost aplikacije je dodavanje ponude prevoza. Klikom na stavku menija "Add offer" korisniku se otvara forma za dodavanje ponude. Za svaku ponudu potrebno je definisati vrijeme i mjesto polaska i odlaska, opis, kapacitet, cijenu.
Klikom na stavku menija "Reservation" korisnik moze da vidi tri liste ponuda. Prva "Offers" lista prikazuje sve ponude koje nisu rezervisane. Klikom na jednu od ponuda iz liste otvara se strana sa detaljima o ponudi. Na toj strani je moguce izvršiti rezervaciju ponude. Nakon što korisnik rezerviše ponudu šalje se obavještenje  o
novoj rezervaciji.Druga "Active" lista predstavlja ponude koje su u toku, odnosno prevoz koji još nije stigao do odredišta.
Treća, "History" lista predstavlja sve zavrsene (Completed ili Canceled) ponude vezane za trenutno autentifikovanog korisnika. Takodje, korisnik može da pregleda sve notifikacije koje su mu pristigle za ponude klikom na stavku menija "Notifiations".
Opcija "Settings" omogućava da korisnik promjeni svoju šifru.
Sinhronizacija je izvršena nad rezervacijama korisnika.
