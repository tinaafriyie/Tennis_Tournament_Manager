/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tennis;

/**
 *
 * @author tina-

 * Interface for playable tennis components (Game, Set, Match).
 * Defines the contract that all playable entities must follow.
 * This demonstrates polymorphism and interface-based design.
 */
public interface Playable {
    
    /**
     * Plays the component until completion
     */
    void play();
    
    /**
     * Checks if the component has finished
     * @return true if complete, false otherwise
     */
    boolean isComplete();
    
    /**
     * Gets the winner of the component
     * @return the winning player, or null if not complete
     */
    Player getWinner();
}
