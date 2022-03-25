package com.albertoornelas.ventasapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    var subTotal = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get references
        val customersTypes = resources.getStringArray(R.array.customersTypes)
        val txtCustomer = findViewById<EditText>(R.id.txtCustomer)
        val txtNumberOfLeaves = findViewById<EditText>(R.id.txtNumberOfLeaves)
        val txtPricePerLeave = findViewById<EditText>(R.id.txtPricePerLeave)
        val btnBuy = findViewById<Button>(R.id.btnBuy)
        val txtResult = findViewById<TextView>(R.id.txtResult)

        // Fill spinner with data
        val spinner = findViewById<Spinner>(R.id.spinnerTypeCustomer)
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, customersTypes)
            spinner.adapter = adapter
        }

        // btn listener pay
        btnBuy.setOnClickListener() {
            val newPrice = getNewPriceDiscount(txtPricePerLeave.text.toString().toDouble(), spinner.selectedItem.toString())
            val subtotal = calculateSubTotal(newPrice, txtNumberOfLeaves.text.toString().toInt())
            val subTotalWithoutDiscount = calculateSubTotalWithoutDisc(txtPricePerLeave.text.toString().toDouble(), txtNumberOfLeaves.text.toString().toInt())
            val customer = Customer(txtCustomer.text.toString(), spinner.selectedItem.toString())
            val sale = Sale(subTotalWithoutDiscount, txtPricePerLeave.text.toString().toDouble(), txtNumberOfLeaves.text.toString().toInt(), subtotal, newPrice)
            val textResult = getResult(customer, sale)
            txtResult.text = textResult
            txtCustomer.setText("")
            txtNumberOfLeaves.setText("")
            txtPricePerLeave.setText("")
        }

    }

    private fun getResult(customer: Customer, sale: Sale) : String {
        return "Cliente: ${customer.customerName} \n " +
                "Tipo de usuario: ${customer.type} \n" +
                "Precio Por Hoja: ${sale.pricePeerLeave} \n " +
                "Precio con descuento: ${sale.pricePeerLeaveWithDisc} \n " +
                "Total de hojas: ${sale.numberOfLeaves} \n " +
                "Subtotal Sin descuento: ${sale.subTotalWithoutDiscount} \n " +
                "Subtotal con descuento: ${sale.subTotalWithDiscount}"
    }

    private fun getNewPriceDiscount(pricePeerLeave: Double, type: String) : Double{
        var newPrice = 0.0
        when (type) {
            "Tipo 1" -> newPrice = pricePeerLeave - (pricePeerLeave * 0.05)
            "Tipo 2" -> newPrice = pricePeerLeave - (pricePeerLeave * 0.08)
            "Tipo 3" -> newPrice = pricePeerLeave - (pricePeerLeave * 0.12)
            "Tipo 4" -> newPrice =  pricePeerLeave - (pricePeerLeave * 0.15)
        }
        return newPrice
    }

    private fun calculateSubTotal(newPrice: Double, numberOfLeaves: Int) : Double {
        return newPrice * numberOfLeaves;
    }

    private fun calculateSubTotalWithoutDisc(originalPrice: Double, numberOfLeaves: Int):Double {
        return originalPrice * numberOfLeaves
    }

    // data class Customer
    data class Customer(
        val customerName: String,
        val type: String,
    )

    // data class Sell
    data class Sale(
        val subTotalWithoutDiscount: Double,
        val pricePeerLeave: Double,
        val numberOfLeaves: Int,
        val subTotalWithDiscount: Double,
        val pricePeerLeaveWithDisc: Double
    )
}