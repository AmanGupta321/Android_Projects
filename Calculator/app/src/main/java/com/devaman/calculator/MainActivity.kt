/*package com.devaman.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
*/
package com.devaman.calculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
//import org.mariuszgromada.math.mxparser.Expression
//import org.w3c.dom.Text
//import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        findViewById<Button>(R.id.button_clear).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = ""
            findViewById<TextView>(R.id.output).text = ""
        }

        findViewById<Button>(R.id.btn_backspace).setOnClickListener {
            vibrator.vibrate(45)
            val textoutput = findViewById<TextView>(R.id.output).text.toString()
           // if(textoutput.length != 0){
                findViewById<TextView>(R.id.output).text = textoutput.dropLast(1)
          //  }
            val textinput = findViewById<TextView>(R.id.input).text.toString()
            //if(textoutput.length == 0 && textinput.length != 0){
                   findViewById<TextView>(R.id.input).text = textinput.dropLast(1)
            //}
        }
        findViewById<Button>(R.id.button_0).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("0")
        }
        findViewById<Button>(R.id.button_1).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("1")
        }
        findViewById<Button>(R.id.button_2).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("2")
        }
        findViewById<Button>(R.id.button_3).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("3")
        }
        findViewById<Button>(R.id.button_4).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("4")
        }
        findViewById<Button>(R.id.button_5).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("5")
        }
        findViewById<Button>(R.id.button_6).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("6")
        }
        findViewById<Button>(R.id.button_7).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("7")
        }
        findViewById<Button>(R.id.button_8).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("8")
        }
        findViewById<Button>(R.id.button_9).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("9")
        }
        findViewById<Button>(R.id.button_dot).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText(".")
        }
        findViewById<Button>(R.id.button_division).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("÷") // ALT + 0247
        }
        findViewById<Button>(R.id.button_multiply).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("×") // ALT + 0215
        }
        findViewById<Button>(R.id.button_subtraction).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("-")
        }
        findViewById<Button>(R.id.button_addition).setOnClickListener {
            vibrator.vibrate(45)
            findViewById<TextView>(R.id.input).text = addToInputText("+")
        }

        findViewById<Button>(R.id.button_equals).setOnClickListener {
            vibrator.vibrate(45)
            Exception_UserInput()
        }
    }

    private fun addToInputText(buttonValue: String):String {
        return findViewById<TextView>(R.id.input).text.toString()+buttonValue
    }

    //External libs Code
    /*
    private fun getInputExpression(): String {
        var expression = findViewById<TextView>(R.id.input).text.replace(Regex("÷"), "/")
        expression = expression.replace(Regex("×"), "*")
        return expression
    }


    private fun showResult() {
        try {
            val expression = getInputExpression()
            val result = Expression(expression).calculate()
            if (result.isNaN()) {
                // Show Error Message
                findViewById<TextView>(R.id.output).text = "Error"
                findViewById<TextView>(R.id.output).setTextColor(ContextCompat.getColor(this, R.color.red))
            } else {
                // Show Result
                findViewById<TextView>(R.id.output).text = DecimalFormat("0.######").format(result).toString()
                //findViewById<TextView>(R.id.output).setTextColor(ContextCompat.getColor(this, R.color.green))
            }
        } catch (e: Exception) {
            // Show Error Message
            findViewById<TextView>(R.id.output).text = "Error"
            findViewById<TextView>(R.id.output).setTextColor(ContextCompat.getColor(this, R.color.red))
        }
    }
     */


    private fun getInputExpression(): String {
        var expression = findViewById<TextView>(R.id.input).text.replace(Regex("÷"), "/")
        expression = expression.replace(Regex("×"), "*")
        return expression
    }

    // Exception function to check whether the UserInput is Valid or not?
    // Eg - 25/0 : Returns Error as number can't be divide by zero
    fun Exception_UserInput():Unit{
        try {
            showResult(getInputExpression())
        } catch (e : ArithmeticException) {
            //print("error")
            findViewById<TextView>(R.id.output).text = "Error"
            findViewById<TextView>(R.id.output).setTextColor(ContextCompat.getColor(this, R.color.red))
        }
        catch(e : NumberFormatException){
            // print("error")
            findViewById<TextView>(R.id.output).text = "Error"
            findViewById<TextView>(R.id.output).setTextColor(ContextCompat.getColor(this, R.color.red))
        }
        catch (e : Exception){
            //print("Error")
            findViewById<TextView>(R.id.output).text = "Error"
            findViewById<TextView>(R.id.output).setTextColor(ContextCompat.getColor(this, R.color.red))
        }
    }

    // Function to Calculate : UserInput
    /* 1. Breaks the input in 2 array of type ArrayList
        i) Numbers in number_array
        ii) Operators in operator_array

       2. Creating 2 more array of operators type ArrayList :
          Here, it calculates the input with DMAS rule where,
          it remove the number from number_array plus, add result in number_array and creates a new updated operator array
                i) operator_array_removed_divide : to update array with left operators (*,-,+)
                ii) operator_array_removed_multiply : to update array with left operators (-,+)

       3. Concatenating the left over number_array with operator_array_removed_multiply as per the userinput
       4. Replacing the - with +- then splitting it with + and at last performing the addition
       5. Now output could be '25.0' so to make valid output '25'
           i) Converting it into string
           ii) Matching with Regex (if true) converting it into int
           iii) At last, resultotp variable has the result
    */
    fun showResult(userinput : String){
        var str = userinput

        if(str[0] == '-'){
            str = "0"+str
        }

        var numbers_array = ArrayList<String>(str.split("+", "-", "*", "/"))
        var operator_array = ArrayList<String>()

        // Divison
        // Multiply
        // Addition
        // Subtraction

        for(i in str){
            if(i == '+' || i=='-'||i=='*'||i=='/'){
                operator_array.add(i.toString())
            }
        }
        // For Division
        var result = 0f
        //print(operator_array)
        var operator_array_removed_divide = ArrayList<String>(operator_array)


        var index = 0

        for(op in operator_array){
            if(op == "/"){
                result = numbers_array[index].plus("f").toFloat() / numbers_array[index+1].plus("f").toFloat()
                numbers_array[index] = result.toString()
                numbers_array.removeAt(index+1)
                operator_array_removed_divide.removeAt(index)
                index = index-1
            }
            index++
        }
        //print(result)

        var operator_array_removed_multiply = ArrayList<String>(operator_array_removed_divide)

        // For Multiplication
        index = 0
        for(op in operator_array_removed_divide){
            if(op == "*"){
                result = numbers_array[index].plus("f").toFloat() * numbers_array[index+1].plus("f").toFloat()
                numbers_array[index] = result.toString()
                numbers_array.removeAt(index+1)
                operator_array_removed_multiply.removeAt(index)
                index = index-1
            }
            index++
        }

        var text : String = numbers_array[0].plus("f")
        for(z in 0..operator_array_removed_multiply.size-1){
            text += operator_array_removed_multiply[z] + numbers_array[z+1].plus("f")
        }

        /*
        print(result)
        println("")
        println("Index : ${index}")
        for(ne in numbers_array){
            print("t : ${ne}")
        }
        println(numbers_array.size)
        println("")
        for(nq in operator_array_removed_multiply){
            print(nq)
        }
        println("")
        println(text)
        */

        //eg : text = 54+400-8+10
        val exp = text
        //eg : exp = 54+400-8+10

        var resultotp = exp.replace("-", "+-").split("+").sumByDouble { it.toDouble() }

        // eg : resultotp = 54+400+-8+10     ->     "54+400"  "-8+10"      ->      sumBy(454+2).toInt
        // How resultotp works after split of + ?
        //val checkcode = listOf(text)//.sumOf { it.toInt() }
        //var sum = checkcode.sumByDouble { it.toDouble() }
        //println(sum)

        var resultotp_str = resultotp.toString()
        var condition_regex_to_output = false

        /*
        Regular Expression used below the code meaning :
        +	This matches the occurrence of the previous character at least one time.	abc+
        [pqr]	This matches any single character present in the set.	[pqr]
        [i-p]	This matches any single character within the range.     [i-p]
        */

        /*
        val pattern = Regex("[1-9]+[.]+[0]+")
        val pattern1 = Regex("[0]+[.]+[0]+")
        if(pattern.containsMatchIn(resultotp_str) || pattern1.containsMatchIn(resultotp_str)){
            condition_regex_to_output = true
        }
        */

        if(resultotp % 1 == 0.0) {
            //println("result : ${resultotp.toInt()}")
            findViewById<TextView>(R.id.output).text = resultotp.toInt().toString()
            findViewById<TextView>(R.id.output).setTextColor(ContextCompat.getColor(this, R.color.white))
        }
        else{
            //println("result : ${resultotp}")
            findViewById<TextView>(R.id.output).text = resultotp.toString()
            findViewById<TextView>(R.id.output).setTextColor(ContextCompat.getColor(this, R.color.white))
        }

    }

}