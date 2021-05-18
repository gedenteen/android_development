package com.example.calculator;

public class logic {
    int firstArg, secondArg;
    StringBuilder inputStr = new StringBuilder();
    private int actionSelected; //+, -, *, /

    private State state;
    private enum State {
        firstArgInput,
        secondArgInput,
        resultShow
    }

    public logic() {
        state = State.firstArgInput;
    }

    public void onNumPressed(int buttonId) {
        if (state == State.resultShow) {
            state = State.firstArgInput;
            inputStr.setLength(0);
        }
        if (inputStr.length() < 9) {
            switch (buttonId) {
                case R.id.zero:
                    if (inputStr.length() != 0)
                        inputStr.append("0");
                    break;
                case R.id.one:
                    inputStr.append("1");
                    break;
                case R.id.two:
                    inputStr.append("2");
                    break;
                case R.id.three:
                    inputStr.append("3");
                    break;
                case R.id.four:
                    inputStr.append("4");
                    break;
                case R.id.five:
                    inputStr.append("5");
                    break;
                case R.id.six:
                    inputStr.append("6");
                    break;
                case R.id.seven:
                    inputStr.append("7");
                    break;
                case R.id.eight:
                    inputStr.append("8");
                    break;
                case R.id.nine:
                    inputStr.append("9");
                    break;
            }
        }
    }

    public void onActionPressed(int actionId) {
        if (actionId == R.id.equals) {
            state = State.resultShow;
            if (actionSelected == R.id.equals) {
                return;
            }
            secondArg = Integer.parseInt(inputStr.toString());
            inputStr.setLength(0) ;
            switch (actionSelected) {
                case R.id.sum:
                    inputStr.append(firstArg + secondArg);
                    break;
                case R.id.sub:
                    inputStr.append(firstArg - secondArg);
                    break;
                case R.id.mul:
                    inputStr.append(firstArg * secondArg);
                    break;
                case R.id.div:
                    inputStr.append(firstArg / secondArg);
                    break;
            }
            actionSelected = R.id.equals; //нужно для случая, когда сразу равно
        }
        else if (actionId == R.id.clear) {
            state = State.firstArgInput;
            inputStr.setLength(0);
        }
        else if (inputStr.length() > 0 && state == State.firstArgInput) {
            firstArg = Integer.parseInt(inputStr.toString());
            state = State.secondArgInput;
            inputStr.setLength(0);
            switch (actionId) {
                case R.id.sum:
                    actionSelected = R.id.sum;
                    break;
                case R.id.sub:
                    actionSelected = R.id.sub;
                    break;
                case R.id.mul:
                    actionSelected = R.id.mul;
                    break;
                case R.id.div:
                    actionSelected = R.id.div;
                    break;
            }
        }
    }

    public String getText() {
        return inputStr.toString();
    }
}
