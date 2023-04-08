package com.example.wgusoftware1;

/**

 A subclass of Part representing a part that is outsourced.
 */
public class OutsourcedPart extends Part {

    /**
     The name of the company that the outsourced part is from.
     */
    private String companyName;

    /**
     Constructs an OutsourcedPart object with the given id, name, price, stock, min, max, and companyName.
     @param id The id of the part.
     @param name The name of the part.
     @param price The price of the part.
     @param stock The current stock level of the part.
     @param min The minimum stock level of the part.
     @param max The maximum stock level of the part.
     @param companyName The name of the company that the outsourced part is from.
     */
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     Returns the name of the company that the outsourced part is from.
     @return The name of the company.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     Sets the name of the company that the outsourced part is from.
     @param companyName The name of the company.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}