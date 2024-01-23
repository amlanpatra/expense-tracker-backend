package org.amlan.expensetracker.utilities;

public class CONSTANT {
    public enum PAYMENT_TYPE
    {
        PAYING("P"),    // PAYING
        BALANCING("B");    // BALANCING
        private final String value;
        private PAYMENT_TYPE(String value) {this.value = value;}
        public String getValue() {return value;}
    };
}
