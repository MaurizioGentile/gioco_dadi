package org.example;

import java.io.*;

class RandomAccessFileExample {
    private RandomAccessFile file;

    public RandomAccessFileExample(String fileName) throws FileNotFoundException {
        this.file = new RandomAccessFile(fileName, "rw");
    }

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

    public void cancellaArchivio() throws IOException {
        file.setLength(0);
        System.out.println("Archivio dei dati cancellato.");
    }

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
}
