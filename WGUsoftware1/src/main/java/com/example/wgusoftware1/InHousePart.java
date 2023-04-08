package com.example.wgusoftware1;
/**

 A subclass of Part representing a part that is made in house.
 */
public class InHousePart extends Part {
    // variables
    private int machineId;

    /**
     * Constructs an InHousePart object with the specified id, name, price, stock, min, max, and machineId.
     * @param id        the id of the part
     * @param name      the name of the part
     * @param price     the price of the part
     * @param stock     the inventory level of the part
     * @param min       the minimum inventory level of the part
     * @param max       the maximum inventory level of the part
     * @param machineId the id of the machine that produces the part
     */
    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Returns the machine ID of the part.
     *
     * @return the machine ID of the part
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets the machine ID of the part.
     *
     * @param machineId the machine ID to set for the part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
