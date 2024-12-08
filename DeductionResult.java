package com.example.idealunitpricecalculator;

public class DeductionResult<U, T> {
    U unitPrice;
    T totalPrice;
    T vatAmount;
    T totalWithVAT;
    T differenceFromOriginal;

    public DeductionResult(U unitPrice, T totalPrice, T vatAmount, T totalWithVAT, T differenceFromOriginal) {
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.vatAmount = vatAmount;
        this.totalWithVAT = totalWithVAT;
        this.differenceFromOriginal = differenceFromOriginal;
    }

    @Override
    public String toString() {
        return String.format("Ideal Unit Price: %s, Deducted Total: %s, VAT: %s, Total with VAT: %s, Difference: %s",
                unitPrice, totalPrice, vatAmount, totalWithVAT, differenceFromOriginal);
    }
}
