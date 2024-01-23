
### RandomAccessFileExample

#### Panoramica

La classe `RandomAccessFileExample` rappresenta un semplice gioco di dadi basato su console che utilizza `RandomAccessFile` per memorizzare i dati dei giocatori e i loro lanci di dadi.

#### Costruttore

```java
/**
 * Costruisce un nuovo RandomAccessFileExample con il nome file specificato.
 *
 * @param fileName Il nome del file da utilizzare per memorizzare i dati del giocatore.
 * @throws FileNotFoundException Se il file specificato non viene trovato.
 */
public RandomAccessFileExample(String fileName) throws FileNotFoundException {
    try {
        this.file = new RandomAccessFile(fileName, "rw");
    } catch (FileNotFoundException e) {
        throw new FileNotFoundException("File non trovato: " + fileName);
    }
}
```

#### Metodi

1. **lanciaDado()**

```java
/**
 * Simula il lancio di un dado per un giocatore, registra il risultato nel file e stampa l'esito.
 *
 * @throws IOException Se si verifica un errore di I/O durante la scrittura nel file.
 */
public void lanciaDado() throws IOException {
    System.out.print("Inserisci il nome del giocatore: ");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String nomeGiocatore = reader.readLine();

    int dado = (int) (Math.random() * 6) + 1;

    file.seek(file.length());
    file.writeUTF(nomeGiocatore);
    file.writeInt(dado);

    System.out.println(nomeGiocatore + " ha lanciato il dado e ottenuto: " + dado);
}
```

2. **calcolaPunteggio()**

```java
/**
 * Calcola il punteggio totale di un giocatore in base ai suoi lanci di dadi e stampa il risultato.
 *
 * @throws IOException Se si verifica un errore di I/O durante la lettura dal file.
 */
public void calcolaPunteggio() throws IOException {
    System.out.print("Inserisci il nome del giocatore: ");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String nomeGiocatore = reader.readLine();

    file.seek(0);
    int punteggio = 0;

    while (file.getFilePointer() < file.length()) {
        String nome = file.readUTF();
        int dado = file.readInt();

        if (nome.equals(nomeGiocatore)) {
            punteggio += dado;
        }
    }

    System.out.println("Il punteggio totale di " + nomeGiocatore + " è: " + punteggio);
}
```

3. **visualizzaVincitore()**

```java
/**
 * Visualizza il vincitore del gioco determinando il giocatore con il punteggio totale più alto.
 *
 * @throws IOException Se si verifica un errore di I/O durante la lettura dal file.
 */
public void visualizzaVincitore() throws IOException {
    file.seek(0);
    String vincitore = "";
    int maxPunteggio = 0;

    while (file.getFilePointer() < file.length()) {
        String nome = file.readUTF();
        int dado = file.readInt();

        int punteggio = dado;
        if (punteggio > maxPunteggio) {
            maxPunteggio = punteggio;
            vincitore = nome;
        }
    }

    System.out.println("Il vincitore è: " + vincitore + " con un punteggio di: " + maxPunteggio);
}
```

4. **cancellaArchivio()**

```java
/**
 * Cancella il contenuto del file, azzerando effettivamente i dati di gioco.
 *
 * @throws IOException Se si verifica un errore di I/O durante il troncamento del file.
 */
public void cancellaArchivio() throws IOException {
    file.setLength(0);
    System.out.println("Archivio dei dati cancellato.");
}
```

#### Metodo Principale

```java
/**
 * Punto di ingresso principale del programma. Consente all'utente di giocare interattivamente al gioco dei dadi.
 *
 * @param args Argomenti da riga di comando (non utilizzati in questa applicazione).
 */
public static void main(String[] args) {
    try {
        RandomAccessFileExample game = new RandomAccessFileExample("giocoDado.dat");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int choice;
        do {
            System.out.println("1. Lancio del dado");
            System.out.println("2. Calcola punteggio di un giocatore");
            System.out.println("3. Visualizza vincitore");
            System.out.println("4. Cancella archivio dei dati");
            System.out.println("5. Esci");
            System.out.print("Scelta: ");

            try {
                choice = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    game.lanciaDado();
                    break;
                case 2:
                    game.calcolaPunteggio();
                    break;
                case 3:
                    game.visualizzaVincitore();
                    break;
                case 4:
                    game.cancellaArchivio();
                    break;
                case 5:
                    System.out.println("Arrivederci!");
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
                    break;
            }

        } while (choice != 5);

    } catch (IOException e) {
        e.printStackTrace();
    }
}
```
