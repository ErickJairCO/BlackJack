/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego.card;

/**
 *
 * @author USER
 */
public class Card {
    private String valor;
    private String tipo;

    public Card(String valor, String tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }
    
    public int getValor() {
        if ("AKQJ".contains(valor)){
            if(valor.equals("A")){
                return 1;
            }
            return 10;
        }
        return Integer.parseInt(valor);
    }
    
    public boolean isAce() {
        return valor.equals("A");
    }
    
    public String getImagePath() {
        System.out.print("Entrando dentro de la ruta: ");
        System.out.println("./juego/images/" + toString() + ".png");
        return "/juego/images/" + toString() + ".png";
    }

    @Override
    public String toString() {
        return valor + "-" + tipo;
    }    
}
