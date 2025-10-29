/**
 * Represents a spectator who can watch and react during a tennis match.
 * This interface is implemented by Player so a player can also be a spectator.
 * @author Thomas
 * @version 1.0
 */
public interface Spectator {

    /**
     * Watches a specific match.
     * @param matchInfo Description of the match being watched
     */
    void watchMatch(String matchInfo);

    /** Applauds the players. */
    void applaud();

    /**
     * Reacts to a specific player's action.
     * @param playerName The name of the player being cheered for
     */
    void reactToPoint(String playerName);
}
