package org.example;

import java.io.*;

public class GiocoDelDado {

    private RandomAccessFile file;

    public GiocoDelDado(String nomeFile) throws IOException {
        file = new RandomAccessFile(nomeFile, "rw");
    }

    public void registraLancio(String giocatore, int faccia) throws IOException {
        long posizione = file.length();
        file.writeUTF(giocatore);
        file.writeInt(faccia);
    }

    public void calcolaPunteggio(String giocatore) throws IOException {
        file.seek(0);

        int punteggio = 0;

        while (file.getFilePointer() < file.length()) {
            String nomeGiocatore = file.readUTF();
            int facciaDado = file.readInt();

            if (nomeGiocatore.equals(giocatore)) {
                punteggio += facciaDado;
            }
        }

        System.out.println("Il punteggio totale di " + giocatore + " è: " + punteggio);
    }

    public void calcolaVincitore() throws IOException {
        file.seek(0);

        String vincitore = null;
        int punteggioMassimo = Integer.MIN_VALUE;

        while (file.getFilePointer() < file.length()) {
            String nomeGiocatore = file.readUTF();
            int facciaDado = file.readInt();

            int punteggioGiocatore = calcolaPunteggioTotale(nomeGiocatore);

            if (punteggioGiocatore > punteggioMassimo) {
                vincitore = nomeGiocatore;
                punteggioMassimo = punteggioGiocatore;
            }
        }

        System.out.println("Il vincitore è: " + vincitore + " con un punteggio di " + punteggioMassimo);
    }

    private int calcolaPunteggioTotale(String giocatore) throws IOException {
        file.seek(0);

        int punteggio = 0;

        while (file.getFilePointer() < file.length()) {
            String nomeGiocatore = file.readUTF();
            int facciaDado = file.readInt();

            if (nomeGiocatore.equals(giocatore)) {
                punteggio += facciaDado;
            }
        }

        return punteggio;
    }

    public void cancellaArchivio() throws IOException {
        file.setLength(0);
        System.out.println("Archivio cancellato con successo.");
    }

    public void chiudiFile() throws IOException {
        file.close();
    }

    public static void main(String[] args) {
        try {
            GiocoDelDado gioco = new GiocoDelDado("archivio.dat");

            while (true) {
                System.out.println("1. Registra lancio");
                System.out.println("2. Calcola punteggio");
                System.out.println("3. Calcola vincitore");
                System.out.println("4. Cancella archivio");
                System.out.println("5. Esci");

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                int scelta = Integer.parseInt(reader.readLine());

                switch (scelta) {
                    case 1:
                        System.out.println("Inserisci il nome del giocatore:");
                        String nomeGiocatore = reader.readLine();
                        System.out.println("Inserisci la faccia del dado:");
                        int facciaDado = Integer.parseInt(reader.readLine());
                        gioco.registraLancio(nomeGiocatore, facciaDado);
                        break;
                    case 2:
                        System.out.println("Inserisci il nome del giocatore:");
                        String giocatore = reader.readLine();
                        gioco.calcolaPunteggio(giocatore);
                        break;
                    case 3:
                        gioco.calcolaVincitore();
                        break;
                    case 4:
                        gioco.cancellaArchivio();
                        break;
                    case 5:
                        gioco.chiudiFile();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
