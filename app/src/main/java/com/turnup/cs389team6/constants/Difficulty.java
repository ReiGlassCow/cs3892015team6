package com.turnup.cs389team6.constants;

/**
 * Created by Dheer on 4/12/2015.
 */
public enum Difficulty {
    LEVEL_ONE(0, 11, Operation.ADD,Operation.SUBTRACT),
    LEVEL_TWO(0, 16, Operation.ADD, Operation.SUBTRACT, Operation.MULTIPLICATION),
    LEVEL_THREE(0, 21, Operation.ADD, Operation.SUBTRACT, Operation.MULTIPLICATION, Operation.DIVISION);

    private final int minValue;
    private final int maxValue;
    private final Operation[] supportedOperations;

    Difficulty(int minValue, int maxValue, Operation... supportedOperations){
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.supportedOperations = supportedOperations;
    }

    public int getMinValue(){
        return minValue;
    }

    public int getMaxValue(){
        return maxValue;
    }

    public Operation[] getSupportedOperations(){
        return supportedOperations;
    }

}
