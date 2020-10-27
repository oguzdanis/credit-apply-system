package com.oguzdanis.creditapplysystem.enumeration;

public class GeneralEnumeration {

    public enum CreditResult{
        TCKNISREQUIRED("Tckn is required.");

        private String message;

        CreditResult(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
