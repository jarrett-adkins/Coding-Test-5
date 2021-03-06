
package com.example.admin.codingtest5.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VITC_ implements Serializable
{

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("quantity")
    @Expose
    private double quantity;
    @SerializedName("unit")
    @Expose
    private String unit;
    private final static long serialVersionUID = 3149434053885386209L;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
