package com.example.calculatoriscalculating

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var result: TextView
    private var currentInput = ""
    private var operator: Char? = null
    private var firstNumber: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        result = findViewById(R.id.display)

        // Numbers
        val buttonIds = listOf(
            R.id.button5_2,
            R.id.button4_1, R.id.button4_2, R.id.button4_3,
            R.id.button3_1, R.id.button3_2, R.id.button3_3,
            R.id.button2_1, R.id.button2_2, R.id.button2_3
        )

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener { onNumberClick(it) }
        }

        // operator
        findViewById<Button>(R.id.button1_4).setOnClickListener { onOperatorClick('/') }
        findViewById<Button>(R.id.button2_4).setOnClickListener { onOperatorClick('x') }
        findViewById<Button>(R.id.button3_4).setOnClickListener { onOperatorClick('-') }
        findViewById<Button>(R.id.button4_4).setOnClickListener { onOperatorClick('+') }
        findViewById<Button>(R.id.button5_4).setOnClickListener {
            result.text = calculate()
            firstNumber = calculate().toIntOrNull()
            currentInput = result.text as String
        }

        // reset
        findViewById<Button>(R.id.button1_1).setOnClickListener { reset() }
        findViewById<Button>(R.id.button1_2).setOnClickListener { reset() }

        findViewById<Button>(R.id.button1_3).setOnClickListener { backspace() }
    }

    private fun onNumberClick(view: View) {
        val button = view as Button
        currentInput += button.text
        result.text = currentInput
    }

    private fun onOperatorClick(op: Char) {
        if (currentInput.isNotEmpty()) {
            firstNumber = currentInput.toInt()
            operator = op
            currentInput = ""
        }
    }

    private fun calculate(): String {
        val secondNumber = currentInput.toIntOrNull()
        return when {
            firstNumber == null || secondNumber == null || operator == null -> "error"
            operator == '+' -> (firstNumber!! + secondNumber).toString()
            operator == '-' -> (firstNumber!! - secondNumber).toString()
            operator == 'x' -> (firstNumber!! * secondNumber).toString()
            operator == '/' -> if (secondNumber != 0) (firstNumber!! / secondNumber).toString() else "error"
            else -> "error"
        }
    }

    private fun reset() {
        currentInput = ""
        result.text = "0"
        firstNumber = null
        operator = null
    }

    private fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            result.text = currentInput.ifEmpty { "0" }
        }
    }
}