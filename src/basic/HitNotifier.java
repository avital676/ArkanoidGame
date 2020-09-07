package basic;

import listeners.HitListener;

/**
 * The HitNotifier interface is implemented in classes that should notify whenever a they're being hit.
 * It has a methods to add and remove listeners.
 */
public interface HitNotifier {

    /**
     * Adds a given hit listener as a listener to hit events of the HitNotifier.
     *
     * @param hl the hit listener to be added.
     */
    void addHitListener(HitListener hl);

    /**
     * Removes a given hit listener from the list of listeners to hit events.
     *
     * @param hl the hit listener to be removed.
     */
    void removeHitListener(HitListener hl);
}