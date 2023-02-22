package edu.monmouth.cs250.student.metriccalcdemo

class Calculator {

    // instance variables
    private var mode:Units = Units.Temperature
    private val temperatureOffset = 32.0
    private val temperatureConversionFactor = 5.0/9.0
    private val lengthConversionFactor = 1.60934
    private val massConversionFactor = 0.453592
    private val volumeConversionFactor = 3.78541

    // methods

    public fun setMode (selectedMode: Units) {
        mode = selectedMode
    }

    public fun convert (inputValue: Double): Double {
        var result = 0.00
        when (mode) {
            Units.Temperature ->
                result = (inputValue - temperatureOffset) * temperatureConversionFactor
            Units.Length ->
                result = inputValue * lengthConversionFactor
            Units.Mass ->
                result = inputValue * massConversionFactor
            Units.Volume ->
                result = inputValue * volumeConversionFactor
        }
        return result
    }
}