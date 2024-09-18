/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego.blackjack;

import juego.card.Card;
import java.util.ArrayList;
import java.util.Random;
import juego.ui.InterfazBlackjack;

/**
 *
 * @author USER
 */
public class Blackjack {
    
    private ArrayList<Card> mazo;
    private Random random = new Random(); // declaracion del valor random
    
    //Variables de juego del dealer
    private Card cartaOculta;
    private ArrayList<Card> manoDealer;
    private int dealerSuma;
    private int dealerAceSuma;
    
    //Varianbles de juego del jugador
    private ArrayList<Card> manoJugador;
    private int jugadorSuma;
    private int jugadorAceSuma;

    public Blackjack() {
        inicioJuego();
        construirMazo();
        barajaMazo();
        
        new InterfazBlackjack(this);

    }
    
    public void inicioJuego() {
        //mazo
        construirMazo();
        barajaMazo();

        //mazo
        manoDealer = new ArrayList<Card>();
        dealerSuma = 0;
        dealerAceSuma = 0;

        cartaOculta = mazo.remove(mazo.size()-1); //remover la carta anterior
        dealerSuma += cartaOculta.getValor();
        dealerAceSuma += cartaOculta.isAce() ? 1 : 0;

        Card card = mazo.remove(mazo.size()-1);
        dealerSuma += card.getValor();
        dealerAceSuma += card.isAce() ? 1 : 0;
        manoDealer.add(card);

        System.out.println("DEALER:");
        System.out.println(cartaOculta);
        System.out.println(manoDealer);
        System.out.println(dealerSuma);
        System.out.println(dealerAceSuma);


        //jugador
        manoJugador = new ArrayList<Card>();
        jugadorSuma = 0;
        jugadorAceSuma = 0;

        for (int i = 0; i < 2; i++) {
            card = mazo.remove(mazo.size()-1);
            jugadorSuma += card.getValor();
            jugadorAceSuma += card.isAce() ? 1 : 0;
            manoJugador.add(card);
        }

        System.out.println("PLAYER: ");
        System.out.println(manoJugador);
        System.out.println(jugadorSuma);
        System.out.println(jugadorAceSuma);
    }
    
    //Construccion del mazo de 52 cartas
    public void construirMazo() {
        mazo = new ArrayList<Card>();
        String[] valoresArreglo = {"A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "J", "Q", "K"};
        String[] tiposArreglo = {"C", "D", "H", "S"};
        
        for(String valor : valoresArreglo) {
            for(String tipo : tiposArreglo) {
                mazo.add(new Card(valor,tipo));
            }
        }
    }
    
    //Baraja el mazo de manera aleatoria
    public void barajaMazo() {
        for (int i = 0; i < mazo.size(); i++){
            int j = random.nextInt(mazo.size());
            Card temporal = mazo.get(i);
            mazo.set(i, mazo.get(j));
            mazo.set(j, temporal);
        }
    }
    
    public boolean hit() {
        Card carta = mazo.remove(mazo.size()-1);
        jugadorSuma += carta.getValor();
        jugadorAceSuma += carta.isAce() ? 1:0;
        manoJugador.add(carta);
        return jugadorSuma > 15;
    }
    
    public void stay () {
        while (dealerSuma < 13) {
                    Card carta = mazo.remove(mazo.size()-1);
                    dealerSuma += carta.getValor();
                    dealerAceSuma += carta.isAce() ? 1 : 0;
                    manoDealer.add(carta);
        }
    }
    
    public static void main(String[] args) {
        new Blackjack();
    }
    
    //Metodos Get

    public Card getCartaOculta() {
        return cartaOculta;
    }

    public int getDealerSuma() {
        return dealerSuma;
    }

    public int getJugadorSuma() {
        return jugadorSuma;
    }

    public ArrayList<Card> getManoDealer() {
        return manoDealer;
    }

    public ArrayList<Card> getManoJugador() {
        return manoJugador;
    }

    
}
